package com.rentalapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
	
		public List<SearchHistory> findByUserId(Long userId);

}
