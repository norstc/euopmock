package com.stt.euopmock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.stt.euopmock.ConfUtil;

@RestController
public class ProvinceIopSimActivityController {
	private static final Logger LOG = LoggerFactory.getLogger(ProvinceIopSimActivityController.class);
	@Autowired
	ResourceLoader resourceLoader;
	
	//模拟省IOP推送活动
	//广东iop， 流程1， 1.4.8， 核心域
	// http://192.168.1.200:9999/provinceiopsimactivity/2200/1/1.4.8/dmz/
	/*
	 * iopId: 2100
	 * process: 1 / 2
	 * version： 1.4.8 / 1.6
	 * network: dmz / core
	 */
	@GetMapping(path="/provinceiopsimactivity/{iopId}/{process}/{version}/{network}")
	public String provinceIopSimActivity(@PathVariable String iopId, @PathVariable String process, @PathVariable String version, @PathVariable String network) {
		LOG.info("value is " + iopId + '/' + process + '/' +  version + '/' + network);
		String localFile="config/sftp.properties";
		String remoteFile = "config/sftp.properties";
		String result = "";
		if(iopId.equals("2200") && process.equals("1") && version.equals("1.4.8") && network.equals("dmz")) {
			// get sftp info
			String CONTENT_FTP_SERVER1 = ConfUtil.getConfUtil("config/sftp.properties").getProperties().getProperty("CONTENT_FTP_SERVER1");
			String CONTENT_FTP_PORT1 = ConfUtil.getConfUtil("config/sftp.properties").getProperties().getProperty("CONTENT_FTP_PORT1");
			
			String CONTENT_FTP_USER1 = ConfUtil.getConfUtil("config/sftp.properties").getProperties().getProperty("CONTENT_FTP_USER1");
			String CONTENT_FTP_PASSWORD1 = ConfUtil.getConfUtil("config/sftp.properties").getProperties().getProperty("CONTENT_FTP_PASSWORD1");
			LOG.info("CONTENT_FTP_SERVER1:"+CONTENT_FTP_SERVER1);
			LOG.info("CONTENT_FTP_PORT1:" + CONTENT_FTP_PORT1);
			LOG.info("CONTENT_FTP_USER1:" + CONTENT_FTP_USER1);
			LOG.info("CONTENT_FTP_PASSWORD1:" + CONTENT_FTP_PASSWORD1);
			int SESSION_TIMEOUT = 10000;
			int CHANNEL_TIMEOUT = 5000;
			
			JSch jsch = new JSch();
			Session jschSession = null;
			try {
				jsch.setKnownHosts("/home/admin/.ssh/known_hosts");
				jschSession = jsch.getSession(CONTENT_FTP_USER1, CONTENT_FTP_SERVER1, Integer.parseInt(CONTENT_FTP_PORT1));
				jschSession.setPassword(CONTENT_FTP_PASSWORD1);
				jschSession.connect(SESSION_TIMEOUT);
				Channel sftp = jschSession.openChannel("sftp");
				sftp.connect(CHANNEL_TIMEOUT);
				ChannelSftp channelSftp = (ChannelSftp) sftp;
				channelSftp.put(localFile, remoteFile);
				
				channelSftp.exit();
				
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			generateSimActivityFile();
			uploadSimActivityFile();
			result = "tbd : "+iopId;
		}else {
			result = "tbd";
		}
		return result;
	}

	private void uploadSimActivityFile() {
		// TODO Auto-generated method stub
		
	}

	private void generateSimActivityFile() {
		// TODO Auto-generated method stub
		
	}

	private void getSftpUsername() {
		// TODO Auto-generated method stub
		
	}
}
