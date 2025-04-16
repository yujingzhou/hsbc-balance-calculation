package com.hsbc.account.test;

import com.github.javafaker.Faker;
import com.hsbc.account.domain.account.Account;

import java.math.BigDecimal;

/**
 * @author yubo
 * @Title: FackerTest
 * @Package com.hsbc.account.test
 * @Description:
 */
public class FakerTest {

    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Account account = new Account();
            account.setCardNo(faker.number().digits(10));
            BigDecimal balance = BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 10000));
            account.setBalance(balance);
            System.out.println(account);
        }
    }
}
