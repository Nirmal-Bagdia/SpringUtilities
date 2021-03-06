package com.aaroo.file.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aaroo.file.service.FileUploadService;

@RestController
@RequestMapping("/api")
public class FileController {

	@Autowired
	FileUploadService fileUploadService;

	@PostMapping(value={"/uploadFiles"},consumes = { "multipart/form-data" })
	public Map<Object, Object> uploadFiles(@RequestPart(value = "files", required = true) MultipartFile[] files) throws IOException {
		return fileUploadService.saveFile(files);
	}

}
