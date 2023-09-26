package com.rentalapp.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import com.rentalapp.exception.NotFoundException;

public class ImageUtils {

	public static String uploadFile(MultipartFile file, String folderPath) {
		try {
			String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	        String filePath = folderPath + File.separator + filename;

	        byte[] fileBytes = file.getBytes();
	        Path path = Paths.get(filePath);
	        Files.write(path, fileBytes);
			return filePath;
 
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

}
