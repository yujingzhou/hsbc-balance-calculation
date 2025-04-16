package com.hsbc.account.domain.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yubo
 * @Title: TransactionEvent
 * @Package com.hsbc.account.domain.transaction
 * @Description: 交易事件
 */
@Data
public class TransactionEvent {
    private Long id;

    private Long fromId;

    private Long toId;

    private Long serialNo;

    private BigDecimal amount;

    private Integer state;

    private String reason;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
