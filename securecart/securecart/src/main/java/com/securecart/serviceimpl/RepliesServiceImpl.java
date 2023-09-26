package com.securecart.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecart.entity.Questions;
import com.securecart.entity.Replies;
import com.securecart.entity.User;
import com.securecart.exception.UserNotFoundException;
import com.securecart.model.RequestReplies;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.repository.QuestionsRepository;
import com.securecart.repository.RepliesRepository;
import com.securecart.service.RepliesService;

@Service
public class RepliesServiceImpl implements RepliesService {

	@Autowired
	IUserAccountRespository userRepo;

	@Autowired
	QuestionsRepository questionsRepository;

	@Autowired
	RepliesRepository repliesRepository;

	@Override
	public Questions saveReplies(RequestReplies requestReplies) {
		User buyer = userRepo.findById(requestReplies.getBuyerId())
				.orElseThrow(() -> new UserNotFoundException("Buyer is not found"));
		Questions questions = questionsRepository.findById(requestReplies.getQuestionsId())
				.orElseThrow(() -> new UserNotFoundException("Questions is not found"));
	
//		if (buyer.getRole().equalsIgnoreCase("buyer")) {
			Replies replies = new Replies();
			
			replies.setDatetime(LocalDateTime.now());
			
			replies.setQuestions(questions);
			
			replies.setUserId(buyer);
			
			replies.setReplyMessage(requestReplies.getReplyMessage());
			repliesRepository.save(replies);
			return questionsRepository.save(questions);
//		}
		
	}

}
