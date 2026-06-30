package com.aaruu.ems.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aaruu.ems.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public String uploadPhoto(MultipartFile file) {

		if (file.isEmpty()) {
			throw new RuntimeException("Photo cannot be empty");
		}

		String contentType = file.getContentType();

		if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {

			throw new RuntimeException("Only JPG and PNG files are allowed");
		}

		if (file.getSize() > 5 * 1024 * 1024) {

			throw new RuntimeException("Photo size should be less than 5 MB");
		}

		return saveFile(file, "images");
	}

	@Override
	public String uploadResume(MultipartFile file) {

		if (file.isEmpty()) {

			throw new RuntimeException("Resume cannot be empty");
		}

		String contentType = file.getContentType();

		if (contentType == null || !contentType.equals("application/pdf")) {

			throw new RuntimeException("Only PDF files are allowed");
		}

		if (file.getSize() > 5 * 1024 * 1024) {

			throw new RuntimeException("Resume size should be less than 5 MB");
		}

		return saveFile(file, "resumes");
	}

	private String saveFile(MultipartFile file, String folderName) {

		try {

			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

			Path path = Paths.get(uploadDir, folderName, fileName);

			Files.createDirectories(path.getParent());

			Files.write(path, file.getBytes());

			return path.toString();

		}

		catch (IOException e) {

			throw new RuntimeException("File upload failed");
		}
	}

	@Override
	public byte[] getFile(String filePath) {

		try {

			Path path = Paths.get(filePath);

			return Files.readAllBytes(path);

		}

		catch (IOException e) {

			throw new RuntimeException("File not found");
		}
	}

}

//| Concept                     | Why we use it                             |
//| --------------------------- | ----------------------------------------- |
//| `MultipartFile`             | Receive uploaded files                    |
//| `UUID.randomUUID()`         | Prevent duplicate file names              |
//| `@Value`                    | Read values from `application.properties` |
//| `Files.createDirectories()` | Create folders automatically              |
//| `Files.write()`             | Save file bytes to disk                   |
//| Validation                  | Security and performance                  |
//| `saveFile()`                | DRY Principle (Don't Repeat Yourself)     |
