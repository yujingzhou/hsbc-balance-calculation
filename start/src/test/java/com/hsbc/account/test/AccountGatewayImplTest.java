package com.hsbc.account.test;

import com.hsbc.account.account.AccountGatewayImpl;
import com.hsbc.account.domain.account.Account;
import com.hsbc.account.mapper.AccountMapper;
import com.hsbc.account.models.AccountDO;
import com.hsbc.account.models.AccountDOExample;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountGatewayImpl.class})
public class AccountGatewayImplTest {

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountGatewayImpl accountGateway;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAccountByCardNo_Success() {
        // 准备测试数据
        String cardNo = "123456";
        AccountDO accountDO = new AccountDO();
        accountDO.setId(1L);
        accountDO.setCardNo(cardNo);
        accountDO.setBalance(new BigDecimal(100.0));
        accountDO.setIsDeleted(0);

        List<AccountDO> accountDOList = Arrays.asList(accountDO);

        // 设置Mock行为
        when(accountMapper.selectByExample(any(AccountDOExample.class))).thenReturn(accountDOList);

        // 执行测试
        Account result = accountGateway.getAccountByCardNo(cardNo);

        // 验证结果
        assertNotNull(result);
        assertEquals(accountDO.getId(), result.getId());
        assertEquals(accountDO.getCardNo(), result.getCardNo());
        assertEquals(accountDO.getBalance(), result.getBalance());
        assertEquals(accountDO.getIsDeleted(), result.getIsDeleted());
        assertEquals(accountDO.getCreateTime(), result.getCreateTime());
        assertEquals(accountDO.getUpdateTime(), result.getUpdateTime());

        verify(accountMapper).selectByExample(any(AccountDOExample.class));
    }

    @Test
    public void testGetAccountByCardNo_NotFound() {
        // 准备测试数据
        String cardNo = "123456";

        // 设置Mock行为
        when(accountMapper.selectByExample(any(AccountDOExample.class))).thenReturn(Collections.emptyList());

        // 执行测试
        Account result = accountGateway.getAccountByCardNo(cardNo);

        // 验证结果
        assertNull(result);
        verify(accountMapper).selectByExample(any(AccountDOExample.class));
    }
}