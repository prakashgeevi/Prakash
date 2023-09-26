package com.securecart.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securecart.constant.Status;
import com.securecart.entity.Category;
import com.securecart.entity.Feedback;
import com.securecart.entity.Product;
import com.securecart.entity.Ratings;
import com.securecart.entity.User;
import com.securecart.exception.IdNotFoundException;
import com.securecart.exception.ProductCustomException;
import com.securecart.exception.ProductNotFoundException;
import com.securecart.model.RequestFeedback;
import com.securecart.model.RequestProduct;
import com.securecart.model.RequestRatings;
import com.securecart.model.RequestUpdateProduct;
import com.securecart.repository.FeedbackRepository;
import com.securecart.repository.ICategoryRepository;
import com.securecart.repository.IProductMappingRespository;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.repository.RatingRepository;
import com.securecart.response.ProductResponse;
import com.securecart.service.CategoryService;
import com.securecart.service.ProductService;


@Service
public class ProductServiceimpl implements ProductService {

	private static final Logger logger = LogManager.getLogger(ProductServiceimpl.class);

	@Autowired
	IProductMappingRespository productRepo;

	@Autowired
	ICategoryRepository categoryRepo;

	@Autowired
	IUserAccountRespository useraccountRepo;

	@Lazy
	@Autowired
	CategoryService categoryService;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	RatingRepository ratingRepo;
	
	@Autowired
	FeedbackRepository feedbackRepo;
 
	public Product saveProduct(String requestProduct, MultipartFile file) throws IOException {
		RequestProduct requestCreateProduct = mapper.readValue(requestProduct, RequestProduct.class);

		try {
			logger.info("save new product - resquestProduct= {}, file={} ", requestProduct, file.getOriginalFilename());
			User useraccount = useraccountRepo.findByUserId(requestCreateProduct.getUserId());
			if (useraccount.getRole().equalsIgnoreCase("seller")) {
				Category category = categoryService.findByCategory(requestCreateProduct.getCategoryId());
				Product product = new Product();
				if (existingProduct(requestCreateProduct.getProductName())) {
					product.setProductName(requestCreateProduct.getProductName());
				} else {
					throw new IdNotFoundException(requestCreateProduct.getProductName() + " already exists. "
							+ " Please add some other new product");
				}
				product.setDescription(requestCreateProduct.getDescription());
				product.setStocks(requestCreateProduct.getStock());
				product.setSeller(useraccount);
				product.setUnit(requestCreateProduct.getUnit());
				product.setPrice(requestCreateProduct.getPrice());
				product.setAddedAt(LocalDateTime.now());
				product.setCategory(category);
				product.setImageData(file.getBytes());
				product.setStatus(Status.PENDING.name());
				return productRepo.save(product);
			}
			else {
				throw new ProductCustomException(
						"you are not admin can't add product" + requestCreateProduct.getUserId());
			}
		} catch (Exception e) {
			throw new ProductCustomException(e.getMessage());
		}
	}
	
	public void deletebyId(Long id, Long userId) {
		User useraccount = useraccountRepo.findByUserId(userId);
		Product product = productRepo.findByProductId(id);
		if (useraccount.getRole().equalsIgnoreCase("seller") && product.getSeller().getUserId()==userId) {
			productRepo.delete(product);
		} else {
			throw new ProductCustomException("You dont have permission to delete this product");
		}
	}

	public Product updateProduct(String updateReqProduct, MultipartFile file, Long id) throws Exception {
		RequestUpdateProduct requestUpdateProduct = mapper.readValue(updateReqProduct, RequestUpdateProduct.class);
		logger.info("save new product - resquestProduct= {} ", requestUpdateProduct);
		User useraccount = useraccountRepo.findByUserId(requestUpdateProduct.getUserId());
		Product product = findByProduct(id);
		if (useraccount.getRole().equalsIgnoreCase("seller") && product.getSeller().getUserId()==useraccount.getUserId()) {
			product.setProductName(requestUpdateProduct.getProductName());
			Category category =  categoryService.findByCategory(requestUpdateProduct.getCategoryId());
			product.setCategory(category);
			product.setDescription(requestUpdateProduct.getDescription());
			product.setStocks(requestUpdateProduct.getStock());
			product.setUnit(requestUpdateProduct.getUnit());
			product.setPrice(requestUpdateProduct.getPrice());
			if (file != null) {
				product.setImageData(file.getBytes());
			}
			return productRepo.save(product);
		}
		else {
			throw new ProductCustomException("You dont have permission to modify this product details");
		}
	}
	
	 
	public Product getProductById(Long productId) {
		logger.info("get product by id productId={} ", productId);
		try {
			return findByProduct(productId);
		}
		catch (Exception e) {
			throw new ProductNotFoundException("Product Not found" + productId + e.getMessage());
		}
	}
	public List<ProductResponse> filterbyId(String pName, Long userId) {
		logger.info("get all  product and filter by category and product name={} ", pName);
		try {
			
			
			if (pName.isEmpty()) {
				 
				if(isAdmin(userId) || isSeller(userId)) {
					return mapFIlterProduct(productRepo.findAllByOrderByAddedAtDesc());
				}
				
				return mapFIlterProduct(productRepo.findAllByStatusOrderByAddedAtDesc("APPROVED"));
			} else {
				if(isAdmin(userId) || isSeller(userId)) {
					return mapFIlterProduct(
						productRepo.findByproductNameContainingOrCategoryCategoryNameContaining(pName, pName));
				}
				return mapFIlterProduct(
						productRepo.findAllByStatusAndProductNameContainingOrStatusAndCategoryCategoryNameContaining("APPROVED", pName,"APPROVED", pName));
			}
			
			
			
		} catch (Exception e) {
			throw new ProductCustomException(e.getMessage());
		}
	}

