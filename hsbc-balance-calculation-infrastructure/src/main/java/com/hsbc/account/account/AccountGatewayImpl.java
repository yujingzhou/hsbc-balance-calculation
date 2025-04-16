package com.hsbc.account.account;

import com.hsbc.account.domain.account.Account;
import com.hsbc.account.domain.account.gateway.AccountGateway;
import com.hsbc.account.mapper.AccountMapper;
import com.hsbc.account.models.AccountDO;
import com.hsbc.account.models.AccountDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yubo
 * @Title: AccountGatewayImpl
 * @Package com.hsbc.account.account
 * @Description: 账户网关实现
 */
@Service
public class AccountGatewayImpl implements AccountGateway {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account getAccountByCardNo(String cardNo) {
        AccountDOExample example = new AccountDOExample();
        example.createCriteria().andCardNoEqualTo(cardNo);
        List<AccountDO> accountDOS = accountMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(accountDOS)) {
            return null;
        } else {
            AccountDO accountDO = accountDOS.get(0);
            Account account = new Account();
            account.setId(accountDO.getId());
            account.setCardNo(accountDO.getCardNo());
            account.setBalance(accountDO.getBalance());
            account.setIsDeleted(accountDO.getIsDeleted());
            account.setCreateTime(accountDO.getCreateTime());
            account.setUpdateTime(accountDO.getUpdateTime());
            return account;
        }
    }

    @Override
    public Account getAccountById(Long id) {
        AccountDO accountDO = accountMapper.selectByPrimaryKey(id);
        if (accountDO == null) {
            return null;
        }
        Account account = new Account();
        account.setId(accountDO.getId());
        account.setCardNo(accountDO.getCardNo());
        account.setBalance(accountDO.getBalance());
        account.setIsDeleted(accountDO.getIsDeleted());
        account.setCreateTime(accountDO.getCreateTime());
        account.setUpdateTime(accountDO.getUpdateTime());
        return account;
    }
}
