package ui;

import model.Food;
import model.Restaurant;
import model.Restaurants;

import javax.swing.*;
import java.util.Scanner;

// References methods from the TellerApp
// This class represents a personal Restaurant Tracker application
public class RestaurantTrackerApp {
    private Restaurants restaurants;
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

        System.out.println("Welcome! ");
        while (keepGoing) {
            homeScreen();
            command = input.next().toLowerCase();

            if (command.equals("%")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }

        System.out.println("\nBye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes Restaurants and scanner
    private void init() {
        restaurants = new Restaurants();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: when application is opened, this displays the initial options
    private void homeScreen() {
        System.out.println("\nSelect from: ");
        System.out.println("\tw -> write new review");
        System.out.println("\tv -> view all my reviews");
        System.out.println("\t% -> quit application");
    }

    // MODIFIES: this
    // EFFECTS: processes the user's commands from the homeScreen
    private void processCommand(String command) {
        if (command.equals("w")) {
            makeNewReview();
        } else if (command.equals("v")) {
            viewRestaurants();
        } else {
            System.out.println("Selection not valid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to make a new review and adds it to the list of reviews
    private void makeNewReview() {
        System.out.println("Enter the name of the restaurant");
        String restaurantName = input.next();
        if (!(restaurants.findRestaurant(restaurantName) == null)) {
            ensureUniqueRName(restaurantName);
        } else {
            System.out.println("\nSelect:");
            System.out.println("\tl -> I like this restaurant");
            System.out.println("\td -> I dislike this restaurant");
            String opinion = input.next();
            boolean isLiked = processLikeOrDislike(opinion);
            Restaurant restaurant = new Restaurant(restaurantName, isLiked);
            restaurants.addRestaurant(restaurant);
            reviewFood(restaurant);
        }
    }

    // EFFECTS: gives options to edit an existing review or choose a new name for the review
    public void ensureUniqueRName(String restaurantName) {
        System.out.println("A review with the same name already exists.");
        System.out.println("\nSelect: ");
        System.out.println("\te -> edit the existing review");
        System.out.println("\tc -> choose a different name");
        String command = input.next();
        processExistingNameNewR(command, restaurantName);
    }

    // EFFECTS: processes command to edit an existing review or choose a different name for the new review
    public void processExistingNameNewR(String command, String restaurantName) {
        if (command.equals("e")) {
            editRestaurant(restaurants.findRestaurant(restaurantName));
        } else {
            makeNewReview();
        }
    }

    // EFFECTS: returns true if user likes the restaurant, false otherwise
    public boolean processLikeOrDislike(String opinion) {
        return (opinion.equals("l"));
    }

    // EFFECTS: gives user options to review a food item or exit the restaurant review
    public void reviewFood(Restaurant restaurant) {
        System.out.println("\nSelect: ");
        System.out.println("\tr -> add new food item");
        System.out.println("\td -> Exit out of this review");
        processOptionToReview(input.next().toLowerCase(), restaurant);
    }

    // EFFECTS: Restaurant
    // EFFECTS: processes the user's command to review/add food or exit out of the review
    public void processOptionToReview(String chooseReview, Restaurant restaurant) {
        if (chooseReview.equals("r")) {
            addFoodItem(restaurant);
        }
    }

    // MODIFIES: Restaurant, Food
    // EFFECTS: consumes user input to add a new tried food item to the review for the restaurant
    public void addFoodItem(Restaurant restaurant) {
        System.out.println("\nEnter a food item to add");
        String foodName = input.next();
        System.out.println("Select: ");
        System.out.println("\tr -> is a tried food");
        System.out.println("\tw -> is a wishList item");
        String command = input.next().toLowerCase();
        Food newFood = new Food(foodName, 0, false);
        if (command.equals("r")) {
            createTriedFood(newFood);
        }
        restaurant.addFoodToFoodList(newFood);
        System.out.println("Item successfully added!");
        restaurant.createRating();
        reviewFood(restaurant);
    }

    // REQUIRES: !Food = isTried()
    // MODIFIES: Food
    // EFFECTS: creates a new triedFood, setting its price and rating
    public void createTriedFood(Food newFood) {
        newFood.makeTried();

        System.out.println("\nEnter its price: ");
        Double price = input.nextDouble();
        newFood.setPrice(price);

        System.out.println("\nEnter a rating between 0 and 5");
        Double rating = input.nextDouble();
        newFood.setRating(rating);
    }

    // EFFECTS: displays restaurant list and then presents user with options
    private void viewRestaurants() {
        if (restaurants.getNumRestaurants() != 0) {
            System.out.println("\n Here are your list of reviews: ");
            for (Restaurant r : restaurants.getAllRestaurants()) {
                System.out.println(r.getRestaurantName());
            }
            notEmptyRestaurantListCommands();
        } else {
            System.out.println("No restaurants have been added yet.");
            System.out.println("Would you like to add a new review?");
            System.out.println("\nSelect: ");
            System.out.println("\ty -> add a new review");
            System.out.println("\tn -> return to home screen");
            if (input.next().equals("y")) {
                makeNewReview();
            } else if (input.next().equals("n")) {
                homeScreen();
            } else {
                System.out.println("Invalid selection.");
                viewRestaurants();
            }
        }
    }

    // REQUIRES: a non-empty restaurant list
    // EFFECTS: displays the liked restaurants and presents options
    public void viewLikedRestaurants() {
        for (Restaurant r : restaurants.getLikedRestaurants()) {
            System.out.println(r.getRestaurantName());
        }
        System.out.println("\nSelect: ");
        System.out.println("\ts -> sort restaurants by rating");
        System.out.println("\te -> exit view");
        String command = input.next();
        processlikedRestaurantListCommand(command);
    }

    // EFFECTS: processes command after a list of liked restaurants has been viewed
    public void processlikedRestaurantListCommand(String command) {
        if (command.equals("s")) {
            restaurants.sortLikedRestaurantsRating();
            viewLikedRestaurants();
        } else {
            notEmptyRestaurantListCommands();
        }
    }

    // EFFECTS: displays the disliked restaurants and presents options
    public void viewDislikedRestaurants() {
        for (Restaurant r : restaurants.getDislikedRestaurants()) {
            System.out.println(r.getRestaurantName());
        }
        System.out.println("\nSelect: ");
        System.out.println("\ts -> sort restaurants by rating");
        System.out.println("\te -> exit view");
        String command = input.next();
        processDislikedRestaurantListCommand(command);
    }

    // EFFECTS: processes command after a list of restaurants has been viewed
    public void processDislikedRestaurantListCommand(String command) {
        if (command.equals("s")) {
            restaurants.sortDislikedRestaurantsRating();
            viewDislikedRestaurants();
        } else {
            notEmptyRestaurantListCommands();
        }
    }

    // EFFECTS: offers options after user has viewed a list of restaurants
    public void notEmptyRestaurantListCommands() {
        System.out.println("\n select:");
        System.out.println("\ts -> sort restaurants by rating");
        System.out.println("\tl -> view liked restaurants");
        System.out.println("\td -> view disliked restaurants");
        System.out.println("\te -> edit or delete a review");
        System.out.println("\tn -> add a new review");
        System.out.println("\th -> return to home page");
        String command = input.next().toLowerCase();
        processNotEmptyRestaurantListCommand(command);
    }

    // EFFECTS: offers option to search up a review name
    public void chooseReview() {
        System.out.println("\nEnter the name of the review you would like to edit or delete: ");
        String chosenRestaurantName = input.next();
        editOrDelete(restaurants.findRestaurant(chosenRestaurantName));
    }

    // EFFECTS: gives options to operate on an existing restaurant review
    public void editOrDelete(Restaurant restaurant) {
        if (restaurant == null) {
            System.out.println("Restaurant not found.");
            viewRestaurants();
        } else {
            viewRestaurantReview(restaurant);
            System.out.println("Select: ");
            System.out.println("\te -> edit this review");
            System.out.println("\td -> delete restaurant review");
            System.out.println("\tu -> exit this review");
            String command = input.next();
            if (command.equals("e")) {
                editRestaurant(restaurant);
            } else if (command.equals("d")) {
                restaurants.removeRestaurant(restaurant);
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
        System.out.println("\tf -> edit a food list");
        System.out.println("\tu -> go back to home page");
        String command = input.next();
        processEditCommand(command, restaurant);
    }

    // EFFECTS: processes commands from the edit-restaurant menu
    public void processEditCommand(String command, Restaurant restaurant) {
        if (command.equals("n")) {
            nameInstructions(restaurant);
        } else if (command.equals("f")) {
            foodListedIns(restaurant);
        }
    }

    // EFFECTS: gives command instructions for editing a food list
    public void foodListedIns(Restaurant restaurant) {
        System.out.println("\nSelect ");
        System.out.println("\na -> add an item");
        System.out.println("\ne -> remove/edit an item");
        String command = input.next();
        processFoodListEditCommand(command, restaurant);
    }

    // MODIFIES: Restaurant
    // EFFECTS: processes command for editing a food list
    public void processFoodListEditCommand(String command, Restaurant restaurant) {
        if (command.equals("a")) {
            addFoodItem(restaurant);
        } else if (command.equals("e")) {
            editDeleteFood(restaurant);
        }
    }

    // EFFECTS: asks user to input a name of a food item already existing
    //          in the selected restaurant review
    public void editDeleteFood(Restaurant restaurant) {
        System.out.println("Enter name of item to edit or remove: ");
        String enterName = input.next();
        Food selected = restaurant.getFoodFromList(enterName);
        chooseFoodOperation(selected, restaurant);
    }

    // EFFECTS: processes command from user selecting a food item from an existing review
    public void chooseFoodOperation(Food selected, Restaurant restaurant) {
        if (selected == null) {
            System.out.println("Item not found.");
            foodListedIns(restaurant);
        } else {
            System.out.println("Select: ");
            System.out.println("\te -> edit name, price, or rating");
            System.out.println("\td -> delete item");
            if (selected.getIsTried()) {
                String command = input.next();
                processFoodEditorDelete(command, selected, restaurant);
            } else {
                System.out.println("\tt -> add to tried foods");
                String command = input.next();
                processWishFoodEditorDelete(command, selected, restaurant);
            }
        }
    }

    // MODIFIES: Restaurant, Food
    // EFFECTS: processes command to add to triedList, edit or delete food item
    public void processFoodEditorDelete(String command, Food selected, Restaurant restaurant) {
        if (command.equals("d")) {
            restaurant.removeFood(selected);
            restaurant.createRating();
        } else {
            editFood(selected, restaurant);
        }
    }

    public void processWishFoodEditorDelete(String command, Food selected, Restaurant restaurant) {
        if (command.equals("t")) {
            restaurant.changeToTriedFoods(selected);
            System.out.println("Item is now in tried foods list.");
            createTriedFood(selected);
            restaurant.createRating();
        } else {
            processFoodEditorDelete(command, selected, restaurant);
        }
    }

    // EFFECTS: options to edit food name, price or rating depending on if food is tried or not
    public void editFood(Food selected, Restaurant restaurant) {
        if (!selected.getIsTried()) {
            System.out.println("Enter new name: ");
            String command = input.next();
            selected.setName(command);
        } else {
            System.out.println("\nSelect: ");
            System.out.println("\tn -> edit name");
            System.out.println("\tp -> new price");
            System.out.println("\tr -> new rating");
            String command = input.next();
            processTriedEdit(command, selected, restaurant);
        }
    }

    // REQUIRES: inputted name is not identical to any food item from this restaurant review
    // MODIFIES: Food, Restaurant
    // EFFECTS: processes command to edit name, price or rating of a tried food item
    public void processTriedEdit(String command, Food selected, Restaurant restaurant) {
        if (command.equals("n")) {
            System.out.println("Enter name: ");
            String newName = input.next();
            selected.setName(newName);
        } else if (command.equals("p")) {
            System.out.println("Enter price: ");
            Double price = input.nextDouble();
            selected.setPrice(price);
        } else if (command.equals("r")) {
            System.out.println("Enter a rating between 0 and 5: ");
            Double rating = input.nextDouble();
            selected.setRating(rating);
            restaurant.createRating();
        }
    }

    // MODIFIES: Restaurant
    // EFFECTS: gives the user instructions to edit restaurant name, modifies the name if it is unique
    public void nameInstructions(Restaurant restaurant) {
        System.out.println("New name: ");
        String newName = input.next();
        makeUniqueName(restaurants.checkandSetNewRname(newName, restaurant), restaurant);
    }

    // EFFECTS: tells user to choose a different restaurant name if name isn't unique
    public void makeUniqueName(boolean unique, Restaurant restaurant) {
        if (!unique) {
            System.out.println("Please pick a unique review name: Enter here: ");
            nameInstructions(restaurant);
        } else {
            editRestaurant(restaurant);
        }
    }

    // EFFECTS: displays a restaurant review
    public void viewRestaurantReview(Restaurant restaurant) {
        System.out.println("Name: " + restaurant.getRestaurantName());
        System.out.println("Rating: " + restaurant.getRating());
        System.out.println("\nTried Foods: ");
        for (Food f : restaurant.getTriedFoods()) {
            System.out.println("\t" + f.getName() + "  \tPrice: " + f.getPrice() + "    "
                    + "\tRating: " + f.getRating() + "/5.0");
        }
        System.out.println("\nWishList Foods: ");
        for (Food f : restaurant.getWishList()) {
            System.out.println("\t" + f.getName());
        }
    }

    // EFFECTS: processes command following the restaurant list view
    public void processNotEmptyRestaurantListCommand(String command) {
        if (command.equals("l")) {
            viewLikedRestaurants();
        } else if (command.equals("s")) {
            restaurants.sortAllRestaurantsRating();
            viewRestaurants();
        } else if (command.equals("d")) {
            viewDislikedRestaurants();
        } else if (command.equals("e")) {
            chooseReview();
        } else if (command.equals("n")) {
            makeNewReview();
        }
    }
}
