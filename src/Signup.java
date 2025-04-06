import java.awt.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Signup extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> accountTypeComboBox;
    private JButton signupButton, backButton;

    public Signup() {
        // Create frame
        setTitle("VCRTS Portal Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        // Username label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 25);
        userLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(userLabel);

        // Username field
        usernameField = new JTextField(15);
        usernameField.setBounds(150, 50, 200, 25);
        add(usernameField);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 90, 100, 25);
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(passwordLabel);

        // Password field
        passwordField = new JPasswordField(15);
        passwordField.setBounds(150, 90, 200, 25);
        add(passwordField);

        // Account type label
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(50, 130, 100, 25);
        accountTypeLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(accountTypeLabel);

        // Account type combo box
        accountTypeComboBox = new JComboBox<>(new String[]{"Vehicle Owner", "Job Owner"});
        accountTypeComboBox.setBounds(150, 130, 200, 25);
        add(accountTypeComboBox);

        // Sign Up button
        signupButton = new JButton("Sign Up");
        signupButton.setBounds(150, 170, 90, 25);
        signupButton.setPreferredSize(new Dimension(140, 30));
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(signupButton);

        // Add action listener to the Sign Up button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean isVehicleOwner = accountTypeComboBox.getSelectedIndex() == 0;

                // Check if the username already exists
                if (isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(Signup.this, "Username already taken. Please choose another.");
                } else {
                    // Save account to CSV
                    saveAccountToCSV(username, password, isVehicleOwner);
                    JOptionPane.showMessageDialog(Signup.this, "Account created successfully!");
                    dispose(); // Close the signup window
                }
            }
        });
        
        //back button to initial screen
        backButton = new JButton("Back");
        backButton.setBounds(50, 200, 90, 25);
        backButton.addActionListener(e -> {
            dispose();
            new InitialScreen();
        });
        add(backButton);
    }

    // Check if the username already exists in Accounts.csv
    private boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(username)) {
                    return true; // Username already exists
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts: " + e.getMessage());
        }
        return false; // Username is available
    }

    // Save account to CSV
    private void saveAccountToCSV(String username, String password, boolean isVehicleOwner) {
        try (FileWriter writer = new FileWriter("Accounts.csv", true)) {
            writer.append("1,") // Assuming the first column is an ID (can be modified)
                  .append(username)
                  .append(",")
                  .append(password)
                  .append(",")
                  .append(String.valueOf(isVehicleOwner))
                  .append("\n");
        } catch (IOException e) {
            System.err.println("Error saving account: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Signup().setVisible(true);
            }
        });
    }
}