package com.tswny.init.service;

import com.tswny.init.config.CustomFileHandlerConfiguration;
import com.tswny.init.web.rest.UploadResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileUploadService {
    private final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    private static final String UPLOAD_ROOT_LOCATION = "/mnt/usr/local/twsny/upload/";

    /**
     * 输出流的方式
     * @param file
     */
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String enCodeFileName = new String(fileName.getBytes(StandardCharsets.UTF_8));
        File filePath = new File(UPLOAD_ROOT_LOCATION);
        if (!filePath.exists()) filePath.mkdirs();

        String relativeFileName = new Date().getTime() + "_" + enCodeFileName;
        File tempFile = new File(filePath, relativeFileName);
        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String absolutePath = tempFile.getAbsolutePath();
        String path = tempFile.getPath();
        return relativeFileName;
    }

    /**
     * copy 文件的方式
     * @param file
     */
    public void upload1(MultipartFile file) {
        String fileOriginalFilename = file.getOriginalFilename();
        log.info("file upload: {}", fileOriginalFilename);
        String relativePath = fileOriginalFilename;

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Paths.get(relativePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy the uploaded file", e);
        }
    }

    public void delete(String relativeFilename) {
        File filePath = new File(UPLOAD_ROOT_LOCATION);
        File file = new File(filePath, relativeFilename);
        if (file.exists()) {
            file.delete();
        }
    }
}
