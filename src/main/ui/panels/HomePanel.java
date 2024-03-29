package ui.panels;

import ui.ReviewTrackerGUI;

import javax.swing.*;
import java.awt.*;

// Represents the homepage the user first sees when opening the application
public class HomePanel extends JPanel {

    private JButton newStart;
    private JButton loadStart;
    private JLabel welcomeText;
    private ReviewTrackerGUI app;

    // EFFECTS: constructs the homepage with 2 buttons
    public HomePanel(ReviewTrackerGUI app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.app = app;
        welcomeText = new JLabel("Welcome!");
        newStart = new JButton("New Set of Reviews");
        loadStart = new JButton("Load and add to my existing reviews");

        customizeWelcome();
        customizeButtons();

        setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: sets the font, colour and size of the welcome text and adds it to the panel
    public void customizeWelcome() {
        welcomeText.setFont(new Font("Impact", Font.BOLD, 30));
        welcomeText.setForeground(new Color(41, 24, 66));
        welcomeText.setPreferredSize(new Dimension(300, 60));
        add(welcomeText);
    }

    // MODIFIES: this
    // EFFECTS: sets the colour, font and sizes of the 2 buttons and adds them to the panel
    public void customizeButtons() {
        newStart.setFont(new Font("Arial", Font.ITALIC, 20));
        newStart.setBackground(new Color(164, 236, 245, 255));
        newStart.setPreferredSize(new Dimension(300, 60));
        newStart.addActionListener(app);
        add(newStart);
        loadStart.setFont(new Font("Arial", Font.ITALIC, 20));
        loadStart.setBackground(new Color(164, 236, 245, 255));
        loadStart.setPreferredSize(new Dimension(300, 60));
        loadStart.addActionListener(app);
        add(loadStart);
    }

    // EFFECTS: returns the newStart button
    public JButton getNewStartButton() {
        return newStart;
    }

    // EFFECTS: returns the loadStart button
    public JButton getLoadStartButton() {
        return loadStart;
    }
}
