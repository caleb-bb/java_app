package com.caleb.chase.customerapi;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

// The @Service annotation tells Spring "this is a bean, so register it in the context"
@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public Customer create(Customer customer) {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> update(Long id, Customer customer){
        return Optional.empty();
    }
}
