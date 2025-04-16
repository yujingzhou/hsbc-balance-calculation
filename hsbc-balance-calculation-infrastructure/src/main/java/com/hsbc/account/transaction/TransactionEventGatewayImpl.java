package com.hsbc.account.transaction;

import com.hsbc.account.domain.transaction.TransactionEvent;
import com.hsbc.account.domain.transaction.gateway.TransactionEventGateway;
import com.hsbc.account.mapper.TransactionEventMapper;
import com.hsbc.account.models.TransactionEventDO;
import com.hsbc.account.models.TransactionEventDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yubo
 * @Title: TransactionEventGatewayImpl
 * @Package com.hsbc.account.transaction
 * @Description: 交易事件网关实现
 */
@Service
public class TransactionEventGatewayImpl implements TransactionEventGateway {

    @Autowired
    private TransactionEventMapper transactionEventMapper;

    @Override
    @Transactional
    public boolean saveTransactionEvent(TransactionEvent transactionEvent) {
        TransactionEventDO transactionEventDO = new TransactionEventDO();
        transactionEventDO.setFromId(transactionEvent.getFromId());
        transactionEventDO.setToId(transactionEvent.getToId());
        transactionEventDO.setSerialNo(transactionEvent.getSerialNo());
        transactionEventDO.setAmount(transactionEvent.getAmount());
        int insertCount = transactionEventMapper.insertSelective(transactionEventDO);
        return insertCount > 0;
    }

    @Override
    public TransactionEvent getTransactionEvent(Long transactionId) {
        TransactionEventDOExample example = new TransactionEventDOExample();
        example.createCriteria().andSerialNoEqualTo(transactionId);
        List<TransactionEventDO> transactionEventDOS = transactionEventMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(transactionEventDOS)) {
            return null;
        }
        TransactionEventDO transactionEventDO = transactionEventDOS.get(0);
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setFromId(transactionEventDO.getFromId());
        transactionEvent.setToId(transactionEventDO.getToId());
        transactionEvent.setSerialNo(transactionEventDO.getSerialNo());
        transactionEvent.setAmount(transactionEventDO.getAmount());
        return transactionEvent;
    }
}
