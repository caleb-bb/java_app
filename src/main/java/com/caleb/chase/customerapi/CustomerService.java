package com.caleb.chase.customerapi;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Customer create(Customer customer);
    Optional<Customer> findById(Long id);
}
