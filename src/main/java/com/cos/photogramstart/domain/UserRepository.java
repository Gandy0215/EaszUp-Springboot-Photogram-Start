package com.cos.photogramstart.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.photogramstart.domain.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
