package com.hsbc.account.domain.transaction;

/**
 * 交易类型
 * @author yubo
 */
public enum TransactionCategoryEnum {
    IN(1), OUT(2);

    private Integer code;

    TransactionCategoryEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
