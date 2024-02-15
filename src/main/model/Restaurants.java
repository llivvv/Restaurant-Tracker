package model;

import java.util.ArrayList;
import java.util.Collections;

//Represents a list of restaurants which have been reviewed

public class Restaurants {

    private ArrayList<Restaurant> restaurants;

    public Restaurants() {
        restaurants = new ArrayList<Restaurant>();
    }

    // Adds a restaurant entry to the restaurants
    public void addRestaurant(Restaurant restaurant) {
        if (!restaurants.contains(restaurant)) {
            restaurants.add(restaurant);
        }
    }

    // REQUIRES: the restaurant exists in the allRestaurants list
    // MODIFIES: this
    // EFFECTS: deletes the restaurant review
    // the user will be selecting the restaurant (not searching its name) to select it
    public void removeRestaurant(Restaurant restaurant) {
        if (restaurants.contains(restaurant)) {
            restaurants.remove(restaurant);
        }
    }

    // sorts the restaurants in the list based on rating (ascending order)
    public void sortByRating() {
        Collections.sort(restaurants, new RestaurantRatingComparator());
        System.out.println(restaurants);
    }

    public Restaurant findRestaurant(String restaurantName) {
        for (Restaurant r : restaurants) {
            if (restaurantName.equals(r.getRestaurantName())) {
                return r;
            }
        }
        return null;
    }

    public boolean checkNoDuplicateNames(String restaurantName) {
        if (findRestaurant(restaurantName) == null) {
            return true;
        } else {
            return false;
        }
    }

    // getters
    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public int getNumRestaurants() {
        return restaurants.size();
    }
}
