package com.morningstar.trackingservice.integrationTests;

import java.util.Properties;
import java.util.UUID;

import org.testng.annotations.DataProvider;

import com.morningstar.trackingservice.utils.Utils;

public class testData {
	Properties testData=Utils.loadProperties("tracingAPIPost.properties");
	
	
	
	protected String uriQA=testData.getProperty("uri");
	protected String uriUAT=testData.getProperty("uriUAT");
	protected String postMethod=testData.getProperty("postMethod");
	private String dataenvironment="tracingPostData.csv";
	
	protected String tracingFundUniverseQuery=testData.getProperty("tracingFundUniverseQuery");
	
	@DataProvider(name ="tracingAPIData")
	public Object[][] tracingAPI(){		
		Object[][] tracingData=Utils.CSVReader(dataenvironment);
		for(int i=0;i<tracingData.length;i++) {
			final String uuid1= UUID.randomUUID().toString().replace("-", "");
			tracingData[i][tracingData[i].length-1]=uuid1;
		}
		return tracingData;
	}
	
	@DataProvider(name ="tracingAPIOne")
	public Object[][] tracingAPIOne(){		
		Object[][] tracingData=Utils.CSVReader(dataenvironment);
		for(int i=0;i<tracingData.length;i++) {
			final String uuid1= UUID.randomUUID().toString().replace("-", "");
			tracingData[i][tracingData[i].length-1]=uuid1;
		}
		return tracingData;
	}
	
	public void setData(String environment) {
		if(environment.equals("UAT"))
		this.dataenvironment="tracingPostDataUAT.csv";
	}
	
}
