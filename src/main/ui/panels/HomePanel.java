package ui.panels;

import ui.ReviewTrackerGUI;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private JButton newStart;
    private JButton loadStart;
    private JLabel welcomeText;
    private JLabel bgImage;
    private ReviewTrackerGUI app;

    public HomePanel(ReviewTrackerGUI app) {
        setLayout(new GridLayout(2, 1, 0, 10));
        this.app = app;
        welcomeText = new JLabel("Welcome!");
        //ImageIcon img = new ImageIcon("home.png");
        //bgImage = new JLabel(img);
        //customizeImage();
        newStart = new JButton("New Set of Reviews");
        loadStart = new JButton("Load and add to my existing reviews");

        customizeWelcome();
        customizeButtons();



        setBackground(new Color(240, 232, 250));
    }

    public void customizeImage() {
        bgImage.setPreferredSize(new Dimension(app.getWidth(), app.getHeight()));
        add(bgImage, BorderLayout.CENTER);
    }

    public void customizeWelcome() {
        welcomeText.setFont(new Font("Impact", Font.BOLD, 30));
        welcomeText.setForeground(new Color(41, 24, 66));
        add(welcomeText);
    }

    public void customizeButtons() {
        newStart.setFont(new Font("Arial", Font.ITALIC, 20));
        newStart.setBackground(new Color(164, 236, 245));
        newStart.setPreferredSize(new Dimension(300, 60));
        newStart.addActionListener(app);
        add(newStart);
        loadStart.setFont(new Font("Arial", Font.ITALIC, 20));
        loadStart.setBackground(new Color(164, 236, 245));
        loadStart.setPreferredSize(new Dimension(300, 60));
        loadStart.addActionListener(app);
        add(loadStart);
    }

    public JButton getNewStartButton() {
        return newStart;
    }

    public JButton getLoadStartButton() {
        return loadStart;
    }
}
