package com.cos.photogramstart.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

	@Transactional(readOnly = true)
	public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
		return imageRepository.mStory(principalId, pageable);
	}


	@Value("${file.path}")
	private String FILE_UPLOAD_PATH;

	private final ImageRepository imageRepository;

	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
		log.info("이미지 이름 :: " + imageFileName);

		Path imageFilePath = Paths.get(FILE_UPLOAD_PATH + imageFileName);

		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
	}
}
