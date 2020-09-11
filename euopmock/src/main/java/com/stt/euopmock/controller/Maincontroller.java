package com.stt.euopmock.controller;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stt.euopmock.model.City;
import com.stt.euopmock.service.ICityService;

import java.io.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@RestController
public class Maincontroller {
	private static final Logger LOG=LoggerFactory.getLogger(Maincontroller.class);
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
    private ICityService cityService;

    @GetMapping(value = "/cities")
    public List<City> getCities() {

        List<City> cities = cityService.findAll();

        return cities;
    }
    

    
    // 模拟一级IOP查询返回
    // http://192.168.1.200:9999/recommendService/services/tagquery/01711165060/0171116512185/15862032301
    @GetMapping(path="/recommendService/services/tagquery/{iopChannelId}/{iopOperationId}/{servNum}")
    public String getIOPActivity(@PathVariable String iopChannelId, @PathVariable String iopOperationId,@PathVariable String servNum) {
    	LOG.info("value is " + iopChannelId+'/'+iopOperationId+'/'+servNum);
    	String result ="";

    	if (iopChannelId.equals( "NN01711165060") && iopOperationId.equals( "0171116512185")  && servNum.equals( "15862032301")) {
    		//咪咕视频 01711165060/0171116512185/15862032301
    	result="{\"resultCode\":\"0000\",\"productInfo\":{\"products\":\"0410000111004262609\",\"imei\":\"\",\"prov_id\":\"\"}}";
    	}else if (iopChannelId.equals( "01711165060") && iopOperationId.equals("0171116512185")  && servNum.equals( "17305196292")){
    		//咪咕视频 01711165060/0171116512185/17305196292
    		Random r = new Random();
    		boolean b1 = r.nextBoolean();
    		//配置两个活动，随机返回一个
    		if(b1) {
    			result = "{\"resultCode\":\"0000\",\"productInfo\":{\"products\":\"0102030120200904001\",\"imei\":\"\",\"prov_id\":\"\"}}";
    		}else {
    			result = "{\"resultCode\":\"0000\",\"productInfo\":{\"products\":\"0102030120200904001\",\"imei\":\"\",\"prov_id\":\"\"}}";
    		}
    		
    		
    	}else if (iopChannelId.equals( "01710210074") && iopOperationId.equals("017102108753")  && servNum.equals( "13427535800")){
    		//上海和你app
    		result = "{\"resultCode\":\"0000\",\"productInfo\":{\"products\":\"0410000111004262609\",\"imei\":\"\",\"prov_id\":\"\"}}";
    	}else {
    		result="{\"resultCode\":\"0001\",\"productInfo\":{\"products\":\"\",\"imei\":\"\",\"prov_id\":\"\"}}";
    	}
    	
		return result;
    }
    
    //模拟省iop实时查询返回
    @PostMapping(path="/{provIOPId}/iopRecommendInfo",consumes="application/json",produces="application/json")
    public String getProvIOPActivity(@PathVariable String provIOPId) {
    	if (provIOPId == null || provIOPId.isEmpty()) {
    		return "provIOPId is a must";
    	}
    	String result="";
    	LOG.info("request is /"+provIOPId+"/iopRecommendInfo" );
    	if (provIOPId.equals("2100")) {
    		//北京IOP
    		result="{\"result\":{\"conversationId\":\"1540545125485a0\",\"responseCode\":\"0000\",\"productInfo\":{\"activityId\":\"210010000018214\",\"subActivityId\":\"210000000182141000003\",\"provId\":\"100\"}}}";
    	}else if(provIOPId.equals("2101")) {
    		//北京IOP2
    		result="{\"result\":{\"data\":{\"activityInfo\":{\"activityId\":\"210110000018214\",\"subActivityId\":\"2101100000182141000003\"}},\"conversationId\":\"2018111515501391504152399103722735802\",\"message\":\"处理成功\",\"responseCode\":\"0000\"}}";
    	}else {
    		//没有查到省IOP
    		result="{\"result\":{\"conversationId\":\"1540545125485a0\",\"responseCode\":\"0001\"}}";
    	}
		
		
    	return result;
    	
    }
    
