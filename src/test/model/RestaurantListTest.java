package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantListTest {

    private Restaurants testReviews;
    private Restaurant liked1;
    private Restaurant liked2;
    private Restaurant disliked3;
    private Restaurant disliked4;
    private Restaurant disliked5;
    private Food triedFood1;
    private Food triedFood2;
    private Food triedFood3;
    private Food triedFood4;

    @BeforeEach
    public void runBefore() {
        testReviews = new Restaurants();
        liked1 = new Restaurant("Restaurant1", true);
        liked2 = new Restaurant("Restaurant2", true);
        disliked3 = new Restaurant("Restaurant3", false);
        disliked4 = new Restaurant("Restaurant4", false);
        disliked5 = new Restaurant("Restaurant5", false);
        triedFood1 = new Food("Food1", 2.5, true);
        triedFood2 = new Food("Food2", 3.5, true);
        triedFood3 = new Food("Food3", 0.2, true);
        triedFood4 = new Food("Food4", 3, true);
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
    public void addAlreadyAddedRestaurantTest() {
        testReviews.addRestaurant(liked1);
        testReviews.addRestaurant(liked2);
        assertEquals(2, testReviews.getNumRestaurants());
        testReviews.addRestaurant(liked1);
        assertEquals(2, testReviews.getNumRestaurants());
    }

    @Test
    public void remove1Restaurant() {
        testReviews.addRestaurant(liked1);
        assertEquals(liked1, testReviews.removeRestaurant(liked1));
        testReviews.removeRestaurant(liked1);
        assertEquals(0, testReviews.getNumRestaurants());
    }

    @Test
    public void remove2Restaurant() {
        testReviews.addRestaurant(liked1);
        testReviews.addRestaurant(disliked3);
        assertEquals(disliked3, testReviews.removeRestaurant(disliked3));
        testReviews.removeRestaurant(disliked3);
        assertEquals(null, testReviews.findRestaurant("Restaurant3"));
        assertEquals(1, testReviews.getLikedRestaurants().size());
        assertEquals(0, testReviews.getDislikedRestaurants().size());
        assertEquals(1, testReviews.getNumRestaurants());
    }

    @Test
    public void removeNonExistingRestaurant() {
        assertEquals(null, testReviews.removeRestaurant(liked1));
    }

    @Test
    public void find1RestaurantTest() {
        testReviews.addRestaurant(liked1);
        testReviews.addRestaurant(disliked3);
        assertEquals(disliked3, testReviews.findRestaurant("Restaurant3"));
    }

    @Test
    public void find2RestaurantTest() {
        testReviews.addRestaurant(disliked3);
        testReviews.addRestaurant(liked2);
        testReviews.addRestaurant(disliked5);
        assertEquals(disliked5, testReviews.findRestaurant("Restaurant5"));
        assertEquals(disliked3, testReviews.findRestaurant("Restaurant3"));
    }

    @Test
    public void findNonExistentRestaurantTest() {
        testReviews.addRestaurant(liked2);
        testReviews.addRestaurant(disliked5);
        assertEquals(null, testReviews.findRestaurant("Restaurant3"));
    }

    @Test
    public void checkAndSetNameTest() {
        testReviews.addRestaurant(liked1);
        testReviews.addRestaurant(liked2);
        testReviews.checkandSetNewRname("Restaurant1", liked1);
        assertEquals("Restaurant1", liked1.getRestaurantName());
    }

    @Test
    public void checkAndSetNameUniqueTest() {
        testReviews.addRestaurant(liked1);
        testReviews.addRestaurant(liked2);
        assertTrue(testReviews.checkandSetNewRname("New1", liked1));
        testReviews.checkandSetNewRname("New1", liked1);
        assertEquals("New1", liked1.getRestaurantName());
    }

    @Test
    public void notUniqueRestaurantNameTest() {
        testReviews.addRestaurant(liked1);
        testReviews.addRestaurant(disliked3);
        assertFalse(testReviews.checkandSetNewRname("Restaurant3", liked1));
        testReviews.checkandSetNewRname("Restaurant3", liked1);
        assertEquals("Restaurant1", liked1.getRestaurantName());
    }

    @Test
    public void sortAllRatingNotInOrderTest() {
        triedFood1.setRating(2);
        triedFood2.setRating(3);
        triedFood3.setRating(0.2);
        triedFood4.setRating(1);
        testReviews.addRestaurant(liked1);
        liked1.addFoodToFoodList(triedFood1);
        liked1.addFoodToFoodList(triedFood2);
        liked1.createRating();
        testReviews.addRestaurant(liked2);
        liked2.addFoodToFoodList(triedFood3);
        liked2.addFoodToFoodList(triedFood4);
        liked2.createRating();
        assertEquals(0, testReviews.getAllRestaurants().indexOf(liked1));
        assertEquals(1, testReviews.getAllRestaurants().indexOf(liked2));
        testReviews.sortAllRestaurants();
        assertEquals(1, testReviews.getAllRestaurants().indexOf(liked1));
    }

    @Test
    public void sortAllRatingInOrderTest() {
        triedFood1.setRating(2);
        triedFood2.setRating(3);
        triedFood3.setRating(0.2);
        triedFood4.setRating(1);
        testReviews.addRestaurant(liked2);
        liked2.addFoodToFoodList(triedFood3);
        liked2.addFoodToFoodList(triedFood4);
        liked2.createRating();
        testReviews.addRestaurant(liked1);
        liked1.addFoodToFoodList(triedFood1);
        liked1.addFoodToFoodList(triedFood2);
        liked1.createRating();
        assertEquals(1, testReviews.getAllRestaurants().indexOf(liked1));
        assertEquals(0, testReviews.getAllRestaurants().indexOf(liked2));
        testReviews.sortAllRestaurants();
        assertEquals(1, testReviews.getAllRestaurants().indexOf(liked1));
    }

    @Test
    public void sortDislikedNotInOrderTest() {
        triedFood1.setRating(2);
        triedFood2.setRating(3);
        triedFood3.setRating(0.2);
        triedFood4.setRating(1);
        testReviews.addRestaurant(disliked3);
        disliked3.addFoodToFoodList(triedFood1);
        disliked3.addFoodToFoodList(triedFood2);
        disliked3.createRating();
        testReviews.addRestaurant(disliked4);
        disliked4.addFoodToFoodList(triedFood3);
        disliked4.addFoodToFoodList(triedFood4);
        disliked4.createRating();
        assertEquals(0, testReviews.getDislikedRestaurants().indexOf(disliked3));
        assertEquals(1, testReviews.getDislikedRestaurants().indexOf(disliked4));
        testReviews.sortDislikedRestaurants();
        assertEquals(0, testReviews.getDislikedRestaurants().indexOf(disliked4));
    }

    @Test
    public void sortLikedNotInOrderTest() {
        triedFood1.setRating(2);
        triedFood2.setRating(3);
        triedFood3.setRating(0.2);
        triedFood4.setRating(1);
        testReviews.addRestaurant(liked1);
        liked1.addFoodToFoodList(triedFood1);
        liked1.addFoodToFoodList(triedFood2);
        liked1.createRating();
        testReviews.addRestaurant(liked2);
        liked2.addFoodToFoodList(triedFood3);
        liked2.addFoodToFoodList(triedFood4);
        liked2.createRating();
        assertEquals(0, testReviews.getAllRestaurants().indexOf(liked1));
        assertEquals(1, testReviews.getAllRestaurants().indexOf(liked2));
        testReviews.sortLikedRestaurants();
        assertEquals(1, testReviews.getLikedRestaurants().indexOf(liked1));
        assertEquals(0, testReviews.getAllRestaurants().indexOf(liked1));
    }
}
