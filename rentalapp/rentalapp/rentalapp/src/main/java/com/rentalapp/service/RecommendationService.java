package com.rentalapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentalapp.entity.Property;
import com.rentalapp.entity.PropertyReservation;
import com.rentalapp.entity.Rating;
import com.rentalapp.entity.SearchHistory;
import com.rentalapp.entity.User;
import com.rentalapp.exception.NotFoundException;
import com.rentalapp.repository.IUserAccountRespository;
import com.rentalapp.repository.Interaction;
import com.rentalapp.repository.PropertyRepository;
import com.rentalapp.repository.PropertyReservationRepository;
import com.rentalapp.repository.RatingRepository;
import com.rentalapp.repository.SearchHistoryRepository;

@Service
public class RecommendationService {

	@Autowired
	IUserAccountRespository userRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private PropertyReservationRepository reservationRepository;

	@Autowired
	SearchHistoryRepository searchHistoryRepo;

	@Autowired
	PropertyRepository houseRepository;

	private static final Logger logger = LogManager.getLogger(RecommendationService.class);

	private final int numLatentFactors = 10;
	private final double learningRate = 0.01;
	private final double regularization = 0.05;
	private final int numIterations = 100;

	private Map<Long, Integer> userIndices;
	private Map<Long, Integer> itemIndices;
	private RealMatrix userMatrix;
	private RealMatrix itemMatrix;

	public void initializeMatrixFactorization() {

		List<Rating> ratings = ratingRepository.findAll();
		List<PropertyReservation> reservations = reservationRepository.findAll();

		logger.info("rating and reservation object with size ratings={}, reservations={}", ratings.size(),
				reservations.size());

		// Combine all interactions into a single list
		List<Interaction> interactions = new ArrayList<>();
		interactions.addAll(ratings);
		interactions.addAll(reservations);
		// interactions.addAll(searchHistories);

		// Create user and item index maps
		userIndices = new HashMap<>();
		itemIndices = new HashMap<>();
		int userIndex = 0;
		int itemIndex = 0;

		for (Interaction interaction : interactions) {
			long userId = 0;
			long itemId = 0;

			if (interaction instanceof Rating) {
				userId = ((Rating) interaction).getUser().getId();
				itemId = ((Rating) interaction).getProperty().getId();
			} else if (interaction instanceof PropertyReservation) {
				userId = ((PropertyReservation) interaction).getUser().getId();
				itemId = ((PropertyReservation) interaction).getProperty().getId();
			} else { // SearchHistory
				// userIdx = ((SearchHistory) interaction).getUser().getId();
				// itemIdx = ((SearchHistory) interaction).getProperty().getId();
			}

			if (!userIndices.containsKey(userId)) {
				userIndices.put(userId, userIndex++);
			}
			if (!itemIndices.containsKey(itemId)) {
				itemIndices.put(itemId, itemIndex++);
			}
		}

		int numUsers = userIndices.size();
		int numItems = itemIndices.size();

		logger.info("numUsers and numItems with values={},{}", numUsers, numItems);

		if (numUsers != 0 && numItems != 0) {
			// Initialize user and item matrices randomly
			userMatrix = MatrixUtils.createRealMatrix(numUsers, numLatentFactors);
			itemMatrix = MatrixUtils.createRealMatrix(numItems, numLatentFactors);

			for (int i = 0; i < numUsers; i++) {
				for (int j = 0; j < numLatentFactors; j++) {
					userMatrix.setEntry(i, j, Math.random());
				}
			}

			for (int i = 0; i < numItems; i++) {
				for (int j = 0; j < numLatentFactors; j++) {
					itemMatrix.setEntry(i, j, Math.random());
				}
			}

			// Perform matrix factorization using ALS
			performAlternatingLeastSquares(interactions);

		}
	}

