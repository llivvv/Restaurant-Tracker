package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

// Represents 3 lists of restaurants: all restaurants, liked restaurants, disliked restaurants
// References JsonSerializationDemo (WorkRoom.toJson(), WorkRoom.thingiesToJson())
public class Reviews implements Writable {

    private ArrayList<Restaurant> allReviews;
    private ArrayList<Restaurant> likedReviews;
    private ArrayList<Restaurant> dislikedReviews;

    // EFFECTS: constructs 3 lists: all restaurants, liked restaurants and disliked restaurants
    public Reviews() {
        allReviews = new ArrayList<Restaurant>();
        likedReviews = new ArrayList<Restaurant>();
        dislikedReviews = new ArrayList<Restaurant>();
    }

    // MODIFIES: this
    // EFFECTS: if restaurant has not yet been added, then
    //          adds a restaurant review to the allRestaurant list
    //          also then adds restaurant review to liked restaurants if it is liked;
    //          restaurant is added to disliked restaurants list if it is disliked
    public void addRestaurant(Restaurant restaurant) {
        findSetRestaurantReviewNumber(restaurant);
        if (!allReviews.contains(restaurant)) {
            allReviews.add(restaurant);
            if (restaurant.getIsLiked()) {
                likedReviews.add(restaurant);
            } else {
                dislikedReviews.add(restaurant);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the restaurant review from the lists it is contained in
    //          returns the review if it existed in a list, otherwise returns null
    public Restaurant removeRestaurant(Restaurant restaurant) {
        if (allReviews.contains(restaurant) && likedReviews.contains(restaurant)) {
            likedReviews.remove(likedReviews.indexOf(restaurant));
            return allReviews.remove(allReviews.indexOf(restaurant));
        } else if (allReviews.contains(restaurant) && dislikedReviews.contains(restaurant)) {
            dislikedReviews.remove(dislikedReviews.indexOf(restaurant));
            return allReviews.remove(allReviews.indexOf(restaurant));
        } else {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts allRestaurants based on rating (ascending order)
    public void sortAllRestaurantsRating() {
        Collections.sort(allReviews, new RestaurantRatingComparator());
    }

    // MODIFIES: this
    // EFFECTS: sorts likedRestaurants based on rating (ascending order)
    public void sortLikedRestaurantsRating() {
        Collections.sort(likedReviews, new RestaurantRatingComparator());
    }

    // MODIFIES: this
    // EFFECTS: sorts dislikedRestaurants based on rating (ascending order)
    public void sortDislikedRestaurantsRating() {
        Collections.sort(dislikedReviews, new RestaurantRatingComparator());
    }

    // MODIFIES: this
    // EFFECTS: sorts allRestaurants by descending review number
    public void sortAllRestaurantsNumber() {
        Collections.sort(allReviews, new RestaurantNumberComparator());
        Collections.reverse(allReviews);
    }

    // MODIFIES: this
    // EFFECTS: sorts likedRestaurants by descending review number
    public void sortLikedRestaurantsNumber() {
        Collections.sort(likedReviews, new RestaurantNumberComparator());
        Collections.reverse(likedReviews);
    }

    // MODIFIES: this
    // EFFECTS: sorts likedRestaurants by descending review number
    public void sortDislikedRestaurantsNumber() {
        Collections.sort(dislikedReviews, new RestaurantNumberComparator());
        Collections.reverse(dislikedReviews);
    }

    // EFFECTS: finds and returns a restaurant review with the corresponding name, otherwise returns null
    public Restaurant findRestaurant(String restaurantName) {
        for (Restaurant r : allReviews) {
            if (restaurantName.equals(r.getRestaurantName())) {
                return r;
            }
        }
        return null;
    }

    // MODIFIES: Restaurant
    // EFFECTS: sets a new name for the restaurant review if there is no other review with the same name
    //          if desired name was the same as its original name or the name was successfully changed,
    //          returns true; returns false if the name was not successfully changed
    public boolean checkandSetNewRname(String restaurantName, Restaurant restaurant) {
        if (restaurantName.equals(restaurant.getRestaurantName())) {
            return true;
        } else if (findRestaurant(restaurantName) == null) {
            restaurant.setRestaurantName(restaurantName);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: Restaurant
    // EFFECTS: gives the restaurant a review number based on when it was created
    public void findSetRestaurantReviewNumber(Restaurant restaurant) {
        int max = 0;
        for (Restaurant r : allReviews) {
            if (r.getReviewNumber() > max) {
                max = r.getReviewNumber();
            }
        }
        restaurant.setReviewNumber(max + 1);
    }

    // EFFECTS: returns the list of all restaurants
    public ArrayList<Restaurant> getAllReviews() {
        return allReviews;
    }

    // EFFECTS: returns the list of liked restaurants
    public ArrayList<Restaurant> getLikedReviews() {
        return likedReviews;
    }

    // EFFECTS: returns the list of disliked restaurants
    public ArrayList<Restaurant> getDislikedReviews() {
        return dislikedReviews;
    }

    // EFFECTS: returns size of allRestaurants list
    public int getNumRestaurants() {
        return allReviews.size();
    }

    // referenced JsonSerializationDemo.WorkRoom.toJson()
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("allRestaurants", allRestaurantsToJson());
        json.put("likedRestaurants", likedRestaurantsToJson());
        json.put("dislikedRestaurants", dislikedRestaurantsToJson());
        return json;
    }

    // referenced JsonSerializationDemo.WorkRoom.thingiesToJson()
    // EFFECTS: returns all restaurants in this Restaurants as a JSON array
    private JSONArray allRestaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Restaurant r : allReviews) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }

    // referenced JsonSerializationDemo.WorkRoom.thingiesToJson()
    // EFFECTS: returns liked restaurants in this Restaurants as a JSON array
    private JSONArray likedRestaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Restaurant r : likedReviews) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }

    // referenced JsonSerializationDemo.WorkRoom.thingiesToJson()
    // EFFECTS: returns disliked restaurants in this Restaurants as a JSON array
    private JSONArray dislikedRestaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Restaurant r : dislikedReviews) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}
