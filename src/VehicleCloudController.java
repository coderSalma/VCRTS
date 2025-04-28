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
        
        private void handleJob(String jobData, PrintWriter out) {
            try {
                final int[] responseHolder = new int[1];

                Runnable dialogTask = () -> {
                    String[] parts = jobData.split(",", -1);
                    String formattedMessage = String.format(
                        "Job ID: %s%nJob Name: %s%nJob Info: %s%nDuration: %s%nDeadline: %s",
                        parts.length > 0 ? parts[0] : "N/A",
                        parts.length > 1 ? parts[1] : "N/A",
                        parts.length > 2 ? parts[2] : "N/A",
                        parts.length > 3 ? parts[3] : "N/A",
                        parts.length > 4 ? parts[4] : "N/A"
                    );

                    responseHolder[0] = JOptionPane.showOptionDialog(
                    	    null,
                    	    formattedMessage,
                    	    "Cloud Controller Job Request",
                    	    JOptionPane.YES_NO_OPTION,
                    	    JOptionPane.QUESTION_MESSAGE,
                    	    null,
                    	    new String[]{"Accept", "Reject"},
                    	    "Accept"
                    	);
                    

                };

                if (SwingUtilities.isEventDispatchThread()) {
                    dialogTask.run();
                } else {
                    SwingUtilities.invokeAndWait(dialogTask);
                }

                int response = responseHolder[0];

                if (response == JOptionPane.YES_OPTION) {
                    out.println("JOB_ACCEPTED");
                    out.flush();
                    saveToCSV("Jobs.csv", jobData);
                    System.out.println(jobData);
                    
                    //splitting job data to insert into sql job table
                    String[] parts = jobData.split(",", -1);
                        int jobId = Integer.parseInt(parts[0]);
                        String jobName = parts[1];
                        String jobInfo = parts[2];
                        int duration = Integer.parseInt(parts[3]);
                        String deadline = parts[4];
                       
                        DBConnection.insertJob(jobId, duration, jobName, jobInfo, deadline);
                    
                    System.out.println("DEBUG: Job approved.");
                } else {
                    out.println("JOB_REJECTED");
                    out.flush();
                    System.out.println("DEBUG: Job rejected.");
                }
            } catch (Exception e) {
                System.err.println("Error in handleJob: " + e.getMessage());
                out.println("JOB_REJECTED:ERROR");
                out.flush();
            }
        }

        
        // Prompt user to accept/reject vehicle, and save if accepted
        private void handleVehicle(String vehicleData, PrintWriter out) {
            SwingUtilities.invokeLater(() -> {
                String[] parts = vehicleData.split(",", -1);
                String formattedMessage = String.format(
                    "Owner ID: %s%nVehicle ID: %s%nModel: %s%nVIN: %s%nResidency Time(hrs): %s",
                    parts.length > 0 ? parts[0] : "N/A",
                    parts.length > 1 ? parts[1] : "N/A",
                    parts.length > 2 ? parts[2] : "N/A",
                    parts.length > 3 ? parts[3] : "N/A",
                    parts.length > 4 ? parts[4] : "N/A"
                );

                int response = JOptionPane.showOptionDialog(
                	    null,
                	    formattedMessage,
                	    "Cloud Controller Vehicle Request",
                	    JOptionPane.YES_NO_OPTION,
                	    JOptionPane.QUESTION_MESSAGE,
                	    null,
                	    new String[]{"Accept", "Reject"},
                	    "Accept"
                	);

                if (response == JOptionPane.YES_OPTION) {
                    saveToCSV("Vehicles.csv", vehicleData);
                    
                    //split vehicle data for sql insert
                    String ownerID = parts[0];
                    int vehicleID = Integer.parseInt(parts[1]);
                    String model = parts[2];
                    String vin = parts[3];
                    String residencyTime = parts[4];
                   
                    DBConnection.insertVehicle(ownerID, vehicleID, model, vin, residencyTime);
                
                    
                    
                    JOptionPane.showMessageDialog(null, "Your vehicle was approved!");
                } else {
                    JOptionPane.showMessageDialog(null, "Your vehicle was rejected.");
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
