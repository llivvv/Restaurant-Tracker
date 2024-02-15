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
            command = input.next();
            command = command.toLowerCase();

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
            writeReview();
        } else if (command.equals("v")) {
            viewRestaurants();
        } else {
            System.out.println("Selection not valid.");
        }
    }

    private void writeReview() {
        System.out.println("Enter the name of the restaurant");
        String restaurantName = input.nextLine();
        System.out.println("\nSelect:");
        System.out.println("\tl -> I like this restaurant");
        System.out.println("\td -> I dislike this restaurant");
        String opinion = input.nextLine();
        boolean isLiked = processLikeOrDislike(opinion);
        Restaurant restaurant = new Restaurant(restaurantName, isLiked);
        addRestaurantToCorrectLists(restaurant);
        reviewFood(restaurant);
    }

    public boolean processLikeOrDislike(String opinion) {
        if (opinion.equals("l")) {
            return true;
        } else {
            return false;
        }
    }

    public void reviewFood(Restaurant restaurant) {
        System.out.println("\nEnter a food item to review");
        String foodName = input.nextLine();

        System.out.println("\nEnter its price: ");
        Double price = Double.valueOf(input.next());

        Food newFood = new Food(foodName, price, true);
        restaurant.addFoodToFoodList(newFood);

        System.out.println("\nEnter a rating between 1 to 5");
        newFood.setRating(Double.valueOf(input.nextLine()));
    }

    public void addRestaurantToCorrectLists(Restaurant restaurant) {
        allRestaurants.addRestaurant(restaurant);
        if (restaurant.getIsLiked()) {
            likedRestaurants.addRestaurant(restaurant);
        } else {
            dislikedRestaurants.addRestaurant(restaurant);
        }
    }


    private void viewRestaurants() {

    }



}
