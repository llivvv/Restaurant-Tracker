package ui;

import model.Reviews;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.Panels.*;

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

public class ReviewTrackerGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/reviews.json";
    private static final String JSON_DUMMY = "./data/testReaderGeneralReviews.json";
    private Reviews reviews;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private HomePanel homePage;
    private LikedList listLiked;
    private DislikedList listDisliked;
    private ListPanel listPanel;
    private EditAddPanel editAdd;
    private TabPanel diffPages;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    // MODIFIES: this
    // EFFECTS:
    public ReviewTrackerGUI() throws FileNotFoundException {
        super("Review Tracker");
        setSize(WIDTH, HEIGHT);
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
        }
    }

    // MODIFIES: this
    // EFFECTS: makes homePage invisible, processes to load or not load existing reviews
    public void leaveHomePage(ActionEvent e) {
        if (e.getSource() == homePage.getLoadStartButton()) {
            loadReviews();
        }
        homePage.setVisible(false);
        listPanel = new ListPanel(reviews.getAllReviews());
        //add(listAll, BorderLayout.WEST);
        listPanel.setPreferredSize(new Dimension((int) (0.5 * WIDTH), HEIGHT));
        getContentPane().add(listPanel, BorderLayout.WEST);
        listPanel.setVisible(true);
    }

    // EFFECTS: returns dimensions of this frame
    public Dimension getDimensions() {
        return new Dimension(WIDTH, HEIGHT);
    }
}
