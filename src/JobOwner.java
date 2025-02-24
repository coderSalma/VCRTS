import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class JobOwner {

<<<<<<< HEAD
	public static void main(String[] args) {
		new MainMenuScreen();
	}

	public static void getMainScreen() {
		new MainMenuScreen();
	}
=======
    private String username;
    private int jobID;
    private int jobDuration;
    private String jobName;
    private String jobInfo;
    private String jobDeadline;

    public JobOwner(String username) { // Added username parameter
        this.username = username;
        new MainMenuScreen(username);
    }
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

	private String username;
	private int jobID;
	private int jobDuration;
	private String jobName;
	private String jobInfo;
	private String jobDeadline;

<<<<<<< HEAD
	public JobOwner(String inputUsername) {
		this.username = inputUsername;
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
			writer.write(
					jobID + "," + jobName + "," + jobInfo + "," + jobDuration + "," + jobDeadline + "," + timestamp);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Screen for the main menu, options will be New Job, Old Jobs, Current Jobs as
	// buttons
	public static class MainMenuScreen {
		private JFrame frame;
		private JButton newJobButton, oldJobButton, currentJobButton;
		private JLabel mainMenuLabel;
		private JPanel panel;
=======
    private void saveJobToCSV(int jobID, String jobName, String jobInfo, int jobDuration, String jobDeadline) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String fileName = username + "Jobs.csv";  

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(jobID + "," + jobName + "," + jobInfo + "," + jobDuration + "," + jobDeadline + "," + timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<JobOwner> readJobFromCSV() { // Made public and return List<JobOwner>
        String fileName = username + "Jobs.csv";
        List<JobOwner> jobs = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            String pulledJob;
            while ((pulledJob = buffer.readLine()) != null) {
                String[] readJob = pulledJob.split(",");
                int tempID = Integer.parseInt(readJob[0]);
                String tempName = readJob[1];
                String tempInfo = readJob[2];
                int tempDuration = Integer.parseInt(readJob[3]);
                String tempDeadline = readJob[4];
                JobOwner CSVJob = new JobOwner(tempID, tempDuration, tempName, tempInfo, tempDeadline);
                jobs.add(CSVJob); // Add job to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobs;
    }
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

		public MainMenuScreen() {
			frame = new JFrame("Select Job Option");
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.getContentPane().setBackground(new Color(204, 229, 255));

<<<<<<< HEAD
			panel = new JPanel();
			// Use BoxLayout to arrange components vertically
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			// panel.setBackground(new Color(204, 229, 255));
=======
        public MainMenuScreen(String username) { // Updated constructor
            frame = new JFrame("Select Job Option");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(204, 229, 255));
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

			mainMenuLabel = new JLabel("Welcome to the Job Management System", SwingConstants.CENTER);
			mainMenuLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			mainMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			newJobButton = new JButton("New Job");
			oldJobButton = new JButton("Old Job(s)");
			currentJobButton = new JButton("Current Job(s)");

			Dimension buttonSize = new Dimension(150, 30);
			newJobButton.setMaximumSize(buttonSize);
			oldJobButton.setMaximumSize(buttonSize);
			currentJobButton.setMaximumSize(buttonSize);

			newJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			oldJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			currentJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);

			newJobButton.addActionListener(e -> {
				frame.dispose();
				new NewJobScreen();
			});

<<<<<<< HEAD
			oldJobButton.addActionListener(e -> {
				frame.dispose();
				new OldJobScreen();
			});

			currentJobButton.addActionListener(e -> {
				frame.dispose();
				new CurrentJobScreen();
			});

			panel.add(mainMenuLabel);
			panel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds space between title and buttons
			panel.add(newJobButton);
			panel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds space between buttons
			panel.add(oldJobButton);
			panel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds space between buttons
			panel.add(currentJobButton);
=======
            newJobButton.addActionListener(e -> {
                frame.dispose();
                new NewJobScreen(username); // Pass username
            });

            oldJobButton.addActionListener(e -> {
                frame.dispose();
                new OldJobScreen(username); // Pass username
            });

            currentJobButton.addActionListener(e -> {
                frame.dispose();
                new CurrentJobScreen(username); // Pass username
            });
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}
	}

	// Prompts the Job Owner for Information about the Job. There is a submit button
	// and a back button. Multiple jobs can be submitted at once.

<<<<<<< HEAD
	public static class NewJobScreen {
		private JFrame frame;
		private JLabel instructions, jobNameLabel, jobInfoLabel, timeMinLabel, jobDeadlineLabel;
		private JTextField jobNameField, jobInfoField, timeMinField, jobDeadlineField;
		private JButton submitJobButton, backButton;

		public NewJobScreen() {
			frame = new JFrame("New Job Submission");
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
=======
    // Prompts the Job Owner for Information about the Job
    public static class NewJobScreen {
        private JFrame frame;
        private JLabel instructions, jobNameLabel, jobInfoLabel, timeMinLabel, jobDeadlineLabel;
        private JTextField jobNameField, jobInfoField, timeMinField, jobDeadlineField;
        private JButton submitJobButton, backButton;
        private JPanel panel;
        private String username;

        public NewJobScreen(String username) { // Added username parameter
            this.username = username;
            frame = new JFrame("New Job Submission");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(6, 2, 10, 10));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			// panel.setBackground(new Color(204, 229, 255));

			instructions = new JLabel("<html>Enter the details of the new job below:<html>", SwingConstants.CENTER);
			instructions.setFont(new Font("Times New Roman", Font.BOLD, 20));
			panel.add(instructions);
			panel.add(new JLabel());

			jobNameLabel = new JLabel("Job Name:");
			jobNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			jobNameField = new JTextField(10);
			panel.add(jobNameLabel);
			panel.add(jobNameField);

			jobInfoLabel = new JLabel("Job Info:");
			jobInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			jobInfoField = new JTextField(10);
			panel.add(jobInfoLabel);
			panel.add(jobInfoField);

			timeMinLabel = new JLabel("Job Duration (hours):");
			timeMinLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			timeMinField = new JTextField(10);
			panel.add(timeMinLabel);
			panel.add(timeMinField);

			jobDeadlineLabel = new JLabel("Job Deadline (YYYY-MM-DD):");
			jobDeadlineLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			jobDeadlineField = new JTextField(10);
			panel.add(jobDeadlineLabel);
			panel.add(jobDeadlineField);

			submitJobButton = new JButton("Submit");
			backButton = new JButton("Back");
			panel.add(submitJobButton);
			panel.add(backButton);

			// Button Action Listeners
			submitJobButton.addActionListener(e -> {
				int newJobID = (int) (Math.random() * 1000);
				String newJobName = jobNameField.getText();
				String newJobInfo = jobInfoField.getText();
				String newJobDeadline = jobDeadlineField.getText();
				int newTimeMin = Integer.parseInt(timeMinField.getText());

<<<<<<< HEAD
				JobOwner newJob = new JobOwner(newJobID, newTimeMin, newJobName, newJobInfo, newJobDeadline);

				saveJobToCSV(newJobID, newJobName, newJobInfo, newTimeMin, newJobDeadline);
=======
                JobOwner newJob = new JobOwner(newJobID, newTimeMin, newJobName, newJobInfo, newJobDeadline);
                newJob.saveJobToCSV(newJobID, newJobName, newJobInfo, newTimeMin, newJobDeadline);
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

				JOptionPane.showMessageDialog(frame, "Job Saved Successfully!");

				jobNameField.setText("");
				jobInfoField.setText("");
				timeMinField.setText("");
				jobDeadlineField.setText("");
			});

<<<<<<< HEAD
			backButton.addActionListener(e -> {
				frame.dispose();
				new MainMenuScreen();
			});

			// Adding panel to the frame
			frame.add(panel);
			frame.setVisible(true);
		}
	}
=======
            backButton.addActionListener(e -> {
                frame.dispose();
                new MainMenuScreen(username); // Pass username
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
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

	// OldJobScreen Should display a list of the old jobs (those marked as
	// completed) or "There are no previous jobs"
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

<<<<<<< HEAD
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			// panel.setBackground(new Color(204, 229, 255));
=======
        public OldJobScreen(String username) {
            frame = new JFrame("Old Job");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(204, 229, 255));
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

			oldJobLabel = new JLabel("Old Jobs will be here.", SwingConstants.CENTER);
			oldJobLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			oldJobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel.add(oldJobLabel);

			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
			
			JButton backButton = new JButton("Back");
			panel.add(backButton);

			backButton.addActionListener(e -> {
				frame.dispose();
				new MainMenuScreen();
			});
		}
	}

	// This will display Current Jobs, the bills (whether they've been paid or not),
	// the info they were prompted for when making the job
	// And for now, whether or not the job is in progress or not

	public static class CurrentJobScreen {
		private JFrame frame;
		private JLabel currentJobLabel;
		private JPanel panel;

<<<<<<< HEAD
		public CurrentJobScreen() {
			frame = new JFrame("Current Job(s)");
			frame.setSize(400, 200);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.getContentPane().setBackground(new Color(204, 229, 255));
=======
        public CurrentJobScreen(String username) {
            frame = new JFrame("Current Job(s)");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(204, 229, 255));
>>>>>>> f9285b0da40d6ff004e664367f891a50f662786f

			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			//panel.setBackground(new Color(204, 229, 255));

			currentJobLabel = new JLabel("Current Job(s) will be here.", SwingConstants.CENTER);
			currentJobLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			currentJobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(currentJobLabel);

			JButton backButton = new JButton("Back");
			panel.add(backButton);

			backButton.addActionListener(e -> {
				frame.dispose();
				new MainMenuScreen();
			});
			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}
	}
}
