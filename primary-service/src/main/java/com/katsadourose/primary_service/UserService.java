package com.katsadourose.primary_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates or updates a user entity
     *
     * @param userEntity the user entity to save
     * @return the saved user entity
     */
    @Transactional
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Retrieves a user by their ID
     *
     * @param id the ID of the user to find
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<UserEntity> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieves all users ordered from newest to oldest
     *
     * @return a list of all users ordered by ID in descending order
     */
    public List<UserEntity> getAllUsersNewestFirst() {
        return userRepository.findAllOrderedByNewestFirst();
    }
}