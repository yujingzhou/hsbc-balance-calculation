package com.hsbc.account.api;

import com.hsbc.account.dto.TransactionCmd;

/**
 *
 * @author yubo
 */
public interface TransactionServiceI {
    void transfer(TransactionCmd command);
}
