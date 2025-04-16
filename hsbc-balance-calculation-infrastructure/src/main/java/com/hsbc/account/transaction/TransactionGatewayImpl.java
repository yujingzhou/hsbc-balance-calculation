package com.hsbc.account.transaction;

import com.hsbc.account.domain.account.Account;
import com.hsbc.account.domain.transaction.TransactionCategoryEnum;
import com.hsbc.account.domain.transaction.TransactionEvent;
import com.hsbc.account.domain.transaction.gateway.TransactionGateway;
import com.hsbc.account.mapper.AccountMapper;
import com.hsbc.account.mapper.TransactionEventMapper;
import com.hsbc.account.mapper.TransactionMapper;
import com.hsbc.account.models.TransactionDO;
import com.hsbc.account.models.TransactionEventDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yubo
 * @Title: TransactionGatewayImpl
 * @Package com.hsbc.account.transaction
 * @Description: 交易网关实现
 */
@Service
public class TransactionGatewayImpl implements TransactionGateway {

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionEventMapper transactionEventMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public boolean saveTransactionForTransfer(Account fromAccount, Account toAccount, TransactionEvent event) {
        TransactionDO fromTransaction = new TransactionDO();
        fromTransaction.setAccountId(fromAccount.getId());
        fromTransaction.setSerialNo(event.getSerialNo());
        fromTransaction.setAmount(event.getAmount());
        fromTransaction.setCategory(TransactionCategoryEnum.OUT.getCode());
        fromTransaction.setIsDeleted(0);
        TransactionDO toTransaction = new TransactionDO();
        toTransaction.setAccountId(fromAccount.getId());
        toTransaction.setSerialNo(event.getSerialNo());
        toTransaction.setAmount(event.getAmount());
        toTransaction.setCategory(TransactionCategoryEnum.IN.getCode());
        toTransaction.setIsDeleted(0);
        int fromTransactionCount = transactionMapper.insertSelective(fromTransaction);
        int toTransactionCount = transactionMapper.insertSelective(toTransaction);
        if (fromTransactionCount > 0 && toTransactionCount > 0) {
            TransactionEventDO transactionEventDO = new TransactionEventDO();
            transactionEventDO.setId(event.getId());
            transactionEventDO.setState(3);
            int eventCount = transactionEventMapper.updateByPrimaryKeySelective(transactionEventDO);
            if (eventCount > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
