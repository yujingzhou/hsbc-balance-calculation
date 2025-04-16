package com.hsbc.account.domain.account.gateway;

import com.hsbc.account.domain.account.Account;

/**
 * @Title: AccountGateway
 * @Package com.hsbc.account.domain.account.gateway
 * @Description: 账户网关
 * @author yubo
 */
public interface AccountGateway {

    /**
     * @Title: getAccount
     * @Description: 获取账户
     * @param cardNo 账户卡号
     * @return
     */
    Account getAccountByCardNo(String cardNo);

    /**
     * @Title: getAccountById
     * @Description: 获取账户
     * @param id 账户ID
     * @return
     */
    Account getAccountById(Long id);
}
