package com.hsbc.account.domain.account;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yubo
 * @Title: Account
 * @Package com.hsbc.account.domain.account
 * @Description: 账户实体
 */
@Data
public class Account {
    private Long id;

    private String cardNo;

    private BigDecimal balance;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
