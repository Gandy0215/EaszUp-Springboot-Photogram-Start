package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Value("${file.path}")
	private String uploadFolder;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);

		registry
			.addResourceHandler("/upload/**")
			.addResourceLocations("file:///" + uploadFolder)
			.setCachePeriod(60*10*6)		//캐싱 1시간
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
	}
}
