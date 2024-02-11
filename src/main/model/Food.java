package model;

// represents a food item with a name and price
public class Food {

    private static final double MAX_RATING = 5.0;
    private String name;
    private double price;
    private double rating;
    private boolean isTried;

    public Food(String n, double p, double r, boolean t) {
        name = n;
        price = p;
        rating = r;
        isTried = t;
    }

    // REQUIRES: rating !null
    // MODIFIES: this
    // EFFECTS: lowers rating to 5 if input was above 5 (maximum)
    public void lowerRating() {
        if (rating > MAX_RATING) {
            this.rating = 5.0;
        }
    }

    public double getRating() {
        return rating;
    }

    public String getName() { return name; }
}
