package com.aaroo.file.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	Map<Object, Object> saveFile(MultipartFile[] files) throws IOException;

}
