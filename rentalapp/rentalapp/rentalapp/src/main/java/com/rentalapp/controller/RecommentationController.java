
package com.rentalapp.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentalapp.entity.Property;
import com.rentalapp.service.RecommendationService;

@RestController
@RequestMapping("/api/recommend")
@CrossOrigin("*")
public class RecommentationController {
	
	private static final Logger logger = LogManager.getLogger(RecommentationController.class);
    @Autowired
    private RecommendationService recommendationService;

    @PostConstruct
    public void initializeMatrixFactorization() {
    	//    recommendationService.initializeMatrixFactorizationForReservation();
    	logger.info("Initialize the Matrix Factorization");
    	
    	   recommendationService.initializeMatrixFactorization();
    	   
       
    }

    @GetMapping("/users/{userId}/recommendations")
    public ResponseEntity<?> getRecommendations(@PathVariable long userId) {
    	logger.info("getRecommendations with Initialize the Matrix Factorization and send userId={}", userId);
    	List<Property> recommendations = recommendationService.getRecommendations(userId, 6);
    		if(recommendations.size()<6) {
    			logger.info("recommendations list is empty or below 6 with size={}",recommendations.size());
    			List<Property> getRandomPropertyList = recommendationService.getRandomPropertyList(recommendations,userId);
    			logger.info("getRecommendations with randrom property list in searchHistory with size={}", getRandomPropertyList.size());
    			 return new ResponseEntity<>(getRandomPropertyList, HttpStatus.OK);
    		}
    	logger.info("getRecommendations with recommend property list with size={}", recommendations.size());
        return new ResponseEntity<>(recommendations.subList(0, 6), HttpStatus.OK);
    }

}
