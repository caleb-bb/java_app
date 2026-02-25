package com.caleb.chase.customerapi;

// get requests return an empty LIST of Customers
// so we need lists as a data structure
import java.util.List;

// Need GetMapping to tell the endpoint how to respond to GETs
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    // this field holds the dependency
    // once constructed, it can't be changed (final)
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> findAll() {
        return customerService.findAll();
        }

}
