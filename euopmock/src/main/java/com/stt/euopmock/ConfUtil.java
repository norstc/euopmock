package com.stt.euopmock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfUtil {
	public static Logger log = LogManager.getLogger(ConfUtil.class);
	private Properties properties;
	private static ConfUtil confUtil = null;

	public Properties getProperties() {
		return properties;
	}
	//lazy  load singleton pattern
	public static ConfUtil getConfUtil(String config_file) {
		if(confUtil == null) {
			return new ConfUtil(config_file);
		}else {
			return confUtil;
		}
	}
	private ConfUtil(String config_file){
		String config_file_path =config_file;//"config/conf_channel.properties";
		properties = new Properties();
		try {
			InputStream config_is = new FileInputStream(config_file_path);
			properties.load(config_is);
			config_is.close();
		}catch (FileNotFoundException f) {
			log.info(f.getStackTrace());
			
		}catch(IOException i) {
			log.info(i.getStackTrace());
			
		}
	}
	public static void main(String[] args) {
		log.info("test conf");
		String CONTENT_FTP_SERVER1 = ConfUtil.getConfUtil("config/sftp.properties").getProperties().getProperty("CONTENT_FTP_SERVER1");
		log.info("CONTENT_FTP_SERVER1  is "+ CONTENT_FTP_SERVER1);
	}
}
