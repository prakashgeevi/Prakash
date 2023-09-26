package com.rentalapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentalapp.entity.PropertyMessages;

@Repository
public interface MessageRepository extends JpaRepository<PropertyMessages, Long> {

		public List<PropertyMessages> findByFromIdAndPropertyIdOrderByCreatedAtDesc(Long fromUserId, Long propertyId);
		
		
		public void deleteByFrom(Long tenantId);

		public List<PropertyMessages> findAllByPropertyUserIdOrderByCreatedAtDesc(Long hostId);
		
		public List<PropertyMessages> findByConversationIdOrderByCreatedAtDesc(String conversationId);
		public List<PropertyMessages> findAllByPropertyUserIdOrderByCreatedAtAsc(Long hostId);
		

		public List<PropertyMessages> findByFromIdAndPropertyId(Long toId, Long propertyId);
}
