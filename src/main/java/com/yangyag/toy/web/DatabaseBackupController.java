package com.yangyag.toy.web;

import com.yangyag.toy.service.DatabaseBackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DatabaseBackupController {

    private final DatabaseBackupService databaseBackupService;

    @GetMapping("/database/backup")
    public ResponseEntity<String> downloadFile() {
        databaseBackupService.download();;

        return ResponseEntity.ok("download succeed");
    }

    @PostMapping("/database/backup")
    public ResponseEntity<Void> uploadFile() {
        databaseBackupService.upload();;

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
