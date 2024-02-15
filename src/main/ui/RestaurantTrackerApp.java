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
        } else if (chooseReview.equals("d")) {
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
        if (allRestaurants.getNumRestaurants() != 0) {
            for (Restaurant r : allRestaurants.getRestaurants()) {
                System.out.println(r.getRestaurantName());
            }
            notEmptyRestaurantListCommands();
        } else {
            System.out.println("No restaurants have been added yet.");
            System.out.println("Would you like to add a new review?");
            if (input.next().equals("y")) {
                makeNewReview();
            } else if (input.next().equals("n")) {
                displayMenu();
            } else {
                System.out.println("Invalid selection.");
                viewRestaurants();
            }
        }
    }

    public void viewLikedRestaurants() {
        for (Restaurant r : likedRestaurants.getRestaurants()) {
            System.out.println(r.getRestaurantName());
        }
        notEmptyRestaurantListCommands();
    }

    public void viewDislikedRestaurants() {
        for (Restaurant r : dislikedRestaurants.getRestaurants()) {
            System.out.println(r.getRestaurantName());
        }
        notEmptyRestaurantListCommands();
    }

    // when the user has viewed a list of restaurants, this tells the user what options they have next
    public void notEmptyRestaurantListCommands() {
        System.out.println("\n select:");
        System.out.println("\tl -> view liked restaurants");
        System.out.println("\td -> view disliked restaurants");
        System.out.println("\te -> edit or delete a review");
        System.out.println("\tn -> add a new review");
        String command = input.next().toLowerCase();
        processNotEmptyRestaurantListCommand(command);
    }

    public void chooseReview() {
        System.out.println("\nEnter the name of the review you would like to edit or delete: ");
        String chosenRestaurantName = input.next();
        editOrDelete(allRestaurants.findRestaurant(chosenRestaurantName));
    }

    public void editOrDelete(Restaurant restaurant) {
        if (restaurant == null) {
            System.out.println("Restaurant not found.");
            viewRestaurants();
        } else {
            System.out.println("Select: ");
            System.out.println("\te -> edit this review");
            System.out.println("\td -> delete restaurant review");
            System.out.println("\tu -> unselect this review");
            String command = input.next();
            if (command.equals("e")) {
                editRestaurant(restaurant);
            } else if (command.equals("d")) {
                allRestaurants.removeRestaurant(restaurant);
                likedRestaurants.removeRestaurant(restaurant);
                dislikedRestaurants.removeRestaurant(restaurant);
            } else {
                viewRestaurants();
            }
        }
    }

    // EFFECTS: allows user to edit the restaurant name and food items
    public void editRestaurant(Restaurant restaurant) {
        viewRestaurantReview(restaurant);
        System.out.println("\nSelect: ");
        System.out.println("\tn -> edit Restaurant name");
        System.out.println("\tf -> edit triedFood list");
        System.out.println("\tw -> edit wishList");
        System.out.println("\tu -> go back to home page");
        String command = input.next();
        processEditCommand(command, restaurant);
    }

    // EFFECTS: processes commands from the edit-restaurant menu
    public void processEditCommand(String command, Restaurant restaurant) {
        if (command.equals("n")) {
            nameInstructions(restaurant);
        } else if (command.equals("f")) {
            TriedListInstructions(restaurant);
        } else if (command.equals("w")) {
            WishEditInstructions(restaurant);
        } else if (command.equals("u")) {
            displayMenu();
        } else {
            System.out.println("Selection invalid.");
            processEditCommand();
        }
    }

    // EFFECTS: gives the user instructions to edit name
    public void nameInstructions(Restaurant restaurant) {
        System.out.println("New name: ");
        String newName = input.next();
        makeUniqueName(allRestaurants.checkNoDuplicateNames(newName), restaurant, newName);
    }

    public void makeUniqueName(boolean unique, Restaurant restaurant, String newName) {
        if (!unique) {
            System.out.println("Please pick a unique review name: Enter here: ");
            nameInstructions(restaurant);
        } else {
            restaurant.setRestaurantName(newName);
        }
    }

    public void viewRestaurantReview(Restaurant restaurant) {}

    public void processNotEmptyRestaurantListCommand(String command) {
        if (command.equals("l")) {
            viewLikedRestaurants();
        } else if (command.equals("d")) {
            viewDislikedRestaurants();
        } else if (command.equals("e")) {
            chooseReview();
        } else if (command.equals("n")) {
            makeNewReview();
        } else {
            System.out.println("Selection invalid.");
            notEmptyRestaurantListCommands();
        }
    }





}
