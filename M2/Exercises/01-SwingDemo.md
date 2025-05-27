# Guided Hands-on Lab: Building a Java Swing GUI with JTable, CRUD, and Search

---

### Before You Begin: Setup Your Java Environment

If you have issues running Java due to admin access restrictions on your computer, follow this guide to run Java without admin privileges:  
➡️ [Running Java Without Admin Access](https://github.com/clydeatmcm/IT101-2/blob/main/M1/RunningJavaWithoutAdminAccess.md)

Make sure you have:
- JDK installed (Java 8 or higher recommended)
- A Java IDE or text editor (e.g., IntelliJ, Eclipse, VS Code)
- Basic understanding of Java programming and Swing

---

## Overview

In this lab, you will build a desktop GUI application using **Java Swing**. The app features:

- A form for data entry (Name, Subscription status, Gender, Comments, Date)
- A **JTable** displaying data entries
- CRUD operations (Create, Read, Update, Delete)
- Search functionality filtering table rows dynamically
- Menu with About and Exit options

---

## Step-by-step Guided Explanation and Code Walkthrough

### Step 1: Importing Necessary Libraries

```java
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Date;
````

* `javax.swing.*`: Core Swing GUI components
* `javax.swing.table.*`: Table-related classes
* `java.awt.*` & `java.awt.event.*`: Layouts and event handling
* `java.text.SimpleDateFormat`: Formatting dates
* `java.util.Date`: Date object manipulation

---

### Step 2: Declare Global Table Components

```java
static DefaultTableModel tableModel;
static JTable table;
```

* We declare `tableModel` and `table` globally to access them in different parts (for CRUD).

---

### Step 3: Create the Main Frame

```java
JFrame frame = new JFrame("Swing Demo with CRUD & Search - Guided Handson Lab in Java GUI");
frame.setSize(1000, 600);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new FlowLayout());
```

* `JFrame` is the main window.
* We set its title, size, and default close operation.
* Use `FlowLayout` to arrange components in a row, wrapping as needed.

---

### Step 4: Build the Form Components

```java
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
```

* Text field for name input.
* Checkbox for subscription status.
* Radio buttons for gender selection, grouped so only one can be selected.
* Text area for comments with scroll pane for overflow.
* Date spinner to pick a date.

---

### Step 5: Format Date

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date selectedDate = (Date) dateSpinner.getValue();
String dateStr = sdf.format(selectedDate);
```

* We format dates as `yyyy-MM-dd` strings for display and storage.
* This will be used when adding or updating rows.

---

### Step 6: Setup Menu Bar

```java
JMenuBar menuBar = new JMenuBar();
JMenu menu = new JMenu("Menu");

JMenuItem aboutItem = new JMenuItem("About the Code");
JMenuItem exitItem = new JMenuItem("Exit");
```

* Create a menu bar with a "Menu" containing "About the Code" and "Exit" options.

---

### Step 7: Add Search Field and Buttons

```java
JTextField searchField = new JTextField(15);

JButton addButton = new JButton("Add");
JButton updateButton = new JButton("Update");
JButton deleteButton = new JButton("Delete");
JButton clearButton = new JButton("Clear");
```

* Search field to filter table entries.
* Buttons for CRUD operations and clearing the form.

---

### Step 8: Prepare Table Data and Model

```java
String[] columns = {"Name", "Subscribed", "Gender", "Comments", "Date"};
Object[][] data = {
    {"Alice", true, "Female", "First entry", "2024-01-01"},
    {"Bob", false, "Male", "Second entry", "2024-02-01"},
    {"Charlie", true, "Male", "Another one", "2024-03-15"}
};

tableModel = new DefaultTableModel(data, columns) {
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) return Boolean.class; // Make "Subscribed" a checkbox column
        return String.class;
    }
};

table = new JTable(tableModel);
JScrollPane tableScroll = new JScrollPane(table);
table.setPreferredScrollableViewportSize(new Dimension(900, 100));
```

* We define columns and initial rows.
* Override `getColumnClass` so the "Subscribed" column shows checkboxes.
* Wrap the table inside a scroll pane.
* Set a preferred size to avoid it expanding too much.

---

### Step 9: Implement Search Filtering

