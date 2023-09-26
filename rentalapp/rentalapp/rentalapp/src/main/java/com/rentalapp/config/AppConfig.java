package com.rentalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rentalapp.model.MatrixFactorization;

@Configuration
public class AppConfig {
	
    @Bean
    public MatrixFactorization matrixFactorization() {
        // Provide the required parameters for the matrix factorization constructor
        int numUsers = 1000;
        int numProperties = 500;
        int numFactors = 10;
        double learningRate = 0.01;
        double regularization = 0.01;

        return new MatrixFactorization(numUsers, numProperties, numFactors, learningRate, regularization);
    }
}
