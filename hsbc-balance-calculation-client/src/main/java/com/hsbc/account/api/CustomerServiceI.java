package com.hsbc.account.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.hsbc.account.dto.CustomerAddCmd;
import com.hsbc.account.dto.CustomerListByNameQry;
import com.hsbc.account.dto.data.CustomerDTO;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd customerAddCmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry);
}
