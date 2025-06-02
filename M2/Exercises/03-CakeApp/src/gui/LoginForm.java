package gui;

import utils.FileCRUDHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private FileCRUDHandler adminHandler;
    
    public LoginForm() {
        adminHandler = new FileCRUDHandler("data/Admin.txt");
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("CJ's Cake Boutique - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(139, 69, 19));
        JLabel headerLabel = new JLabel("CJ's Cake Boutique", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(headerLabel);
        
        // Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        loginPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginPanel.add(passwordField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(139, 69, 19));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.addActionListener(new LoginActionListener());
        
        registerButton = new JButton("Register New Admin");
        registerButton.setBackground(new Color(210, 180, 140));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        registerButton.addActionListener(e -> openRegisterForm());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);
        
        add(headerPanel, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);
        
        // Footer
        JLabel footerLabel = new JLabel("Welcome to CJ's Cake Management System", JLabel.CENTER);
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Enter key support
        getRootPane().setDefaultButton(loginButton);
    }
    
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginForm.this, 
                    "Please enter both username and password.", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(LoginForm.this, 
                    "Login successful! Welcome to CJ's Cake Boutique.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                SwingUtilities.invokeLater(() -> {
                    new Dashboard(username).setVisible(true);
                    LoginForm.this.dispose();
                });
            } else {
                JOptionPane.showMessageDialog(LoginForm.this, 
                    "Invalid username or password.", 
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        }
    }
    
    private boolean authenticateUser(String username, String password) {
        List<String> admins = adminHandler.readAll();
        for (String admin : admins) {
            String[] parts = admin.split("##");
            if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    private void openRegisterForm() {
        new RegisterForm(this).setVisible(true);
    }
    
    public void refreshAfterRegistration() {
        usernameField.setText("");
        passwordField.setText("");
        usernameField.requestFocus();
    }
}
