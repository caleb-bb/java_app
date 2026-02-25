package com.caleb.chase.customerapi;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    // this field holds the dependency
    // once constructed, it can't be changed (final)
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

}
