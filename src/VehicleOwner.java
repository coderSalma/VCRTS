import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VehicleOwner {

    public static void main(String[] args) {
        new MainMenuScreen();
    }
    
    public static void getMainScreen() {
        new MainMenuScreen();
    }
    
    // Fields for a vehicle record
    private String ownerID;
    private String vehicleID;
    private String model;
    private String vin;
    private int residencyTime; // in hours

    public VehicleOwner() {
    }
    
    public VehicleOwner(String ownerID, String vehicleID, String model, String vin, int residencyTime) {
        this.ownerID = ownerID;
        this.vehicleID = vehicleID;
        this.model = model;
        this.vin = vin;
        this.residencyTime = residencyTime;
    }
    
    // Save vehicle details to a CSV file with a timestamp
    private static void saveVehicleToCSV(String ownerID, String vehicleID, String model, String vin, int residencyTime) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String fileName = "Vehicles.csv";  

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(ownerID + "," + vehicleID + "," + model + "," + vin + "," + residencyTime + "," + timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Main Menu Screen for Vehicle Owner Options
    public static class MainMenuScreen {
        private JFrame frame;
        private JButton newVehicleButton, updateVehicleButton, viewVehicleButton;
        private JLabel mainMenuLabel;
        private JPanel panel;

        public MainMenuScreen() {
            frame = new JFrame("Select Vehicle Option");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            mainMenuLabel = new JLabel("Welcome to the Vehicle Management System", SwingConstants.CENTER);
            mainMenuLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
            mainMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            newVehicleButton = new JButton("New Vehicle");
            updateVehicleButton = new JButton("Update Vehicle");
            viewVehicleButton = new JButton("View Vehicle");

            newVehicleButton.setPreferredSize(new Dimension(140, 30));
            updateVehicleButton.setPreferredSize(new Dimension(140, 30));
            viewVehicleButton.setPreferredSize(new Dimension(140, 30));

            newVehicleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            updateVehicleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            viewVehicleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            newVehicleButton.addActionListener(e -> {
                frame.dispose();
                new NewVehicleScreen();
            });

            updateVehicleButton.addActionListener(e -> {
                frame.dispose();
                new UpdateVehicleScreen();
            });

            viewVehicleButton.addActionListener(e -> {
                frame.dispose();
                new ViewVehicleScreen();
            });

            panel.add(mainMenuLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(newVehicleButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(updateVehicleButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(viewVehicleButton);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }
    
    // New Vehicle Screen - prompts the owner for vehicle details
    public static class NewVehicleScreen {
        private JFrame frame;
        private JLabel instructions, ownerIDLabel, vehicleIDLabel, modelLabel, vinLabel, residencyTimeLabel;
        private JTextField ownerIDField, vehicleIDField, modelField, vinField, residencyTimeField;
        private JButton submitButton, backButton;
        private JPanel panel;

        public NewVehicleScreen() {
            frame = new JFrame("New Vehicle Submission");
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            instructions = new JLabel("<html>Enter the details of your vehicle below:</html>", SwingConstants.CENTER);
            instructions.setFont(new Font("Times New Roman", Font.BOLD, 16));
            instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

            //owner id panel
            ownerIDLabel = new JLabel("Owner ID:");
            ownerIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            ownerIDField = new JTextField();
            ownerIDField.setMaximumSize(new Dimension(800, 30));
            ownerIDField.setPreferredSize(new Dimension(250, 30));
      
            JPanel ownerIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ownerIDLabel.setPreferredSize(new Dimension(250, 30));
            ownerIDPanel.add(ownerIDLabel);
            ownerIDPanel.add(ownerIDField);
            
            //vehicle id panel
            vehicleIDLabel = new JLabel("Vehicle ID:");
            vehicleIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            vehicleIDField = new JTextField();
            vehicleIDField.setMaximumSize(new Dimension(800, 30));
            vehicleIDField.setPreferredSize(new Dimension(250, 30));

            JPanel vehicleIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            vehicleIDLabel.setPreferredSize(new Dimension(250, 30));
            vehicleIDPanel.add(vehicleIDLabel);
            vehicleIDPanel.add(vehicleIDField);

            //model panel
            modelLabel = new JLabel("Model:");
            modelLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            modelField = new JTextField();
            modelField.setMaximumSize(new Dimension(800, 30));
            modelField.setPreferredSize(new Dimension(250, 30));
 
            JPanel modelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            modelLabel.setPreferredSize(new Dimension(250, 20));
            modelPanel.add(modelLabel);
            modelPanel.add(modelField);

            //vin panel
            vinLabel = new JLabel("VIN:");
            vinLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            vinField = new JTextField();
            vinField.setMaximumSize(new Dimension(800, 30));
            vinField.setPreferredSize(new Dimension(250, 20));

            JPanel vinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            vinLabel.setPreferredSize(new Dimension(250, 30));
            vinPanel.add(vinLabel);
            vinPanel.add(vinField);
            
            //residency panel
            residencyTimeLabel = new JLabel("Residency Time (hrs):");
            residencyTimeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            residencyTimeField = new JTextField();
            residencyTimeField.setMaximumSize(new Dimension(800, 30));
            residencyTimeField.setPreferredSize(new Dimension(250, 30));

            JPanel residencyTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            residencyTimeLabel.setPreferredSize(new Dimension(250, 30));
            residencyTimePanel.add(residencyTimeLabel);
            residencyTimePanel.add(residencyTimeField);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            submitButton = new JButton("Submit");
            backButton = new JButton("Back");
            buttonPanel.add(backButton);
            buttonPanel.add(submitButton);
            panel.add(buttonPanel);


            submitButton.addActionListener(e -> {
                String ownerID = ownerIDField.getText().trim();
                String vehicleID = vehicleIDField.getText().trim();
                String model = modelField.getText().trim();
                String vin = vinField.getText().trim();
                int residencyTime;
                try {
                    residencyTime = Integer.parseInt(residencyTimeField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Residency Time must be an integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Save vehicle details to CSV
                saveVehicleToCSV(ownerID, vehicleID, model, vin, residencyTime);
                JOptionPane.showMessageDialog(frame, "Vehicle Saved Successfully!");

                ownerIDField.setText("");
                vehicleIDField.setText("");
                modelField.setText("");
                vinField.setText("");
                residencyTimeField.setText("");
            });

            backButton.addActionListener(e -> {
                frame.dispose();
                new MainMenuScreen();
            });

            panel.add(instructions);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(ownerIDPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(vehicleIDPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(modelPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(vinPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(residencyTimePanel);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(submitButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(backButton);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }
    
    // Update Vehicle Screen - allows the owner to update an existing vehicle record.
    public static class UpdateVehicleScreen {
        private JFrame frame;
        private JLabel instructions, ownerIDLabel, vehicleIDLabel, modelLabel, vinLabel, residencyTimeLabel;
        private JTextField ownerIDField, vehicleIDField, modelField, vinField, residencyTimeField;
        private JButton updateButton, backButton;
        private JPanel panel;

        public UpdateVehicleScreen() {
            frame = new JFrame("Update Vehicle Information");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            instructions = new JLabel("<html>Enter the updated details of your vehicle below:</html>", SwingConstants.CENTER);
            instructions.setFont(new Font("Times New Roman", Font.BOLD, 16));
            instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

            //owner id panel
            ownerIDLabel = new JLabel("Owner ID:");
            ownerIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            ownerIDField = new JTextField();
            ownerIDField.setMaximumSize(new Dimension(800, 30));
            ownerIDField.setPreferredSize(new Dimension(250, 30));
            JPanel ownerIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ownerIDLabel.setPreferredSize(new Dimension(200, 30));
            ownerIDPanel.add(ownerIDLabel);
            ownerIDPanel.add(ownerIDField);

            //vehicle id panel
            vehicleIDLabel = new JLabel("Vehicle ID:");
            vehicleIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            vehicleIDField = new JTextField(20);
            vehicleIDField.setMaximumSize(new Dimension(800, 30));
            vehicleIDField.setPreferredSize(new Dimension(250, 30));
            JPanel vehicleIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            vehicleIDLabel.setPreferredSize(new Dimension(200, 30));
            vehicleIDPanel.add(vehicleIDLabel);
            vehicleIDPanel.add(vehicleIDField);

            //model panel
            modelLabel = new JLabel("Model:");
            modelLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            modelField = new JTextField(20);
            modelField.setMaximumSize(new Dimension(800, 30));
            modelField.setPreferredSize(new Dimension(250, 30));
            JPanel modelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            modelLabel.setPreferredSize(new Dimension(200, 30));
            modelPanel.add(modelLabel);
            modelPanel.add(modelField);

            vinLabel = new JLabel("VIN:");
            vinLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            vinField = new JTextField(20);
            vinField.setMaximumSize(new Dimension(800, 30));
            vinField.setPreferredSize(new Dimension(250, 30));
            JPanel vinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            vinLabel.setPreferredSize(new Dimension(200, 30));
            vinPanel.add(vinLabel);
            vinPanel.add(vinField);
            
            residencyTimeLabel = new JLabel("Residency Time (hrs):");
            residencyTimeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            residencyTimeField = new JTextField();
            residencyTimeField.setMaximumSize(new Dimension(800, 30));
            residencyTimeField.setPreferredSize(new Dimension(250, 30));
            JPanel residencyTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            residencyTimeLabel.setPreferredSize(new Dimension(200, 30));
            residencyTimePanel.add(residencyTimeLabel);
            residencyTimePanel.add(residencyTimeField);

            updateButton = new JButton("Update");
            backButton = new JButton("Back");

            updateButton.addActionListener(e -> {
                String ownerID = ownerIDField.getText().trim();
                String vehicleID = vehicleIDField.getText().trim();
                String model = modelField.getText().trim();
                String vin = vinField.getText().trim();
                int residencyTime;
                try {
                    residencyTime = Integer.parseInt(residencyTimeField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Residency Time must be an integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Save updated vehicle details to CSV
                saveVehicleToCSV(ownerID, vehicleID, model, vin, residencyTime);
                JOptionPane.showMessageDialog(frame, "Vehicle Updated Successfully!");

                ownerIDField.setText("");
                vehicleIDField.setText("");
                modelField.setText("");
                vinField.setText("");
                residencyTimeField.setText("");
            });

            backButton.addActionListener(e -> {
                frame.dispose();
                new MainMenuScreen();
            });

            panel.add(instructions);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(ownerIDPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(vehicleIDPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(modelPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(vinPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(residencyTimePanel);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(updateButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(backButton);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }
    
    // View Vehicle Screen - prompts for Owner ID and Vehicle ID to display details.
    public static class ViewVehicleScreen {
        private JFrame frame;
        private JLabel instructions, ownerIDLabel, vehicleIDLabel;
        private JTextField ownerIDField, vehicleIDField;
        private JButton viewButton, backButton;
        private JPanel panel;

        public ViewVehicleScreen() {
            frame = new JFrame("View Vehicle Information");
            frame.setSize(450, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            instructions = new JLabel("<html>Enter the Owner ID and Vehicle ID to view details:</html>", SwingConstants.CENTER);
            instructions.setFont(new Font("Times New Roman", Font.BOLD, 16));
            instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

            //owner id panel
            ownerIDLabel = new JLabel("Owner ID:");
            ownerIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            ownerIDField = new JTextField();
            ownerIDField.setMaximumSize(new Dimension(800, 30));
            ownerIDField.setPreferredSize(new Dimension(250, 30));
            JPanel ownerIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ownerIDLabel.setPreferredSize(new Dimension(150, 30));
            ownerIDPanel.add(ownerIDLabel);
            ownerIDPanel.add(ownerIDField);


            //vehicle id panel
            vehicleIDLabel = new JLabel("Vehicle ID:");
            vehicleIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            vehicleIDField = new JTextField();
            vehicleIDField.setMaximumSize(new Dimension(400, 30));
            vehicleIDField.setPreferredSize(new Dimension(250, 30));
            JPanel vehicleIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            vehicleIDLabel.setPreferredSize(new Dimension(150, 30));  
            vehicleIDPanel.add(vehicleIDLabel);
            vehicleIDPanel.add(vehicleIDField);
            

            viewButton = new JButton("View");
            backButton = new JButton("Back");

            viewButton.addActionListener(e -> {
                String ownerID = ownerIDField.getText().trim();
                String vehicleID = vehicleIDField.getText().trim();
                JOptionPane.showMessageDialog(frame, "Displaying details for Vehicle ID: " + vehicleID + " of Owner ID: " + ownerID);
            });

            backButton.addActionListener(e -> {
                frame.dispose();
                new MainMenuScreen();
            });

            panel.add(instructions);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(ownerIDPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(vehicleIDPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(viewButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(backButton);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }
}
