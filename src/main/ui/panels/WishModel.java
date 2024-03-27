package ui.panels;

import model.Food;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WishModel extends AbstractTableModel {
    private final String[] columns = {"Food Name"};
    private List<Food> foods;


    public WishModel(List<Food> foodList) {
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
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
}
