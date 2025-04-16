package com.hsbc.account.mapper;

import com.hsbc.account.models.TransactionEventDO;
import com.hsbc.account.models.TransactionEventDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionEventMapper {
    long countByExample(TransactionEventDOExample example);

    int deleteByExample(TransactionEventDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TransactionEventDO record);

    int insertSelective(TransactionEventDO record);

    List<TransactionEventDO> selectByExample(TransactionEventDOExample example);

    TransactionEventDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TransactionEventDO record, @Param("example") TransactionEventDOExample example);

    int updateByExample(@Param("record") TransactionEventDO record, @Param("example") TransactionEventDOExample example);

    int updateByPrimaryKeySelective(TransactionEventDO record);

    int updateByPrimaryKey(TransactionEventDO record);
}