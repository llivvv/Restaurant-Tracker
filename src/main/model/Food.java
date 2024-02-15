package model;

// represents a food item with a name and price
public class Food {

    private static final double MAX_RATING = 5.0;
    private String name;
    private double price;
    private double rating;
    private boolean isTried;

    public Food(String name, double price, boolean isTried) {
        this.name = name;
        this.price = price;
        this.isTried = isTried;
    }

    // MODIFIES: this
    // EFFECTS: sets the food's rating if it has been tried, otherwise set rating to 0
    public void setRating(double rating) {
        if (isTried) {
            this.rating = rating;
            lowerRating();
        } else {
            this.rating = 0.0;
        }
    }

    // REQUIRES: rating !null
    // MODIFIES: this
    // EFFECTS: lowers rating to 5 if input was above 5 (maximum)
    public void lowerRating() {
        if (rating > MAX_RATING) {
            this.rating = 5.0;
        }
    }

    // REQUIRES: isTried == false
    // MODIFIES: this
    // EFFECTS: changes food's status to isTried
    public void makeTried() {
        isTried = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean getisTried() {
        return isTried;
    }
}
