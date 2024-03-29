package ui.panels;

import model.Food;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static java.lang.String.valueOf;

// References: https://youtu.be/22MBsRYuM4Q?si=1u2f3aA3RuT-jL9O

public class FoodTable extends JPanel {

    private JTable foodTable;
    private AbstractTableModel foodModel;
    private EditandViewPanel parent;

    public FoodTable(List<Food> foodList, EditandViewPanel parent) {
        this.parent = parent;
        foodModel = new TriedModel(foodList);
        foodTable = new JTable();
        foodTable.setModel(foodModel);
        foodModel.addTableModelListener(foodTable);
        //scroller = new JScrollPane();
        //scroller.setViewportView(foodTable);
        //add(scroller);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(foodTable.getTableHeader());
        add(foodTable);

        addEditFields(foodList);
    }

//        JPanel editTriedFields = new JPanel();
//        editTriedFields.setLayout(new BoxLayout(editTriedFields, BoxLayout.X_AXIS));
//
//        JTextField foodName = new JTextField(8);
//        JTextField foodPrice = new JTextField(3);
//        JTextField foodRating = new JTextField(3);
//        editTriedFields.add(foodName);
//        editTriedFields.add(foodPrice);
//        editTriedFields.add(foodRating);
//        add(editTriedFields);
//
//        foodTable.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int i = foodTable.getSelectedRow();
//                foodName.setText(foodList.get(i).getName());
//                foodPrice.setText(valueOf(foodList.get(i).getPrice()));
//                foodRating.setText(valueOf(foodList.get(i).getRating()));
//
//            }
//        });



//        if (foodList.size() != 0) {
//            if (foodList.get(0).getIsTried() == true) {
//                AbstractTableModel foodModel = new TriedModel(foodList);
//                foodTable = new JTable();
//                foodTable.setModel(foodModel);
//            } else {
//                AbstractTableModel foodModel = new WishModel(foodList);
//            }
//            //foodTable.setModel(foodModel);
//            //scroller = new JScrollPane();
//            //scroller.add(foodTable);
//            //add(scroller);
//            add(foodTable);
//        } else {
//            JPanel headings = new JPanel();
//            JLabel name = new JLabel("Name");
//            JLabel price = new JLabel("Price ($)");
//            JLabel rating = new JLabel("Rating");
//            headings.add(name);
//            headings.add(price);
//            headings.add(rating);
//            headings.setLayout(new GridLayout(1, 3, 0, 0));
//            add(headings);
//        }


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void addEditFields(List<Food> foodList) {
        JPanel editTriedFields = new JPanel();
        editTriedFields.setLayout(new BoxLayout(editTriedFields, BoxLayout.X_AXIS));
        JTextField foodName = new JTextField(8);
        JTextField foodPrice = new JTextField(3);
        JTextField foodRating = new JTextField(3);
        JButton btnDone = new JButton("Update");
        JButton btnAdd = new JButton("Add Food");
        editTriedFields.add(foodName);
        editTriedFields.add(foodPrice);
        editTriedFields.add(foodRating);
        editTriedFields.add(btnDone);
        editTriedFields.add(btnAdd);
        foodName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foodName.setText(foodName.getText());
            }
        });
        foodPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foodPrice.setText(foodPrice.getText());
            }
        });
        foodRating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foodRating.setText(foodRating.getText());
            }
        });

        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (foodTable.getSelectedRow() != -1) {
                    int i = foodTable.getSelectedRow();
                    //foodModel.isCellEditable(i, 0);
                    foodModel.setValueAt(foodName.getText(), i, 0);
                    foodModel.setValueAt(foodPrice.getText(), i, 1);
                    foodModel.setValueAt(foodRating.getText(), i, 2);
                    foodList.get(i).setName(foodName.getText());
                    foodList.get(i).setPrice(Double.parseDouble(foodPrice.getText()));
                    foodList.get(i).setRating(Double.parseDouble(foodRating.getText()));
                    parent.getRestaurant().createRating();
                    foodName.setText(" ");
                    foodPrice.setText(" ");
                    foodRating.setText(" ");
                    foodModel.fireTableCellUpdated(i, 0);
                    foodModel.fireTableCellUpdated(i, 1);
                    foodModel.fireTableCellUpdated(i, 2);
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Food newFood = new Food(foodName.getText(), Double.parseDouble(foodPrice.getText()), true);
                newFood.setRating(Double.parseDouble(foodRating.getText()));
                foodName.setText(" ");
                foodPrice.setText(" ");
                foodRating.setText(" ");
                foodList.add(newFood);
                parent.getRestaurant().createRating();
                //parent.resetEditView();
                parent.repaintEditable();
                //parent.editView();
                //revalidate(); //gets rid of add button
                //repaint(); //gets rid of add button
                //parent.revalidate();
                //parent.repaint();
                //parent.repaintEditable();
            }
        });

        add(editTriedFields);
        foodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = foodTable.getSelectedRow();
                foodName.setText(foodList.get(i).getName());
                foodPrice.setText(valueOf(foodList.get(i).getPrice()));
                foodRating.setText(valueOf(foodList.get(i).getRating()));
            }
        });
    }

}
