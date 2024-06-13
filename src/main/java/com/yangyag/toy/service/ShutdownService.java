package com.yangyag.toy.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShutdownService implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ShutdownService.class);
    private final DatabaseBackupService databaseBackupService;

    @Override
    public void destroy() throws Exception {
        try {
            databaseBackupService.upload();
            logger.info("Database file uploaded before shutdown.");
        } catch (Exception e) {
            logger.error("Error during database backup upload", e);
        }
    }
}
