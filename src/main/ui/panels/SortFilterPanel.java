package ui.panels;

import ui.ReviewTrackerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// References: https://youtu.be/EAxV_eoYrIg?si=6eFVm2ZQ5bR0unmi

// Represents panel on top of screen that allows user to choose to see specific lists of reviews
public class SortFilterPanel extends JPanel implements ActionListener {

    String[] sortOptions = {"Sort All By Increasing Rating", "Sort Liked By Increasing Rating", "Sort Disliked By Increasing Rating",
            "Sort All By Most Recently Created", "Filter Liked", "Filter Disliked"};

    private JComboBox sortBox;
    private ReviewTrackerGUI app;
    private ListPanel listPanel;

    // MODIFIES: this
    // EFFECTS: constructs combo box and label
    public SortFilterPanel(ReviewTrackerGUI app, ListPanel listPanel) {
        this.app = app;

        JLabel chooseView = new JLabel("Choose List View: ");
        add(chooseView);
        this.listPanel = listPanel;
        sortBox = new JComboBox(sortOptions);
        sortBox.addActionListener(this);
        add(sortBox);
        //sortBox.setSelectedIndex(0);
    }

    // EFFECTS: returns the name of the selected option
    public String getSelectedBox() {
        String str = (String) sortBox.getSelectedItem();
        return str;
    }

    // EFFECTS: sets the selected box to Sort All By Most Recently Created
    public void resetBox() {
        sortBox.setSelectedItem("Sort All By Most Recently Created");
    }

    // MODIFIES: ListPanel
    // EFFECTS: changes the list view based on the combo box's selected option
    @Override
    public void actionPerformed(ActionEvent e) {
        if (getSelectedBox() == "Sort All By Increasing Rating") {
            app.getReviews().sortRating(app.getReviews().getAllReviews());
            listPanel.changeReview(app.getReviews().getAllReviews());
        } else if (getSelectedBox() == "Sort Liked By Increasing Rating") {
            app.getReviews().sortRating(app.getReviews().getLikedReviews());
            listPanel.changeReview(app.getReviews().getLikedReviews());
        } else if (getSelectedBox() == "Sort Disliked By Increasing Rating") {
            app.getReviews().sortRating(app.getReviews().getDislikedReviews());
            listPanel.changeReview(app.getReviews().getDislikedReviews());
        } else if (getSelectedBox() == "Sort All By Most Recently Created") {
            app.getReviews().sortReviewNumber(app.getReviews().getAllReviews());
            listPanel.changeReview(app.getReviews().getAllReviews());
        } else if (getSelectedBox() == "Filter Liked") {
            listPanel.changeReview(app.getReviews().getLikedReviews());
        } else {
            listPanel.changeReview(app.getReviews().getDislikedReviews());
        }
    }
}
