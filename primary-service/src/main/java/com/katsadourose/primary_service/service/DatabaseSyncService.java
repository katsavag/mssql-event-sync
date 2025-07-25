package com.katsadourose.primary_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katsadourose.primary_service.dto.UserDto;
import com.katsadourose.primary_service.entity.UserEntity;
import com.katsadourose.primary_service.entity.UserMessageOutbox;
import com.katsadourose.primary_service.repository.primary.UserRepository;
import com.katsadourose.primary_service.repository.secondary.UserMessageOutboxRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class DatabaseSyncService {

    private static final Logger logger = Logger.getLogger(DatabaseSyncService.class.getName());

    private final UserRepository userRepository;
    private final UserMessageOutboxRepository userMessageOutboxRepository;
    private final ObjectMapper objectMapper;


    public DatabaseSyncService(UserRepository userRepository, UserMessageOutboxRepository userMessageOutboxRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.userMessageOutboxRepository = userMessageOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void syncDatabases() {
        logger.info("Starting database synchronization...");

        try {
            List<UserMessageOutbox> userMessageOutboxes = userMessageOutboxRepository.findAllUnpublishedOrderedByNewestFirst();
            logger.info("Found " + userMessageOutboxes.size() + " unpublished messages.");

            for (UserMessageOutbox userMessageOutbox : userMessageOutboxes) {
                switch (userMessageOutbox.getEventType()) {
                    case DELETE -> handleDeleteEvent(userMessageOutbox.getPayload());
                    case INSERT -> handleInsertEvent(userMessageOutbox.getPayload());
                    case UPDATE -> handleUpdateEvent(userMessageOutbox.getPayload());
                    default -> logger.warning("Unknown event type: " + userMessageOutbox.getEventType());
                }
            }

            logger.info("Database synchronization completed successfully. Synced " + userMessageOutboxes.size() + " users.");
        } catch (Exception e) {
            logger.severe("Error during database synchronization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleInsertEvent(String userEntityJson) {
        if (StringUtils.isNotBlank(userEntityJson)) {
            UserDto userDto = objectMapper.convertValue(userEntityJson, UserDto.class);
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userDto.Name());
            userEntity.setEmail(userDto.Email());
            userRepository.save(userEntity);
        }
        logger.warning("Received empty user entity JSON.");
    }

    private void handleUpdateEvent(String userEntityJson) {
        if (StringUtils.isNotBlank(userEntityJson)) {
            UserDto userDto = objectMapper.convertValue(userEntityJson, UserDto.class);
            UserEntity userEntity = userRepository.findById(userDto.Id()).orElse(null);
            if (userEntity != null) {
                userEntity.setName(userDto.Name());
                userEntity.setEmail(userDto.Email());
                userRepository.save(userEntity);
            }
            logger.warning("Received update event for non-existent user: " + userDto.Id());
        }
    }

    private void handleDeleteEvent(String userEntityJson) {
        if (StringUtils.isNotBlank(userEntityJson)) {
            UserDto userDto = objectMapper.convertValue(userEntityJson, UserDto.class);
            userRepository.findById(userDto.Id()).ifPresent(userRepository::delete);
            logger.warning("Received delete event for non-existent user: " + userDto.Id());
        }
        logger.warning("Received empty user entity JSON.");
    }
}
