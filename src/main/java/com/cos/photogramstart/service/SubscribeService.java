package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;

	@Transactional
	public int 구독하기(int fromUserId, int toUserId) {
		int result = -1;
		try {
			result = subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독하였습니다.");
		}
		return result;
	}

	@Transactional
	public int 구독취소하기(int fromUserId, int toUserId) {
		return subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
