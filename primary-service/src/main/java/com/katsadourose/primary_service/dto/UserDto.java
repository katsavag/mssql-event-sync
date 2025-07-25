package com.katsadourose.primary_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(
        Integer Id,
        String Name,
        String Email
) {}

