package gui;

import model.Cake;
import utils.FileCRUDHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CakeManager extends JPanel {
    private JTable cakeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, updateButton, deleteButton, searchButton, clearButton;
    private FileCRUDHandler cakeHandler;
    private TableRowSorter<DefaultTableModel> sorter;
    
    // Form fields for add/update
    private JTextField cakeIdField, nameField, descriptionField, priceField;
    private JComboBox<String> statusCombo, categoryCombo;
    
    public CakeManager() {
        cakeHandler = new FileCRUDHandler("data/Cakes.txt");
        initializeComponents();
        loadCakeData();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Cake Management"));
        
        // Top Panel - Search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search:"));
        
        searchField = new JTextField(20);
        searchField.addActionListener(e -> performSearch());
        topPanel.add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        topPanel.add(searchButton);
        
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearSearch());
        topPanel.add(clearButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center Panel - Table
        String[] columnNames = {"Cake ID", "Name", "Description", "Price", "Status", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        cakeTable = new JTable(tableModel);
        cakeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cakeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.get
