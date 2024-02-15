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
        System.out.println("\tr -> add new food item");
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
            addFoodItem(restaurant);
        } else if (chooseReview.equals("d")) {
            displayMenu();
        } else {
            System.out.println("Selection not valid");
            reviewFood(restaurant);
        }
    }

    // EFFECTS: consumes user input to add a new tried food item to the review for the restaurant
    public void addFoodItem(Restaurant restaurant) {

        System.out.println("\nEnter a food item to add");
        String foodName = input.next();
        System.out.println("Select: ");
        System.out.println("\tr -> is a tried food");
        System.out.println("\tw -> is a wishList item");
        String command = input.next().toLowerCase();
        if (command.equals("r")) {
            System.out.println("\nEnter its price: ");
            Double price = Double.valueOf(input.next());

            Food newFood = new Food(foodName, price, true);
            restaurant.addFoodToFoodList(newFood);

            System.out.println("\nEnter a rating between 1 to 5");
            newFood.setRating(Double.valueOf(input.next()));
        } else {
            Food newFood = new Food(foodName, 0.0, false);
            restaurant.addFoodToFoodList(newFood);
        }
        System.out.println("Item successfully added!");
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

    // EFFECTS: gives options to operate on an existing restaurant review
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
        } else if (command.equals("u")) {
            displayMenu();
        } else {
            System.out.println("Selection invalid.");
            editRestaurant(restaurant);
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

    public void processFoodListEditCommand(String command, Restaurant restaurant) {
        if (command.equals("a")) {
            addFoodItem(restaurant);
        } else if (command.equals("e")) {
            editDeleteFood(restaurant);
        }
    }

    public void editDeleteFood(Restaurant restaurant) {
        System.out.println("Enter name of item to edit or remove: ");
        String enterName = input.next();
        Food selected = restaurant.getFoodFromList(enterName);
        chooseFoodOperation(selected, restaurant);
    }

    public void chooseFoodOperation(Food selected, Restaurant restaurant) {
        if (selected == null) {
            System.out.println("Item not found.");
            foodListedIns(restaurant);
        } else if  {
            System.out.println("Select: ");
            System.out.println("\tt -> add to tried foods");
            System.out.println("\Edit name, price, or rating");
            String command = input.next();
            processFoodaddorEdit(command, selected, restaurant);
        }
    }

    public void processFoodaddorEdit(String command, Food selected, Restaurant restaurant) {
        if (command.equals("t")) {
            restaurant.changeToTriedFoods(selected);
            System.out.println("Item is now in tried foods list.");
        } else {
            editFood(selected, restaurant);
        }
    }

    // EFFECTS: gives the user instructions to edit name
    public void nameInstructions(Restaurant restaurant) {
        System.out.println("New name: ");
        String newName = input.next();
        makeUniqueName(allRestaurants.checkandSetNewRname(newName), restaurant);
    }

    public void makeUniqueName(boolean unique, Restaurant restaurant) {
        if (!unique) {
            System.out.println("Please pick a unique review name: Enter here: ");
            nameInstructions(restaurant);
        } else {

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
