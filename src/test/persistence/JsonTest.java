package persistence;

import model.Food;
import model.Restaurant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    // numT represents number of tried foods
    // numW represents number of wishList items
    protected void checkRestaurant(String name,
                                   boolean liked,
                                   int numT,
                                   int numW,
                                   int rating,
                                   int reviewNum,
                                   Restaurant r) {
        assertEquals(name, r.getRestaurantName());
        assertEquals(liked, r.getIsLiked());
        assertEquals(numT, r.getTriedFoods().size());
        assertEquals(numW, r.getWishList().size());
        assertEquals(rating, r.getRating());
        assertEquals(reviewNum, r.getReviewNumber());
    }
}
