import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Controller 
{
	private static Controller instance; 
	ArrayList<Integer> timeEstimate = new ArrayList<Integer>();
	Queue<JobOwner> queue = new LinkedList<>();
	JobOwner job;
	
	private Controller()
	{
	
	}
	
	public static Controller getInstance()
	{
		if(instance == null)
		{
			instance = new Controller();
		}
		return instance;
	}
	
	
	public void addToQueue(JobOwner job){
		queue.add(job);

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
public void addToTimeEstimate(JobOwner job) {
	timeEstimate.add(job.getJobDuration());
	
}
public int estimateWaitTime(JobOwner job) {
	int waitTime=queue.peek().getJobDuration();
	for(JobOwner inQueue : queue) {
		if(inQueue.equals(job)) {
			break;
		}
		waitTime = inQueue.getJobDuration()+waitTime;
	}
	return waitTime;
	
}

public ArrayList<Integer> calculateCompletionTimes() {
    ArrayList<Integer> completionTimes = new ArrayList<>();
    int currentTime = 0;
    for (JobOwner job : queue) {
        currentTime += job.getJobDuration();
        completionTimes.add(currentTime);
    }
    return completionTimes;
}

}