	private void performAlternatingLeastSquares(List<Interaction> interactions) {
		int numUsers = userIndices.size();
		int numItems = itemIndices.size();

		for (int iter = 0; iter < numIterations; iter++) {
			// Update user matrix
			for (Interaction interaction : interactions) {
				int userIdx = 0;
				int itemIdx = 0;

				if (interaction instanceof Rating) {
					userIdx = userIndices.get(((Rating) interaction).getUser().getId());
					itemIdx = itemIndices.get(((Rating) interaction).getProperty().getId());
				} else if (interaction instanceof PropertyReservation) {
					userIdx = userIndices.get(((PropertyReservation) interaction).getUser().getId());
					itemIdx = itemIndices.get(((PropertyReservation) interaction).getProperty().getId());
				} else { // SearchHistory
							// userIdx = userIndices.get(((SearchHistory) interaction).getUser().getId());
							// itemIdx = itemIndices.get(((SearchHistory)
							// interaction).getProperty().getId());
				}

				RealMatrix itemVec = itemMatrix.getRowMatrix(itemIdx).transpose();

				double[] userLatentFactors = userMatrix.getRow(userIdx);
				double predictedRating = dotProduct(userLatentFactors, itemVec.getColumn(0));
				double error;

				if (interaction instanceof Rating) {
					error = ((Rating) interaction).getRating() - predictedRating;
				} else { // Reservation or SearchHistory
					error = 1.0; // Assuming binary interaction (1 for interaction, 0 for no interaction)
				}

				for (int k = 0; k < numLatentFactors; k++) {
					double userFactor = userLatentFactors[k];
					double itemFactor = itemVec.getEntry(k, 0);

					double updatedUserFactor = userFactor
							+ learningRate * (2 * error * itemFactor - regularization * userFactor);
					userMatrix.setEntry(userIdx, k, updatedUserFactor);
				}
			}

			// Update item matrix
			for (Interaction interaction : interactions) {
				int userIdx = 0;
				int itemIdx = 0;

				if (interaction instanceof Rating) {
					userIdx = userIndices.get(((Rating) interaction).getUser().getId());
					itemIdx = itemIndices.get(((Rating) interaction).getProperty().getId());
				} else if (interaction instanceof PropertyReservation) {
					userIdx = userIndices.get(((PropertyReservation) interaction).getUser().getId());
					itemIdx = itemIndices.get(((PropertyReservation) interaction).getProperty().getId());
				} else { // SearchHistory
					// userIdx = userIndices.get(((SearchHistory) interaction).getUser().getId());
					// itemIdx = itemIndices.get(((SearchHistory)
					// interaction).getProperty().getId());
				}

				RealMatrix userVec = userMatrix.getRowMatrix(userIdx).transpose();

				double[] itemLatentFactors = itemMatrix.getRow(itemIdx);
				double predictedRating = dotProduct(userVec.getColumn(0), itemLatentFactors);
				double error;

				if (interaction instanceof Rating) {
					error = ((Rating) interaction).getRating() - predictedRating;
				} else { // Reservation or SearchHistory
					error = 1.0; // Assuming binary interaction (1 for interaction, 0 for no interaction)
				}

				for (int k = 0; k < numLatentFactors; k++) {
					double userFactor = userVec.getEntry(k, 0);
					double itemFactor = itemLatentFactors[k];

					double updatedItemFactor = itemFactor
							+ learningRate * (2 * error * userFactor - regularization * itemFactor);
					itemMatrix.setEntry(itemIdx, k, updatedItemFactor);
				}
			}
		}
	}

	// Other methods...

	private int getUserIndex(long userId) {
		logger.info("userId with checking user matrix with userId={} ", userId);
		return userIndices.getOrDefault(userId, -1);
	}

	private int getItemIndex(long itemId) {
		return itemIndices.getOrDefault(itemId, -1);
	}

	private double dotProduct(double[] vec1, double[] vec2) {
		double result = 0;
		for (int k = 0; k < numLatentFactors; k++) {
			result += vec1[k] * vec2[k];
		}
		return result;
	}