    //模拟手厅运营位接口, 读取数据文件，将其内容返回
    @PostMapping(path="/1000003/op",consumes="application/json", produces="application/json")
    public String get1000003Op() {
    	LOG.info("request is /1000003/op");
    	String result="";
    	//直接从文件中读取
    	Resource resource=resourceLoader.getResource("classpath:testdata/08_20200907.js");
    	
    	try {
			Reader reader = new InputStreamReader(resource.getInputStream(),"UTF-8");
			result=FileCopyUtils.copyToString(reader);
			
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	return result;
    }
    
    //模拟省IOP规范-v68  9.9	星火联盟用户基础标签实时查询接口
    // http://192.168.1.200:9999/2371/tag
    @PostMapping(path="/{provId}/tag",consumes="application/json",produces="application/json")
    public String getTag(@PathVariable String provId) {
    	String result="";
    	
    	if (provId.equals("2371")) {
    		//河南
    		result="{\"result\":{\"message\":\"操作成功\",\"responseCode\":\"0000\",\"conversationId\":\"20160608120012888888\",\"labelInfo\":{\"attrList\":[{\"attrId\":\"000201601212154500000055\",\"attrName\":\"网龄(月)\",\"attrValue\":\"12\",\"unit\":\"月\",\"group\":\"000201611020903500000000|基本属性\"},{\"attrId\":\"000201601122246350000005\",\"attrName\":\"客户星级\",\"attrValue\":\"4\",\"unit\":null,\"group\":\"000201611020903500000000|基本属性\"}]}}}";
    	}
		return result;
    	
    }
    
    //模拟审核反馈
    //iop_url_97004=http://192.168.1.200:9999/1001/auditFeedBack
    @PostMapping(path="/{iopId}/auditFeedBack")
    public String getIOPAuditFeedBack(@PathVariable String iopId) {
    	if (iopId == null || iopId.isEmpty()) {
    		return "iopId is a must";
    	}
    	String result="";
    	LOG.info("request is /"+iopId+"/auditFeedBack" );
    	if (iopId.equals("1001")) {
    		//一级IOP
    		//直接从文件中读取
        	Resource resource=resourceLoader.getResource("classpath:testdata/E2GNetContentFeedbackService_response.txt");
        	Reader reader;
			try {
				reader = new InputStreamReader(resource.getInputStream(),"UTF-8");
				result=FileCopyUtils.copyToString(reader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		//result="{\"result\":{\"conversationId\":\"1540545125485a0\",\"responseCode\":\"0000\",\"productInfo\":{\"activityId\":\"210010000018214\",\"subActivityId\":\"210000000182141000003\",\"provId\":\"100\"}}}";
    	}else if(iopId.equals("2100")) {
    		//北京IOP
    		result="{\"result\":{\"data\":{\"activityInfo\":{\"activityId\":\"210110000018214\",\"subActivityId\":\"2101100000182141000003\"}},\"conversationId\":\"2018111515501391504152399103722735802\",\"message\":\"处理成功\",\"responseCode\":\"0000\"}}";
    	}else if(iopId.equals("iop_url_97004")) {
    		//一级IOP iop_url_97004
    		result="{\"result\":{\"data\":{\"activityInfo\":{\"activityId\":\"210110000018214\",\"subActivityId\":\"2101100000182141000003\"}},\"conversationId\":\"2018111515501391504152399103722735802\",\"message\":\"处理成功\",\"responseCode\":\"0000\"}}";
    	}else {
    		//没有查到省IOP
    		result="{\"result\":{\"conversationId\":\"1540545125485a0\",\"responseCode\":\"0001\"}}";
    	}
		
		
    	return result;
    	
    }
    
    
  //模拟渠道状态变更反馈
   // #一级iop
   // iop1001_status_feedback_url=http://192.168.1.200:9999/1001/statusFeedBack
    @PostMapping(path="/{iopId}/statusFeedBack")
    public String getStatusFeedBack(@PathVariable String iopId) {
    	if (iopId == null || iopId.isEmpty()) {
    		return "iopId is a must";
    	}
    	String result="";
    	LOG.info("request is /"+iopId+"/statusFeedBack" );
    	if (iopId.equals("1001")) {
    		//一级IOP 还未实现，生产上不会有这个，主要是针对省iop
    		//直接从文件中读取
        	Resource resource=resourceLoader.getResource("classpath:testdata/E2GNetContentFeedbackService_response.txt");
        	Reader reader;
			try {
				reader = new InputStreamReader(resource.getInputStream(),"UTF-8");
				result=FileCopyUtils.copyToString(reader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		//result="{\"result\":{\"conversationId\":\"1540545125485a0\",\"responseCode\":\"0000\",\"productInfo\":{\"activityId\":\"210010000018214\",\"subActivityId\":\"210000000182141000003\",\"provId\":\"100\"}}}";
    	}else if(iopId.equals("2100")) {
    		//北京IOP
    		result="{\"result\":{\"conversationId\":\"20160608120012888888\",\"responseCode\":\"0000\"}}";
    	}else {
    		//没有查到省IOP
    		result="{\"result\":{\"conversationId\":\"20160608120012888888\",\"responseCode\":\"9998\"}}";
    	}
		
		
    	return result;
    	
    }
}
