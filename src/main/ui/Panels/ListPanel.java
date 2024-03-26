package ui.Panels;

import model.Restaurant;
import ui.ReviewTrackerGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

// References: https://youtu.be/c41tND6HJeY?si=TogCOtPYSgk6oMYh

public class ListPanel extends JPanel implements ListSelectionListener {

    private JList<Restaurant> listDisplay;
    private DefaultListModel listModel;
    private ReviewTrackerGUI app;

    // MODIFIES: this
    // EFFECTS: constructs and customizes JList
    public ListPanel(ReviewTrackerGUI app, List<Restaurant> reviews) {
        this.app = app;
        listModel = new DefaultListModel();
        for (Restaurant r : reviews) {
            System.out.println(r);
            listModel.addElement(r);
        }

        // dummy restaurants
//        listModel.addElement(new Restaurant("yo", false));
//        listModel.addElement(new Restaurant("hi", true));
//        listModel.addElement(new Restaurant("hello", true));
//        listModel.addElement(new Restaurant("asoid v", true));
//        listModel.addElement(new Restaurant("aslkdfj", true));

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
        listDisplay.setFont(new Font("Arial", Font.ITALIC, 50));
        listDisplay.setFixedCellHeight(100);
        listDisplay.setFixedCellWidth(300);
        listDisplay.setPreferredSize(new Dimension(300, 400));
        listDisplay.setVisibleRowCount(2);
        JScrollPane listScroller = new JScrollPane(listDisplay);
        add(listScroller);
    }

    public Restaurant selectedRestaurant() {
        return (Restaurant) listDisplay.getSelectedValue();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (listDisplay.getSelectedIndex() != -1) {
            app.displayResInfo(listDisplay.getSelectedValue());
        }
//        Restaurant selected = listDisplay.getSelectedValue();
//        System.out.println(selected);
//        Restaurant selected = (Restaurant) listDisplay.getSelectedValue();
    }
}
