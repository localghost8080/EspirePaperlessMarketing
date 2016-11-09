package com.espire.jobexecutor;

import com.espire.job.BatchEmailJob;

public interface EmailJobExecutor {

	public void sendEmail(BatchEmailJob batchJob);
	
}
