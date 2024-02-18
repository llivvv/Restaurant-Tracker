package model;

// compares 2 restaurants
// referred to this website to learn how to do this:
// https://www.freecodecamp.org/news/how-to-sort-a-list-in-java/#:~:text=To%20do%20this%2C%20you%20will,integer%20indicating%20their%20relative%20order.
public class RestaurantRatingComparator implements java.util.Comparator<Restaurant> {

    // EFFECTS: compares 2 restaurants
    @Override
    public int compare(Restaurant a, Restaurant b) {
        return (int) (a.getRating() - b.getRating());
    }
}
