package ui;

import model.Food;
import model.Restaurant;
import model.Restaurants;

import java.util.Scanner;

// References methods from the TellerApp
// Personal Restaurant Tracker application
public class RestaurantTrackerApp {
    private Restaurants allRestaurants;
    private Restaurants likedRestaurants;
    private Restaurants dislikedRestaurants;
    private Scanner input;

    // EFFECTS: runs the restaurant tracker application
    public RestaurantTrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next().toLowerCase();

            if (command.equals("%")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }

        System.out.println("\nBye!");
    }

    private void init() {
        allRestaurants = new Restaurants();
        likedRestaurants = new Restaurants();
        dislikedRestaurants = new Restaurants();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void displayMenu() {
        System.out.println("\nWelcome! Select from");
        System.out.println("\tw -> write new review");
        System.out.println("\tv -> view all my reviews");
        System.out.println("\t% -> quit application");
    }

    private void processCommand(String command) {
        if (command.equals("w")) {
            makeNewReview();
        } else if (command.equals("v")) {
            viewRestaurants();
        } else {
            System.out.println("Selection not valid.");
        }
    }

    private void makeNewReview() {
        System.out.println("Enter the name of the restaurant");
        String restaurantName = input.next();
        System.out.println("\nSelect:");
        System.out.println("\tl -> I like this restaurant");
        System.out.println("\td -> I dislike this restaurant");
        String opinion = input.next();
        boolean isLiked = processLikeOrDislike(opinion);
        Restaurant restaurant = new Restaurant(restaurantName, isLiked);
        addRestaurantToCorrectLists(restaurant);
        reviewFood(restaurant);
        restaurant.createRating();
    }

    public boolean processLikeOrDislike(String opinion) {
        if (opinion.equals("l")) {
            return true;
        } else {
            return false;
        }
    }

    public void reviewFood(Restaurant restaurant) {
        System.out.println("\nSelect: ");
        System.out.println("\tr -> Review a food item");
        System.out.println("\tw -> Add a wishlist item");
        System.out.println("\td -> Exit out of this review");
        processOptionToReview(input.next().toLowerCase(), restaurant);
    }

    public void addRestaurantToCorrectLists(Restaurant restaurant) {
        allRestaurants.addRestaurant(restaurant);
        if (restaurant.getIsLiked()) {
            likedRestaurants.addRestaurant(restaurant);
        } else {
            dislikedRestaurants.addRestaurant(restaurant);
        }
    }

    public void processOptionToReview(String chooseReview, Restaurant restaurant) {
        if (chooseReview.equals("r")) {
            writeFoodReview(restaurant);
        } else if (chooseReview.equals("w")) {
            writeWishListItem(restaurant);
        } else if (chooseReview.equals("D")) {
            displayMenu();
        } else {
            System.out.println("Selection not valid");
            reviewFood(restaurant);
        }
    }

    // EFFECTS: consumes user input to add a new tried food item to the review for the restaurant
    public void writeFoodReview(Restaurant restaurant) {

        System.out.println("\nEnter a food item to review");
        String foodName = input.next();

        System.out.println("\nEnter its price: ");
        Double price = Double.valueOf(input.next());

        Food newFood = new Food(foodName, price, true);
        restaurant.addFoodToFoodList(newFood);

        System.out.println("\nEnter a rating between 1 to 5");
        newFood.setRating(Double.valueOf(input.next()));

        System.out.println("\nItem added!");

        reviewFood(restaurant);
    }

    public void writeWishListItem(Restaurant restaurant) {

        System.out.println("\n Name of food to add to wishlist: ");
        String foodName = input.next();

        Food newFood = new Food(foodName, 0.0, false);
        restaurant.addFoodToFoodList(newFood);

        System.out.println("\nItem added!");

        reviewFood(restaurant);
    }

    private void viewRestaurants() {
        for (Restaurant r : allRestaurants.getRestaurants()) {
            System.out.println(r.getRestaurantName());
        }
    }
}
