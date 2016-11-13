package com.espire.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Configuration {
	
	private static Properties properties;
	final static Logger log = Logger.getLogger(Configuration.class);
	
	/**
	 * This method reads the configuration property file and provides convenience methods to get specific values.
	 * @param propertyFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void loadProperties(String propertyFile) throws FileNotFoundException,IOException{
		if(properties==null){
			InputStream in = new FileInputStream(new File(propertyFile));
			properties = new Properties();
			properties.load(in);
			properties.entrySet().stream().forEach((entry)->log.info(""+entry.getKey()+" : "+entry.getValue()));
			String smtpPassword = properties.getProperty("mail.smtp.password");
			Base64 base64 = new Base64();
			String decodedPassw = new String(base64.decode(smtpPassword));
			properties.setProperty("mail.smtp.password", decodedPassw);
		}
				
	}
	
	public static String getProperty(String propertyName){
		return properties.getProperty(propertyName);
	}
	
	public static Properties getProperties(){
		return properties;
	}
	
	

}
