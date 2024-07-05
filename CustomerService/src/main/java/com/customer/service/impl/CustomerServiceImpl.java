package com.customer.service.impl;

import com.customer.repositories.CustomerRepository;
import com.customer.models.Customer;
import com.customer.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository _customerrepo;

    @Override
    public Customer getCustomer(int customer_id) {
        Optional<Customer> customer=_customerrepo.findById(customer_id);
        if(customer.isPresent()) {
            return customer.get();
        }
        throw new EntityNotFoundException("Customer not found with id: " + customer_id);

    }

    @Override
    public List<Customer> getCustomers() {
        return _customerrepo.findAll();
    }

    @Override
    public boolean createCustomer(Customer customer) {
        try {
            _customerrepo.save(customer);
            return true;
        } catch (DataAccessException e) {
            // Handle exceptions related to data access (e.g., database connection issues)
            System.err.println("DataAccessException: Failed to save customer due to database error.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // Handle exceptions related to illegal arguments (e.g., invalid customer data)
            System.err.println("IllegalArgumentException: Failed to save customer due to invalid data.");
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any other exceptions
            System.err.println("Exception: An unexpected error occurred while saving the customer.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Customer updateCustomer( int id, Customer customer) {
        return null;
    }

    @Override
    public boolean deleteCustomer(int id) {

        _customerrepo.deleteById(id);
        return true;
    }
}
