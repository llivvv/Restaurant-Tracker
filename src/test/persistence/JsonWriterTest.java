package persistence;

import model.Reviews;
import model.Food;
import model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// References JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;
    private Food tf1r2;
    private Food tf2r2;
    private Food w1r2;
    private Food tf1r3;


    @BeforeEach
    void runBefore() {
        r1 = new Restaurant("cafffe", true);
        r2 = new Restaurant("ShinyTea", false);
        r3 = new Restaurant("fghijk", false);
        tf1r2 = new Food("Roasted Milk Tea", 6.5, true);
        tf2r2 = new Food("Milk Tea", 1, true);
        w1r2 = new Food("Fruit Tea", 0, false);
        tf1r3 = new Food("melonBitter", 22, true);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Reviews rs = new Reviews();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyReviews() {
        try {
            Reviews rs = new Reviews();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyReviews.json");
            writer.open();
            writer.write(rs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyReviews.json");
            rs = reader.read();
            assertEquals(0, rs.getAllReviews().size());
            assertEquals(0, rs.getLikedReviews().size());
            assertEquals(0, rs.getDislikedReviews().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // METHOD TOO LONG? (34 lines)
    @Test
    void testWriterGeneralWorkroom() {
        try {
            Reviews rs = new Reviews();
            r1.createRating();
            rs.addRestaurant(r1);
            r2.addFoodToFoodList(tf1r2);
            tf1r2.setRating(0.2);
            r2.addFoodToFoodList(tf2r2);
            tf2r2.setRating(4.0);
            r2.addFoodToFoodList(w1r2);
            r2.createRating();
            rs.addRestaurant(r2);
            r3.addFoodToFoodList(tf1r3);
            tf1r3.setRating(1.1);
            r3.createRating();
            rs.addRestaurant(r3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralReviews.json");
            writer.open();
            writer.write(rs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralReviews.json");
            rs = reader.read();
            assertEquals(3, rs.getAllReviews().size());
            assertEquals(1, rs.getLikedReviews().size());
            assertEquals(2, rs.getDislikedReviews().size());
            checkRestaurant("cafffe", true, 0, 0, 0, 1,
                    rs.getLikedReviews().get(0));
            checkRestaurant("ShinyTea", false, 2, 1, 2, 2,
                    rs.getDislikedReviews().get(0));
            checkRestaurant("fghijk", false, 1, 0, 1, 3,
                    rs.getDislikedReviews().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
