package com.tests.integrationTests.Test;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class tracingAPIpostTest extends testData {
	public String environment;
	public String scope;
	
	@Parameters({"environment","scope"})
	@Test
	void TestGetResponse(String param,String scope) {

	}
	
	@Parameters({"environment","scope"})
	@Test
	void TestGetResponseRegression(String param,String scope) {

	}
	
	
	


}
