package ui.panels;

import ui.ReviewTrackerGUI;

import javax.swing.*;
import java.awt.*;

// Represents a panel having a button to create a new review

public class TabPanel extends Panel {

    private JButton btnCreate;
    private ReviewTrackerGUI app;

    // MODIFIES: this
    // EFFECTS: constructs panel with button added
    public TabPanel(ReviewTrackerGUI app) {

        this.app = app;
        btnCreate = new JButton(" + ");

        configureButtons();
        add(btnCreate);

        setBackground(new Color(133, 99, 81));
    }

    // MODIFIES: this
    // EFFECTS: adds action listener and sets this button's preferred size
    public void configureButtons() {
        btnCreate.addActionListener(app);
        btnCreate.setPreferredSize(new Dimension(60, 20));
    }

    // EFFECTS: returns btnCreate
    public JButton getBtnCreate() {
        return btnCreate;
    }

}