	private int getMaxIndex(double[] arr) {
		int maxIndex = -1;
		double maxValue = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > maxValue) {
				maxValue = arr[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private Long getItemIdFromIndex(int index) {
		return itemIndices.entrySet().stream().filter(entry -> entry.getValue() == index).map(Map.Entry::getKey)
				.findFirst().orElse(null);
	}

	public List<Property> getRecommendations(long userId, int numRecommendations) {
		int userIndex = getUserIndex(userId);
		if (userIndex < 0) {
			logger.info("User not found in the user matrix");
			return List.of(); // User not found in the user matrix
		}
		RealMatrix userVec = userMatrix.getRowMatrix(userIndex).transpose();
		double[] recommendationsScores = new double[itemMatrix.getRowDimension()];
		// Calculate recommendation scores
		for (int j = 0; j < itemMatrix.getRowDimension(); j++) {
			RealMatrix itemVec = itemMatrix.getRowMatrix(j).transpose();
			recommendationsScores[j] = dotProduct(userVec.getColumn(0), itemVec.getColumn(0));
		}
		// Sort the items based on scores
		List<Long> recommendations = new ArrayList<>();
		for (int i = 0; i < numRecommendations; i++) {
			int maxIndex = getMaxIndex(recommendationsScores);
			if (maxIndex >= 0) {
				recommendations.add(getItemIdFromIndex(maxIndex));
				recommendationsScores[maxIndex] = Double.NEGATIVE_INFINITY;
			}
		}
		logger.info("recommendations list of id values with size={}", recommendations.size());
		List<Property> propertyList = houseRepository.findByIdIn(recommendations);
		return propertyList;
	}
 
	public List<Property> getRandomPropertyList(List<Property> propertyList, long userId) {

		List<Long> recommendations = propertyList.stream().map(e -> e.getId()).collect(Collectors.toList());
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("user id not found in recommendation"));
		if (propertyList.size() < 6) {
			logger.info("The recommendations property list is ={}", propertyList.size(), " below 6,");
			List<SearchHistory> searchHistoryList = searchHistoryRepo.findByUserId(userId);

			SearchHistory searchHistory = null;
			if (!searchHistoryList.isEmpty()) {
				searchHistory = searchHistoryList.stream().findFirst().get();
			}
			if (searchHistory == null) {

				System.out.println("=========293==========");

				logger.info("The search history data is null and get matching userdata of property list");
				List<Property> propertyRandomList = houseRepository.findByCityOrStateOrCountry(user.getCity(),
						user.getState(), user.getCountry());

				System.out.println(user.getCity() + user.getState() + user.getCountry());

				logger.info("searchPropertyList before filtering with size={} ", propertyRandomList.size());
				propertyRandomList = propertyRandomList.stream().filter(e -> !recommendations.contains(e.getId()))
						.collect(Collectors.toList());
				logger.info("searchPropertyList after filtering with size={} ", propertyRandomList.size());

				propertyRandomList.addAll(propertyList);
				if (propertyRandomList.size() > 6) {
					return propertyRandomList.subList(0, 6);
				}

				if (propertyRandomList.isEmpty()) {

					List<Property> allpropertyList = houseRepository.findDistinctByOrderByOverAllRatingDesc();
					if (allpropertyList.size() > 6) {
						return allpropertyList.subList(0, 6);
					} else {
						return allpropertyList;
					}

				}

				return propertyRandomList;
			} else {
				System.out.println("=========325==========");
				logger.info("The search history data with get matching userdata of property list");

				if (searchHistory.getCity() != null || searchHistory.getCountry() != null
						|| searchHistory.getState() != null || searchHistory.getStartDate() != null
						|| searchHistory.getEndDate() != null) 
				{
					List<Property> searchPropertyList = new ArrayList<>();
					if (searchHistory.getCountry() != null && searchHistory.getState() != null
							&& searchHistory.getCity() != null) {
						searchPropertyList = houseRepository.findDistinctByCityAndStateAndCountry(searchHistory.getCity(),
								searchHistory.getState(), searchHistory.getCountry());
					} else if (searchHistory.getCountry() != null && searchHistory.getState() != null
							&& searchHistory.getCity() == null) {
						searchPropertyList = houseRepository.findDistinctByStateAndCountry(searchHistory.getState(),
								searchHistory.getCountry());
					} else if (searchHistory.getCountry() != null && searchHistory.getState() == null
							&& searchHistory.getCity() == null) {
						searchPropertyList = houseRepository.findDistinctByCountry(searchHistory.getCountry());
					}
					searchPropertyList.removeIf(Objects::isNull);
					logger.info("searchPropertyList before filtering with size={} ", searchPropertyList.size());
					searchPropertyList = searchPropertyList.stream().filter(e -> !recommendations.contains(e.getId()))
							.collect(Collectors.toList());
					logger.info("searchPropertyList after filtering with size={}", searchPropertyList.size());
					searchPropertyList.addAll(propertyList);
					logger.info("The search history data with get matching userdata of property list with size={}",
							searchPropertyList.size());

					if (searchPropertyList.size() < 6) {
						List<Property> allpropertyList = houseRepository.findDistinctByOrderByOverAllRatingDesc();
						List<Long> searchIdList = searchPropertyList.stream().map(e -> e.getId()).collect(Collectors.toList());
						allpropertyList = allpropertyList.stream().filter(e -> !searchIdList.contains(e.getId()))
								.collect(Collectors.toList());
						List<Property> newList = allpropertyList.subList(0, 6-searchPropertyList.size());
						searchPropertyList.addAll(newList);
						return searchPropertyList;
					}
					else if (searchPropertyList.size() > 6) {
						return searchPropertyList.subList(0, 6);
					}
					else if (searchPropertyList.size() == 0) {
						List<Property> allpropertyList = houseRepository.findDistinctByOrderByOverAllRatingDesc();
						if (allpropertyList.size() > 6) {
							return allpropertyList.subList(0, 6);
 
						}
						else
						{
							return allpropertyList;
						}
					}
					return searchPropertyList;
				} else {
					System.out.println("=========362==========");
					List<Property> allpropertyList = houseRepository.findDistinctByOrderByOverAllRatingDesc();
					if (allpropertyList.size() > 6) {
						return allpropertyList.subList(0, 6);
					} else {
						return allpropertyList;
					}
				}
			}
		}
		return propertyList;
	}
}
