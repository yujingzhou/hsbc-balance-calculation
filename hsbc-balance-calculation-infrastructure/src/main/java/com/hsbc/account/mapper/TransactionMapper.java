package com.hsbc.account.mapper;

import com.hsbc.account.models.TransactionDO;
import com.hsbc.account.models.TransactionDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionMapper {
    long countByExample(TransactionDOExample example);

    int deleteByExample(TransactionDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TransactionDO record);

    int insertSelective(TransactionDO record);

    List<TransactionDO> selectByExample(TransactionDOExample example);

    TransactionDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TransactionDO record, @Param("example") TransactionDOExample example);

    int updateByExample(@Param("record") TransactionDO record, @Param("example") TransactionDOExample example);

    int updateByPrimaryKeySelective(TransactionDO record);

    int updateByPrimaryKey(TransactionDO record);
}