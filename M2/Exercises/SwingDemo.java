import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SwingDemo {
    // Define table components globally for CRUD access
    static DefaultTableModel tableModel;
    static JTable table;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Swing Demo with CRUD & Search - Guided Handson Lab in Java GUI");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // --- Form Fields ---
        JTextField nameField = new JTextField(10);
        JCheckBox subscribeBox = new JCheckBox("Subscribed");
        JRadioButton maleRadio = new JRadioButton("Male");
        JRadioButton femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        JTextArea commentArea = new JTextArea(3, 20);
        JScrollPane commentScroll = new JScrollPane(commentArea);
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        // dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = (Date) dateSpinner.getValue();
        String dateStr = sdf.format(selectedDate);        

        // --- Menu Bar ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem aboutItem = new JMenuItem("About the Code");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Search Field
        JTextField searchField = new JTextField(15);

        // Buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        // --- Table Setup ---
        String[] columns = {"Name", "Subscribed", "Gender", "Comments", "Date"};
        Object[][] data = {
            {"Alice", true, "Female", "First entry", "2024-01-01"},
            {"Bob", false, "Male", "Second entry", "2024-02-01"},
            {"Charlie", true, "Male", "Another one", "2024-03-15"}
        };

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Boolean.class;
                return String.class;
            }
        };

        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(900, 100));

        // Search filter logic
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String keyword = searchField.getText();
                if (keyword.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
                }
            }
        });

        // About Dialog
        aboutItem.addActionListener(e -> {
            String aboutText = "Guided Hands-on Lab in CCIS by ccrbalaman\nSwingDemo Example\nIncludes: JTable, CRUD, Search, Menu, Dialogs.";
            JOptionPane.showMessageDialog(frame, aboutText, "About the Code", JOptionPane.INFORMATION_MESSAGE);
        });

        // Exit Confirmation
        exitItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });        

        // Add Button Action
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            boolean subscribed = subscribeBox.isSelected();
            String gender = maleRadio.isSelected() ? "Male" : (femaleRadio.isSelected() ? "Female" : "");
            String comments = commentArea.getText();
            // Date selectedDate = (Date) dateSpinner.getValue();
            // String dateStr = sdf.format(selectedDate);

            if (!name.isEmpty() && !gender.isEmpty()) {
                tableModel.addRow(new Object[]{name, subscribed, gender, comments, dateStr});
                clearFields(nameField, subscribeBox, genderGroup, commentArea, dateSpinner);
            } else {
                JOptionPane.showMessageDialog(frame, "Name and Gender are required.");
            }
        });

        // Update Button Action
        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                row = table.convertRowIndexToModel(row);
                tableModel.setValueAt(nameField.getText(), row, 0);
                tableModel.setValueAt(subscribeBox.isSelected(), row, 1);
                tableModel.setValueAt(maleRadio.isSelected() ? "Male" : "Female", row, 2);
                tableModel.setValueAt(commentArea.getText(), row, 3);
                tableModel.setValueAt(dateStr, row, 4);
                clearFields(nameField, subscribeBox, genderGroup, commentArea, dateSpinner);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a row to update.");
            }
        });

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                row = table.convertRowIndexToModel(row);
                tableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a row to delete.");
            }
        });

        // Clear Form
        clearButton.addActionListener(e -> {
            clearFields(nameField, subscribeBox, genderGroup, commentArea, dateSpinner);
        });

        // Row click fills the form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                row = table.convertRowIndexToModel(row);
                nameField.setText((String) tableModel.getValueAt(row, 0));
                subscribeBox.setSelected((Boolean) tableModel.getValueAt(row, 1));
                String gender = (String) tableModel.getValueAt(row, 2);
                maleRadio.setSelected("Male".equals(gender));
                femaleRadio.setSelected("Female".equals(gender));
                commentArea.setText((String) tableModel.getValueAt(row, 3));
                try {
                    // Parse the stored date string back to Date for spinner
                    Date date = sdf.parse((String) tableModel.getValueAt(row, 4));
                    dateSpinner.setValue(date);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        // Add menu items to menu
        menu.add(aboutItem);
        menu.add(exitItem);
        menuBar.add(menu);

        // --- UI Layout ---
        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(subscribeBox);
        frame.add(new JLabel("Gender:"));
        frame.add(maleRadio);
        frame.add(femaleRadio);
        frame.add(new JLabel("Comments:"));
        frame.add(commentScroll);
        frame.add(new JLabel("Date:"));
        frame.add(dateSpinner);
        frame.add(addButton);
        frame.add(updateButton);
        frame.add(deleteButton);
        frame.add(clearButton);
        frame.add(new JLabel("Search:"));
        frame.add(searchField);
        frame.add(tableScroll);
        // Set the menu bar on the frame
        frame.setJMenuBar(menuBar);


        frame.setVisible(true);
    }

    // Helper method to clear the form fields
    static void clearFields(JTextField nameField, JCheckBox subscribeBox, ButtonGroup genderGroup, JTextArea commentArea, JSpinner dateSpinner) {
        nameField.setText("");
        subscribeBox.setSelected(false);
        genderGroup.clearSelection();
        commentArea.setText("");
        dateSpinner.setValue(new java.util.Date());
    }
}