```java
TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
table.setRowSorter(sorter);

searchField.addKeyListener(new KeyAdapter() {
    public void keyReleased(KeyEvent e) {
        String keyword = searchField.getText();
        if (keyword.trim().length() == 0) {
            sorter.setRowFilter(null); // Show all rows if empty
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword)); // Case-insensitive filter
        }
    }
});
```

* `TableRowSorter` allows dynamic sorting and filtering.
* We listen to keystrokes on the search field and update the filter accordingly.

---

### Step 10: Menu Actions

```java
aboutItem.addActionListener(e -> {
    String aboutText = "Guided Hands-on Lab in CCIS by ccrbalaman\nSwingDemo Example\nIncludes: JTable, CRUD, Search, Menu, Dialogs.";
    JOptionPane.showMessageDialog(frame, aboutText, "About the Code", JOptionPane.INFORMATION_MESSAGE);
});

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
```

* Clicking "About" shows an info dialog.
* Clicking "Exit" prompts for confirmation before closing.

---

### Step 11: Button Actions - Add

```java
addButton.addActionListener(e -> {
    String name = nameField.getText();
    boolean subscribed = subscribeBox.isSelected();
    String gender = maleRadio.isSelected() ? "Male" : (femaleRadio.isSelected() ? "Female" : "");
    String comments = commentArea.getText();

    if (!name.isEmpty() && !gender.isEmpty()) {
        tableModel.addRow(new Object[]{name, subscribed, gender, comments, dateStr});
        clearFields(nameField, subscribeBox, genderGroup, commentArea, dateSpinner);
    } else {
        JOptionPane.showMessageDialog(frame, "Name and Gender are required.");
    }
});
```

* Validates required fields.
* Adds new row to the table model.
* Clears the form after adding.

---

### Step 12: Button Actions - Update

```java
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
```

* Updates the selected row with form data.
* Converts the row index due to sorting/filtering.
* Shows message if no row is selected.

---

### Step 13: Button Actions - Delete

```java
deleteButton.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row >= 0) {
        row = table.convertRowIndexToModel(row);
        tableModel.removeRow(row);
    } else {
        JOptionPane.showMessageDialog(frame, "Select a row to delete.");
    }
});
```

* Deletes selected row from the table.
* Requires a selection, otherwise shows message.

---

### Step 14: Button Actions - Clear

```java
clearButton.addActionListener(e -> {
    clearFields(nameField, subscribeBox, genderGroup, commentArea, dateSpinner);
});
```

* Clears the form fields.

---

### Step 15: Populate Form on Table Row Click

```java
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
            Date date = sdf.parse((String) tableModel.getValueAt(row, 4));
            dateSpinner.setValue(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
});
```

* When a table row is clicked, form fields fill with the selected row's data.
* This allows easy updates.

---

### Step 16: Add Components to Frame and Show

```java
// Add menu items to menu and menu bar
menu.add(aboutItem);
menu.add(exitItem);
menuBar.add(menu);

// Add form fields, buttons, search, and table to the frame
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

// Set menu bar and make frame visible
frame.setJMenuBar(menuBar);
frame.setVisible(true);
```

* Organizes the UI components.
* Sets the menu bar.
* Displays the window.

---

### Step 17: Helper Method to Clear Fields

```java
static void clearFields(JTextField nameField, JCheckBox subscribeBox, ButtonGroup genderGroup, JTextArea commentArea, JSpinner dateSpinner) {
    nameField.setText("");
    subscribeBox.setSelected(false);
    genderGroup.clearSelection();
    commentArea.setText("");
    dateSpinner.setValue(new java.util.Date());
}
```

* Resets all input fields to their initial state.

---

# 🎉 Congratulations! You have built a functional Swing GUI with CRUD, search, and menu support!

---

# Challenge Exercises

Try to solve these challenges to deepen your understanding.

1. **Add Validation for Comments Field:**
   Ensure the comments do not exceed 200 characters. Show a warning if exceeded.

2. **Enable Multi-row Delete:**
   Modify the delete action to allow deleting multiple selected rows at once.

3. **Sort by Date:**
   Add functionality to sort the table by date column both ascending and descending.

4. **Add Export to CSV:**
   Add a menu item "Export to CSV" that saves current table data to a CSV file.

5. **Add a Reset Button for Search:**
   Add a button that clears the search field and resets the table filter instantly.

---
