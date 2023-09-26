package com.rentalapp.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentalapp.entity.Property;
import com.rentalapp.entity.PropertyMessages;
import com.rentalapp.entity.User;
import com.rentalapp.exception.NotFoundException;
import com.rentalapp.exception.PropertyException;
import com.rentalapp.model.RequestHostReply;
import com.rentalapp.model.RequestPropertyMessage;
import com.rentalapp.repository.IUserAccountRespository;
import com.rentalapp.repository.MessageRepository;
import com.rentalapp.repository.PropertyRepository;

@Service
public class MessageService {

	@Autowired
	MessageRepository meessageRepo;

	@Autowired
	PropertyRepository propertyRepo;

	@Autowired
	IUserAccountRespository userRepo;

	public PropertyMessages sendMessage(RequestPropertyMessage requestMessage) {

		User fromUser = userRepo.findById(requestMessage.getFromId())
				.orElseThrow(() -> new NotFoundException("User Id not found"));

		User toUser = userRepo.findById(requestMessage.getToId())
				.orElseThrow(() -> new NotFoundException("User Id not found"));

		List<PropertyMessages> messageList = null;
		if (fromUser.getRole().equalsIgnoreCase("host")) {
			messageList = meessageRepo.findByFromIdAndPropertyId(requestMessage.getToId(),
					requestMessage.getPropertyId());
		} else {
			messageList = meessageRepo.findByFromIdAndPropertyId(requestMessage.getFromId(),
					requestMessage.getPropertyId());
		}

		PropertyMessages messages = new PropertyMessages();

		if (!messageList.isEmpty()) {
			PropertyMessages propertyMessages = messageList.stream().findFirst().get();
			messages.setConversationId(propertyMessages.getConversationId());
		} else {
			messages.setConversationId(UUID.randomUUID().toString());
		}

		Property property = propertyRepo.findById(requestMessage.getPropertyId())
				.orElseThrow(() -> new NotFoundException("Property Id not found"));
		messages.setProperty(property);
		messages.setMessage(requestMessage.getMessage());
		messages.setFrom(fromUser);
		messages.setTo(toUser);

		messages.setCreatedAt(LocalDateTime.now());
		return meessageRepo.save(messages);
	}
	
	
	public PropertyMessages sendMessage(RequestHostReply requestMessage) {

		User fromUser = userRepo.findById(requestMessage.getFromId())
				.orElseThrow(() -> new NotFoundException("User Id not found"));

		List<PropertyMessages> propertyMessage = meessageRepo
				.findByConversationIdOrderByCreatedAtDesc(requestMessage.getConvertId());
		
		

		PropertyMessages messages = new PropertyMessages();

		if (!propertyMessage.isEmpty()) {
			PropertyMessages propertyMessages = propertyMessage.stream().findFirst().get();
			messages.setConversationId(propertyMessages.getConversationId());
			messages.setProperty(propertyMessages.getProperty());
			messages.setTo(propertyMessages.getTo());
		}

		messages.setMessage(requestMessage.getMessage());
		messages.setFrom(fromUser);

		messages.setCreatedAt(LocalDateTime.now());
		return meessageRepo.save(messages);
	}

	public Map<String, List<PropertyMessages>> getAllMessagesUser(Long toId) {
		List<PropertyMessages> propertyUserIdList = meessageRepo
				.findAllByPropertyUserIdOrderByCreatedAtAsc(toId);
		Map<String, List<PropertyMessages>> map = propertyUserIdList.stream()
				.collect(Collectors.groupingBy(PropertyMessages::getConversationId));
		Map<String, List<PropertyMessages>> res = new HashMap<>();
		for (Entry<String, List<PropertyMessages>> entry : map.entrySet()) {
			String messageKey = new StringBuilder()
					.append(entry.getValue().get(0).getFrom().getFirstName())
					.append(" ")
					.append(entry.getValue().get(0).getFrom().getLastName())
					.append("$$$$")
					.append(entry.getValue().get(0).getProperty().getName())
					.append("####")
					.append(entry.getValue().get(0).getConversationId()).toString();
			res.put(messageKey, entry.getValue());
		}
		return res;
	}

	public List<PropertyMessages> getByMessageConversationId(String conversationId) {
		try {
			List<PropertyMessages> propertyMessage = meessageRepo
					.findByConversationIdOrderByCreatedAtDesc(conversationId);
			return propertyMessage;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PropertyException(e.getMessage());
		}
	}

	public List<PropertyMessages> getAllMessageByTenantIdAndPropertyId(Long tenantId, Long propertId) {
		try {
			System.out.println(tenantId);
			User user = userRepo.findById(tenantId).orElseThrow(() -> new NotFoundException("teanant id not found"));
			if (!user.getRole().equalsIgnoreCase("tenant")) {
				throw new PropertyException("This user can't get any messages. role mismatch");
			}
			List<PropertyMessages> propertyMessage = meessageRepo
					.findByFromIdAndPropertyIdOrderByCreatedAtDesc(tenantId, propertId);
			
			if(!propertyMessage.isEmpty()) {
				return meessageRepo
						.findByConversationIdOrderByCreatedAtDesc(propertyMessage.get(0).getConversationId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new PropertyException(e.getMessage());
		}
		return null;
	}

	public void deleteMessageTenantUser(Long tanatId) {
		meessageRepo.deleteByFrom(tanatId);
	}
	
	
	public void deleteByConversationId(String conversationId) {
		try {
		List<PropertyMessages> messageList = meessageRepo.findByConversationIdOrderByCreatedAtDesc(conversationId);
		
		if(!messageList.isEmpty()) {
			meessageRepo.deleteAll(messageList);
		}
		else {
			throw new PropertyException("conversationList is empty");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PropertyException(e.getMessage());
		}
		
		
	}

}
