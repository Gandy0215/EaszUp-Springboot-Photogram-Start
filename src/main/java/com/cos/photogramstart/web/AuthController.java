package com.cos.photogramstart.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AuthController {

	private final AuthService authService;

	@GetMapping("/auth/signin")
	public String signinForm(){
		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm(){
		return "auth/signup";
	}

	@PostMapping("/auth/signup")
	public String signup(SignupDto signupDto){
		//log.info(signupDto.toString());
		User user = signupDto.toEntity();
		//log.info(user.toString());

		User userEntity = authService.회원가입(user);
		System.out.println(userEntity.toString());

		return "auth/signin";
	}
}
