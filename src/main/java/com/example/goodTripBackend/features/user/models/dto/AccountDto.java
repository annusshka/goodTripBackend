package com.example.goodTripBackend.features.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phone;
}
