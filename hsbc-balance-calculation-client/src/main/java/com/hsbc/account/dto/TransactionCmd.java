package com.hsbc.account.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 转账参数
 *
 * TransactionCmd
 * @author yubo
 */
@Data
public class TransactionCmd {

    /**
     * 转出账户卡号
     */
    private String fromCardNo;

    /**
     * 转入账户卡号
     */
    private String toCardNo;

    /**
     * 转账金额
     */
    private BigDecimal amount;
}

