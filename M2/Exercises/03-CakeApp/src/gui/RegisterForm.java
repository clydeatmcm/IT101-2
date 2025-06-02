package gui;

import utils.FileCRUDHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class RegisterForm extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;
    private FileCRUDHandler adminHandler;
    private LoginForm parentForm;
    
    public RegisterForm(LoginForm parent) {
        super(parent, "Register New Admin", true);
        this.parentForm = parent;
        adminHandler = new FileCRUDHandler("data/Admin.txt");
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(139, 69, 19));
        JLabel headerLabel = new JLabel("Register New Administrator", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        headerPanel.add(headerLabel);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField, gbc);
        
        // Full Name
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        fullNameField = new JTextField(20);
        formPanel.add(fullNameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(139, 69, 19));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.addActionListener(new RegisterActionListener());
        
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 220, 220));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        getRootPane().setDefaultButton(registerButton);
    }
    
    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            
            // Validation
            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterForm.this,
                    "Please fill in all fields.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(RegisterForm.this,
                    "Passwords do not match.",
                    "Password Error", JOptionPane.WARNING_MESSAGE);
                confirmPasswordField.setText("");
                return;
            }
            
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(RegisterForm.this,
                    "Password must be at least 6 characters long.",
                    "Password Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (isUsernameExists(username)) {
                JOptionPane.showMessageDialog(RegisterForm.this,
                    "Username already exists. Please choose a different username.",
                    "Username Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(RegisterForm.this,
                    "Please enter a valid email address.",
                    "Email Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Register new admin
            String adminRecord = username + "##" + password + "##" + fullName + "##" + email + "##" + LocalDate.now();
            
            if (adminHandler.append(adminRecord)) {
                JOptionPane.showMessageDialog(RegisterForm.this,
                    "Admin registered successfully!",
                    "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                
                parentForm.refreshAfterRegistration();
                dispose();
            } else {
                JOptionPane.showMessageDialog(RegisterForm.this,
                    "Registration failed. Please try again.",
                    "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean isUsernameExists(String username) {
        List<String> admins = adminHandler.readAll();
        for (String admin : admins) {
            String[] parts = admin.split("##");
            if (parts.length > 0 && parts[0].equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
}
