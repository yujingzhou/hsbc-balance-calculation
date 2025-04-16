package com.hsbc.account.domain.transaction.gateway;

import com.hsbc.account.domain.transaction.TransactionEvent;

/**
 * 交易事件网关
 * @author yubo
 */
public interface TransactionEventGateway {
    boolean saveTransactionEvent(TransactionEvent transactionEvent);
    TransactionEvent getTransactionEvent(Long transactionId);
}
