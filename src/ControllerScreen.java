import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Queue;
import javax.swing.*;

public class ControllerScreen {
    private Controller controller;
    private JFrame frame;
    private JLabel currentJobLabel;
    private JPanel panel;
    private JPanel initialPanel;
    private JPanel vehiclesPanel;

    private JTextArea jobsArea;
    private JTextArea completionTimesArea;
    private JTextArea vehiclesArea;

    private static final int SERVER_PORT = 12345; // Define server port
    
    public ControllerScreen() {
        controller = Controller.getInstance();

        frame = new JFrame("Cloud Controller");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(204, 229, 255));

        createMainScreen();
        jobControls();
        vehicleControls();

        frame.add(initialPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Start the server in a new thread
        new Thread(this::startServer).start();
    }

    private void createMainScreen() {
        initialPanel = new JPanel();
        initialPanel.setLayout(new BoxLayout(initialPanel, BoxLayout.Y_AXIS));
        initialPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Cloud Controller Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        initialPanel.add(welcomeLabel);
        initialPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JButton viewJobsButton = new JButton("Job Controls");
        viewJobsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listener to get queued job info / completion times
        viewJobsButton.addActionListener(e -> {
            displayJobsFromQueue();
            frame.getContentPane().removeAll();
            frame.add(panel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        initialPanel.add(viewJobsButton);
        initialPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton viewVehiclesButton = new JButton("Vehicle Controls");
        viewVehiclesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listener to get vehicle info / residency time
        viewVehiclesButton.addActionListener(e -> {
            displayVehiclesFromCSV();
            frame.getContentPane().removeAll();
            frame.add(vehiclesPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });
        initialPanel.add(viewVehiclesButton);
    }

    private void jobControls() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        currentJobLabel = new JLabel("Current Jobs in Controller Queue", SwingConstants.CENTER);
        currentJobLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        currentJobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(currentJobLabel);

        jobsArea = new JTextArea(10, 40);
        jobsArea.setEditable(false);
        jobsArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane jobsScrollPane = new JScrollPane(jobsArea);
        panel.add(jobsScrollPane);

        completionTimesArea = new JTextArea(10, 40);
        completionTimesArea.setEditable(false);
        completionTimesArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane completionScrollPane = new JScrollPane(completionTimesArea);
        panel.add(completionScrollPane);

        JButton calculateCompletionButton = new JButton("Calculate Completion Times");
        calculateCompletionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton refreshButton = new JButton("Refresh Job List");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> displayJobsFromQueue());
        panel.add(refreshButton);
        
        
        // Read directly from arrayList of calculated times
        calculateCompletionButton.addActionListener(e -> {
            ArrayList<Integer> completionTimes = controller.calculateCompletionTimes();
            if (completionTimes.isEmpty()) {
                completionTimesArea.setText("No jobs to calculate completion times.");
            } else {
                StringBuilder output = new StringBuilder("Completion Times:\n");
                for (int i = 0; i < completionTimes.size(); i++) {
                    output.append("Job ").append(i + 1).append(": ").append(completionTimes.get(i)).append(" hours\n");
                }
                completionTimesArea.setText(output.toString());
            }
        });
        panel.add(calculateCompletionButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(initialPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });
        panel.add(backButton);
    }

    private void vehicleControls() {
        vehiclesPanel = new JPanel();
        vehiclesPanel.setLayout(new BoxLayout(vehiclesPanel, BoxLayout.Y_AXIS));
        vehiclesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel vehiclesLabel = new JLabel("All Vehicles in System", SwingConstants.CENTER);
        vehiclesLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        vehiclesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        vehiclesPanel.add(vehiclesLabel);

        vehiclesArea = new JTextArea(15, 50);
        vehiclesArea.setEditable(false);
        vehiclesArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane vehiclesScrollPane = new JScrollPane(vehiclesArea);
        vehiclesPanel.add(vehiclesScrollPane);

        JButton refreshButton = new JButton("Refresh Vehicle List");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> displayVehiclesFromCSV());
        vehiclesPanel.add(refreshButton);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(initialPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });
        vehiclesPanel.add(backButton);
    }

    private void displayVehiclesFromCSV() {
        StringBuilder vehiclesOutput = new StringBuilder("");

        String fileName = "Vehicles.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 6) {
                    vehiclesOutput.append("Owner ID: ").append(tokens[0]).append("\n")
                            .append("Vehicle ID: ").append(tokens[1]).append("\n")
                            .append("Model: ").append(tokens[2]).append("\n")
                            .append("VIN: ").append(tokens[3]).append("\n")
                            .append("Residency Time: ").append(tokens[4]).append(" hours\n")
                            .append("Arrival Time: ").append(tokens[5]).append("\n")
                            .append("----------------------------------------------------------------------\n");
                }
            }
        } catch (FileNotFoundException e) {
            vehiclesOutput.append("No file found.");
        } catch (IOException e) {
            vehiclesOutput.append("Error reading file.");
        }

        vehiclesArea.setText(vehiclesOutput.toString());
    }

    private void displayJobsFromQueue() {
        Queue<JobOwner> jobsQueue = controller.getQueue();

        if (jobsQueue.isEmpty()) {
            jobsArea.setText("No current jobs in the queue.");
        } else {
            StringBuilder jobsOutput = new StringBuilder("Current Jobs in Queue:\n");
            for (JobOwner job : jobsQueue) {
                jobsOutput.append("ID: ").append(job.getJobID())
                        .append(", Name: ").append(job.getJobName())
                        .append(", Duration: ").append(job.getJobDuration()).append(" hours\n");
            }
            jobsArea.setText(jobsOutput.toString());
        }
    }

    // Start the server and listen for incoming job/vehicle requests
    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Client handler for accepting job or vehicle data and updating UI
    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String data = in.readLine();

                if (data != null) {
                    // Here you can either process jobs or vehicles based on your logic
                    SwingUtilities.invokeLater(() -> {
                        // Update UI in real-time
                        jobsArea.append("New job received: " + data + "\n");
                    });
                } else {
                    out.println("No data received.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void loadAcceptedJobs() {
        try (BufferedReader reader = new BufferedReader(new FileReader("jobs.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Add these parts to your GUI table/list/etc.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ControllerScreen();
    }
}
