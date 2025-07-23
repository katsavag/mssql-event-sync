package com.katsadourose.primary_service.mapper;

import com.katsadourose.primary_service.entity.UserEntity;
import com.katsadourose.primary_service.dto.CreateUserDto;
import com.katsadourose.primary_service.dto.UserDto;
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
