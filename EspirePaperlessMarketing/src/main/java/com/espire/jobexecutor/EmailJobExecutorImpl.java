package com.espire.jobexecutor;

import org.apache.log4j.Logger;

import com.espire.configuration.Configuration;
import com.espire.job.BatchEmailJob;
import com.espire.job.EmailJob;

public class EmailJobExecutorImpl implements EmailJobExecutor {
	
	final static Logger log = Logger.getLogger(EmailJobExecutorImpl.class);	
	SendEmailEngine engine = new SendEmailEngine();
	
	@Override
	public void sendEmail(BatchEmailJob batchJob){
		long sleepDuration = Long.parseLong(Configuration.getProperty("email.send.interval"));
		log.info("Starting job for JobId "+batchJob.getBatchJobId());
		log.info("Count of emails in job "+batchJob.getEmailJobList().size());
		batchJob.getEmailJobList().stream().forEach((job)->{
			try{
				Thread.sleep(sleepDuration);
				
			}catch(InterruptedException ie){
				log.error(ie);
			}
			doSend(job);});

	}
	
	private void doSend(EmailJob email){
		log.info("Sending email :" +email.toString());
		engine.sendEmail(email);
	}

}
