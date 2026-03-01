package com.caleb.chase.customerapi;

public class CustomerNotFoundException extends RuntimeException {
    private final Long id;

    public CustomerNotFoundException(Long id) {
        super("Customer not found");
        this.id = id;
    }

    public Long getId() { return id; }

}
