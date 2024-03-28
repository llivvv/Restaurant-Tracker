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
    private DefaultTableModel wishModel;
    private JPanel parent;
    //private JScrollPane scroller;

    public FoodTable(List<Food> foodList, JPanel parent) {
        this.parent = parent;
        AbstractTableModel foodModel = new TriedModel(foodList);
        foodTable = new JTable();
        foodTable.setModel(foodModel);
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

    public void addEditFields(List<Food> foodList) {
        JPanel editTriedFields = new JPanel();
        editTriedFields.setLayout(new BoxLayout(editTriedFields, BoxLayout.X_AXIS));
        JTextField foodName = new JTextField(8);
        JTextField foodPrice = new JTextField(3);
        JTextField foodRating = new JTextField(3);
        JButton btnDone = new JButton("Done");
        editTriedFields.add(foodName);
        editTriedFields.add(foodPrice);
        editTriedFields.add(foodRating);
        editTriedFields.add(btnDone);
//        foodName.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                foodName.setText(foodName.getText());
//            }
//        });
//        foodPrice.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                foodPrice.setText(foodPrice.getText());
//            }
//        });
//        foodRating.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                foodRating.setText(foodRating.getText());
//            }
//        });

        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (foodTable.getSelectedRow() != -1) {
                    int i = foodTable.getSelectedRow();
                    foodModel.setValueAt(foodName.getText(), i, 0);
                    foodModel.setValueAt(foodPrice.getText(), i, 1);
                    foodModel.setValueAt(foodRating.getText(), i, 2);
                    foodList.get(i).setName(foodName.getText());
                    foodList.get(i).setPrice(Double.parseDouble(foodPrice.getText()));
                    foodList.get(i).setRating(Double.parseDouble(foodPrice.getText()));
                }
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
