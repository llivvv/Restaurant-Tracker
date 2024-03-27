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

public class EditandViewPanel extends JPanel implements ActionListener {

    private JPanel editable;
    private JPanel viewOnly;
    private JButton editButton;
    private JButton deleteButton;
    private JButton doneButton;
    private ReviewTrackerGUI app;
    private Restaurant restaurant;

    public EditandViewPanel(ReviewTrackerGUI app, Restaurant restaurant) {

        this.restaurant = restaurant;
        this.app = app;
        editable = new JPanel();
        viewOnly = new JPanel();
        //viewOnly.setLayout(new FlowLayout());
        editButton = new JButton("Edit");
        editButton.addActionListener(this);
        deleteButton = new JButton("Delete review");
        deleteButton.addActionListener(this);
        doneButton = new JButton("Done");
        //add(doneButton);

        setRestaurant();

        initView();

        //setOpaque(true);
        setBackground(new Color(252, 240, 176));
        System.out.println("yo ");
    }

    public void setRestaurant() {
        //customizeEditable();
        customizeViewOnly();

        System.out.println("displaying: " + restaurant.getRestaurantName());
    }

    // TODO
    public void customizeEditable() {
        editable.setLayout(new BoxLayout(editable, BoxLayout.Y_AXIS));
        JPanel nameLabelText = new JPanel();
        displayEditName(nameLabelText);
        editable.add(nameLabelText);
        editable.setBackground(new Color(80, 107, 134));
        JLabel triedFoodLabel = new JLabel("Tried/Reviewed Foods: ");
        System.out.println("is this method being called");
        // stub
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
                //restaurant.setRestaurantName(nameField.getText());
                nameField.setText(nameField.getText());
                setNewRestaurantName(nameField.getText());
            }
        });
        JLabel restaurantRating = new JLabel("~" + restaurant.getRating() + " stars");
        nameLabelText.add(restaurantName);
        nameLabelText.add(nameField);
        nameLabelText.add(restaurantRating);
    }

    public void setNewRestaurantName(String name) {
        System.out.println(" this works ");
        if (!app.getReviews().checkandSetNewRname(name, restaurant)) {
            JOptionPane.showMessageDialog(null,
                    ("A review exists with the same name." + " Please choose a new name"));
            System.out.println("bad name");
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
            //viewOnly.add(editButton);
            //viewOnly.add(deleteButton);
            //viewOnly.setLayout(new GridLayout(3, 1, 5, 40));
            //viewOnly.setSize(new Dimension((int)0.4 * app.getWidth(), (int) 0.9 * app.getHeight()));
            viewOnly.setBackground(new Color(179, 215, 250));

//            viewOnly.add(nameRating);
//            nameRating.setVisible(true);
//            viewOnly.add(allTried);
//            allTried.setVisible(true);
//            viewOnly.add(wish);
//            wish.setVisible(true);
//            viewOnly.setLayout(new GridLayout(3, 1, 5, 40));
//            viewOnly.setBackground(new Color(200, 100, 157));
        }
    }

    public void displayNameNRating(JPanel nameRating) {
        JLabel resName = new JLabel(restaurant.getRestaurantName());
        resName.setFont(new Font("Impact", Font.BOLD, 30));
        JLabel resRating = new JLabel("~" + valueOf(restaurant.getRating()) + " stars");
        resName.setFont(new Font("Impact", Font.ITALIC, 20));
        nameRating.setPreferredSize(new Dimension(400, 60));
        nameRating.add(resName);
        nameRating.add(resRating);
        nameRating.setLayout(new GridLayout(1, 2, 10, 10));
        nameRating.setOpaque(false);
        //nameRating.setSize(new Dimension((int)0.4 * app.getWidth(), 35));
    }

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
        allTried.add(triedFoodTitle);
        allTried.add(triedFieldTitle);
        for (Food f : restaurant.getTriedFoods()) {
            // System.out.println(f.getName());
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
        allTried.setLayout(new GridLayout(0, 1, 30, 0));
        allTried.setOpaque(false);
        allTried.setBackground(new Color(151, 245, 253));
    }

    // TODO
    public void displayWishList(JPanel wish) {
        JLabel empty = new JLabel(" ");
        empty.setOpaque(false);
        empty.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel wishTitle = new JLabel("WishList: ");
        wishTitle.setFont(new Font("Impact", Font.PLAIN, 20));
        JLabel name1 = new JLabel("Name");
        JLabel name2 = new JLabel("Name");
        JPanel wishFieldTitle = new JPanel();
        wishFieldTitle.add(name1);
        wishFieldTitle.add(name2);
        wishFieldTitle.setLayout(new GridLayout(1, 2, 0, 0));

        wish.add(empty);
        wish.add(wishTitle);
        wish.add(wishFieldTitle);

        JPanel foodNames = new JPanel();
        for (Food f : restaurant.getWishList()) {
            //JPanel namePanel = new JPanel();
            //namePanel.setOpaque(false);
            JLabel foodName = new JLabel(f.getName());
            //namePanel.add(foodName);
            //foodNames.add(namePanel);
            foodNames.add(foodName);
        }
        foodNames.setLayout(new GridLayout(0, 2, 10, 10));
        wish.add(foodNames);
        //wish.setOpaque(false);
        wish.setLayout(new GridLayout(0, 1, 0, 0));
        wish.setBackground(new Color(164, 236, 245));
        System.out.println("bruh");
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            customizeEditable();
            editView();
        } else if (e.getSource() == deleteButton) {
            app.deleteRestaurant(restaurant);
            viewOnly.setVisible(false);
        }
    }
}
