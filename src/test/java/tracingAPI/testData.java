package tracingAPI;

import java.util.Properties;
import java.util.UUID;

import org.testng.annotations.DataProvider;

import utils.Utils;

public class testData {
	Properties testData=Utils.loadProperties("tracingAPIPost.properties");;
	
	String uri=testData.getProperty("uri");
	String postMethod=testData.getProperty("postMethod");
	
	String tracingFundUniverseQuery=testData.getProperty("tracingFundUniverseQuery");
	
	@DataProvider(name ="tracingAPIData")
	public Object[][] tracingAPI(){		
		Object[][] tracingData=Utils.CSVReader("tracingPostData.csv");
		for(int i=0;i<tracingData.length;i++) {
			final String uuid1= UUID.randomUUID().toString().replace("-", "");
			tracingData[i][tracingData[i].length-1]=uuid1;
		}
		return tracingData;
	}
}
