# Guided Hands-on Lab: Building a Task Tracker in Java Swing

> **Before You Begin:** If you're having issues running Java on a computer due to administrative access restrictions, refer to this guide: [Running Java Without Admin Access](https://github.com/clydeatmcm/IT101-2/blob/main/M1/RunningJavaWithoutAdminAccess.md).

---

## 📚 Overview

In this lab, you will build a professional-looking **Task Tracker Application** using Java Swing. This app allows users to add, update, delete, search, and display tasks. We'll break the code into manageable steps and explain each component.

By the end, you'll:

* Understand the use of various Swing components
* Apply event-driven programming in Java
* Customize a user interface with fonts and colors

---

## 📄 Step-by-Step Breakdown

### Step 1: Create the Main Frame

```java
JFrame frame = new JFrame("Task Tracker Application");
frame.setSize(1100, 650);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());
```

* `JFrame` is the main window.
* `BorderLayout` allows us to add components to specific regions.

---

### Step 2: Set Styling

```java
Font labelFont = new Font("Arial", Font.BOLD, 14);
Color bgColor = new Color(245, 245, 245);
```

* `Font` and `Color` are used to improve visual aesthetics.

---

### Step 3: Create the Form Panel

```java
JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
formPanel.setBorder(BorderFactory.createTitledBorder("Task Details"));
```

* `JPanel` groups related input fields.
* `GridLayout` organizes fields into rows and columns.

#### Input Components:

* `JTextField` for task name
* `JCheckBox` for priority
* `JRadioButton` for status (Pending or Completed)
* `JTextArea` for description
* `JSpinner` for due date (Date selection)

---

### Step 4: Group and Organize Fields

```java
ButtonGroup statusGroup = new ButtonGroup();
statusGroup.add(pendingRadio);
statusGroup.add(completedRadio);
```

* Groups radio buttons so only one can be selected at a time.

```java
formPanel.add(lblTask);
formPanel.add(taskField);
...
```

* Fields are added to the `formPanel` in a logical order.

---

### Step 5: Add Action Buttons

```java
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
buttonPanel.add(addBtn);
buttonPanel.add(updateBtn);
buttonPanel.add(deleteBtn);
buttonPanel.add(clearBtn);
```

* Buttons trigger actions like add, update, delete, and clear.

---

### Step 6: Create the Table to Display Tasks

```java
DefaultTableModel tableModel = new DefaultTableModel(data, columns);
JTable table = new JTable(tableModel);
```

* `JTable` displays all task records.
* `DefaultTableModel` defines how data is stored in the table.

```java
table.setRowSorter(new TableRowSorter<>(tableModel));
```

* Enables table filtering (for search).

---

### Step 7: Search Functionality

```java
searchField.addKeyListener(new KeyAdapter() {
    public void keyReleased(KeyEvent e) {
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
    }
});
```

* Enables real-time filtering of the table as the user types.

---

### Step 8: Menu Bar

```java
JMenuBar menuBar = new JMenuBar();
JMenu menu = new JMenu("Options");
JMenuItem about = new JMenuItem("About");
```

* Adds a basic menu with options like About and Exit.

---

### Step 9: Button Actions (Add, Update, Delete, Clear)

Each button has its own listener.

#### Add Task:

```java
addBtn.addActionListener(e -> {
    tableModel.addRow(new Object[]{...});
});
```

#### Update Task:

```java
updateBtn.addActionListener(e -> {
    int row = table.getSelectedRow();
    tableModel.setValueAt(...);
});
```

#### Delete Task:

```java
tableModel.removeRow(row);
```

#### Clear Form:

```java
clearForm(...);
```

---

### Step 10: Final Assembly

```java
frame.add(topPanel, BorderLayout.NORTH);
frame.add(searchPanel, BorderLayout.CENTER);
frame.add(tableScroll, BorderLayout.SOUTH);
frame.setVisible(true);
```

* Combines all UI sections into the final layout.

---

## 🎓 Challenge Exercises

Test your understanding with these challenges:

### Challenge 1:

**Add a new field** to the form for "Assigned To" (use `JTextField`) and include it in the table.

### Challenge 2:

**Highlight overdue tasks** in red. Use the current date to compare with the due date.

### Challenge 3:

**Save and load tasks** to/from a CSV file using `FileWriter` and `BufferedReader`.

### Challenge 4:

**Add task category filtering** using a `JComboBox` with categories like Work, Personal, School.

### Challenge 5:

**Add a progress tracker** using a `JProgressBar` that reflects the ratio of completed tasks.

---

## 🚀 Conclusion

You've built a fully functional task tracker using Java Swing and explored a variety of Swing components. Try the challenges to deepen your understanding, and feel free to expand your app further!

