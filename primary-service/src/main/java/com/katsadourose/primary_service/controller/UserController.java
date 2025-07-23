package com.katsadourose.primary_service.controller;

import com.katsadourose.primary_service.dto.UserDto;
import com.katsadourose.primary_service.mapper.UserMapper;
import com.katsadourose.primary_service.service.UserService;
import com.katsadourose.primary_service.entity.UserEntity;
import com.katsadourose.primary_service.dto.CreateUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API endpoints for managing users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<UserDto> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User data to create", 
                    required = true, content = @Content(schema = @Schema(implementation = CreateUserDto.class)))
            @RequestBody CreateUserDto createUserDto) {
        UserEntity userEntity = userMapper.toEntity(createUserDto);
        UserEntity savedUser = userService.saveUser(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toDto(savedUser));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Returns a user based on the provided ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable Integer id) {
        return userService.getUserById(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users sorted by newest first")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                content = @Content(mediaType = "application/json", 
                        schema = @Schema(implementation = UserDto.class, type = "array")))
    })
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsersNewestFirst()
                .stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }
}
