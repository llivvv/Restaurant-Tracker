package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of reviews
public class Reviews {

    protected List<Restaurant> reviews;

    public Reviews() {
        reviews = new ArrayList<Restaurant>();
    }

    // MODIFIES: this
    // EFFECTS: if restaurant has not yet been added, then
    //          adds a restaurant review to reviews
    public void addRestaurant(Restaurant restaurant) {
        if (!reviews.contains(restaurant)) {
            reviews.add(restaurant);
            findSetRestaurantReviewNumber(restaurant);
        }
    }

    // MODIFIES: this
    // EFFECTS: if review is found, removes restaurant review from reviews
    public void removeRestaurant(Restaurant restaurant) {
        if (reviews.contains(restaurant)) {
            reviews.remove(restaurant);
        }
    }

    // MODIFIES: Restaurant
    // EFFECTS: gives the restaurant a review number based on when it was created
    public void findSetRestaurantReviewNumber(Restaurant restaurant) {
        int max = 0;
        for (Restaurant r : reviews) {
            if (r.getReviewNumber() > max) {
                max = r.getReviewNumber();
            }
        }
        restaurant.setReviewNumber(max + 1);
    }

    // EFFECTS: finds and returns a restaurant review with the corresponding name, otherwise returns null
    public Restaurant findRestaurant(String restaurantName) {
        for (Restaurant r : reviews) {
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
}
