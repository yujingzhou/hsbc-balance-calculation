package com.hsbc.account.domain.customer.gateway;

import com.hsbc.account.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);
}
