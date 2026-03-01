package com.caleb.chase.customerapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.caleb.chase.customerapi.CustomerService;
import com.caleb.chase.customerapi.dto.CustomerDTO;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"customer-events"})

class CustomerEventTest {
    @Autowired CustomerService customerService;
    // @Autowired KafkaConsumer<String, String> testConsumer;

    @Test
    void creatingCustomer_publishesEvent() {
        customerService.create(new CustomerDTO("Alice", "alice@test.com"));
    }
}
