package com.hsbc.account.transaction;

import com.hsbc.account.api.TransactionServiceI;
import com.hsbc.account.domain.account.Account;
import com.hsbc.account.domain.account.gateway.AccountGateway;
import com.hsbc.account.domain.transaction.TransactionEvent;
import com.hsbc.account.domain.transaction.gateway.TransactionEventGateway;
import com.hsbc.account.domain.transaction.gateway.TransactionGateway;
import com.hsbc.account.dto.TransactionCmd;
import com.hsbc.account.mns.MnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yubo
 * @Title: TransactionServiceImpl
 * @Package com.hsbc.account.transaction
 * @Description: 转账实现类
 */
@Service
public class TransactionServiceImpl implements TransactionServiceI {

    @Autowired
    private TransactionGateway transactionGateway;

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private TransactionEventGateway transactionEventGateway;

    @Autowired
    private MnsService mnsService;

    @Autowired
    private RedisTemplate redisTemplate;

    // 定义格式（无分隔符，连续字符）
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    Long transactionIdPerSecond = 1000000L;

    @Override
    public void transfer(TransactionCmd command) {
        Account fromAccount = accountGateway.getAccountByCardNo(command.getFromCardNo());
        if (fromAccount == null) {
            throw new RuntimeException("转出账户不存在");
        }
        if (command.getAmount().compareTo(fromAccount.getBalance()) > 0) {
            throw new RuntimeException("余额不足");
        }
        Account toAccount = accountGateway.getAccountByCardNo(command.getToCardNo());
        if (toAccount == null) {
            throw new RuntimeException("转入账户不存在");
        }
        // 生成支持每秒 1000000 个请求的流水号
        Long incrementNumber = redisTemplate.opsForValue().increment("incrementNumber", 1);
        Long transactionId =  Long.parseLong(LocalDateTime.now().format(formatter)) * transactionIdPerSecond + incrementNumber;
        // 保存交易流水
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setFromId(fromAccount.getId());
        transactionEvent.setToId(toAccount.getId());
        transactionEvent.setSerialNo(transactionId);
        transactionEvent.setAmount(command.getAmount());
        boolean saveTransactionEvent = transactionEventGateway.saveTransactionEvent(transactionEvent);
        if (!saveTransactionEvent) {
            throw new RuntimeException("保存交易流水失败");
        }
        mnsService.sendMsg(transactionId.toString());
    }
}
