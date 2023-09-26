package com.securecart.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecart.entity.Questions;
import com.securecart.entity.User;
import com.securecart.exception.UserNotFoundException;
import com.securecart.model.RequestQuestions;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.repository.QuestionsRepository;
import com.securecart.service.QuestionsService;

@Service
public class QuestionsServiceImpl implements QuestionsService {

	@Autowired
	QuestionsRepository questionsRepository;

	@Autowired
	IUserAccountRespository userRepo;

	@Override
	public Questions saveQuestions(RequestQuestions requestQuestions) {
		User buyer = userRepo.findById(requestQuestions.getBuyerId())
				.orElseThrow(() -> new UserNotFoundException("buyer is not found"));
		User seller = userRepo.findById(requestQuestions.getSellerId())
				.orElseThrow(() -> new UserNotFoundException("seller is not found"));
		Questions questions = new Questions();
		questions.setBuyerId(buyer);
		questions.setQuestion(requestQuestions.getQuestion());
		questions.setDatetime(LocalDateTime.now());
		questions.setSellerId(seller);
		// questions.setReplies(requestQuestions.get);
		return questionsRepository.save(questions);
	}

	@Override
	public List<Questions> getAllQuestions(Long userId) {

		User buyer = userRepo.findByUserId(userId);

		if (buyer.getRole().equals("buyer")) {
			return questionsRepository.findAllByBuyerIdUserIdOrderByDatetimeDesc(userId);
		} else if(buyer.getRole().equals("seller")) {
			return questionsRepository.findAllBySellerIdUserIdOrderByDatetimeDesc(userId);
		}  else if(buyer.getRole().equals("admin")) {
			return questionsRepository.findAllByOrderByDatetimeDesc();
		} 

		return new ArrayList<>();
	}
}
