package com.hsbc.account.test;

import com.hsbc.account.api.TransactionServiceI;
import com.hsbc.account.dto.TransactionCmd;
import com.hsbc.account.web.TransactionController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({TransactionController.class})
public class TransactionControllerTest {

    @Mock
    private TransactionServiceI transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransfer_Success() {
        // 准备测试数据
        TransactionCmd command = new TransactionCmd();
        
        // 模拟服务调用
        doNothing().when(transactionService).transfer(any(TransactionCmd.class));
        
        // 执行测试
        ResponseEntity<String> response = transactionController.transfer(command);
        
        // 验证结果
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("交易已提交", response.getBody());
        verify(transactionService, times(1)).transfer(command);
    }

    @Test
    public void testTransfer_Failure() {
        // 准备测试数据
        TransactionCmd command = new TransactionCmd();
        String errorMessage = "余额不足";
        
        // 模拟服务调用异常
        doThrow(new RuntimeException(errorMessage))
            .when(transactionService).transfer(any(TransactionCmd.class));
        
        // 执行测试
        ResponseEntity<String> response = transactionController.transfer(command);
        
        // 验证结果
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("交易失败：" + errorMessage, response.getBody());
        verify(transactionService, times(1)).transfer(command);
    }
}