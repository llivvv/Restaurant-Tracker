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

// Represents tried foods table with header and food names, prices, ratings,text fields and buttons to edit each food

public class FoodTable extends JPanel implements ActionListener {

    private JTable foodTable;
    private AbstractTableModel foodModel;
    private EditandViewPanel parent;
    private JTextField foodName;
    private JTextField foodPrice;
    private JTextField foodRating;
    private JButton btnDone;
    private JButton btnAdd;

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
        configureTextFields();
//        JTextField foodName = new JTextField(8);
//        JTextField foodPrice = new JTextField(3);
//        JTextField foodRating = new JTextField(3);
        configureButtons();
//        JButton btnDone = new JButton("Update");
//        JButton btnAdd = new JButton("Add Food");
        editTriedFields.add(foodName);
        editTriedFields.add(foodPrice);
        editTriedFields.add(foodRating);
        editTriedFields.add(btnDone);
        editTriedFields.add(btnAdd);
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
                    setValuesForRow(i);
                    //foodModel.isCellEditable(i, 0);
//                    foodModel.setValueAt(foodName.getText(), i, 0);
//                    foodModel.setValueAt(foodPrice.getText(), i, 1);
//                    foodModel.setValueAt(foodRating.getText(), i, 2);
                    alterFoodInstance(foodList, i);
//                    foodList.get(i).setName(foodName.getText());
//                    foodList.get(i).setPrice(Double.parseDouble(foodPrice.getText()));
//                    foodList.get(i).setRating(Double.parseDouble(foodRating.getText()));
                    parent.getRestaurant().createRating();
                    setTextBlank();
                    ensureTableUpdate(i);
//                    foodModel.fireTableCellUpdated(i, 0);
//                    foodModel.fireTableCellUpdated(i, 1);
//                    foodModel.fireTableCellUpdated(i, 2);
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Food newFood = new Food(foodName.getText(), Double.parseDouble(foodPrice.getText()), true);
                newFood.setRating(Double.parseDouble(foodRating.getText()));
                setTextBlank();
//                foodName.setText(" ");
//                foodPrice.setText(" ");
//                foodRating.setText(" ");
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
                tableIntoTextField(foodList, i);
//                foodName.setText(foodList.get(i).getName());
//                foodPrice.setText(valueOf(foodList.get(i).getPrice()));
//                foodRating.setText(valueOf(foodList.get(i).getRating()));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: places info about food in selected row, into the text fields
    public void tableIntoTextField(List<Food> foodList, int i) {
        foodName.setText(foodList.get(i).getName());
        foodPrice.setText(valueOf(foodList.get(i).getPrice()));
        foodRating.setText(valueOf(foodList.get(i).getRating()));
    }

    // REQUIRES: i != -1
    // MODIFIES: food
    // EFFECTS: sets the food values to what is in the text field
    public void alterFoodInstance(List<Food> foodList, int i) {
        foodList.get(i).setName(foodName.getText());
        foodList.get(i).setPrice(Double.parseDouble(foodPrice.getText()));
        foodList.get(i).setRating(Double.parseDouble(foodRating.getText()));
    }

    // MODIFIES: this
    // EFFECTS: constructs and adds action listeners to each text field
    public void configureTextFields() {
        foodName = new JTextField(8);
        foodPrice = new JTextField(3);
        foodRating = new JTextField(3);
        foodName.addActionListener(this);
        foodPrice.addActionListener(this);
        foodRating.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: constructs 2 buttons
    public void configureButtons() {
        btnDone = new JButton("Update");
        btnAdd = new JButton("Add Food");
    }

    // EFFECTS: handles user's input to the text fields
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == foodName) {
            foodName.setText(foodName.getText());
        } else if (e.getSource() == foodPrice) {
            foodPrice.setText(foodPrice.getText());
        } else if (e.getSource() == foodRating) {
            foodRating.setText(foodRating.getText());
        }
    }

    // REQUIRES: i != -1
    // MODIFIES: foodModel
    // EFFECTS: sets the table model values for all cells in row i
    public void setValuesForRow(int i) {
        foodModel.setValueAt(foodName.getText(), i, 0);
        foodModel.setValueAt(foodPrice.getText(), i, 1);
        foodModel.setValueAt(foodRating.getText(), i, 2);
    }

    // MODIFIES: this
    // EFFECTS: makes the text fields empty
    public void setTextBlank() {
        foodName.setText(" ");
        foodPrice.setText(" ");
        foodRating.setText(" ");
    }

    // REQUIRES: i != -1
    // EFFECTS: notifies table that cells in model have been updated
    public void ensureTableUpdate(int i) {
        foodModel.fireTableCellUpdated(i, 0);
        foodModel.fireTableCellUpdated(i, 1);
        foodModel.fireTableCellUpdated(i, 2);
    }


}
