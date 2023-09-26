//package com.rentalapp.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.rentalapp.entity.Property;
//import com.rentalapp.entity.PropertyQuestions;
//import com.rentalapp.entity.User;
//import com.rentalapp.exception.NotFoundException;
//import com.rentalapp.model.RequestQuestions;
//import com.rentalapp.repository.IUserAccountRespository;
//import com.rentalapp.repository.PropertyQuestionsRepository;
//import com.rentalapp.repository.PropertyRepository;
//
//@Service
//public class QuestionsService {
//
//	@Autowired
//	PropertyQuestionsRepository questionsRepo;
//
//	@Autowired
//	IUserAccountRespository userRepo;
//
//	@Autowired
//	PropertyRepository propertyRepo;
//
//	public PropertyQuestions saveQuestions(RequestQuestions requestQuestions) {
//		User user = userRepo.findById(requestQuestions.getUserId()).get();
//		if (user != null) {
//			Property property = propertyRepo.findById(requestQuestions.getPropertyId())
//					.orElseThrow(() -> new NotFoundException("property id not found"));
//
//			PropertyQuestions questions = new PropertyQuestions();
//			questions.setProperty(property);
//			questions.setQuestions(requestQuestions.getQuestion());
//			questions.setAnswer(requestQuestions.getAnswers());
//			questions.setAddedAt(LocalDateTime.now());
//			return questionsRepo.save(questions);
//		}
//		
//		return null;
//	}
//
//	public List<PropertyQuestions> getAllQuestions(Long propertId) {
//		return questionsRepo.findByPropertyId(propertId);
//	}
//	public PropertyQuestions updateQuestion(@Valid RequestQuestions requestQuestions, Long questionId) {
//		PropertyQuestions updatePropertyQuestions = questionsRepo.findById(questionId)
//				.orElseThrow(() -> new NotFoundException("Question id not found"));
//		Property property = propertyRepo.findById(requestQuestions.getPropertyId()).orElseThrow(() -> new NotFoundException("property not found"));
//		updatePropertyQuestions.setProperty(property);
//		updatePropertyQuestions.setAnswer(requestQuestions.getAnswers());
//		updatePropertyQuestions.setQuestions(requestQuestions.getQuestion());
//		return questionsRepo.save(updatePropertyQuestions);
//		 
//	}
//	
//	public void deleteByQuestion(Long questionId) {
//		questionsRepo.deleteById(questionId);
//	}
//}
