package com.caleb.chase.customerapi;

import java.util.ArrayList;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// This annotation tells Spring to instantiate this class as a bean at startup
@Component
public class CustomerEventConsumer {
    private ArrayList<String> messages = new ArrayList<>();

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    @KafkaListener(topics = "customer-events")
    private void snarfMessage(String message) {
        this.messages.add(message);
    }
}
