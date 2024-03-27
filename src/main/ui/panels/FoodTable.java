package ui.panels;

import model.Food;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FoodTable extends JPanel {

    private JTable foodTable;
    private AbstractTableModel foodModel;
    private DefaultTableModel wishModel;
    //private JScrollPane scroller;

    public FoodTable(List<Food> foodList) {

        if (foodList.size() != 0) {
            if (foodList.get(0).getIsTried() == true) {
                AbstractTableModel foodModel = new TriedModel(foodList);
                foodTable = new JTable();
                foodTable.setModel(foodModel);
            } else {
                AbstractTableModel foodModel = new WishModel(foodList);
            }
            //foodTable.setModel(foodModel);
            //scroller = new JScrollPane();
            //scroller.add(foodTable);
            //add(scroller);
            add(foodTable);
        } else {
            JPanel headings = new JPanel();
            JLabel name = new JLabel("Name");
            JLabel price = new JLabel("Price ($)");
            JLabel rating = new JLabel("Rating");
            headings.add(name);
            headings.add(price);
            headings.add(rating);
            headings.setLayout(new GridLayout(1, 3, 0, 0));
            add(headings);
        }
    }
}
