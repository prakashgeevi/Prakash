package com.securecart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.securecart.entity.Category;
import com.securecart.entity.Product;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
	
	
	 
	public List<Category> findAllByOrderByCategoryNameAsc();
	
	 
	public List<Category> findByCategoryNameContaining(String name);

}
