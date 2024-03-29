package ui.panels;

import model.Food;
import model.Restaurant;
import ui.ReviewTrackerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.String.valueOf;

// References: https://youtu.be/dcdlqhMluC0?si=F1YXBkSInsHDDP4L
//             https://youtu.be/AD0GQxYOI_Y?si=V8ZBU5P7FzDFLCi1

// Represents the panel in centre allowing user to see info about restaurant or edit the review
public class EditandViewPanel extends JPanel implements ActionListener {

    private JPanel editable;
    private JPanel viewOnly;
    private JButton editButton;
    private JButton deleteButton;
    private JButton doneButton;
    private ReviewTrackerGUI app;
    private Restaurant restaurant;
    private FoodTable triedFoodTable;
    private JButton btnAdd;
    private JTextField wishEdit;
    private JButton btnWishMove;
    private JButton btnWishAdd;

    // MODIFIES: this
    // EFFECTS: constructs the panel with buttons, a viewOnly and editable panel
    public EditandViewPanel(ReviewTrackerGUI app, Restaurant restaurant) {

        this.restaurant = restaurant;
        this.app = app;
        editable = new JPanel();
        viewOnly = new JPanel();
        editButton = new JButton("Edit");
        editButton.addActionListener(this);
        deleteButton = new JButton("Delete review");
        deleteButton.addActionListener(this);
        doneButton = new JButton("Done");
        doneButton.addActionListener(this);

        setRestaurant();

        initView();

        setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: opens the viewOnly view of the restaurant when the restaurant is first selected
    public void setRestaurant() {
        customizeViewOnly();
    }

    // MODIFIES: this
    // EFFECTS: adds components to the editable view of the restaurant
    public void customizeEditable() {
        editable.setLayout(new BoxLayout(editable, BoxLayout.PAGE_AXIS));
        JPanel nameLabelText = new JPanel();
        displayEditName(nameLabelText);
        editable.add(nameLabelText);
        editable.setBackground(new Color(143, 241, 219));
        JLabel triedFoodLabel = new JLabel("Tried/Reviewed Foods: ");
        editable.add(triedFoodLabel);
        triedFoodTable = new FoodTable(restaurant.getTriedFoods(), this);
        editable.add(triedFoodTable);
        JPanel wish = new JPanel();
        displayWishList(wish);
        editable.add(wish);
        JPanel editWish = new JPanel();
        editWishItem(editWish);
        editable.add(editWish);
        JPanel forDoneButton = new JPanel();
        forDoneButton.add(doneButton);
        forDoneButton.setOpaque(false);
        editable.add(forDoneButton);
    }

    // MODIFIES: this
    // EFFECTS: displays text field and buttons to add or move (to tried) a wishlist food item
    public void editWishItem(JPanel editWish) {
        wishEdit = new JTextField(15);
        wishEdit.addActionListener(this);
        editWish.setLayout(new BoxLayout(editWish, BoxLayout.Y_AXIS));
        editWish.add(wishEdit);
        btnWishMove = new JButton("Move to tried");
        btnWishMove.addActionListener(this);
        btnWishAdd = new JButton("Add Food");
        btnWishAdd.addActionListener(this);
        editWish.add(btnWishMove);
        editWish.add(btnWishAdd);
        editWish.setBackground(new Color(255, 196, 196));

    }

    // EFFECTS: displays label and text field for editing restaurant name
    public void displayEditName(JPanel nameLabelText) {
        JLabel restaurantName = new JLabel("Restaurant Name");
        JTextField nameField = new JTextField(15);
        nameField.setText(restaurant.getRestaurantName());
        nameField.setAlignmentX(CENTER_ALIGNMENT);
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText(nameField.getText());
                setNewRestaurantName(nameField.getText());
            }
        });
        nameLabelText.add(restaurantName);
        nameLabelText.add(nameField);
        nameLabelText.setOpaque(false);
    }

    // MODIFIES: restaurant
    // EFFECTS: sets the restaurant's new name or shows message if name already exists
    public void setNewRestaurantName(String name) {
        if (!app.getReviews().checkandSetNewRname(name, restaurant)) {
            JOptionPane.showMessageDialog(null,
                    ("A review exists with the same name." + " Please choose a new name"));
        }
    }

    // MODIFIES: this
    // EFFECTS: ViewOnly panel displays information about the current restaurant
    public void customizeViewOnly() {
        if (restaurant != null) {

            viewOnly.setLayout(new BoxLayout(viewOnly, BoxLayout.Y_AXIS));
            JPanel nameRating = new JPanel();
            displayNameNRating(nameRating);
            nameRating.setAlignmentX(CENTER_ALIGNMENT);
            viewOnly.add(nameRating);
            JPanel allTried = new JPanel();
            displayTriedFoods(allTried);
            allTried.setAlignmentX(CENTER_ALIGNMENT);
            viewOnly.add(allTried);
            JPanel wish = new JPanel();
            displayWishList(wish);
            wish.setAlignmentX(CENTER_ALIGNMENT);
            viewOnly.add(wish);
            JPanel viewButtons = new JPanel();
            displayViewButtons(viewButtons);
            viewButtons.setAlignmentX(CENTER_ALIGNMENT);
            viewOnly.add(viewButtons);
            viewOnly.setBackground(new Color(250, 182, 216));

        }
    }

    // MODIFIES: this
    // EFFECTS: displays the restaurant name and rating on the viewOnly panel
    public void displayNameNRating(JPanel nameRating) {
        JLabel resName = new JLabel(restaurant.getRestaurantName());
        resName.setFont(new Font("Impact", Font.BOLD, 30));
        JLabel resRating = new JLabel("~" + valueOf(restaurant.getRating()) + " stars");
        resName.setFont(new Font("Impact", Font.ITALIC, 20));
        nameRating.setPreferredSize(new Dimension(300, 60));
        nameRating.add(resName);
        nameRating.add(resRating);
        nameRating.setLayout(new GridLayout(1, 2, 10, 10));
        nameRating.setOpaque(false);
    }

    // EFFECTS: displays tried foods with heading, "table heading" and each food
    public void displayTriedFoods(JPanel allTried) {
        JLabel triedFoodTitle = new JLabel("ReviewedFoods: ");
        triedFoodTitle.setFont(new Font("Impact", Font.PLAIN, 20));
        JPanel triedFieldTitle = new JPanel();
        JLabel name = new JLabel("Name");
        JLabel price = new JLabel("Price ($)");
        JLabel rating = new JLabel("Rating");
        triedFieldTitle.add(name);
        triedFieldTitle.add(price);
        triedFieldTitle.add(rating);
        triedFieldTitle.setLayout(new GridLayout(1, 3, 0, 0));
        triedFieldTitle.setOpaque(false);
        allTried.add(triedFoodTitle);
        allTried.add(triedFieldTitle);
        for (Food f : restaurant.getTriedFoods()) {
            labelPerTriedFood(f, allTried);
        }
        allTried.setLayout(new GridLayout(0, 1, 30, 0));
        allTried.setOpaque(true);
        allTried.setBackground(new Color(143, 241, 219));
    }

    // EFFECTS: creates label for food; with name, price, rating, then adds it to a panel
    public void labelPerTriedFood(Food f, JPanel allTried) {
        JPanel foodInfo = new JPanel();
        foodInfo.setOpaque(false);
        JLabel foodName = new JLabel(f.getName());
        JLabel foodPrice = new JLabel(valueOf(f.getPrice()));
        JLabel foodRating = new JLabel(valueOf(f.getRating()));
        foodInfo.add(foodName);
        foodInfo.add(foodPrice);
        foodInfo.add(foodRating);
        foodInfo.setLayout(new GridLayout(1, 3, 0, 0));
        allTried.add(foodInfo);
    }

    // EFFECTS: displays wishlist items with heading, "table headers", and food names
    public void displayWishList(JPanel wish) {
        JLabel empty = new JLabel(" ");
        empty.setSize(10, 5);
        empty.setOpaque(false);
        empty.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel wishTitle = new JLabel("WishList:  ");
        wishTitle.setHorizontalAlignment(SwingConstants.LEFT);
        wishTitle.setFont(new Font("Impact", Font.PLAIN, 20));
        JPanel wishFieldTitle = new JPanel();
        createWishFieldTitle(wishFieldTitle);

        wish.add(empty);
        wish.add(wishTitle);
        wish.add(wishFieldTitle);

        JPanel foodNames = new JPanel();
        for (Food f : restaurant.getWishList()) {
            JLabel foodName = new JLabel(f.getName());
            foodNames.add(foodName);
        }
        wish.setLayout(new BoxLayout(wish, BoxLayout.Y_AXIS));
        foodNames.setLayout(new GridLayout(0, 2, 0, 0));
        foodNames.setOpaque(false);
        wish.add(foodNames);
        wish.setBackground(new Color(117, 178, 183));
    }

    // EFFECTS: creates the headers for 2 columns of wishlist food names
    public void createWishFieldTitle(JPanel wishFieldTitle) {
        JLabel name1 = new JLabel("Name");
        name1.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 13));
        JLabel name2 = new JLabel("Name");
        name2.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 13));
        wishFieldTitle.add(name1);
        wishFieldTitle.add(name2);
        wishFieldTitle.setLayout(new GridLayout(1, 2, 0, 0));
        wishFieldTitle.setOpaque(false);
    }

    // EFFECTS: displays buttons which are on the viewOnly panel (edit and delete)
    public void displayViewButtons(JPanel viewButtons) {
        viewButtons.add(editButton);
        viewButtons.add(deleteButton);
        viewButtons.setLayout(new BoxLayout(viewButtons, BoxLayout.X_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets viewOnly to visible and editable to invisible
    public void initView() {
        add(editable, BorderLayout.CENTER);
        add(viewOnly, BorderLayout.CENTER);
        editable.setVisible(false);
        viewOnly.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets viewOnly to invisible and editable to visible
    public void editView() {
        viewOnly.setVisible(false);
        editable.setVisible(true);
    }

    // EFFECTS: returns the current set restaurant
    public Restaurant getRestaurant() {
        return restaurant;
    }

    // EFFECTS: refreshes the editable panel
    public void repaintEditable() {
        doneButton.revalidate();
        editable.revalidate();
        editable.repaint();
    }

    // MODIFIES: this
    // EFFECTS: handles different buttons being pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            customizeEditable();
            editView();
        } else if (e.getSource() == deleteButton) {
            app.deleteRestaurant(restaurant);
            viewOnly.setVisible(false);
        } else if (e.getSource() == wishEdit) {
            wishEdit.setText(wishEdit.getText());
        } else if (e.getSource() == btnWishMove) {
            String nameFind = wishEdit.getText();
            changeFoodToTriedDisplay(nameFind);
        } else if (e.getSource() == btnWishAdd) {
            String newWishName = wishEdit.getText();
            Food newWishFood = new Food(newWishName, 0, false);
            wishEdit.setText(" ");
            newWishFood.setRating(0);
            restaurant.getWishList().add(newWishFood);
        } else if (e.getSource() == doneButton) {
            app.displayResInfo(restaurant);
        }
    }

    // MODIFIES: this
    // EFFECTS: moves food item from wishList to triedFoods and displays it
    public void changeFoodToTriedDisplay(String nameFind) {
        if (!nameFind.isEmpty() && (restaurant.getFoodFromList(nameFind) != null)) {
            Food target = restaurant.getFoodFromList(nameFind);
            restaurant.getWishList().remove(target);
            target.makeTried();
            wishEdit.setText(" ");
            editable.revalidate();
            addTriedInfo(target);
            restaurant.getTriedFoods().add(target);
            restaurant.createRating();
        }
    }

    // MODIFIES: food
    // EFFECTS: prompt user to enter food rating and price when it is made tried
    public void addTriedInfo(Food target) {
        String strPrice = JOptionPane.showInputDialog("Please enter a price ($): ");
        Double price = Double.parseDouble(strPrice);
        target.setPrice(price);
        String strRating = JOptionPane.showInputDialog("Please enter a rating (from 0 to 5");
        Double rating = Double.parseDouble(strRating);
        target.setRating(rating);
    }
}
