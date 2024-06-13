package com.yangyag.toy.service.impl;

import com.yangyag.toy.service.DatabaseBackupService;
import com.yangyag.toy.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class SQLiteService implements DatabaseBackupService {
    private final S3Service s3Service;

    @Override
    public void download() {

        try {
            File dbFile = s3Service.downloadFile("mydatabase.db");

            // 다운로드한 파일을 /var/app/current 디렉토리로 이동
            File targetFile = new File("/var/app/current/mydatabase.db");
            Files.move(dbFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Database file moved to: " + targetFile.getAbsolutePath());
        } catch(Exception e) {
            e.printStackTrace();;
        }
    }

    @Override
    public void upload() {
        String dbPath = "/var/app/current/mydatabase.db";
        File dbFile = new File(dbPath);

        s3Service.uploadFile("mydatabase.db", dbFile);
    }
}
