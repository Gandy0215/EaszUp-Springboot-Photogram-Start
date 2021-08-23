package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, length = 100)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;

	private String website;
	private String bio;
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;

	private String profileImageUrl;
	@Column(nullable = false)
	private String role;

	// LAZY : User를 Select 할 떄 해당 userId로 등록 된 image들을 가져오지마
	//         단, getImages() 함수를 호출 할 때 가져와
	// Eager : User를 Select 할 떄 해당 userId로 등록 된 image들을 가져와
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"})		//json 으로 파싱할 때에 무시되도록 선언(순환참조 방지)
	private List<Image> images;

	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
