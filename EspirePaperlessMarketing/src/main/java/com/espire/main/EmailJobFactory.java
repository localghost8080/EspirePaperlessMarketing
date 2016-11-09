package com.espire.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.espire.configuration.Configuration;
import com.espire.job.BatchEmailJob;
import com.espire.job.EmailJob;

public class EmailJobFactory {

	final static Logger log = Logger.getLogger(EmailJobFactory.class);	

	/**
	 * This method creates a list of jobs based on the static configuration provided in the properties
	 * @return
	 */
	private String emailTemplate ="";
	
	public EmailJobFactory(){
		
		try{
		
			emailTemplate =new String(Files.readAllBytes(Paths.get(Configuration.getProperty("email.template.filepath"))));
			
		}
		catch(IOException ie){
			log.error(ie);
		}
	}
	
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

	public BatchEmailJob createPartialEmailJobs(int index){
		return null;
	}
	
	private void parseEmailBody(EmailJob emailJob){
		
		String readTemplate = new String(emailTemplate);
		Pattern namepattern = Pattern.compile("%name%");
		Matcher matcher = namepattern.matcher(readTemplate);
		readTemplate=matcher.replaceAll(emailJob.getName());
		Pattern idPattern = Pattern.compile("%uniqueid%");
		matcher = idPattern.matcher(readTemplate);
		readTemplate=matcher.replaceAll(emailJob.getTrackingId());
		emailJob.setEmailBody(readTemplate);
	}



}
