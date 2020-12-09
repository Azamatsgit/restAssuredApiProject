package com.morningstar.trackingservice.integrationTests.tracingAPI;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.morningstar.trackingservice.config.marlinDBConnection;
import com.morningstar.trackingservice.integrationTests.testData;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class tracingAPIpostTest extends testData {
	public String environment;
	public String scope;
	@Parameters({"environment","scope"})
	@Test
	void TestGetResponse(String param,String scope) {
		environment=param;
		this.scope=scope;
		Response response = null;
		if(environment.equals("QA")) {
			 response= RestAssured.get(uriQA+postMethod);
			 System.out.println(scope);
		}else if(environment.equals("UAT")) {
			 response= RestAssured.get(uriUAT+postMethod);
		}else if(environment.equals("PROD")) {
			
		}else {
			System.err.println("Not a valid environment");
		}
		
		Assert.assertEquals(response.getStatusCode(), 405);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "tracingAPIData")
	void TestPostExecutionAndResponse(String clientId,String clientCode,String filename,String totalRecords,String totalRecordsUpdated,String totalRecordsAdded,
			String totalRecordsRemoved,String conflictingFunds,String s3FileSourceLocations,String createdBy,String uploadMethod,
			String status,String duplicateFunds,String missingIdentifierFunds,String invalidProductIdentifierFunds,String modifiedBy,String traceId){
		
		JSONObject request=new JSONObject();
		request.put("clientId", Integer.parseInt(clientId));
		request.put("clientCode", clientCode);
		request.put("filename", filename);
		request.put("totalRecords", Integer.parseInt(totalRecords));
		request.put("totalRecordsUpdated", Integer.parseInt(totalRecordsUpdated));
		request.put("totalRecordsAdded", Integer.parseInt(totalRecordsAdded));
		request.put("totalRecordsRemoved", Integer.parseInt(totalRecordsRemoved));
		request.put("conflictingFunds", conflictingFunds);
		request.put("s3FileSourceLocations", s3FileSourceLocations);
		request.put("createdBy", createdBy);
		request.put("uploadMethod", uploadMethod);
		request.put("status", status);
		request.put("duplicateFunds", duplicateFunds);
		request.put("missingIdentifierFunds", missingIdentifierFunds);
		request.put("invalidProductIdentifierFunds", invalidProductIdentifierFunds);
		request.put("modifiedBy", modifiedBy);
		if(environment.equals("QA")) {
			RestAssured.baseURI=uriQA;
		}else if(environment.equals("UAT")) {
			RestAssured.baseURI=uriUAT;
		}else if(environment.equals("PROD")) {
			
		}else {
			System.err.println("Not a valid environment");
		}
		
		RequestSpecification requestPOST = RestAssured.given();
		
		requestPOST.header("X-B3-TraceId",traceId);
		requestPOST.header("X-APP-NAME","Fund Universe");
		requestPOST.body(request.toJSONString());
		Response response = requestPOST.post(postMethod);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getBody().asString(), "Ok!");
	}
	
	
	@Test(dataProvider = "tracingAPIData")
	void test03DBChecking(String clientId,String clientCode,String filename,String totalRecords,String totalRecordsUpdated,String totalRecordsAdded,
			String totalRecordsRemoved,String conflictingFunds,String s3FileSourceLocations,String createdBy,String uploadMethod,
			String status,String duplicateFunds,String missingIdentifierFunds,String invalidProductIdentifierFunds,String modifiedBy,String traceId) {
		marlinDBConnection dbCon=new marlinDBConnection();
		if(environment.equals("QA")) {
			dbCon.setQA();
		}else if(environment.equals("UAT")) {
			dbCon.setUAT();
		}else if(environment.equals("PROD")) {
			
		}else {
			System.err.println("Not a valid environment");
		}
		
		ArrayList<String> result=dbCon.query(tracingFundUniverseQuery+clientId+"order by created_date desc");
		if(scope.equals("REGRESSION")){
			Assert.assertEquals(result.get(0),clientId+"");
			Assert.assertEquals(result.get(1),filename+"");
			Assert.assertEquals(result.get(4),conflictingFunds+"");
		}
		//traceID
		Assert.assertNotEquals( "", result.get(19));
	}
	
	

}
