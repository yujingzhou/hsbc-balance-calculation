package com.hsbc.account.domain.transaction.gateway;

import com.hsbc.account.domain.account.Account;
import com.hsbc.account.domain.transaction.TransactionEvent;

/**
 * 交易网关
 * @author yubo
 */
public interface TransactionGateway {
    /**
     * 保存转账交易
     * @param fromAccount 转出账户
     * @param toAccount 转入账户
     * @param event 交易事件
     * @return
     */
    boolean saveTransactionForTransfer(Account fromAccount, Account toAccount, TransactionEvent event);
}
