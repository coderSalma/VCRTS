import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
public class Controller {
ArrayList<Integer> timeEstimate = new ArrayList<Integer>();
Queue<JobOwner> queue = new LinkedList<>();
JobOwner job;
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
}
