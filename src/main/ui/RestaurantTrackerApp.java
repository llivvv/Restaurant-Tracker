package ui;

import model.Restaurants;

// Personal Restaurant Tracker application
public class RestaurantTrackerApp {
    private Restaurants reviewList;

    // EFFECTS: runs the restaurant tracker application
    public RestaurantTrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean KeepGoing = true;
        String command = null;

        init();
    }

    public void init() {}
}
