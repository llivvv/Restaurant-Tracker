package ui;

import java.io.FileNotFoundException;

// runs the calls and runs the RestaurantTrackerGUI
// references https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class Main {
    public static void main(String[] args) {
        try {
            //new RestaurantTrackerApp();
            new ReviewTrackerGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
