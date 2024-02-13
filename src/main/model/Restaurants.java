package model;

import java.util.ArrayList;

//Represents 3 lists of restaurants: all, liked, disliked restaurants

public class Restaurants {

    private ArrayList<Restaurant> allRestaurants;
    private ArrayList<Restaurant> likedRestaurants;
    private ArrayList<Restaurant> dislikedRestaurants;

    public Restaurants() {
        allRestaurants = new ArrayList<Restaurant>();
        likedRestaurants = new ArrayList<Restaurant>();
        dislikedRestaurants = new ArrayList<Restaurant>();
    }

    // Adds a restaurant entry to all restaurants and either Liked or Disliked Restaurants
    public void addRestaurant(Restaurant restaurant) {
        allRestaurants.add(restaurant);
        if (restaurant.getIsLiked()) {
            addtoLikedRestaurants(restaurant);
        } else {
            addtoDislikedRestaurants(restaurant);
        }
    }

    public void addtoLikedRestaurants(Restaurant restaurant) {
        likedRestaurants.add(restaurant);
    }

    public void addtoDislikedRestaurants(Restaurant restaurant) {
        dislikedRestaurants.add(restaurant);
    }

    // REQUIRES: the restaurant exists in the allRestaurants list
    // EFFECTS: deletes the restaurant review
    // the user will be selecting the restaurant (not searching its name) to select it
    public void removeRestaurant(Restaurant restaurant) {
        allRestaurants.remove(restaurant);
        if (restaurant.getIsLiked()) {
            likedRestaurants.remove(restaurant);
        } else {
            dislikedRestaurants.remove(restaurant);
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
}
