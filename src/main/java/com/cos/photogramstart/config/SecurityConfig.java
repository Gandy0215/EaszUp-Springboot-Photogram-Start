package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuth2DetailsService oAuth2DetailsService;


	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/api/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin")			// get
			.loginProcessingUrl("/auth/signin") // post
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2DetailsService)
		;
	}
}
