package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }
}
