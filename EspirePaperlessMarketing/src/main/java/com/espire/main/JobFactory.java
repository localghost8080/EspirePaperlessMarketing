package com.espire.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.espire.configuration.Configuration;
import com.espire.job.BatchEmailJob;
import com.espire.job.EmailJob;

public abstract class JobFactory {
	
	final static Logger log = Logger.getLogger(JobFactory.class);	
	private String emailTemplate ="";

	public abstract BatchEmailJob createEmailJobs();
	
	public JobFactory(){
		
		try{
			emailTemplate=new String(Files.readAllBytes(Paths.get(Configuration.getProperty("email.template.filepath"))));
		}
		catch(IOException ie){
			log.error(ie);
		}
	}
		
	void parseEmailBody(EmailJob emailJob){
		
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
