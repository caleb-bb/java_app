package com.caleb.chase.customerapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomer_assignsId() {
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setEmail("alice@test.com");

        Customer saved = customerRepository.save(customer);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Alice");
    }

    @Test
    void findById_returnsCustomer() {
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setEmail("alice@test.com");
        Customer saved = customerRepository.save(customer);

        var found = customerRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
    }

    @Test
    void findByEmailContaining_returnsMatches() {
        Customer bob = new Customer();
        bob.setName("Bob");
        bob.setEmail("bob@test.com");
        customerRepository.save(bob);
        customerRepository.flush();

        var results = customerRepository.findByEmailContaining("bob");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Bob");
    }

}
