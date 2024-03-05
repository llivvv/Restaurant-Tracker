package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a restaurant with a name, liked/disliked, tried foods, foods to try (wishlist),
// rating, and a review number
// References JsonSerializationDemo (WorkRoom.toJson(), WorkRoom.thingiesToJson())
public class Restaurant implements Writable {

    private String name;
    private boolean isLiked;
    private List<Food> triedFoods;
    private List<Food> wishList;
    private int rating;
    private int reviewNumber;

    // EFFECTS: creates a new restaurant review with no foods, initial 0 rating, and intial 0 reviewNumber
    public Restaurant(String name, boolean opinion) {
        this.name = name;
        this.isLiked = opinion;
        triedFoods = new ArrayList<Food>();
        wishList = new ArrayList<Food>();
        this.reviewNumber = 0;
    }

    // REQUIRES: Food is not already in triedFoods nor wishList
    // MODIFIES: this, Food
    // EFFECTS: if item isTried, adds item to triedFoods, otherwise adds it to wishList
    public void addFoodToFoodList(Food food) {
        if (food.getIsTried()) {
            triedFoods.add(food);
        } else {
            wishList.add(food);
        }
    }

    // REQUIRES: item is in wishlist
    // MODIFIES: this
    // EFFECTS: changes food to tried, removes item from wishList and adds it to triedFoods
    public void changeToTriedFoods(Food food) {
        food.makeTried();
        if (!triedFoods.contains(food)) {
            wishList.remove(food);
            triedFoods.add(food);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a rating for the restaurant using the triedFood ratings
    //          creates a rating of 0 if no triedFoods are in the review
    public void createRating() {
        if (getNumTriedFoods() == 0) {
            this.rating = 0;
        } else {
            double sumRating = 0;
            for (Food f : triedFoods) {
                sumRating = f.getRating() + sumRating;
            }
            this.rating = (int) Math.round(sumRating / (triedFoods.size()));
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the restaurant's rating to rating
    public void setRating(int rating) {
        this.rating = rating;
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
    public int getRating() {
        return rating;
    }

    // EFFECTS: returns the list of tried foods
    public List<Food> getTriedFoods() {
        return triedFoods;
    }

    // EFFECTS: returns the list of wish list foods
    public List<Food> getWishList() {
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

    // EFFECTS: returns the number of foods in the triedfoods list
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

    // referenced JsonSerializationDemo.WorkRoom.toJson()
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("restaurantName", name);
        json.put("isLiked", isLiked);
        json.put("triedFoods", triedFoodsToJson());
        json.put("wishList", wishListToJson());
        json.put("restaurantRating", rating);
        json.put("reviewNumber", reviewNumber);
        return json;
    }

    // referenced JsonSerializationDemo.WorkRoom.thingiesToJson()
    // EFFECTS: returns all triedFoods in this Restaurant as a JSON array
    private JSONArray triedFoodsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food f : triedFoods) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }

    // referenced JsonSerializationDemo.WorkRoom.thingiesToJson()
    // EFFECTS: returns all wishList items in this Restaurant as a JSON array
    private JSONArray wishListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food f : wishList) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}
