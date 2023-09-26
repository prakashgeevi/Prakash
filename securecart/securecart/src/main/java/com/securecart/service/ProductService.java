package com.securecart.service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.securecart.entity.Feedback;
import com.securecart.entity.Product;
import com.securecart.entity.Ratings;
import com.securecart.model.RequestFeedback;
import com.securecart.model.RequestRatings;
import com.securecart.response.ProductResponse;

@Service
public interface ProductService {

	public Product saveProduct(String requestProduct, MultipartFile file)
			throws IOException, SQLIntegrityConstraintViolationException;

	public void deletebyId(Long id, Long userId);

	public Product updateProduct(String requestProduct, MultipartFile file, Long id) throws Exception;

	public Product getProductById(Long productId);

	public List<ProductResponse> filterbyId(String pName, Long userId);

	public boolean isAdmin(Long userId);
	
	public boolean isSeller(Long userId);
	
	public List<ProductResponse> getAllPendingPoduct();
	
	public Product setStatusApproveProduct(Long productId, Long userId, String status);

	public Product saveReview(@Valid RequestFeedback feedback);

	public Product saveRating(@Valid RequestRatings rating);

}
