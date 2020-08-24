package com.stt.euopmock.model;

public class Result {
	// "resultCode":"0000","productInfo":{"products":"0410000111004262609","imei":"","prov_id":""}
	private String resultCode;
	private ProductInfo productInfo;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

}
