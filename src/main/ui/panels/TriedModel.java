package ui.panels;

import model.Food;

import javax.swing.table.AbstractTableModel;
import java.util.List;

// References: https://youtu.be/AD0GQxYOI_Y?si=V8ZBU5P7FzDFLCi1

public class TriedModel extends AbstractTableModel {

    private final String[] columns = {"Food Name", "Price ($)", "Rating"};
    private List<Food> foods;


    public TriedModel(List<Food> foodList) {
        this.foods = foodList;
    }

    @Override
    public int getRowCount() {
        return foods.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

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

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        } else {
            return Object.class;
        }
    }
}
