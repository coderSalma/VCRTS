import javax.swing.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class VehicleCloudController {

    public static final int PORT = 12345; // Server port number
    private static final Controller controller = Controller.getInstance(); // Controller instance
    private static JTextArea jobsArea;

    public static void main(String[] args) {
        // Start the server
        SwingUtilities.invokeLater(() -> new VehicleCloudController());
        new VehicleCloudController().startServer();
    }

    // Start listening for incoming client connections
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("VehicleCloudController server started on port " + PORT);

            // Continuously accept client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection received from: " + clientSocket.getInetAddress());

                // Handle each client connection on a separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    // Inner class to handle each client connection
    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                // Read data sent by the client
                String incomingData = in.readLine();

                if (incomingData != null) {
                    // Check if it's job-related or vehicle-related data
                    if (incomingData.startsWith("JOB:")) {
                        handleJob(incomingData.substring(4), out);
                    } else if (incomingData.startsWith("VEHICLE:")) {
                        handleVehicle(incomingData.substring(8), out);
                    } else {
                        out.println("Unknown data format.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Prompt user to accept/reject job, and save if accepted
        private void handleJob(String jobData, PrintWriter out) {
            // Use SwingUtilities.invokeLater to ensure dialog is shown on the EDT thread
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int response = JOptionPane.showConfirmDialog(
                        null, // No parent component (centers the dialog)
                        "Do you accept this job?\n" + jobData, // Job data message
                        "Job Request", // Dialog title
                        JOptionPane.YES_NO_OPTION // Show Yes and No buttons
                    );
        
                    // Process response
                    if (response == JOptionPane.YES_OPTION) {
                        // Only save to CSV if accepted
                        saveToCSV("Jobs.csv", jobData);
                        JOptionPane.showMessageDialog(null, "Your job was approved!");
                    } else {
                        // Do not save to CSV if rejected
                    	JOptionPane.showMessageDialog(null, "Your job was rejected.");
                    }
                }
            });
        }
        
        

        // Prompt user to accept/reject vehicle, and save if accepted
        private void handleVehicle(String vehicleData, PrintWriter out) {
            // Ensure the dialog is shown on the EDT thread
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int response = JOptionPane.showConfirmDialog(
                        null, // Parent component, null centers the dialog on the screen
                        "Do you accept this vehicle?\n" + vehicleData, // Message to display
                        "Vehicle Request", // Dialog title
                        JOptionPane.YES_NO_OPTION // Show Yes and No buttons
                    );
        
                    // Handle the response
                    if (response == JOptionPane.YES_OPTION) {
                        saveToCSV("Vehicles.csv", vehicleData);
                        JOptionPane.showMessageDialog(null, "Your vehicle was approved!");
                    } else {
                    	 JOptionPane.showMessageDialog(null, "Your vehicle was rejected.");
                    }
                }
            });
        }
        

        // Append data to the specified CSV file with a timestamp
        private void saveToCSV(String filename, String data) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                writer.write(data + "," + timestamp);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // JobOwner class to hold job-related information
    public static class JobOwner {
        private String jobName;
        private int jobID;
        private int jobDuration;
        private String jobInfo;
        private String jobDeadline;
        private String username;

        public JobOwner(String jobName, int jobID, int jobDuration, String jobInfo, String jobDeadline, String username) {
            this.jobName = jobName;
            this.jobID = jobID;
            this.jobDuration = jobDuration;
            this.jobInfo = jobInfo;
            this.jobDeadline = jobDeadline;
            this.username = username;
        }

        // Getters for job attributes
        public String getJobName() { return jobName; }
        public int getJobID() { return jobID; }
        public int getJobDuration() { return jobDuration; }
        public String getJobInfo() { return jobInfo; }
        public String getJobDeadline() { return jobDeadline; }
        public String getUsername() { return username; }
    }
}
