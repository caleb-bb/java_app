package com.caleb.chase.customerapi;

// This is the only thing the Customer controller can see. It just sees the
// public interface defined here and the signatures. When one of these functions
// is called by CustomerController, it's CustomerServiceImpl that does the real
// work.

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Customer create(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> update(Long id, Customer customer);
}
