package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantListTest {

    private Restaurants testReviews;
    private Restaurant liked1;
    private Restaurant liked2;
    private Restaurant disliked3;
    private Restaurant disliked4;
    private Restaurant disliked5;

    @BeforeEach
    public void runBefore() {
        testReviews = new Restaurants();
        liked1 = new Restaurant("Restaurant1", true);
        liked2 = new Restaurant("Restaurant2", true);
        disliked3 = new Restaurant("Restaurant3", false);
        disliked4 = new Restaurant("Restaurant4", false);
        disliked5 = new Restaurant("Restaurant5", false);
    }

    @Test
    public void addLikedRestaurantTest() {
        testReviews.addRestaurant(liked1);
        assertEquals(1, testReviews.getLikedRestaurants().size());
        assertEquals(0, testReviews.getDislikedRestaurants().size());
        assertEquals(1, testReviews.getNumRestaurants());
    }

    @Test
    public void addDislikedRestaurantTest() {
        testReviews.addRestaurant(disliked3);
        assertEquals(0, testReviews.getLikedRestaurants().size());
        assertEquals(1, testReviews.getDislikedRestaurants().size());
        assertEquals(1, testReviews.getNumRestaurants());
    }

    @Test
    public void add2LikedRestaurantsTest() {
        testReviews.addRestaurant(liked1);
        testReviews.addRestaurant(liked2);
        assertEquals(2, testReviews.getLikedRestaurants().size());
        assertEquals(2, testReviews.getNumRestaurants());
    }

    @Test
    public void remove1Restaurant() {
        testReviews.addRestaurant(liked1);
        testReviews.removeRestaurant(liked1);
        assertEquals(0, testReviews.getNumRestaurants());
    }
}
