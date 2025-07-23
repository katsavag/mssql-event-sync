package com.katsadourose.primary_service.controller;

import com.katsadourose.primary_service.entity.SecondaryUserEntity;
import com.katsadourose.primary_service.repository.secondary.SecondaryUserRepository;
import com.katsadourose.primary_service.service.DatabaseSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private final DatabaseSyncService databaseSyncService;
    private final SecondaryUserRepository secondaryUserRepository;

    @Autowired
    public SyncController(DatabaseSyncService databaseSyncService, SecondaryUserRepository secondaryUserRepository) {
        this.databaseSyncService = databaseSyncService;
        this.secondaryUserRepository = secondaryUserRepository;
    }

    @PostMapping
    public ResponseEntity<String> triggerSync() {
        databaseSyncService.syncDatabases();
        return ResponseEntity.ok("Synchronization triggered successfully");
    }

    @GetMapping("/secondary-users")
    public ResponseEntity<List<SecondaryUserEntity>> getSecondaryUsers() {
        List<SecondaryUserEntity> users = secondaryUserRepository.findAll();
        return ResponseEntity.ok(users);
    }
}