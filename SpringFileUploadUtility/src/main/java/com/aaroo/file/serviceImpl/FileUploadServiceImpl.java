package com.aaroo.file.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aaroo.file.model.FileDetail;
import com.aaroo.file.repository.FileUploadRepository;
import com.aaroo.file.service.FileUploadService;
import com.aaroo.file.util.Constant;
import com.aaroo.file.util.ResponseConstant;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	final static Logger LOGGER = LoggerFactory.getLogger(FileUploadServiceImpl.class);

	@Autowired
	FileUploadRepository fileUploadRepository;

	private final Path fileStorageLocation = Paths.get(Constant.UPLOAD_LOCATION);

	@Override
	public Map<Object, Object> saveFile(MultipartFile[] files) throws IOException {

		Map<Object, Object> map = new HashMap<>();

		FileDetail fileDetail = new FileDetail();

		for (MultipartFile file : files) {
			if (!file.isEmpty()) {

				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path filePath = this.fileStorageLocation.resolve(fileName);
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				Long currentTimeMilisecs = System.currentTimeMillis();
				fileDetail.setFileName(fileName);
				fileDetail.setFilePath(Constant.UPLOAD_LOCATION + "\\" + currentTimeMilisecs + "_" + file.getOriginalFilename());
				fileDetail.setFileType(file.getContentType());
				fileDetail.setFileSize(file.getSize());
				fileUploadRepository.save(fileDetail);

				map.put(ResponseConstant.RESPONSE_MESSAGE, ResponseConstant.SUCCESS_MESSAGE);
				map.put(ResponseConstant.RESPONSE_SUCCESS_CODE, ResponseConstant.SUCCESS_CODE);
				map.put(ResponseConstant.RESPONSE_OBJECT, fileDetail);

				LOGGER.info("File successfully uploaded");
			} else
				LOGGER.warn("File is empty");
		}

		return map;
	}

}
