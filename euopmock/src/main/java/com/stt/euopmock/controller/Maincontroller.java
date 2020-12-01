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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stt.euopmock.model.City;
import com.stt.euopmock.service.ICityService;

import java.io.*;
import java.net.URLDecoder;

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
    	}else if (iopChannelId.equals("01705161003")) {
    		// 1000003
    		result = "{\"resultCode\":\"0000\",\"productInfo\":{\"products\":\"20201020015\",\"imei\":\"\",\"prov_id\":\"\"}}";
    	}
    	else {
    		result="{\"resultCode\":\"0001\",\"productInfo\":{\"products\":\"\",\"imei\":\"\",\"prov_id\":\"\"}}";
    	}
    	
		return result;
    }
    
    //模拟一级iop批量查询
   // #呼池
   // batch_iop_url_4=http://192.168.1.200:9999/4/recommendService/services/tagqueryiops
    //#VGOP
    //batch_iop_url_41=http://192.168.1.200:9999/41/recommendService/services/tagqueryiops
    // http://192.168.1.200:9999/4/recommendService/services/tagqueryiops/01705161003/08003/15862032301
    @GetMapping(path="/{iopLocation}/recommendService/services/tagqueryiops/{channelCode}/{batchId}/{servNum}")
    public String getIOPActivity(@PathVariable String iopLocation,@PathVariable String channelCode, @PathVariable String batchId,@PathVariable String servNum) {
    	LOG.info("value is " +iopLocation + "/recommendService/services/tagquery/"+ channelCode+'/'+batchId+'/'+servNum);
    	String result ="";
    	if(iopLocation.equals("4")) {
    		LOG.info("requst is go to 4,呼池");
    	}else {
    		LOG.info("request is go to 41, VGOP");
    	}
    	if (channelCode.equals( "01705161003") && batchId.equals( "008003")  && servNum.equals( "15862032301")) {
    		// 1170516100012 	首页-主广告1号位 	其它 	一级手厅 	一级IOP系统 	一级营销活动-一级手厅-营销活动-3257号位 	017051613257 	已上报 	1/0 
    		// 1170516100007 	窗帘广告位置1 	其它 	一级手厅 	一级IOP系统 	一级营销活动-一级手厅-营销活动-4065号位 	017051614065 
    		//一级手厅 01705161003/008003/15862032301
    		//查询成功
    		result="{\"resultCode\":\"0000\",\"productInfos\":[{\"active_id\":\"3242342543\",\"tag_id\":\"017051613257\",\"products\":\"20201015002\",\"imei\":\"\",\"prov_id\":\"\"},{\"active_id\":\"42243551\",\"tag_id\":\"017051614065\",\"products\":\"20201023004\",\"imei\":\"\",\"prov_id\":\"\"}]}";
    	}else if (channelCode.equals( "01705161091") && batchId.equals( "091001")  && servNum.equals( "18706716196")){
    		//流量中心
    		//http://192.168.1.200:9999/4/recommendService/services/tagqueryiops/01705161091/091001/18706716196
    		result="{\"resultCode\":\"0000\",\"productInfos\":[{\"active_id\":\"20201201001017051610910170516112785\",\"tag_id\":\"0170516112785\",\"products\":\"20201201p1\",\"imei\":\"\",\"prov_id\":\"\"},{\"active_id\":\"42243551\",\"tag_id\":\"0170516112793\",\"products\":\"20201201p1\",\"imei\":\"\",\"prov_id\":\"\"},{\"active_id\":\"42243551\",\"tag_id\":\"0170516112801\",\"products\":\"20201201p1\",\"imei\":\"\",\"prov_id\":\"\"}]}";
    	}
    	else {
    		// 查询成功，没有活动
    		result="{\"resultCode\":\"0001\",\"productInfo\":{\"products\":\"\",\"imei\":\"\",\"prov_id\":\"\"}}";
    	}
    	
		return result;
    }
   
    
    //模拟省iop实时查询返回
    // http://192.168.1.200:9999/2100/iopRecommendInfo
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
    // http://192.168.1.200:9999/1000003/op
    // TODO 用curl手动能拿到结果，但是程序无法解析，待确认
    @PostMapping(path="/1000003/op",consumes="application/json", produces="application/json")
    public String get1000003Op() {
    	LOG.info("request is /1000003/op");
    	String result="";
    	//直接从文件中读取
    	//20201029：运营位信息修改物料字数限制
    	Resource resource=resourceLoader.getResource("classpath:testdata/08_20201026_2.json");
    	
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
    	LOG.info("result is : " + result);
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
    //一级iop是webservice接口
    //省iop是http+json
    //iop_url_97004=http://192.168.1.200:9999/1001/auditFeedBack
    @PostMapping(path="/{iopId}/auditFeedBack")
    public String getIOPAuditFeedBack(@PathVariable String iopId, @RequestBody	byte[] data) {
    	if (iopId == null || iopId.isEmpty()) {
    		return "iopId is a must";
    	}
    	String result="";
    	LOG.info("request is /"+iopId+"/auditFeedBack" );
    	try {
			String rb = URLDecoder.decode(new String(data,"utf8"),"utf8");
			LOG.info("request data is :" + rb);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
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
    		result="{\"result\":{\"message\":\"操作成功\",\"success\":true,\"conversationId\":\"2020091821000012888888\"}}";
    	}else if(iopId.equals("2791")) {
    		//江西IOP  2791
    		result="{\"result\":{\"message\":\"操作成功\",\"success\":true,\"conversationId\":\"2020091827910012888888\"}}";
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
