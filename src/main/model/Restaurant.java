package model;

import java.util.ArrayList;

// Represents a restaurant with a name, liked/disliked, liked items, disliked items, wishlist items
// and number of times its review has been viewed
public class Restaurant {

    private String name;
    private boolean isLiked;
    private ArrayList<Food> triedFoods;
    private ArrayList<Food> wishList;
    private int views;
    private double rating;

    // EFFECTS: creates a new restaurant review with no foods, initial 0 rating, 0 views
    public Restaurant(String name, boolean opinion) {
        this.name = name;
        this.isLiked = opinion;
        this.views = 0;
    }

    // REQUIRES: item is not already in triedFoods nor wishList
    // MODIFIES: this, Food
    // EFFECTS: adds food item to triedFoods, and sets item to tried
    public void addItemToTriedFoods(Food food) {
        triedFoods.add(food);
    }

    // REQUIRES: item is not already in triedFoods nor wishList
    // MODIFIES: this, Food
    // EFFECTS: adds food item to wishlist
    public void addItemToWishList(Food food) {
        wishList.add(food);
    }

    public ArrayList<Food> getTriedFoods() {
        return triedFoods;
    }

    public ArrayList<Food> getWishList() {
        return wishList;
    }

    // REQUIRES: there is at least 1 item in triedFoods or wishList
    // MODIFIES: this
    // EFFECTS: creates a rating for the restaurant by averaging out the food ratings
    public void createRating() {
        double sumRating = 0;
        for (Food f : triedFoods) {
            sumRating = f.getRating() + sumRating;
        }
        this.rating = sumRating/(triedFoods.size());
    }
}
