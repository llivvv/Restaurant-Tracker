package ui;

import model.Food;
import model.Restaurant;
import model.Restaurants;

import java.util.Scanner;

// References methods from the TellerApp
// Personal Restaurant Tracker application
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

    private void init() {
        restaurants = new Restaurants();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void homeScreen() {
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
        restaurants.addRestaurant(restaurant);
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

    public void processOptionToReview(String chooseReview, Restaurant restaurant) {
        if (chooseReview.equals("r")) {
            addFoodItem(restaurant);
        } else if (chooseReview.equals("d")) {
            homeScreen();
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
            Double price = input.nextDouble();

            Food newFood = new Food(foodName, price, true);
            restaurant.addFoodToFoodList(newFood);

            System.out.println("\nEnter a rating between 1 to 5");
            Double rating = input.nextDouble();
            newFood.setRating(rating);
        } else {
            Food newFood = new Food(foodName, 0.0, false);
            restaurant.addFoodToFoodList(newFood);
        }
        System.out.println("Item successfully added!");
        reviewFood(restaurant);
    }

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

    public void viewLikedRestaurants() {
        for (Restaurant r : restaurants.getLikedRestaurants()) {
            System.out.println(r.getRestaurantName());
        }
        notEmptyRestaurantListCommands();
    }

    public void viewDislikedRestaurants() {
        for (Restaurant r : restaurants.getDislikedRestaurants()) {
            System.out.println(r.getRestaurantName());
        }
        System.out.println("\nSelect: ");
        System.out.println("\ts -> sort restaurants by rating");
        System.out.println("\te -> exit view");
        String command = input.next();
        processRestaurantListCommand(command);
    }

    // EFFECTS: processes command after a list of restaurants has been viewed
    public void processRestaurantListCommand(String command) {
        if (command.equals("s")) {
            restaurants.sortDislikedRestaurants();
            viewDislikedRestaurants();
        } else {
            notEmptyRestaurantListCommands();
        }
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
            System.out.println("\tu -> unselect this review");
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
        } else if (command.equals("u")) {
            homeScreen();
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
        } else {
            System.out.println("Select: ");
            System.out.println("\tt -> add to tried foods");
            System.out.println("\te ->Edit name, price, or rating");
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

    public void editFood(Food selected, Restaurant restaurant) {
        if (!selected.getisTried()) {
            System.out.println("Enter new name: ");
            String command = input.next();
            selected.setName(command);
        } else {
            System.out.println("\nSelect: ");
            System.out.println("\tn -> edit name");
            System.out.println("\tp -> change price");
            System.out.println("\tr -> change rating");
            String command = input.next();
            processTriedEdit(command, selected, restaurant);
        }
    }

    public void processTriedEdit(String command, Food selected, Restaurant restaurant) {
        if (command.equals("n")) {
            String newName = input.next();
            selected.setName(newName);
        } else if (command.equals("p")) {
            Double price = input.nextDouble();
            selected.setPrice(price);
        } else if (command.equals("r")) {
            Double rating = input.nextDouble();
            selected.setRating(rating);
            restaurant.createRating();
        }
    }

    // EFFECTS: gives the user instructions to edit name
    public void nameInstructions(Restaurant restaurant) {
        System.out.println("New name: ");
        String newName = input.next();
        makeUniqueName(restaurants.checkandSetNewRname(newName, restaurant), restaurant);
    }

    public void makeUniqueName(boolean unique, Restaurant restaurant) {
        if (!unique) {
            System.out.println("Please pick a unique review name: Enter here: ");
            nameInstructions(restaurant);
        } else {
            editRestaurant(restaurant);
        }
    }

    public void viewRestaurantReview(Restaurant restaurant) {
        System.out.println("Name: " + restaurant.getRestaurantName());
        System.out.println("Rating: " + restaurant.getRating());
        System.out.println("\nTried Foods: ");
        for (Food f : restaurant.getTriedFoods()) {
            System.out.println("\t" + f.getName() + "  \tPrice: " + f.getPrice() + "    \tRating: " + f.getRating());
        }
        System.out.println("\nWishList Foods: ");
        for (Food f : restaurant.getWishList()) {
            System.out.println("\t" + f.getName());
        }
    }

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
