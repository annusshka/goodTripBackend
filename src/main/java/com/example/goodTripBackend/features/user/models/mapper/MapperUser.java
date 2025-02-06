package com.example.goodTripBackend.features.user.models.mapper;

import com.example.goodTripBackend.features.user.models.dto.AccountDto;
import com.example.goodTripBackend.features.user.models.entities.User;
import org.springframework.stereotype.Service;

@Service
public class MapperUser {

    public AccountDto mapToAccountDto(User user) {
        return AccountDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
