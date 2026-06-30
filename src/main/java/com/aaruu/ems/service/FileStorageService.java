package com.aaruu.ems.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String uploadPhoto(MultipartFile file);

	String uploadResume(MultipartFile file);

	byte[] getFile(String filePath);
}
