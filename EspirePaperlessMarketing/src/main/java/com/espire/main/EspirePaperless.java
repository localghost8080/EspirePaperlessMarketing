package com.espire.main;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.espire.configuration.Configuration;
import com.espire.job.BatchEmailJob;
import com.espire.jobexecutor.EmailJobExecutor;
import com.espire.jobexecutor.EmailJobExecutorImpl;

public class EspirePaperless {

	final static Logger log = Logger.getLogger(EspirePaperless.class);	
	public static void main(String[] args) {
		try{
			log.info("=============Strarting Application=============");
			log.info("Application properties :" + args[0]);
			Configuration.loadProperties(args[0]);
			EmailJobFactory jobFactory = new EmailJobFactory();
			BatchEmailJob batchJob = null;
			switch(args[1]){
			case "NEW" : batchJob= jobFactory.createEmailJobs();break;
			//case "RERUN" : batchJob = jobFactory.createPartialEmailJobs(Integer.parseInt(args[2]));break;
			}
			EmailJobExecutor executor = new EmailJobExecutorImpl();
			executor.sendEmail(batchJob);
			log.info("=============Terminating Application=============");
		}
		catch(IOException ie){
			log.error(ie);
		}
	}

}
