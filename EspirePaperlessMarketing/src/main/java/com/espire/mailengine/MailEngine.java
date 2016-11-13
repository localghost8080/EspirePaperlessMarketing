package com.espire.mailengine;

import com.espire.job.EmailJob;

public interface MailEngine {

	public void sendEmail(EmailJob emailJob);
	
}
