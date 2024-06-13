package com.yangyag.toy.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartUpService implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartUpService.class);
    private final DatabaseBackupService databaseBackupService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            databaseBackupService.download();
            logger.info("Database File Downloaded after application start up.");
        } catch (Exception e) {
            logger.error("Error during database backup download", e);
        }

    }
}
