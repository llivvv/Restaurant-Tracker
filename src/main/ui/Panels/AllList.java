package ui.Panels;

import model.Restaurant;
import model.Reviews;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// References: https://youtu.be/c41tND6HJeY?si=TogCOtPYSgk6oMYh

public class AllList extends JPanel implements ListSelectionListener {

    private JList listDisplay;
    private DefaultListModel listModel;

    // MODIFIES: this
    // EFFECTS: constructs and customizes JList
    public AllList(List<Restaurant> reviews) {

        listModel = new DefaultListModel();
//        for (Restaurant r : reviews) {
//            listModel.addElement(r);
//        }

        listModel.addElement(new Restaurant("yo", false));
        listModel.addElement(new Restaurant("hi", true));

        listDisplay = new JList(listModel);
        customizeList();

        setOpaque(true);
        this.setBackground(new Color(205, 233, 243));
    }

    // MODIFIES: this
    // EFFECTS: customizes the view of the list and sets selection mode
    public void customizeList() {
        listDisplay.setSelectionBackground(new Color(255, 196, 196));
        listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDisplay.setVisibleRowCount(7);
        add(listDisplay);
        listDisplay.setFont(new Font("Arial", Font.ITALIC, 50));
        listDisplay.setFixedCellHeight(100);
        listDisplay.setFixedCellWidth(300);
        listDisplay.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
