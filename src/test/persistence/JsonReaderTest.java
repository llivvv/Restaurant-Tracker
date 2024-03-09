package persistence;

import model.Food;
import model.Reviews;
import model.Restaurant;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// References JsonSerializationDemo
// references https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Reviews rs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyReviews() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyReviews.json");
        try {
            Reviews rs = reader.read();
            assertEquals(0, rs.getAllReviews().size());
            assertEquals(0, rs.getLikedReviews().size());
            assertEquals(0, rs.getDislikedReviews().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralReviews() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralReviews.json");
        try {
            Reviews rs = reader.read();
            List<Restaurant> allR = rs.getAllReviews();
            assertEquals(3, allR.size());
            List<Restaurant> LikedR = rs.getLikedReviews();
            assertEquals(2, LikedR.size());
            List<Restaurant> dLikedR = rs.getDislikedReviews();
            assertEquals(1, dLikedR.size());
            checkRestaurant("cafe", true, 0, 0, 0, 1, allR.get(0));
            checkRestaurant("cafe", true, 0, 0, 0, 1, LikedR.get(0));
            checkRestaurant("ChaTime", true, 2, 1, 5, 2, allR.get(1));
            checkRestaurant("ChaTime",true, 2, 1, 5, 2, LikedR.get(1));
            checkRestaurant("abc", false, 1, 0, 1, 3, allR.get(2));
            checkRestaurant("abc", false, 1, 0, 1, 3, dLikedR.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
