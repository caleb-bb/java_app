package com.caleb.chase.customerapi;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CustomerEventPublisher(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(Customer customer) {
        kafkaTemplate.send("customer-events", customer.getId().toString(),
                           customer.getName() + "," + customer.getEmail());
    }
}
