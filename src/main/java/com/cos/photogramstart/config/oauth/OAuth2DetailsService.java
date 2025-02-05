package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.UserRepository;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> userInfo = oAuth2User.getAttributes();

		String userName = "facebook_" + (String)userInfo.get("name");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String name = (String)userInfo.get("name");
		String email = (String)userInfo.get("email");

		User userEntity = userRepository.findByUsername(userName);

		if (userEntity == null) {
			User user = User.builder()
				.username(userName)
				.password(password)
				.name(name)
				.email(email)
				.role("ROLE_USER")
				.build();

			return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());

		} else {
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}
	}
}
