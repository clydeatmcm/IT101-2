import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTrackerApp {
    static DefaultTableModel tableModel;
    static JTable table;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Tracker Application");
        frame.setSize(1100, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Set custom font and color
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color bgColor = new Color(245, 245, 245);
        Color headerColor = new Color(30, 144, 255);

        // --- Panel for Form ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Task Details"));
        formPanel.setBackground(bgColor);

        JTextField taskField = new JTextField(15);
        JCheckBox priorityBox = new JCheckBox("High Priority");
        JRadioButton pendingRadio = new JRadioButton("Pending");
        JRadioButton completedRadio = new JRadioButton("Completed");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(pendingRadio);
        statusGroup.add(completedRadio);
        JTextArea descArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descArea);
        JSpinner dueDateSpinner = new JSpinner(new SpinnerDateModel());

        JLabel lblTask = new JLabel("Task Name:");
        JLabel lblStatus = new JLabel("Status:");
        JLabel lblDesc = new JLabel("Description:");
        JLabel lblDate = new JLabel("Due Date:");

        for (JLabel lbl : new JLabel[]{lblTask, lblStatus, lblDesc, lblDate}) {
            lbl.setFont(labelFont);
        }

        formPanel.add(lblTask);
        formPanel.add(taskField);
        formPanel.add(new JLabel(""));
        formPanel.add(priorityBox);
        formPanel.add(lblStatus);
        JPanel statusPanel = new JPanel();
        statusPanel.add(pendingRadio);
        statusPanel.add(completedRadio);
        statusPanel.setBackground(bgColor);
        formPanel.add(statusPanel);
        formPanel.add(lblDesc);
        formPanel.add(descScroll);
        formPanel.add(lblDate);
        formPanel.add(dueDateSpinner);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addBtn = new JButton("Add Task");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        // --- Table and Search ---
        String[] columns = {"Task", "Priority", "Status", "Description", "Due Date"};
        Object[][] data = {
            {"Design UI", true, "Pending", "Create UI layout for app", "2025-06-01"},
            {"Write Code", false, "Completed", "Develop the core logic", "2025-06-05"}
        };

        tableModel = new DefaultTableModel(data, columns) {
            public Class<?> getColumnClass(int column) {
                if (column == 1) return Boolean.class;
                return String.class;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(1000, 150));

        JTextField searchField = new JTextField(20);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText();
                sorter.setRowFilter(text.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(labelFont);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // --- Menu Bar ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem about = new JMenuItem("About");
        JMenuItem exit = new JMenuItem("Exit");

        about.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                "Task Tracker Application\nCreated with Java Swing.\nIncludes table, form, CRUD, and search.",
                "About", JOptionPane.INFORMATION_MESSAGE);
        });

        exit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Exit application?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        menu.add(about);
        menu.add(exit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // --- Button Actions ---
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        addBtn.addActionListener(e -> {
            String task = taskField.getText();
            boolean priority = priorityBox.isSelected();
            String status = pendingRadio.isSelected() ? "Pending" : completedRadio.isSelected() ? "Completed" : "";
            String desc = descArea.getText();
            String date = sdf.format((Date) dueDateSpinner.getValue());

            if (!task.isEmpty() && !status.isEmpty()) {
                tableModel.addRow(new Object[]{task, priority, status, desc, date});
                clearForm(taskField, priorityBox, statusGroup, descArea, dueDateSpinner);
            } else {
                JOptionPane.showMessageDialog(frame, "Task and Status are required.");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                row = table.convertRowIndexToModel(row);
                tableModel.setValueAt(taskField.getText(), row, 0);
                tableModel.setValueAt(priorityBox.isSelected(), row, 1);
                tableModel.setValueAt(pendingRadio.isSelected() ? "Pending" : "Completed", row, 2);
                tableModel.setValueAt(descArea.getText(), row, 3);
                tableModel.setValueAt(sdf.format((Date) dueDateSpinner.getValue()), row, 4);
                clearForm(taskField, priorityBox, statusGroup, descArea, dueDateSpinner);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a task to update.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                row = table.convertRowIndexToModel(row);
                tableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a task to delete.");
            }
        });

        clearBtn.addActionListener(e -> {
            clearForm(taskField, priorityBox, statusGroup, descArea, dueDateSpinner);
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                row = table.convertRowIndexToModel(row);
                taskField.setText((String) tableModel.getValueAt(row, 0));
                priorityBox.setSelected((Boolean) tableModel.getValueAt(row, 1));
                String status = (String) tableModel.getValueAt(row, 2);
                pendingRadio.setSelected("Pending".equals(status));
                completedRadio.setSelected("Completed".equals(status));
                descArea.setText((String) tableModel.getValueAt(row, 3));
                try {
                    dueDateSpinner.setValue(sdf.parse((String) tableModel.getValueAt(row, 4)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // --- Final Layout ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(searchPanel, BorderLayout.CENTER);
        frame.add(tableScroll, BorderLayout.SOUTH);

        frame.getContentPane().setBackground(bgColor);
        frame.setVisible(true);
    }

    static void clearForm(JTextField taskField, JCheckBox priorityBox, ButtonGroup statusGroup, JTextArea descArea, JSpinner dueDateSpinner) {
        taskField.setText("");
        priorityBox.setSelected(false);
        statusGroup.clearSelection();
        descArea.setText("");
        dueDateSpinner.setValue(new Date());
    }
}
