package com.customer.service;

import com.customer.models.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    Customer getCustomer(int customer_id);

    List<Customer> getCustomers();

    boolean createCustomer(Customer customer);

    Customer updateCustomer(int id, Customer customer);

    boolean deleteCustomer(int id);
}
