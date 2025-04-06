import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class VehicleCloudController {

    public static final int PORT = 12345; // Server port number

    public static void main(String[] args) {
        // Start the server
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
            int response = JOptionPane.showConfirmDialog(
                null,
                "Do you accept this job?\n" + jobData,
                "Job Request",
                JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                saveToCSV("Jobs.csv", jobData);
                out.println("Job accepted and saved.");
            } else {
                out.println("Job rejected.");
            }
        }

        // Prompt user to accept/reject vehicle, and save if accepted
        private void handleVehicle(String vehicleData, PrintWriter out) {
            int response = JOptionPane.showConfirmDialog(
                null,
                "Do you accept this vehicle?\n" + vehicleData,
                "Vehicle Request",
                JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                saveToCSV("Vehicles.csv", vehicleData);
                out.println("Vehicle accepted and saved.");
            } else {
                out.println("Vehicle rejected.");
            }
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
}
