package com.stt.euopmock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProvinceIopSimActivityController {
	private static final Logger LOG = LoggerFactory.getLogger(ProvinceIopSimActivityController.class);
	@Autowired
	ResourceLoader resourceLoader;
	
	//模拟省IOP推送活动
	// http://192.168.1.200:9999/provinceiopsimactivity/2100/1/1.4.8/dmz/
	/*
	 * iopId: 2100
	 * process: 1 / 2
	 * version： 1.4.8 / 1.6
	 * network: dmz / core
	 */
	@GetMapping(path="/provinceiopsimactivity/{iopId}/{process}/{version}/{network}")
	public String provinceIopSimActivity(@PathVariable String iopId, @PathVariable String process, @PathVariable String version, @PathVariable String network) {
		LOG.info("value is " + iopId + '/' + process + '/' +  version + '/' + network);
		String result = "";
		if(iopId.equals("2100") && process.equals("1") && version.equals("1.4.8") && network.equals("dmz")) {
			result = "tbd : "+iopId;
		}else {
			result = "tbd";
		}
		return result;
	}
}