	public List<ProductResponse> mapFIlterProduct(List<Product> product) {
		return product.stream()
				.map(e -> new ProductResponse(e.getProductId(), e.getProductName(), e.getStocks(), e.getUnit(),
						e.getCategory().getCategoryId(), e.getCategory().getCategoryName(), e.getPrice(),
						e.getImageData(), averageRatings(e.getProductId()), e.getStatus()))
				.collect(Collectors.toList());

	}

	public boolean isAdmin(Long userId) {
		User useraccount = useraccountRepo.findByUserId(userId);
		return useraccount.getRole().equalsIgnoreCase("admin");
	}
	
	public boolean isSeller(Long userId) {
		User useraccount = useraccountRepo.findByUserId(userId);
		return useraccount.getRole().equalsIgnoreCase("seller");
	}
	
	public boolean isBuyer(Long userId) {
		User useraccount = useraccountRepo.findByUserId(userId);
		return useraccount.getRole().equalsIgnoreCase("buyer");
	}
	public boolean existingProduct(String productName) {
		Product existingProduct = productRepo.findByProductName(productName);
		return existingProduct == null;
	}
	
	public Product findByProduct(Long productId) {
		return productRepo.findByProductId(productId);
	}

	@Override
	public List<ProductResponse> getAllPendingPoduct() {
		return mapFIlterProduct(productRepo.findAllByStatus(Status.PENDING.name()));
	}
	
	public double averageRatings(long productId) {
		List<Ratings> raitings = ratingRepo.findByProductProductId(productId);
		List<Double> collect = raitings.stream().map(e -> e.getRating()).collect(Collectors.toList());
        return collect.stream().mapToDouble(a -> a).average().orElse(0);
	}


	@Override
	public Product setStatusApproveProduct(Long productId, Long userId, String status) {
		 Product product = findByProduct(productId);
		 if(isAdmin(userId)) {
		 if(status.equalsIgnoreCase("pending")) {
			 product.setStatus(Status.PENDING.name());	
			}
			else if(status.equalsIgnoreCase("approved")) {
				 product.setStatus(Status.APPROVED.name());	
			}
		return productRepo.save(product);
		 }
		 return null;
	}

	@Override
	public Product saveReview(@Valid RequestFeedback feedback) {
		 Product product = findByProduct(feedback.getProductId());
		 
		 Feedback fe = feedbackRepo.findByProductProductIdAndBuyerIdUserId(feedback.getProductId(), feedback.getUserId());
		 if(fe == null) {
			 fe = new Feedback();
			 User useraccount = useraccountRepo.findByUserIdAndRole(feedback.getUserId(), "buyer");
			 fe.setBuyerId(useraccount);
			 fe.setProduct(product);
		 }
		
		 fe.setFeedbackContent(feedback.getMessage());
		feedbackRepo.save(fe);
		return  findByProduct(feedback.getProductId());
	}

	@Override
	public Product saveRating(@Valid RequestRatings rating) {
		Product product = findByProduct(rating.getProductId());
		Ratings rate = ratingRepo.findByProductProductIdAndBuyerIdUserId(rating.getProductId(), rating.getUserId());
		 if(rate == null) {
			 rate = new Ratings();
			 User useraccount = useraccountRepo.findByUserIdAndRole(rating.getUserId(), "buyer");
			 rate.setBuyerId(useraccount);
			 rate.setProduct(product);
		 }
		 
		 rate.setRating(rating.getRating());
		ratingRepo.save(rate);
		return findByProduct(rating.getProductId());
	}

	 
}
