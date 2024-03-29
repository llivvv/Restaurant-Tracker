package ui.panels;

import model.Food;

import javax.swing.table.AbstractTableModel;
import java.util.List;

// References: https://youtu.be/AD0GQxYOI_Y?si=V8ZBU5P7FzDFLCi1
//             methods were taken directly from this youtube video

// Represents the model used for the tried food JTable

public class TriedModel extends AbstractTableModel {

    private final String[] columns = {"Food Name", "Price ($)", "Rating"};
    private List<Food> foods;


    // MODIFIES: this
    // EFFECTS: constructs the model using a list of foods
    public TriedModel(List<Food> foodList) {
        this.foods = foodList;
    }

    // EFFECTS: counts number of rows needed based on size of food list
    @Override
    public int getRowCount() {
        return foods.size();
    }

    // EFFECTS: counts columns needed
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    // EFFECTS: returns value at a row and column of model
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return foods.get(rowIndex).getName();
        } else if (columnIndex == 1) {
            return foods.get(rowIndex).getPrice();
        } else {
            return foods.get(rowIndex).getRating();
        }
    }

    // EFFECTS: returns the header name of the column
    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }

    // EFFECTS: returns the class of the value in column
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        } else {
            return Object.class;
        }
    }
}
