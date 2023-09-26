package com.rentalapp.model;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.rentalapp.entity.Rating;

 
@Component
public class MatrixFactorization {
    private int numUsers;
    public int numProperties;
    private int numFactors;
    private double learningRate;
    private double regularization;

    private double[][] userMatrix;
    private double[][] propertyMatrix;

     
    public MatrixFactorization(int numUsers, int numProperties, int numFactors, double learningRate, double regularization) {
        this.numUsers = numUsers;
        this.numProperties = numProperties;
        this.numFactors = numFactors;
        this.learningRate = learningRate;
        this.regularization = regularization;

        // Initialize user and property matrices with random values
        userMatrix = new double[numUsers][numFactors];
        propertyMatrix = new double[numProperties][numFactors];
        initializeMatrix(userMatrix);
        initializeMatrix(propertyMatrix);
    }

    public void train(List<Rating> ratings, int numIterations) {
        for (int iter = 0; iter < numIterations; iter++) {
            for (Rating rating : ratings) {
                int userIndex = rating.getUser().getUserId().intValue() - 1;
                int propertyIndex = rating.getId().intValue() - 1;
                double ratingValue = rating.getRating();

                double prediction = predictRating(userIndex, propertyIndex);
                double error = ratingValue - prediction;

                // Update user and property matrices based on the error
                for (int factor = 0; factor < numFactors; factor++) {
                    double userValue = userMatrix[userIndex][factor];
                    double propertyValue = propertyMatrix[propertyIndex][factor];

                    userMatrix[userIndex][factor] += learningRate * (error * propertyValue - regularization * userValue);
                    propertyMatrix[propertyIndex][factor] += learningRate * (error * userValue - regularization * propertyValue);
                }
            }
        }
    }

    public double predictRating(int userIndex, long propertyIndex) {
        double prediction = 0.0;
        for (int factor = 0; factor < numFactors; factor++) {
            prediction += userMatrix[userIndex][factor] * propertyMatrix[(int) propertyIndex][factor];
        }
        return prediction;
    }

    private void initializeMatrix(double[][] matrix) {
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }
    }
}

