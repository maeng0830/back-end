package com.project.devgram.controller;


import com.project.devgram.service.ImageUploader;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class s3Controller {

	private final ImageUploader uploader;

	@PostMapping("/image")
	public void uploadImage(@RequestPart List<MultipartFile> multipartFile) throws IOException {
		uploader.upload(multipartFile.get(0), "devgram");
	}
}
