package ui.panels;

import ui.ReviewTrackerGUI;

import javax.swing.*;
import java.awt.*;

// Represents a panel having a button to create a new review and returning to homepage
public class TabPanel extends Panel {

    private JButton btnCreate;
    private JButton btnHome;
    private ReviewTrackerGUI app;

    public TabPanel(ReviewTrackerGUI app) {

        this.app = app;
        btnCreate = new JButton(" + ");
        btnHome = new JButton(" Home ");

        configureButtons();
        add(btnCreate);
        //add(btnHome);

        setBackground(new Color(161, 119, 100));
    }

    public void configureButtons() {
        btnCreate.addActionListener(app);
        btnCreate.setPreferredSize(new Dimension(60, 20));
        //btnHome.addActionListener(app);
    }

    public JButton getBtnCreate() {
        return btnCreate;
    }

//    public JButton getBtnHome() {
//        return btnHome;
//    }
}
