package com.katsadourose.primary_service;

import com.katsadourose.primary_service.UserEntity;
import com.katsadourose.primary_service.CreateUserDto;
import com.katsadourose.primary_service.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(CreateUserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        return entity;
    }

    public UserDto toDto(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }
}