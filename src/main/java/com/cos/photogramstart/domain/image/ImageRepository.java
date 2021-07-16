package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	@Query(value = "SELECT * FROM Image WHERE userId IN (SELECT toUserId From Subscribe WHERE fromUserId = :principalId)", nativeQuery = true)
	Page<Image> mStory(int principalId, Pageable pageable);
}
