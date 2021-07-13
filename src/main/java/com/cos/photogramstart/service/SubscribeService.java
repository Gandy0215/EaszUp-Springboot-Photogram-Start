package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	private final EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUSerId) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM Subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if((? = u.id), 1, 0) equalUserState ");
		sb.append("From User u INNER JOIN Subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?");

		Query query = entityManager.createNativeQuery(sb.toString())
			.setParameter(1, principalId)
			.setParameter(2, principalId)
			.setParameter(3, pageUSerId);

		JpaResultMapper resultMapper = new JpaResultMapper();
		List<SubscribeDto> list = resultMapper.list(query, SubscribeDto.class);

		return list;
	}

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
