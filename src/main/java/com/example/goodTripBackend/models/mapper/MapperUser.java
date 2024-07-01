package com.example.goodTripBackend.models.mapper;

import com.example.goodTripBackend.models.dto.AccountDto;
import com.example.goodTripBackend.models.entities.User;
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
