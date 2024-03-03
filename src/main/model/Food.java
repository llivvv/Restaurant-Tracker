package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a food item with a status of tried or not tried, name, price, and rating
public class Food implements Writable {

    private static final double MAX_RATING = 5.0;
    private String name;
    private double price;
    private double rating;
    private boolean isTried;

    // EFFECTS: constructs a food item with a name, price and tried-status
    public Food(String name, double price, boolean isTried) {
        this.name = name;
        this.price = price;
        this.isTried = isTried;
    }

    // MODIFIES: this
    // EFFECTS: sets the food's rating to rating if it has been tried, otherwise sets rating to 0
    public void setRating(double rating) {
        if (isTried) {
            this.rating = rating;
            fixRating();
        } else {
            this.rating = 0.0;
        }
    }

    // MODIFIES: this
    // EFFECTS: lowers rating to 5 if rating was above 5 (maximum), or raises it to 0 if rating < 0
    public void fixRating() {
        if (rating > MAX_RATING) {
            this.rating = 5.0;
        } else if (rating < 0) {
            this.rating = 0;
        }
    }

    // REQUIRES: isTried == false
    // MODIFIES: this
    // EFFECTS: changes food's status to isTried
    public void makeTried() {
        isTried = true;
    }

    // EFFECTS: sets the food's name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: sets the food's price
    public void setPrice(Double price) {
        this.price = price;
    }

    // EFFECTS: returns the food's rating
    public double getRating() {
        return rating;
    }

    // EFFECTS: returns the food's name
    public String getName() {
        return name;
    }

    // EFFECTS: returns the food's price
    public double getPrice() {
        return price;
    }

    // EFFECTS: returns whether the food is tried
    public boolean getIsTried() {
        return isTried;
    }

    // referenced JsonSerializationDemo.WorkRoom.toJson()
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("MAXRATING", MAX_RATING);
        json.put("name", name);
        json.put("price", price);
        json.put("foodRating", rating);
        json.put("isTried", isTried);
        return json;
    }
}
