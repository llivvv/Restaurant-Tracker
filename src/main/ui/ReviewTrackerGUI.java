package ui;

import model.Event;
import model.EventLog;
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

// Represents a personal restaurant tracker application with a GUI
public class ReviewTrackerGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/reviews.json";
    private Reviews reviews;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private HomePanel homePage;
    private ListPanel listPanel;
    private EditandViewPanel editAdd;
    private TabPanel tabs;
    private JLabel bgImage;
    private SortFilterPanel sfPanel;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    // image reference: https://www.realsimple.com/holidays-entertaining/entertaining/everyday-celebrations/picnic-packing-checklist
    // MODIFIES: this
    // EFFECTS: constructs this GUI with a set homePage and image
    public ReviewTrackerGUI() throws FileNotFoundException {
        super("Review Tracker");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        getContentPane().setBackground(new Color(205, 233, 243));

        reviews = new Reviews();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

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

    // MODIFIES: this
    // EFFECTS: opening screen; sets image and homepage
    public void loadPanels() {
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/home.png"));
        bgImage = new JLabel(imageIcon);
        bgImage.setVisible(true);
        bgImage.setSize(WIDTH, HEIGHT);
        homePage = new HomePanel(this);
        homePage.setVisible(true);
        getContentPane().add(bgImage);
        getContentPane().add(homePage, BorderLayout.CENTER);
    }

    // MODIFIES: Reviews
    // EFFECTS: dialog box prompts user to save their work and processes their choice
    public void askSave() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Would you like to save today's progress before quitting the app?",
                "Save Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (choice == 0) {
            saveReviews();
        } else {
            JOptionPane.showMessageDialog(null, "Progress not saved.");
        }
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
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
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
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
        }
    }

    // MODIFIES: this
    // EFFECTS: makes homePage invisible, processes to load or not load existing reviews
    public void leaveHomePage(ActionEvent e) {
        if (e.getSource() == homePage.getLoadStartButton()) {
            loadReviews();
        }
        bgImage.setVisible(false);
        homePage.setVisible(false);
        repaint();
        customizeListPanel();
        customizeSortPanel(this, listPanel);
        customizeTabPanel();
    }

    // EFFECTS: prompts user to enter a new name for a new review
    public void makeNewReview() {
        String name = JOptionPane.showInputDialog("Please enter a unique review name: ");
        if (reviews.findRestaurant(name) != null) {
            fixDuplicateName(reviews.findRestaurant(name));
        } else {
            initRestaurant(name);
        }
    }

    // MODIFIES: reviews, listPanel
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
        }
    }

    // MODIFIES: listPanel
    // EFFECTS: prompts user to choose a new name or pick a review to edit
    public void fixDuplicateName(Restaurant restaurant) {
        Object[] options = { "Choose a different name", "Edit existing review", "cancel" };
        int choice = JOptionPane.showOptionDialog(null, "A review with the same name already exists.",
                "Duplicate name", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                options[2]);

        if (choice == 0) {
            makeNewReview();
        } else if (choice == 1) {
            reviews.sortReviewNumber(reviews.getAllReviews());
            sfPanel.resetBox();
            listPanel.setListSelection(restaurant);
            editAdd.revalidate();
            editAdd.repaint();
        }
    }

    // MODIFIES: this, SortFilterPanel
    // EFFECTS: adds sort and filter panel to the frame and makes it visible
    public void customizeSortPanel(ReviewTrackerGUI app, ListPanel listPanel) {
        sfPanel = new SortFilterPanel(this, listPanel);
        sfPanel.setSize(new Dimension(400, 75));
        getContentPane().add(sfPanel, BorderLayout.NORTH);
        sfPanel.setVisible(true);
    }

    // MODIFIES: this, listPanel
    // EFFECTS: creates a new listPanel and adds it to this
    public void customizeListPanel() {
        listPanel = new ListPanel(this, reviews.getAllReviews());
        listPanel.setSize(new Dimension(400, HEIGHT - 75)); // 0.5 * WIDTH
        getContentPane().add(listPanel, BorderLayout.WEST);
        listPanel.setVisible(true);
    }

    // MODIFIES: this, TabPanel
    // EFFECTS: creates a new tab panel and adds it to this
    public void customizeTabPanel() {
        tabs = new TabPanel(this);
        tabs.setSize(new Dimension(75, HEIGHT));
        getContentPane().add(tabs, BorderLayout.EAST);
        tabs.setVisible(true);
    }

    // MODIFIES: this, EditandViewPanel
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
    }

    // EFFECTS: returns width of this frame
    public int getWidth() {
        return WIDTH;
    }

    // EFFECTS: returns height of this frame
    public int getHeight() {
        return HEIGHT;
    }

    // EFFECTS: returns reviews
    public Reviews getReviews() {
        return reviews;
    }

    // MODIFIES: this, Reviews, EditAddPanel, ListPanel
    // EFFECTS: deletes a restaurant review the application
    public void deleteRestaurant(Restaurant restaurant) {
        reviews.removeRestaurant(restaurant);
        editAdd.setVisible(false);
        listPanel.deleteFromJList();
    }
}
