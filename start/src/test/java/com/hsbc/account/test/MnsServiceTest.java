package com.hsbc.account.test;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.hsbc.account.mns.MnsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author yubo
 * @Title: MnsServiceTest
 * @Package com.hsbc.account.test
 * @Description:
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({MnsService.class})
public class MnsServiceTest {

    @InjectMocks
    private MnsService mnsService;

    @Mock
    private MNSClient client;

    @Mock
    private CloudQueue queue;

    @Mock
    private Message message;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMsg() throws Exception {
        // 准备测试数据
        String msg = "test message";

        // mock 行为
        when(client.getQueueRef(anyString())).thenReturn(queue);
        when(queue.putMessage(any(Message.class))).thenReturn(message);
        when(message.getMessageId()).thenReturn("123");

        // 执行测试
        mnsService.sendMsg(msg);

        // 验证调用
        verify(client, times(1)).getQueueRef(anyString());
        verify(queue, times(10)).putMessage(any(Message.class));
        verify(client, times(1)).close();
    }

    @Test
    public void testSendMsg_ClientException() throws Exception {
        // 准备测试数据
        String msg = "test message";

        // mock 异常
        when(client.getQueueRef(anyString())).thenThrow(new RuntimeException("测试异常"));

        // 执行测试
        mnsService.sendMsg(msg);

        // 验证调用
        verify(client, times(1)).close();
    }

    @Test
    public void testSendMsg_ServiceException() throws Exception {
        // 准备测试数据
        String msg = "test message";

        // mock 异常
        ServiceException se = new ServiceException("QueueNotExist", "测试异常");
        when(client.getQueueRef(anyString())).thenThrow(se);

        // 执行测试
        mnsService.sendMsg(msg);

        // 验证调用
        verify(client, times(1)).close();
    }
}
