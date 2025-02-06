package com.example.goodTripBackend.features.auth.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "Surname is mandatory")
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Phone is mandatory")
    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @Size(min = 6, message = "Password should be 6 characters long minimum")
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
