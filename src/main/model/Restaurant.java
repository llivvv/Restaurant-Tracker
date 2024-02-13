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
        triedFoods = new ArrayList<Food>();
        wishList = new ArrayList<Food>();
    }

    // REQUIRES: item is not already in triedFoods nor wishList
    // MODIFIES: this, Food
    // EFFECTS: if item isTried, adds item to triedFoods, otherwise adds it to wishList
    public void addFoodToFoodList(Food food) {
        if (food.getisTried()) {
            triedFoods.add(food);
        } else {
            wishList.add(food);
        }
    }

    // REQUIRES: item is in wishlist
    // MODIFIES: this
    // EFFECTS: removes item from wishList and adds it to end of triedFoods
    public void changeToTriedFoods(Food food) {
        if (!triedFoods.contains(food)) {
            wishList.remove(food);
            triedFoods.add(food);
        }
    }

    // REQUIRES: there is at least 1 item in triedFoods
    // MODIFIES: this
    // EFFECTS: creates a rating for the restaurant by averaging out the food ratings
    public void createRating() {
        double sumRating = 0;
        for (Food f : triedFoods) {
            sumRating = f.getRating() + sumRating;
        }
        this.rating = sumRating / (triedFoods.size());
    }

    // getter: returns whether restaurant is liked
    public boolean getIsLiked() {
        return isLiked;
    }

    // getter
    public double getRating() {
        return rating;
    }

    // getters: returns the lists of foods
    public ArrayList<Food> getTriedFoods() {
        return triedFoods;
    }

    public ArrayList<Food> getWishList() {
        return wishList;
    }

    // getters: returns the food item when its name is entered
    public Food getFoodFromList(String name) {
        for (Food f : triedFoods) {
            if (f.getName() == name) {
                return f;
            }
        }
        for (Food f: wishList) {
            if (f.getName() == name) {
                return f;
            }
        }
        System.out.println("Food is not associated with this restaurant.");
        return null;
    }

}
