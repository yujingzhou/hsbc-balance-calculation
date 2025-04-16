package com.hsbc.account.domain.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yubo
 * @Title: Transaction
 * @Package com.hsbc.account.domain.transaction
 * @Description: 交易
 */
@Data
public class Transaction {
    private Long id;

    private Long accountId;

    private Long serialNo;

    private BigDecimal amount;

    private Integer category;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
