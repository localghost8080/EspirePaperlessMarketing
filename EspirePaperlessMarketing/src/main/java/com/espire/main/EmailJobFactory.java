package com.espire.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.espire.configuration.Configuration;
import com.espire.job.BatchEmailJob;
import com.espire.job.EmailJob;

public class EmailJobFactory extends JobFactory {

	final static Logger log = Logger.getLogger(EmailJobFactory.class);	

	public BatchEmailJob createEmailJobs(){
		BatchEmailJob batchJob = new BatchEmailJob();
		batchJob.setBatchJobId(""+new Date().getTime());
		batchJob.setEmailJobList(new ArrayList<EmailJob>());
		try{
			BufferedReader buff = Files.newBufferedReader(Paths.get(Configuration.getProperty("email.data.filepath")));
			buff.lines().forEach((line)->{
				String[] inputLine = line.split(",");
				EmailJob emailJob= new EmailJob(inputLine[0],inputLine[1],inputLine[2]);
				emailJob.setSubject(Configuration.getProperty("email.static.subject"));
				parseEmailBody(emailJob);
				batchJob.getEmailJobList().add(emailJob);
			});
		}catch(IOException ioe){
			log.error(ioe);
		}

		return batchJob;
	}

}
