package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import com.cos.photogramstart.web.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;
	private final SubscribeService subscribeService;

	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		User userEntity = userService.회원프로필사진변경(principalId, profileImageFile);
		principalDetails.setUser(userEntity);
		return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
	}

	@GetMapping("/api/user/{pageUSerId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUSerId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		List<SubscribeDto> subscribeDtoList = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUSerId);

		return new ResponseEntity<>(new CMRespDto<>(1, "구독자리스트 조회성공", subscribeDtoList), HttpStatus.OK);
	}

	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id,
		@Valid UserUpdateDto userUpdateDto,
		BindingResult bindingResult,    // 반드시 @Vaild 뒤에 적어야 함
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity);
			return new CMRespDto<>(1, "회원수정완료", userEntity);
	}
}
