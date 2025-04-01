import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
public class VehicleCloudController {
	public static void main(String[] args) {
		ArrayList<Job> jobs = new ArrayList<Job>();
		//need to store given jobs somehow?
		//for later reference in the panel

	}
	public static JobOwner getJobInfo(JobOwner newJob) {
		int jobID = newJob.getJobID();
		String jobDeadline=newJob.getJobDeadline();
		String jobInfo=newJob.getJobInfo();
		String jobName=newJob.getJobName();
		String username = newJob.getUsername();
		int jobDuration = newJob.getJobDuration();
		JobOwner job = new JobOwner(username,jobID,jobDuration,jobName, jobInfo, jobDeadline);
		return job;
	}
	
	public static void acceptJob(JobOwner job) {
		//press button
		//button sends message to here
		//
		//some code that sends a message via socket to the client
		//also saves job
		saveJobToCSV();
	}
	public static void rejectJob() {
		//some code that sends message to client
		jobs.remove(0);
		//remove the job from the array list
	}
	//
    public void saveJobToCSV(int jobID, String jobName, String jobInfo, int jobDuration, String jobDeadline) {
    	String fileName =  username + "Jobs.csv"; 
    	System.out.println(username);
    	File file = new File(fileName);
    	try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(jobID + "," + jobName + "," + jobInfo + "," + jobDuration + "," + jobDeadline + "," + timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
