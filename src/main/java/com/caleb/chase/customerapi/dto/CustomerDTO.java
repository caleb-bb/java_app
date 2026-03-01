package com.caleb.chase.customerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record CustomerDTO(
                          @NotBlank String name,
                          @NotBlank String email) {}
