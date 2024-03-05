package persistence;

import model.Reviews;
import model.Restaurant;
import model.Food;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// References JsonSerializationDemo's JsonReader class
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Restaurants from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Reviews read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReviews(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses reviews from JSON object and returns it
    private Reviews parseReviews(JSONObject jsonObject) {
        Reviews reviews = new Reviews();
        addAllRestaurants(reviews, jsonObject);
        addLikedRestaurants(reviews, jsonObject);
        addDislikedRestaurants(reviews, jsonObject);
        return reviews;
    }

    // MODIFIES: reviews
    // EFFECTS: parses all restaurants from JSON object and adds them to Reviews
    private void addAllRestaurants(Reviews reviews, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allRestaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(reviews, nextRestaurant);
        }
    }

    // MODIFIES: reviews
    // EFFECTS: parses liked restaurants from JSON object and adds them to Reviews
    private void addLikedRestaurants(Reviews reviews, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("likedRestaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            copyToLikedDisliked(reviews, nextRestaurant);
        }
    }

    // MODIFIES: reviews
    // EFFECTS: parses disliked restaurants from JSON object and adds them to Reviews
    private void addDislikedRestaurants(Reviews reviews, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("dislikedRestaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            copyToLikedDisliked(reviews, nextRestaurant);
        }
    }

    // MODIFIES: reviews
    // EFFECTS: parses restaurant from JSON object and adds it to Reviews
    private void addRestaurant(Reviews reviews, JSONObject jsonObject) {
        String name = jsonObject.getString("restaurantName");
        Boolean isLiked = jsonObject.getBoolean("isLiked");
        Restaurant restaurant = new Restaurant(name, isLiked);
        addTriedFoods(restaurant, jsonObject);
        addWishListFoods(restaurant, jsonObject);
        int restaurantRating = jsonObject.getInt("restaurantRating");
        int reviewNumber = jsonObject.getInt("reviewNumber");
        restaurant.setReviewNumber(reviewNumber);
        restaurant.setRating(restaurantRating);
        reviews.addToAll(restaurant);
    }

    // MODIFIES: reviews
    // EFFECTS: finds restaurant from all reviews and adds to liked or disliked reviews
    private boolean copyToLikedDisliked(Reviews reviews, JSONObject jsonObject) {
        int reviewNumber = jsonObject.getInt("reviewNumber");
        for (Restaurant r : reviews.getAllReviews()) {
            if (r.getReviewNumber() == reviewNumber) {
                return reviews.addToLikedDisliked(r);
            }
        }
        return false;
    }

    private void addTriedFoods(Restaurant restaurant, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("triedFoods");
        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            addFood(restaurant, nextFood);
        }
    }

    private void addWishListFoods(Restaurant restaurant, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("wishList");
        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            addFood(restaurant, nextFood);
        }
    }

    private void addFood(Restaurant restaurant, JSONObject jsonObject) {
        String name = jsonObject.getString("FoodName");
        double price = jsonObject.getDouble("price");
        double rating = jsonObject.getDouble("foodRating");
        Boolean isTried = jsonObject.getBoolean("isTried");
        Food food = new Food(name, price, isTried);
        food.setRating(rating);
        restaurant.addFoodToFoodList(food);
    }
}

