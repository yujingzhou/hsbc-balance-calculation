package com.hsbc.account.mns;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.hsbc.account.domain.account.Account;
import com.hsbc.account.domain.account.gateway.AccountGateway;
import com.hsbc.account.domain.transaction.TransactionEvent;
import com.hsbc.account.domain.transaction.gateway.TransactionEventGateway;
import com.hsbc.account.domain.transaction.gateway.TransactionGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

/**
 * @author yubo
 * @Title: MnsService
 * @Package com.hsbc.account.mns
 * @Description: 消息服务
 */
@Slf4j
@Service
public class MnsService {

    @Value("${mns.queueName}")
    private String QUEUE_NAME;

    @Autowired
    private MNSClient client;

    @Autowired
    private TransactionEventGateway transactionEventGateway;

    @Autowired
    private TransactionGateway transactionGateway;

    @Autowired
    private AccountGateway accountGateway;

    /**
     * 发送消息
     * @param msg 消息
     */
    public void sendMsg(String msg) {
        try {
            CloudQueue queue = client.getQueueRef(QUEUE_NAME);
            for (int i = 0; i < 10; i++) {
                Message message = new Message();
                message.setMessageBodyAsRawString(msg);

                Message putMsg = queue.putMessage(message);
                System.out.println("Send message id is: " + putMsg.getMessageId());
            }
        } catch (ClientException ce) {
            System.out.println("Something wrong with the network connection between client and MNS service."
                                       + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se) {
            if (se.getErrorCode().equals("QueueNotExist")) {
                System.out.println("Queue is not exist.Please create before use");
            } else if (se.getErrorCode().equals("TimeExpired")) {
                System.out.println("The request is time expired. Please check your local machine timeclock");
            }
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknown exception happened!");
            e.printStackTrace();
        }

        client.close();
    }


    /**
     * 循环接收消息
     */
    @PostConstruct
    public void loopReceive() {
        CloudQueue queue = client.getQueueRef(QUEUE_NAME);
        while (true) {
            // 循环执行
            try {
                // 推荐: 使用的 长轮询批量拉取模型
                longPollingBatchReceive(queue);
            } catch (ClientException ce) {
                System.out.println("Something wrong with the network connection between client and MNS service."
                                           + "Please check your network and DNS availablity.");
                // 客户端异常，默认为抖动，触发下次重试
            } catch (ServiceException se) {
                if (se.getErrorCode().equals("QueueNotExist")) {
                    System.out.println("Queue is not exist.Please create queue before use");
                    client.close();
                    return;
                } else if (se.getErrorCode().equals("TimeExpired")) {
                    System.out.println("The request is time expired. Please check your local machine timeclock");
                    return;
                }
                // 其他的服务端异常，默认为抖动，触发下次重试
            } catch (Exception e) {
                System.out.println("Unknown exception happened!e:"+e.getMessage());
                // 其他异常，默认为抖动，触发下次重试
            }

        }
    }

    private void longPollingBatchReceive(CloudQueue queue) {

        System.out.println("=============start longPollingBatchReceive=============");

        // 一次性拉取 最多 xx 条消息
        int batchSize = 15;
        // 长轮询时间为 xx s
        int waitSeconds = 15;

        List<Message> messages = queue.batchPopMessage(batchSize, waitSeconds);
        if (messages != null && messages.size() > 0) {

            for (Message message : messages) {
                dealMsgAndDelete(queue,message);
            }
        }

        System.out.println("=============end longPollingBatchReceive=============");

    }

    private void dealMsgAndDelete(CloudQueue queue, Message popMsg) {
        if (popMsg != null) {
            // 查询账户 转账
            //remember to  delete message when consume message successfully.
            TransactionEvent transactionEvent = transactionEventGateway.getTransactionEvent(Long.parseLong(popMsg.getMessageBodyAsRawString()));
            if (Objects.isNull(transactionEvent) ) {
                log.error("消息处理失败，没有找到对应的交易记录");
            } else {
                Account fromAccount = accountGateway.getAccountById(transactionEvent.getFromId());
                Account toAccount = accountGateway.getAccountById(transactionEvent.getToId());
                if (Objects.isNull(fromAccount) || Objects.isNull(toAccount)) {
                    log.error("消息处理失败，没有找到对应的账户");
                }
                boolean transferResult = transactionGateway.saveTransactionForTransfer(fromAccount, toAccount, transactionEvent);
                if (!transferResult) {
                    log.error("消息处理失败，转账失败");
                }
            }

            queue.deleteMessage(popMsg.getReceiptHandle());
        }
    }
}
