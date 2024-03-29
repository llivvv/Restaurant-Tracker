package ui.panels;

import model.Restaurant;
import ui.ReviewTrackerGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

// References: https://youtu.be/c41tND6HJeY?si=TogCOtPYSgk6oMYh
//             https://youtu.be/w7xiY0fc6cs?si=Z2hfMyfmPku6z17f

public class ListPanel extends JPanel implements ListSelectionListener {

    private JList<Restaurant> listDisplay;
    private DefaultListModel listModel;
    private ReviewTrackerGUI app;
    private List<Restaurant> currReviews;

    // MODIFIES: this
    // EFFECTS: constructs and customizes JList
    public ListPanel(ReviewTrackerGUI app, List<Restaurant> reviews) {
        this.app = app;
        currReviews = reviews;
        listModel = new DefaultListModel();
        for (Restaurant r : reviews) {
            System.out.println(r);
            listModel.addElement(r);
        }

        listDisplay = new JList(listModel);
        customizeList();

        setOpaque(true);
        this.setBackground(new Color(218, 245, 255));
    }

    // MODIFIES: this
    // EFFECTS: customizes the view of the list and sets selection mode, adds action listener
    public void customizeList() {
        listDisplay.setSelectionBackground(new Color(234, 219, 255));
        listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDisplay.addListSelectionListener(this);
        listDisplay.setFont(new Font("Arial", Font.ITALIC, 40));
        listDisplay.setFixedCellHeight(45);
        listDisplay.setFixedCellWidth(300);
        listDisplay.setPreferredSize(new Dimension(300, 400));
        listDisplay.setVisibleRowCount(8);
        JScrollPane listScroller = new JScrollPane(listDisplay);
        add(listScroller);
    }

    // MODIFIES: this
    // EFFECTS: deletes a review from the list view
    public void deleteFromJList() {
        int index = listDisplay.getSelectedIndex();
        listModel.removeElementAt(index);
    }

    // MODIFIES: this
    // EFFECTS: changes the list of reviews to be displayed
    public void changeReview(List<Restaurant> reviews) {
        listModel = new DefaultListModel();
        currReviews = reviews;
        for (Restaurant r : reviews) {
            System.out.println(r);
            listModel.addElement(r);
        }
        listDisplay.setModel(listModel);
    }

    // MODIFIES: this
    // EFFECTS: adds a new review to the list display
    public void addToJList(Restaurant restaurant) {
        listModel.addElement(restaurant);
    }

    // MODIFIES: this
    // EFFECTS: selects the list item that matches the given restaurant
    public void setListSelection(Restaurant restaurant) {
        for (Restaurant r : currReviews) {
            if (r.equals(restaurant)) {
                int i = currReviews.indexOf(r);
                listDisplay.setSelectedIndex(i);
                break;
            }
        }
    }

    // MODIFIES: ReviewTrackerGUI
    // EFFECTS: displays the information of a restaurant that has just been selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (listDisplay.getSelectedIndex() != -1) {
            app.displayResInfo(listDisplay.getSelectedValue());
        }
    }
}
