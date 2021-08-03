package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userid) {

		/// DB 저장에 필요한 데이터만 만들어서 insert
		// Return 할 때 필요한 정보가 있다면 함꼐 만들거나 select 해서 insert 해야함
		Image image = new Image();
		User user = new User();

		image.setId(imageId);
		user.setId(userid);

		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(user);

		return commentRepository.save(comment);
	}

	@Transactional
	public void 댓글삭제(int id) {
		commentRepository.deleteById(id);
	}
}
