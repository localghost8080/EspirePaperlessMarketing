package com.espire.main;

import org.apache.log4j.Logger;

import com.espire.configuration.Configuration;
import com.espire.job.BatchEmailJob;
import com.espire.jobexecutor.EmailJobExecutor;
import com.espire.jobexecutor.EmailJobExecutorImpl;
import com.espire.mailengine.MailEngine;
import com.espire.mailengine.SendEmailEngine;


public class EspirePaperless {

	final static Logger log = Logger.getLogger(EspirePaperless.class);	
	public static void main(String[] args) {
		try{
			log.info("=============Strarting Application=============");
			
			log.info("Application properties :" + args[0]);
			Configuration.loadProperties(args[0]);
			BatchEmailJob batchJob = null;
			switch(args[1]){
			case "NEW" : batchJob= new EmailJobFactory().createEmailJobs();break;
			//case "RERUN" : batchJob = jobFactory.createPartialEmailJobs(Integer.parseInt(args[2]));break;
			}
			MailEngine engine = new SendEmailEngine();
			EmailJobExecutor executor = new EmailJobExecutorImpl(engine);
			//executor.sendEmail(batchJob);
			log.info("=============Terminating Application=============");
		}
		catch(Exception ie){
			log.error(ie);
		}
	}

}
