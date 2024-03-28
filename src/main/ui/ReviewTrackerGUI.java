package ui;

import model.Restaurant;
import model.Reviews;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// References: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters (SmartHome)
//             https://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
//             https://youtu.be/OI-TFbHQhtA?si=_ZUwX9mAbhoBNQu0 (processing button clicks)
//             https://stackoverflow.com/questions/13334198/java-custom-buttons-in-showinputdialog

public class ReviewTrackerGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/reviews.json";
    private static final String JSON_DUMMY = "./data/trashReader.json";
    private Reviews reviews;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private HomePanel homePage;
    //private LikedList listLiked;
    //private DislikedList listDisliked;
    private ListPanel listPanel;
    private EditandViewPanel editAdd;
    private TabPanel tabs;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    // MODIFIES: this
    // EFFECTS:
    public ReviewTrackerGUI() throws FileNotFoundException {
        super("Review Tracker");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        getContentPane().setBackground(new Color(205, 233, 243));

        reviews = new Reviews();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_DUMMY);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                askSave();
            }
        });

        loadPanels();
        setVisible(true);
    }

    // EFFECTS:
    public void loadPanels() {
        homePage = new HomePanel(this);
        homePage.setVisible(true);
        getContentPane().add(homePage, BorderLayout.CENTER);
        //editAdd = new EditandViewPanel(null);
        //getContentPane().add(editAdd);
        //editAdd.setVisible(false);
//        listPanel = new ListPanel(reviews.getAllReviews());
//        //add(listAll, BorderLayout.WEST);
//        listPanel.setPreferredSize(new Dimension((int) (0.5 * WIDTH), HEIGHT));
//        getContentPane().add(listPanel, BorderLayout.WEST);
//        listPanel.setVisible(false);
    }

    // MODIFIES: Reviews;
    // EFFECTS: dialog box prompts user to save their work and listens to choice
    public void askSave() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Would you like to save today's progress before quitting the app?",
                "Save Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (choice == 0) {
            saveReviews();
        } else {
            JOptionPane.showMessageDialog(null, "Progress not saved.");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // EFFECTS: saves entire state of application to JSON
    private void saveReviews() {
        try {
            jsonWriter.open();
            jsonWriter.write(reviews);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved to " + JSON_STORE + "!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Reviews from file
    private void loadReviews() {
        try {
            reviews = jsonReader.read();
            System.out.println("Loaded reviews from " + JSON_DUMMY);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_DUMMY);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes button clicks; sets visibility of panels
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == (homePage.getNewStartButton())) || (e.getSource() == (homePage.getLoadStartButton()))) {
            leaveHomePage(e);
        } else if (e.getSource() == tabs.getBtnCreate()) {
            makeNewReview();
        } else if (e.getSource() == tabs.getBtnHome()) {
            returnHomePage();
        }
    }

    // MODIFIES: this
    // EFFECTS: makes homePage invisible, processes to load or not load existing reviews
    public void leaveHomePage(ActionEvent e) {
        if (e.getSource() == homePage.getLoadStartButton()) {
            loadReviews();
        }
        homePage.setVisible(false);
        repaint();
        customizeListPanel();
        customizeTabPanel();
//        listPanel = new ListPanel(this, reviews.getAllReviews());
//        //add(listAll, BorderLayout.WEST);
//        listPanel.setSize(new Dimension(400, HEIGHT)); // 0.5 * WIDTH
//        getContentPane().add(listPanel, BorderLayout.WEST);
//        listPanel.setVisible(true);
    }

    // TODO
    // MODIFIES: this
    // EFFECTS: sets all panels to invisible except for homepage
    public void returnHomePage() {
        //
    }

    public void makeNewReview() {
        String name = JOptionPane.showInputDialog("Please enter a unique review name: ");
        if (reviews.findRestaurant(name) != null) {
            fixDuplicateName(reviews.findRestaurant(name));
        } else {
            initRestaurant(name);
        }
    }

    // EFFECTS: asks user about like vs dislike and creates a new review
    public void initRestaurant(String name) {
        Object[] options = { "Liked", "Disliked", "cancel review"};
        int choice = JOptionPane.showOptionDialog(null, "Is " + name + " liked or disliked?",
                "Liked or Disliked", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                options[2]);

        if (choice != 2) {
            Restaurant created;
            if (choice == 0) {
                created = new Restaurant(name, true);
            } else {
                created = new Restaurant(name, false);
            }
            reviews.addRestaurant(created);
            listPanel.addToJList(created);
            // TODO
        }
    }

    public void fixDuplicateName(Restaurant restaurant) {
        Object[] options = { "Choose a different name", "Edit existing review", "cancel" };
        int choice = JOptionPane.showOptionDialog(null, "A review with the same name already exists.",
                "Duplicate name", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                options[2]);

        if (choice == 0) {
            makeNewReview();
        } else if (choice == 1) {
            /// TODO
        }
    }

    public void customizeListPanel() {
        listPanel = new ListPanel(this, reviews.getAllReviews());
        //add(listAll, BorderLayout.WEST);
        listPanel.setSize(new Dimension(400, HEIGHT)); // 0.5 * WIDTH
        getContentPane().add(listPanel, BorderLayout.WEST);
        listPanel.setVisible(true);
    }

    public void customizeTabPanel() {
        tabs = new TabPanel(this);
        tabs.setSize(new Dimension(75, HEIGHT));
        getContentPane().add(tabs, BorderLayout.EAST);
        tabs.setVisible(true);
    }

    // EFFECTS: displays the information of the selected restaurant
    public void displayResInfo(Restaurant restaurant) {
        if (editAdd != null) {
            if (editAdd.isVisible()) {
                editAdd.setVisible(false);
            }
        }
        editAdd = new EditandViewPanel(this, restaurant);
        editAdd.setSize(new Dimension(400, HEIGHT)); // 0.5 * WIDTH
        getContentPane().add(editAdd, BorderLayout.CENTER);
        //setLayout(new GridLayout(1, 2));
        editAdd.setVisible(true);
        System.out.println("why isn't the panel visible");
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void deleteRestaurant(Restaurant restaurant) {
        reviews.removeRestaurant(restaurant);
        editAdd.setVisible(false);
        listPanel.deleteFromJList();
    }
//    // EFFECTS: returns dimensions of this frame
//    public Dimension getDimensions() {
//        return new Dimension(WIDTH, HEIGHT);
//    }

    //    // EFFECTS: processes the selection of a list element
//    @Override
//    public void valueChanged(ListSelectionEvent e) {
//        if (listPanel.selectedRestaurant() != null) {
//            Restaurant selected = listPanel.selectedRestaurant();
//            editAdd = new EditandViewPanel(listPanel.selectedRestaurant());
//            editAdd.setPreferredSize(new Dimension((int) (0.5 * WIDTH), HEIGHT));
//            getContentPane().add(editAdd, BorderLayout.EAST);
//            editAdd.setVisible(true);
//        }
//    }
}
