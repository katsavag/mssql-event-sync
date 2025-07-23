package com.katsadourose.primary_service.service;

import com.katsadourose.primary_service.entity.UserEntity;
import com.katsadourose.primary_service.entity.SecondaryUserEntity;
import com.katsadourose.primary_service.entity.UserMessageOutbox;
import com.katsadourose.primary_service.repository.primary.UserRepository;
import com.katsadourose.primary_service.repository.secondary.SecondaryUserRepository;
import com.katsadourose.primary_service.repository.secondary.UserMessageOutboxRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class DatabaseSyncService {

    private static final Logger logger = Logger.getLogger(DatabaseSyncService.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DatabaseSyncService.class);

    private final UserRepository userRepository;
    private final UserMessageOutboxRepository userMessageOutboxRepository;

    @Autowired
    public DatabaseSyncService(UserRepository userRepository, UserMessageOutboxRepository userMessageOutboxRepository) {
        this.userRepository = userRepository;
        this.userMessageOutboxRepository = userMessageOutboxRepository;
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void syncDatabases() {
        logger.info("Starting database synchronization...");

        try {
            List<UserMessageOutbox> userMessageOutboxes = userMessageOutboxRepository.findAllUnpublishedOrderedByNewestFirst();

            logger.info("Found " + userMessageOutboxes.size() + " unpublished messages.");
            logger.info("Found " + userMessageOutboxes.toString());

            logger.info("Database synchronization completed successfully. Synced " + userMessageOutboxes.size() + " users.");
        } catch (Exception e) {
            logger.severe("Error during database synchronization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
