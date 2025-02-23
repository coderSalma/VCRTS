import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JobOwner {

    public static void main(String[] args) {
        new MainMenuScreen();
    }

    private int jobID;
    private int jobDuration;
    private String jobName;
    private String jobInfo;
    private String jobDeadline;

    public JobOwner() {
    }

    public JobOwner(int jobID, int jobDuration, String jobName, String jobInfo, String jobDeadline) {
        this.jobID = jobID;
        this.jobDuration = jobDuration;
        this.jobName = jobName;
        this.jobInfo = jobInfo;
        this.jobDeadline = jobDeadline;
    }

    private static void saveJobToCSV(int jobID, String jobName, String jobInfo, int jobDuration, String jobDeadline) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String fileName = "Jobs.csv";  

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(jobID + "," + jobName + "," + jobInfo + "," + jobDuration + "," + jobDeadline + "," + timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Screen for the main menu, options will be New Job, Old Jobs, Current Jobs as buttons
    public static class MainMenuScreen {
        private JFrame frame;
        private JButton newJobButton, oldJobButton, currentJobButton;
        private JLabel mainMenuLabel;
        private JPanel panel;

        public MainMenuScreen() {
            frame = new JFrame("Select Job Option");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(204, 229, 255));

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(new Color(204, 229, 255));

            mainMenuLabel = new JLabel("Welcome to the Job Management System", SwingConstants.CENTER);
            mainMenuLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
            mainMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            newJobButton = new JButton("New Job");
            oldJobButton = new JButton("Old Job(s)");
            currentJobButton = new JButton("Current Job(s)");

            newJobButton.setPreferredSize(new Dimension(120, 30));
            oldJobButton.setPreferredSize(new Dimension(120, 30));
            currentJobButton.setPreferredSize(new Dimension(120, 30));

            newJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            oldJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            currentJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            newJobButton.addActionListener(e -> {
                frame.dispose();
                new NewJobScreen();
            });

            oldJobButton.addActionListener(e -> {
                frame.dispose();
                new OldJobScreen();
            });

            currentJobButton.addActionListener(e -> {
                frame.dispose();
                new CurrentJobScreen();
            });

            panel.add(mainMenuLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(newJobButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(oldJobButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(currentJobButton);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }

    //Prompts the Job Owner for Information about the Job. There is a submit button
    //and a back button. Multiple jobs can be submitted at once. 
    public static class NewJobScreen {
        private JFrame frame;
        private JLabel instructions, jobNameLabel, jobInfoLabel, timeMinLabel, jobDeadlineLabel;
        private JTextField jobNameField, jobInfoField, timeMinField, jobDeadlineField;
        private JButton submitJobButton, backButton;
        private JPanel panel;

        public NewJobScreen() {
            frame = new JFrame("New Job Submission");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(new Color(204, 229, 255));

            instructions = new JLabel("<html>Enter the details of the new job below:</html>", SwingConstants.CENTER);
            instructions.setFont(new Font("Times New Roman", Font.BOLD, 16));
            instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

            jobNameLabel = new JLabel("Job Name:");
            jobNameLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
            jobNameField = new JTextField(40);
            jobNameField.setMaximumSize(new Dimension(400, 30));

            jobInfoLabel = new JLabel("Job Info:");
            jobInfoLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
            jobInfoField = new JTextField(40);
            jobInfoField.setMaximumSize(new Dimension(400, 30));

            timeMinLabel = new JLabel("Job Duration (hours):");
            timeMinLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
            timeMinField = new JTextField(5);
            timeMinField.setMaximumSize(new Dimension(400, 30));

            jobDeadlineLabel = new JLabel("Job Deadline (YYYY-MM-DD):");
            jobDeadlineLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
            jobDeadlineField = new JTextField(10);
            jobDeadlineField.setMaximumSize(new Dimension(400, 30));

            submitJobButton = new JButton("Submit");
            backButton = new JButton("Back");

            submitJobButton.addActionListener(e -> {
                int newJobID = (int) (Math.random() * 1000);
                String newJobName = jobNameField.getText();
                String newJobInfo = jobInfoField.getText();
                String newJobDeadline = jobDeadlineField.getText();
                int newTimeMin = Integer.parseInt(timeMinField.getText());

                JobOwner newJob = new JobOwner(newJobID, newTimeMin, newJobName, newJobInfo, newJobDeadline);

                saveJobToCSV(newJobID, newJobName, newJobInfo, newTimeMin, newJobDeadline);

                JOptionPane.showMessageDialog(frame, "Job Saved Successfully!");

                jobNameField.setText("");
                jobInfoField.setText("");
                timeMinField.setText("");
                jobDeadlineField.setText("");
            });

            backButton.addActionListener(e -> {
                frame.dispose();
                new MainMenuScreen();
            });

            panel.add(instructions);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(jobNameLabel);
            panel.add(jobNameField);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(jobInfoLabel);
            panel.add(jobInfoField);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(timeMinLabel);
            panel.add(timeMinField);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(jobDeadlineLabel);
            panel.add(jobDeadlineField);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(submitJobButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(backButton);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }

    //OldJobScreen Should display a list of the old jobs (those marked as completed) or "There are no previous jobs"
    public static class OldJobScreen {
        private JFrame frame;
        private JLabel oldJobLabel;
        private JPanel panel;

        public OldJobScreen() {
            frame = new JFrame("Old Job");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(204, 229, 255));

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(new Color(204, 229, 255));

            oldJobLabel = new JLabel("Old Jobs will be here.", SwingConstants.CENTER);
            oldJobLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
            oldJobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(oldJobLabel);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }

   //This will display Current Jobs, the bills (whether they've been paid or not), the info they were prompted for when making the job
   //And for now, whether or not the job is in progress or not

    public static class CurrentJobScreen {
        private JFrame frame;
        private JLabel currentJobLabel;
        private JPanel panel;

        public CurrentJobScreen() {
            frame = new JFrame("Current Job(s)");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(204, 229, 255));

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(new Color(204, 229, 255));

            currentJobLabel = new JLabel("Current Job(s) will be here.", SwingConstants.CENTER);
            currentJobLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
            currentJobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(currentJobLabel);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }
}
