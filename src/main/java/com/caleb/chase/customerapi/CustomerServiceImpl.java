// This module does all the work when the Customer controller calls functions
// defined in the customer service interface.

package com.caleb.chase.customerapi;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.caleb.chase.customerapi.dto.CustomerDTO;

// The @Service annotation tells Spring "this is a bean, so register it in the context"
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerEventPublisher eventPublisher;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerEventPublisher eventPublisher) {
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public Customer create(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        Customer saved = customerRepository.save(customer);
        eventPublisher.publish(saved);
        return saved;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> update(Long id, Customer customer){
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {}
}
