package com.caleb.chase.customerapi;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

        @Test
        void getCustomer_returnsEmptyList() throws Exception {
            when(customerService.findAll()).thenReturn(List.of());
            mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getCustomer_notFound_returns404() throws Exception {
        when(customerService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerById_returnsCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        customer.setEmail("alice@test.com");

        when(customerService.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Alice"))
            .andExpect(jsonPath("$.email").value("alice@test.com"));
    }

    @Test
    void createCustomer_returns202WithIdAndCreatedAt() throws Exception {
        Customer saved = new Customer();
        saved.setId(1L);
        saved.setName("Alice");
        saved.setEmail("alice@test.com");
        saved.setCreatedAt(LocalDateTime.now());

        when(customerService.create(any())).thenReturn(saved);

        mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Alice\",\"email\":\"alice@test.com\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.createdAt").exists());
    }
}
