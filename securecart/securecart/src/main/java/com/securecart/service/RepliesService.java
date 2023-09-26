package com.securecart.service;

import org.springframework.stereotype.Service;

import com.securecart.entity.Questions;
import com.securecart.model.RequestReplies;

@Service
public interface RepliesService {

	Questions saveReplies(RequestReplies requestReplies);

}
