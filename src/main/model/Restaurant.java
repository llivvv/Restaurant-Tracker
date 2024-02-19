package model;

import java.util.ArrayList;

// Represents a restaurant with a name, liked/disliked, foods that have been tried wishlist items
// foods to try, and a rating
public class Restaurant {

    private String name;
    private boolean isLiked;
    private ArrayList<Food> triedFoods;
    private ArrayList<Food> wishList;
    private double rating;
    private int reviewNumber;

    // EFFECTS: creates a new restaurant review with no foods, initial 0 rating
    public Restaurant(String name, boolean opinion) {
        this.name = name;
        this.isLiked = opinion;
        triedFoods = new ArrayList<Food>();
        wishList = new ArrayList<Food>();
        this.reviewNumber = 0;
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
    // EFFECTS: changes food to tried, removes item from wishList and adds it triedFoods
    public void changeToTriedFoods(Food food) {
        food.makeTried();
        if (!triedFoods.contains(food)) {
            wishList.remove(food);
            triedFoods.add(food);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a rating for the restaurant by taking the average of the food ratings
    //          creates a rating of 0 if no tried foods are in the review
    public void createRating() {
        if (getNumTriedFoods() == 0) {
            this.rating = 0;
        } else {
            double sumRating = 0;
            for (Food f : triedFoods) {
                sumRating = f.getRating() + sumRating;
            }
            this.rating = sumRating / (triedFoods.size());
        }
    }

    // REQUIRES: food is in triedFoods or wishList
    // MODIFIES: this
    // EFFECTS: removes the food item from the restaurant review
    public void removeFood(Food food) {
        if (triedFoods.contains(food)) {
            triedFoods.remove(food);
        } else {
            wishList.remove(food);
        }
    }

    // EFFECTS: returns whether restaurant is liked
    public boolean getIsLiked() {
        return isLiked;
    }

    // EFFECTS: returns the restaurant's rating
    public double getRating() {
        return rating;
    }

    // EFFECTS: returns the list of tried foods
    public ArrayList<Food> getTriedFoods() {
        return triedFoods;
    }

    // EFFECTS: returns the list of wish list foods
    public ArrayList<Food> getWishList() {
        return wishList;
    }

    // EFFECTS: if food with the same name is found, returns the food, otherwise returns null
    public Food getFoodFromList(String name) {
        for (Food f : triedFoods) {
            if (name.equals(f.getName())) {
                return f;
            }
        }
        for (Food f : wishList) {
            if (name.equals(f.getName())) {
                return f;
            }
        }
        return null;
    }

    // EFFECTS: sets the restaurant's name
    public void setRestaurantName(String name) {
        this.name = name;
    }

    // EFFECTS: sets the restaurant's review number
    public void setReviewNumber(int number) {
        this.reviewNumber = number;
    }

    // EFFECTS: returns the number of foods in the tried foods list
    public int getNumTriedFoods() {
        return triedFoods.size();
    }

    // EFFECTS: returns the number of foods in the wishlist
    public int getNumWishList() {
        return wishList.size();
    }

    // EFFECTS: returns the restaurant name
    public String getRestaurantName() {
        return name;
    }

    // EFFECTS: returns the restaurant's review number
    public int getReviewNumber() {
        return reviewNumber;
    }
}
