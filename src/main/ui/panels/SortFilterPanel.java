package ui.panels;

import ui.ReviewTrackerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// References: https://youtu.be/EAxV_eoYrIg?si=6eFVm2ZQ5bR0unmi

public class SortFilterPanel extends JPanel implements ActionListener {

    String[] sortOptions = {"Sort All", "Sort Liked", "Sort Disliked", "Sort By Most Recently Created", "Filter Liked",
        "Filter Disliked"};

    private JComboBox sortBox;
    private ReviewTrackerGUI app;
    private ListPanel listPanel;

    public SortFilterPanel(ReviewTrackerGUI app, ListPanel listPanel) {
        this.app = app;

        JLabel chooseView = new JLabel("Choose List View: ");
        add(chooseView);
        this.listPanel = listPanel;
        sortBox = new JComboBox(sortOptions);
        sortBox.addActionListener(this);
        add(sortBox);
        sortBox.setSelectedIndex(0);
    }

    public String getSelectedBox() {
        String str = (String) sortBox.getSelectedItem();
        return str;
    }

    public JComboBox getSortBox() {
        return sortBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getSelectedBox() == "Sort All") {
            app.getReviews().sortRating(app.getReviews().getAllReviews());
            listPanel.changeReview(app.getReviews().getAllReviews());
        } else if (getSelectedBox() == "Sort Liked") {
            app.getReviews().sortRating(app.getReviews().getLikedReviews());
            listPanel.changeReview(app.getReviews().getLikedReviews());
        } else if (getSelectedBox() == "Sort Disliked") {
            app.getReviews().sortRating(app.getReviews().getDislikedReviews());
            listPanel.changeReview(app.getReviews().getDislikedReviews());
        } else if (getSelectedBox() == "Sort By Most Recently Created") {
            app.getReviews().sortReviewNumber(app.getReviews().getAllReviews());
            listPanel.changeReview(app.getReviews().getAllReviews());
        } else if (getSelectedBox() == "Filter Liked") {
            listPanel.changeReview(app.getReviews().getLikedReviews());
        } else {
            listPanel.changeReview(app.getReviews().getDislikedReviews());
        }
    }
}
