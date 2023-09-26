package com.securecart.service;



import com.securecart.entity.Questions;

import com.securecart.model.RequestQuestions;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface QuestionsService {

	Questions saveQuestions(RequestQuestions requestQuestions);

	List<Questions> getAllQuestions(Long userId);

}
