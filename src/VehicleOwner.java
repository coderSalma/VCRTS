import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VehicleOwner {

	private static final String SERVER_ADDRESS = "localhost";
	private static final int PORT = 12345;

	public static void main(String[] args) {
		new MainMenuScreen();
	}

	public static void getMainScreen() {
		new MainMenuScreen();
	}

	// Instance fields for vehicle details
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
	private static void saveVehicleToCSV(String ownerID, String vehicleID, String model, String vin,
			int residencyTime) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String fileName = "Vehicles.csv";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
			writer.write(ownerID + "," + vehicleID + "," + model + "," + vin + "," + residencyTime + "," + timestamp);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ----------------------- Main Menu Screen -----------------------
	public static class MainMenuScreen extends JFrame {
		private JFrame frame;
		private JButton newVehicleButton, updateVehicleButton, viewVehicleButton, backButton;;
		private JLabel mainMenuLabel;
		private JPanel panel, backButtonPanel;

		public MainMenuScreen() {
			frame = new JFrame("Select Vehicle Option");
			frame.setSize(400, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);

			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			mainMenuLabel = new JLabel("Welcome to the Vehicle Management System", SwingConstants.CENTER);
			mainMenuLabel.setFont(new Font("Serif", Font.BOLD, 16));
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

			backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			backButton = new JButton("Back");
			backButton.setPreferredSize(new Dimension(90, 25));
			backButton.setBounds(50, 200, 90, 25);
			backButton.addActionListener(e -> {
				frame.dispose();
				new DashboardScreen();
			});

			backButtonPanel.add(backButton);

			panel.add(mainMenuLabel);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(newVehicleButton);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(updateVehicleButton);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(viewVehicleButton);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			frame.add(panel);
			frame.add(backButtonPanel, BorderLayout.SOUTH);
			frame.setVisible(true);

		}
	}

	// ----------------------- New Vehicle Screen -----------------------
	public static class NewVehicleScreen {
		private JFrame frame;
		private JLabel instructions, ownerIDLabel, vehicleIDLabel, modelLabel, vinLabel, residencyTimeLabel;
		private JTextField ownerIDField, vehicleIDField, modelField, vinField, residencyTimeField;
		private JButton submitButton, backButton;
		private JPanel panel;

		public NewVehicleScreen() {
			frame = new JFrame("New Vehicle Submission");
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);

			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			instructions = new JLabel("<html>Enter the details of your vehicle below:</html>", SwingConstants.CENTER);
			instructions.setFont(new Font("Times New Roman", Font.BOLD, 16));
			instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

			ownerIDLabel = new JLabel("Owner ID:");
			ownerIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			ownerIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			ownerIDField = new JTextField(20);
			ownerIDField.setMaximumSize(new Dimension(400, 30));
			ownerIDField.setAlignmentX(Component.CENTER_ALIGNMENT);

			vehicleIDLabel = new JLabel("Vehicle ID:");
			vehicleIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			vehicleIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			vehicleIDField = new JTextField(20);
			vehicleIDField.setMaximumSize(new Dimension(400, 30));
			vehicleIDField.setAlignmentX(Component.CENTER_ALIGNMENT);

			modelLabel = new JLabel("Model:");
			modelLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			modelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			modelField = new JTextField(20);
			modelField.setMaximumSize(new Dimension(400, 30));
			modelField.setAlignmentX(Component.CENTER_ALIGNMENT);

			vinLabel = new JLabel("VIN:");
			vinLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			vinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			vinField = new JTextField(20);
			vinField.setMaximumSize(new Dimension(400, 30));
			vinField.setAlignmentX(Component.CENTER_ALIGNMENT);

			residencyTimeLabel = new JLabel("Residency Time (hrs):");
			residencyTimeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			residencyTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			residencyTimeField = new JTextField(5);
			residencyTimeField.setMaximumSize(new Dimension(400, 30));
			residencyTimeField.setAlignmentX(Component.CENTER_ALIGNMENT);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			JButton submitButton = new JButton("Submit");
			JButton backButton = new JButton("Back");
			buttonPanel.add(backButton);
			buttonPanel.add(Box.createRigidArea(new Dimension(70, 0)));
			buttonPanel.add(submitButton);
			buttonPanel.setPreferredSize(new Dimension(300, 40));

			submitButton.addActionListener(e -> {
				String ownerID = ownerIDField.getText().trim();
				String vehicleID = vehicleIDField.getText().trim();
				String model = modelField.getText().trim();
				String vin = vinField.getText().trim();
				int residencyTime;
				try {
					residencyTime = Integer.parseInt(residencyTimeField.getText().trim());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Residency Time must be an integer.", "Input Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				VehicleOwner.promptVCC(ownerID, vehicleID, model, vin, residencyTime);

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
			panel.add(ownerIDLabel);
			panel.add(ownerIDField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(vehicleIDLabel);
			panel.add(vehicleIDField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(modelLabel);
			panel.add(modelField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(vinLabel);
			panel.add(vinField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(residencyTimeLabel);
			panel.add(residencyTimeField);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(buttonPanel);
			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}
	}

	// ----------------------- Update Vehicle Screen -----------------------
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

			instructions = new JLabel("<html>Enter the updated details of your vehicle below:</html>",
					SwingConstants.CENTER);
			instructions.setFont(new Font("Times New Roman", Font.BOLD, 16));
			instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

			ownerIDLabel = new JLabel("Owner ID:");
			ownerIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			ownerIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			ownerIDField = new JTextField(20);
			ownerIDField.setMaximumSize(new Dimension(400, 30));
			ownerIDField.setAlignmentX(Component.CENTER_ALIGNMENT);

			vehicleIDLabel = new JLabel("Vehicle ID:");
			vehicleIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			vehicleIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			vehicleIDField = new JTextField(20);
			vehicleIDField.setMaximumSize(new Dimension(400, 30));
			vehicleIDField.setAlignmentX(Component.CENTER_ALIGNMENT);

			modelLabel = new JLabel("Model:");
			modelLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			modelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			modelField = new JTextField(20);
			modelField.setMaximumSize(new Dimension(400, 30));
			modelField.setAlignmentX(Component.CENTER_ALIGNMENT);

			vinLabel = new JLabel("VIN:");
			vinLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			vinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			vinField = new JTextField(20);
			vinField.setMaximumSize(new Dimension(400, 30));
			vinField.setAlignmentX(Component.CENTER_ALIGNMENT);

			residencyTimeLabel = new JLabel("Residency Time (hrs):");
			residencyTimeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			residencyTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			residencyTimeField = new JTextField(5);
			residencyTimeField.setMaximumSize(new Dimension(400, 30));
			residencyTimeField.setAlignmentX(Component.CENTER_ALIGNMENT);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			JButton updateButton = new JButton("Update");
			JButton backButton = new JButton("Back");
			buttonPanel.add(backButton);
			buttonPanel.add(Box.createRigidArea(new Dimension(70, 0)));
			buttonPanel.add(updateButton);
			buttonPanel.setPreferredSize(new Dimension(300, 40));

			updateButton.addActionListener(e -> {
				String ownerID = ownerIDField.getText().trim();
				String vehicleID = vehicleIDField.getText().trim();
				String model = modelField.getText().trim();
				String vin = vinField.getText().trim();
				int residencyTime;
				try {
					residencyTime = Integer.parseInt(residencyTimeField.getText().trim());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Residency Time must be an integer.", "Input Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

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
			panel.add(ownerIDLabel);
			panel.add(ownerIDField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(vehicleIDLabel);
			panel.add(vehicleIDField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(modelLabel);
			panel.add(modelField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(vinLabel);
			panel.add(vinField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(residencyTimeLabel);
			panel.add(residencyTimeField);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(buttonPanel);

			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}
	}

	// ----------------------- View Vehicle Screen -----------------------
	// Reads the CSV file ("Vehicles.csv") and displays all details (owner ID,
	// vehicle ID, model, VIN, residency time, timestamp)
	public static class ViewVehicleScreen {
		private JFrame frame;
		private JLabel instructions, ownerIDLabel, vehicleIDLabel;
		private JTextField ownerIDField, vehicleIDField;
		private JButton viewButton, backButton;
		private JPanel panel;

		public ViewVehicleScreen() {
			frame = new JFrame("View Vehicle Information");
			frame.setSize(400, 350);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);

			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			instructions = new JLabel("<html>Enter the Owner ID and Vehicle ID to view details:</html>",
					SwingConstants.CENTER);
			instructions.setFont(new Font("Times New Roman", Font.BOLD, 16));
			instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

			ownerIDLabel = new JLabel("Owner ID:");
			ownerIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			ownerIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			ownerIDField = new JTextField(20);
			ownerIDField.setMaximumSize(new Dimension(400, 30));
			ownerIDField.setAlignmentX(Component.CENTER_ALIGNMENT);

			vehicleIDLabel = new JLabel("Vehicle ID:");
			vehicleIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			vehicleIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			vehicleIDField = new JTextField(20);
			vehicleIDField.setMaximumSize(new Dimension(400, 30));
			vehicleIDField.setAlignmentX(Component.CENTER_ALIGNMENT);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			JButton viewButton = new JButton("View");
			JButton backButton = new JButton("Back");
			buttonPanel.add(backButton);
			buttonPanel.add(Box.createRigidArea(new Dimension(70, 0)));
			buttonPanel.add(viewButton);
			buttonPanel.setPreferredSize(new Dimension(300, 40));

			viewButton.addActionListener(e -> {
				String searchOwner = ownerIDField.getText().trim();
				String searchVehicle = vehicleIDField.getText().trim();
				String details = "";
				boolean found = false;

				try (BufferedReader reader = new BufferedReader(new FileReader("Vehicles.csv"))) {
					String line;
					while ((line = reader.readLine()) != null) {
						String[] tokens = line.split(",");
						if (tokens.length >= 6) {
							String owner = tokens[0];
							String vehicle = tokens[1];
							if (owner.equals(searchOwner) && vehicle.equals(searchVehicle)) {
								details = "Owner ID: " + tokens[0] + "\n" + "Vehicle ID: " + tokens[1] + "\n"
										+ "Model: " + tokens[2] + "\n" + "VIN: " + tokens[3] + "\n" + "Residency Time: "
										+ tokens[4] + " hrs\n" + "Timestamp: " + tokens[5];
								found = true;
								break;
							}
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				if (found) {
					JOptionPane.showMessageDialog(frame, details, "Vehicle Details", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame,
							"No vehicle record found for the given Owner ID and Vehicle ID.", "Not Found",
							JOptionPane.ERROR_MESSAGE);
				}
			});

			backButton.addActionListener(e -> {
				frame.dispose();
				new MainMenuScreen();
			});

			panel.add(instructions);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(ownerIDLabel);
			panel.add(ownerIDField);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(vehicleIDLabel);
			panel.add(vehicleIDField);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(buttonPanel);

			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}
	}

	
	public static void promptVCC(String ownerID, String vehicleID, String model, String vin, int residencyTime) {
		// TODO Auto-generated method stub

		try {

			Socket socket = new Socket(SERVER_ADDRESS, PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String vehicleData = "VEHICLE:" + ownerID + "," + vehicleID + "," + model + "," + vin + "," + residencyTime;


			out.println(vehicleData);
			String response = in.readLine();
			JOptionPane.showMessageDialog(null, response);

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error connecting to VC Controller: " + e.getMessage());
		}
	}

}