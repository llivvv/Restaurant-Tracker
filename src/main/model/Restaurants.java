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
    // Removes the restaurant and returns it
    public Restaurant removeRestaurant(Restaurant restaurant) {
        if (restaurants.contains(restaurant)) {
            restaurants.remove(restaurants.indexOf(restaurant));
        }
        return null;
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

    public boolean checkandSetNewRname(String restaurantName) {
        Restaurant selected = removeRestaurant(findRestaurant(restaurantName));
        if (findRestaurant(restaurantName) == null) {
            addRestaurant(selected);
            selected.setRestaurantName(restaurantName);
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
