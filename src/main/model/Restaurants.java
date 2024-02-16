package model;

import java.util.ArrayList;
import java.util.Collections;

//Represents 3 lists of restaurants which have been reviewed

public class Restaurants {

    private ArrayList<Restaurant> allRestaurants;
    private ArrayList<Restaurant> likedRestaurants;
    private ArrayList<Restaurant> dislikedRestaurants;

    public Restaurants() {
        allRestaurants = new ArrayList<Restaurant>();
        likedRestaurants = new ArrayList<Restaurant>();
        dislikedRestaurants = new ArrayList<Restaurant>();
    }

    // Adds a restaurant entry to the restaurants
    public void addRestaurant(Restaurant restaurant) {
        if (!allRestaurants.contains(restaurant)) {
            allRestaurants.add(restaurant);
        }
        if (restaurant.getIsLiked()) {
            likedRestaurants.add(restaurant);
        } else {
            dislikedRestaurants.add(restaurant);
        }
    }

    // REQUIRES: the restaurant exists in the allRestaurants list
    // MODIFIES: this
    // EFFECTS: deletes the restaurant review from the lists it is contained in
    // Removes the restaurant and returns it
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

    // EFFECTS: sorts allRestaurants based on rating
    public void sortAllRestaurants() {
        Collections.sort(allRestaurants, new RestaurantRatingComparator());
    }

    // EFFECTS: sorts likedRestaurants based on rating
    public void sortLikedRestaurants() {
        Collections.sort(likedRestaurants, new RestaurantRatingComparator());
    }

    // EFFECTS: sorts dislikedRestaurants based on rating
    public void sortDislikedRestaurants() {
        Collections.sort(dislikedRestaurants, new RestaurantRatingComparator());
    }

    public Restaurant findRestaurant(String restaurantName) {
        for (Restaurant r : allRestaurants) {
            if (restaurantName.equals(r.getRestaurantName())) {
                return r;
            }
        }
        return null;
    }

    public boolean checkandSetNewRname(String restaurantName, Restaurant restaurant) {
        if (findRestaurant(restaurantName) == null) {
            restaurant.setRestaurantName(restaurantName);
            return true;
        } else {
            return false;
        }
    }

    // getters
    public ArrayList<Restaurant> getAllRestaurants() {
        return allRestaurants;
    }

    public ArrayList<Restaurant> getLikedRestaurants() {
        return likedRestaurants;
    }

    public ArrayList<Restaurant> getDislikedRestaurants() {
        return dislikedRestaurants;
    }

    // EFFECTS: returns size of allRestaurants list
    public int getNumRestaurants() {
        return allRestaurants.size();
    }
}
