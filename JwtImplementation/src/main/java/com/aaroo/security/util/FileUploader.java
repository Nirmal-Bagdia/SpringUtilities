package com.aaroo.security.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploader {

	public static String uploadProfileImage(MultipartFile mulfile, String path) {

		if (!mulfile.isEmpty()) {
			Long timestamp = System.currentTimeMillis();

			String filename = mulfile.getOriginalFilename();
			String extension = FilenameUtils.getExtension(filename);

			filename = timestamp.toString().concat("." + extension);

			try {
				byte[] bytes = mulfile.getBytes();

				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(path + filename, true));
				buffStream.write(bytes);
				buffStream.close();
				return filename;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";

	}

}
