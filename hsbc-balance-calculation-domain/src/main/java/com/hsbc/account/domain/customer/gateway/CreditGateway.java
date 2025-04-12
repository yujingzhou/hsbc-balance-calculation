package com.hsbc.account.domain.customer.gateway;

import com.hsbc.account.domain.customer.Credit;

//Assume that the credit info is in another distributed Service
public interface CreditGateway {
    Credit getCredit(String customerId);
}
