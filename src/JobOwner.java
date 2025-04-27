import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;


public class JobOwner extends JFrame{
	private String username;
	private int jobID;
	private int jobDuration;
	private String jobName;
	private String jobInfo;
	private String jobDeadline;
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 12345;

	public JobOwner(String username) { // Added username parameter
		this.username = username;
		new MainMenuScreen(username);
	}

	public JobOwner(String username, int jobID, int jobDuration, String jobName, String jobInfo, String jobDeadline) {
		this.username = username;
		this.jobID = jobID;
		this.jobDuration = jobDuration;
		this.jobName = jobName;
		this.jobInfo = jobInfo;
		this.jobDeadline = jobDeadline;
	}

	// getters
	public String getUsername() {
		return username;
	}

	public int getJobID() {
		return jobID;
	}

	public int getJobDuration() {
		return jobDuration;
	}

	public String getJobName() {
		return jobName;
	}

	public String getJobInfo() {
		return jobInfo;
	}

	public String getJobDeadline() {
		return jobDeadline;
	}

//end of getters
	/*
	 * public void saveJobToCSV(int jobID, String jobName, String jobInfo, int
	 * jobDuration, String jobDeadline) { String fileName = username + "Jobs.csv";
	 * System.out.println(username); File file = new File(fileName); try {
	 * file.createNewFile(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } String timestamp = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); try
	 * (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
	 * writer.write(jobID + "," + jobName + "," + jobInfo + "," + jobDuration + ","
	 * + jobDeadline + "," + timestamp); writer.newLine(); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

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
				JobOwner CSVJob = new JobOwner(username, tempID, tempDuration, tempName, tempInfo, tempDeadline);
				jobs.add(CSVJob); // Add job to the list
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jobs;
	}

	// Screen for the main menu, options will be New Job, Old Jobs, Current Jobs as
	// buttons
	public static class MainMenuScreen {
		private JFrame frame;
		private JButton newJobButton, oldJobButton, currentJobButton, backButton;
		private JLabel mainMenuLabel;
		private JPanel panel, backButtonPanel;

		public MainMenuScreen(String username) { // Updated constructor
			frame = new JFrame("Select Job Option");
			frame.setSize(400, 300);
			frame.setLocation(-600,300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//frame.setLocationRelativeTo(null);
			frame.getContentPane().setBackground(new Color(204, 229, 255));

			panel = new JPanel();
			// Use BoxLayout to arrange components vertically
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			// panel.setBackground(new Color(204, 229, 255));

			mainMenuLabel = new JLabel("Welcome to the Job Management System", SwingConstants.CENTER);
			mainMenuLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			mainMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			newJobButton = new JButton("New Job");
			oldJobButton = new JButton("All Jobs");
			currentJobButton = new JButton("Current Jobs");

			Dimension buttonSize = new Dimension(120, 30);
			newJobButton.setMaximumSize(buttonSize);
			oldJobButton.setMaximumSize(buttonSize);
			currentJobButton.setMaximumSize(buttonSize);

			newJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			oldJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			currentJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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
			panel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds space between title and buttons
			panel.add(newJobButton);
			panel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds space between buttons
			panel.add(oldJobButton);
			panel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds space between buttons
			panel.add(currentJobButton);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));

			frame.add(panel, BorderLayout.CENTER);
			frame.add(backButtonPanel, BorderLayout.SOUTH);
			frame.setVisible(true);
		}
	}

	// Prompts the Job Owner for Information about the Job
	public static class NewJobScreen {
		private JFrame frame;
		private JLabel instructions, jobNameLabel, jobInfoLabel, timeMinLabel, jobDeadlineLabel, jobIdLabel;
		private JTextField jobNameField, jobInfoField, timeMinField, jobDeadlineField, jobIdField;
		private JButton submitJobButton, backButton;
		private JPanel panel;
		private String username;

		public NewJobScreen(String username) { // Added username parameter
			this.username = username;
			
			frame = new JFrame("New Job Submission");
			frame.setSize(600, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(6, 2, 10, 10));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

			instructions = new JLabel("<html>Enter the details of the new job below:<html>", SwingConstants.CENTER);
			instructions.setFont(new Font("Times New Roman", Font.BOLD, 20));
			JPanel instructionsPanel = new JPanel();
			instructionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			instructionsPanel.add(instructions);

			// job id panel
			jobIdLabel = new JLabel("Job ID:");
			jobIdLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			jobIdField = new JTextField();
			jobIdField.setMaximumSize(new Dimension(800, 30));
			jobIdField.setPreferredSize(new Dimension(250, 30));
			JPanel jobIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			jobIdLabel.setPreferredSize(new Dimension(250, 30));
			jobIdPanel.add(jobIdLabel);
			jobIdPanel.add(jobIdField);
			panel.add(jobIdPanel);

			// job name panel
			jobNameLabel = new JLabel("Job Name:");
			jobNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			jobNameField = new JTextField();
			jobNameField.setMaximumSize(new Dimension(800, 30));
			jobNameField.setPreferredSize(new Dimension(250, 30));
			JPanel jobNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			jobNameLabel.setPreferredSize(new Dimension(250, 30));
			jobNamePanel.add(jobNameLabel);
			jobNamePanel.add(jobNameField);
			panel.add(jobNamePanel);

			// job info panel
			jobInfoLabel = new JLabel("Job Info:");
			jobInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			jobInfoField = new JTextField();
			jobInfoField.setMaximumSize(new Dimension(800, 30));
			jobInfoField.setPreferredSize(new Dimension(250, 30));
			JPanel jobInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			jobInfoLabel.setPreferredSize(new Dimension(250, 30));
			jobInfoPanel.add(jobInfoLabel);
			jobInfoPanel.add(jobInfoField);
			panel.add(jobInfoPanel);

			// time panel
			timeMinLabel = new JLabel("Job Duration (hours):");
			timeMinLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			timeMinField = new JTextField();
			timeMinField.setMaximumSize(new Dimension(800, 30));
			timeMinField.setPreferredSize(new Dimension(250, 30));
			JPanel timeMinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			timeMinLabel.setPreferredSize(new Dimension(250, 30));
			timeMinPanel.add(timeMinLabel);
			timeMinPanel.add(timeMinField);
			panel.add(timeMinPanel);

			// job deadline panel
			jobDeadlineLabel = new JLabel("Job Deadline (YYYY-MM-DD):");
			jobDeadlineLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			jobDeadlineField = new JTextField();
			jobDeadlineField.setMaximumSize(new Dimension(800, 30));
			jobDeadlineField.setPreferredSize(new Dimension(250, 30));
			JPanel jobDeadlinePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			jobDeadlineLabel.setPreferredSize(new Dimension(250, 30));
			jobDeadlinePanel.add(jobDeadlineLabel);
			jobDeadlinePanel.add(jobDeadlineField);
			panel.add(jobDeadlinePanel);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			JButton submitJobButton = new JButton("Submit");
			JButton backButton = new JButton("Back");
			buttonPanel.add(backButton);
			buttonPanel.add(Box.createRigidArea(new Dimension(70, 0)));
			buttonPanel.add(submitJobButton);
			buttonPanel.setPreferredSize(new Dimension(300, 40));

			// Button Action Listeners
			submitJobButton.addActionListener(e -> {
			    new Thread(() -> {
			        try {
			            int newJobID = Integer.parseInt(jobIdField.getText());
			            String newJobName = jobNameField.getText();
			            String newJobInfo = jobInfoField.getText();
			            String newJobDeadline = jobDeadlineField.getText();
			            int newTimeMin = Integer.parseInt(timeMinField.getText());

			            Controller controller = Controller.getInstance();

			            JobOwner newJob = new JobOwner(newJobName, newJobID, newTimeMin, newJobInfo, newJobDeadline, username);
			            boolean approved = newJob.promptVCC(newJobID, newJobName, newJobInfo, newTimeMin, newJobDeadline);

			            if (approved) {
			                controller.addToQueue(newJob);
			                JOptionPane.showMessageDialog(null, "Job submitted and approved.");
			            } else {
			                JOptionPane.showMessageDialog(null, "Job was rejected by the controller.");
			            }

			            SwingUtilities.invokeLater(() -> {
			                jobIdField.setText("");
			                jobNameField.setText("");
			                jobInfoField.setText("");
			                timeMinField.setText("");
			                jobDeadlineField.setText("");
			            });
			        } catch (Exception ex) {
			            ex.printStackTrace();
			            SwingUtilities.invokeLater(() ->
			                JOptionPane.showMessageDialog(null, "Error submitting job: " + ex.getMessage())
			            );
			        }
			    }).start();
			});


			backButton.addActionListener(e -> {
				frame.dispose();
				new MainMenuScreen(username); // Pass username
			});

			panel.add(instructions);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(jobIdPanel);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(jobNamePanel);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(jobInfoPanel);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(timeMinPanel);
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			panel.add(jobDeadlinePanel);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(buttonPanel);

			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}
	}

	// OldJobScreen Should display a list of the old jobs (those marked as
	// completed) or "There are no previous jobs"
	public static class OldJobScreen {
		private JFrame frame;
		private JLabel oldJobLabel;
		private JPanel panel;
		private JTextArea jobsArea;

		public OldJobScreen(String username) {
			frame = new JFrame("User Jobs");
			frame.setSize(600, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.getContentPane().setBackground(new Color(204, 229, 255));

			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			oldJobLabel = new JLabel(username + "\'s Jobs", SwingConstants.CENTER);
			oldJobLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			oldJobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel.add(oldJobLabel);

			jobsArea = new JTextArea(20, 40);
			jobsArea.setEditable(false);
			jobsArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			JScrollPane jobsScrollPane = new JScrollPane(jobsArea);
			panel.add(jobsScrollPane);

			frame.add(panel, BorderLayout.CENTER);

			JButton backButton = new JButton("Back");
			panel.add(backButton);

			loadCurrentJobs(username);

			backButton.addActionListener(e -> {
				frame.dispose();
				// new MainMenuScreen();
				new JobOwner(username);
			});

			frame.setVisible(true);
		}

		// show all jobs for user
		private void loadCurrentJobs(String username) {
			JobOwner jobOwner = new JobOwner(username);
			List<JobOwner> jobs = jobOwner.readJobFromCSV();

			if (jobs.isEmpty()) {
				jobsArea.setText("No current jobs.");
			} else {
				StringBuilder jobsOutput = new StringBuilder("\n");
				for (JobOwner job : jobs) {
					jobsOutput.append("ID: ").append(job.getJobID()).append(", Name: ").append(job.getJobName())
							.append(", Duration: ").append(job.getJobDuration()).append(" hours\n");
				}
				jobsArea.setText(jobsOutput.toString());
			}
		}
	}

	// This will display Current Jobs, the bills (whether they've been paid or not),
	// the info they were prompted for when making the job
	// And for now, whether or not the job is in progress or not

	public static class CurrentJobScreen {
		private JFrame frame;
		private JLabel currentJobLabel;
		private JPanel panel;

		private JTextArea jobsArea;
		private JTextArea completionTimesArea;
		private Controller controller;

		public CurrentJobScreen(String username) {
			controller = Controller.getInstance();
			frame = new JFrame("Current Job(s)");
			frame.setSize(600, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.getContentPane().setBackground(new Color(204, 229, 255));

			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			currentJobLabel = new JLabel("Current Job(s) will be here.", SwingConstants.CENTER);
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

			// button to calc times
			JButton calculateCompletionButton = new JButton("Calculate Completion Times");
			calculateCompletionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			calculateCompletionButton.addActionListener(e -> {

				ArrayList<Integer> completionTimes = controller.calculateCompletionTimes();
				if (completionTimes.isEmpty()) {
					completionTimesArea.setText("No jobs to calculate completion times.");
				} else {
					StringBuilder output = new StringBuilder("Completion Times:\n");
					for (int i = 0; i < completionTimes.size(); i++) {
						output.append("Job ").append(i + 1).append(": ").append(completionTimes.get(i))
								.append(" hours\n");
					}
					completionTimesArea.setText(output.toString());
				}
			});
			panel.add(calculateCompletionButton);

			// Back button
			JButton backButton = new JButton("Back");
			backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			backButton.addActionListener(e -> {
				frame.dispose();
				new JobOwner(username);
			});
			panel.add(backButton);

			displayJobsFromQueue();

			frame.add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}

		// display jobs in queue
		private void displayJobsFromQueue() {
			Queue<JobOwner> jobsQueue = controller.getQueue();

			if (jobsQueue.isEmpty()) {
				jobsArea.setText("No current jobs in the queue.");
			} else {
				StringBuilder jobsOutput = new StringBuilder("Current Jobs in Queue:\n");
				for (JobOwner job : jobsQueue) {
					jobsOutput.append("ID: ").append(job.getJobID()).append(", Name: ").append(job.getJobName())
							.append(", Duration: ").append(job.getJobDuration()).append(" hours\n");
				}
				jobsArea.setText(jobsOutput.toString());
			}
		}
	}

	public boolean promptVCC(int newJobID, String newJobName, String newJobInfo, int newTimeMin, String newJobDeadline) 
	{
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) 
		{
			out.println("JOB:" + newJobID + "," + newJobName + "," + newJobInfo + "," + newTimeMin + "," + newJobDeadline);

			String response = in.readLine();
			System.out.println(response);
			
			return "JOB_ACCEPTED".equals(response);

		} catch (Exception e) {
		
			return false;
		}
	}

}