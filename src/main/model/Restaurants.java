package model;

import java.util.ArrayList;
import java.util.Collections;

// Represents 3 lists of restaurants: all restaurants, liked restaurants, disliked restaurants
public class Restaurants {

    private ArrayList<Restaurant> allRestaurants;
    private ArrayList<Restaurant> likedRestaurants;
    private ArrayList<Restaurant> dislikedRestaurants;

    // EFFECTS: constructs a list of all restaurants, liked restaurants and disliked restaurants
    public Restaurants() {
        allRestaurants = new ArrayList<Restaurant>();
        likedRestaurants = new ArrayList<Restaurant>();
        dislikedRestaurants = new ArrayList<Restaurant>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a restaurant review to the entire restaurant list and liked or disliked restaurants
    //          if it hasn't already been added
    public void addRestaurant(Restaurant restaurant) {
        if (!allRestaurants.contains(restaurant)) {
            allRestaurants.add(restaurant);
            if (restaurant.getIsLiked()) {
                likedRestaurants.add(restaurant);
            } else {
                dislikedRestaurants.add(restaurant);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the restaurant review from the lists it is contained in
    //          returns the review if it existed in a list, otherwise returns null
    public Restaurant removeRestaurant(Restaurant restaurant) {
        if (allRestaurants.contains(restaurant) && likedRestaurants.contains(restaurant)) {
            likedRestaurants.remove(likedRestaurants.indexOf(restaurant));
            return allRestaurants.remove(allRestaurants.indexOf(restaurant));
        } else if (allRestaurants.contains(restaurant) && dislikedRestaurants.contains(restaurant)) {
            dislikedRestaurants.remove(dislikedRestaurants.indexOf(restaurant));
            return allRestaurants.remove(allRestaurants.indexOf(restaurant));
        } else {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts allRestaurants based on rating
    public void sortAllRestaurants() {
        Collections.sort(allRestaurants, new RestaurantRatingComparator());
    }

    // MODIFIES: this
    // EFFECTS: sorts likedRestaurants based on rating
    public void sortLikedRestaurants() {
        Collections.sort(likedRestaurants, new RestaurantRatingComparator());
    }

    // MODIFIES: this
    // EFFECTS: sorts dislikedRestaurants based on rating
    public void sortDislikedRestaurants() {
        Collections.sort(dislikedRestaurants, new RestaurantRatingComparator());
    }

    // EFFECTS: finds and returns restaurant in the list of restaurant given its name, otherwise returns null
    public Restaurant findRestaurant(String restaurantName) {
        for (Restaurant r : allRestaurants) {
            if (restaurantName.equals(r.getRestaurantName())) {
                return r;
            }
        }
        return null;
    }

    // MODIFIES: Restaurant
    // EFFECTS: sets a new name for the restaurant review if there is no other review with the same name
    //          returns true if name was changed, false if not changed
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

    // EFFECTS: returns the list of all restaurants
    public ArrayList<Restaurant> getAllRestaurants() {
        return allRestaurants;
    }

    // EFFECTS: returns the list of liked restaurants
    public ArrayList<Restaurant> getLikedRestaurants() {
        return likedRestaurants;
    }

    // EFFECTS: returns the list of disliked restaurants
    public ArrayList<Restaurant> getDislikedRestaurants() {
        return dislikedRestaurants;
    }

    // EFFECTS: returns size of allRestaurants list
    public int getNumRestaurants() {
        return allRestaurants.size();
    }
}
