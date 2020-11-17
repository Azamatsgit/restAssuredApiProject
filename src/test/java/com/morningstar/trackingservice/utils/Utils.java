package com.morningstar.trackingservice.utils;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Utils {
	
	public static Logger logger = Logger.getLogger("tracingAPI");
	public static final String propertiesFolder="src/test/resources/properties";
	public static final String csvData="src/test/resources/data";
	
	public static Properties loadProperties(String configFile) {
		Properties mainProperties = new Properties();

	    FileInputStream file;
	    try {
	    	file = new FileInputStream(propertiesFolder+"/"+configFile);
			//file = new FileInputStream(propertiesFolder+"/"+configFile);
			mainProperties.load(file);
		    file.close();
		} catch (Exception e1) {
			logger.log(Level.SEVERE,"Couldnt create property file "+e1.getMessage());
			System.err.println("Couldnt load property file "+e1.getMessage());
		}
	    return mainProperties;
	}
	
	public static Object[][] CSVReader(String file) {
		String[][] object = null;
		ArrayList<String[]> object2 = new ArrayList<String[]>();
		int objectCounter=0;
		try {
			CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
			CSVReader reader = new CSVReaderBuilder(new FileReader(csvData+"/"+file)).withCSVParser(parser).build();
			String[] nextLine;
			nextLine=reader.readNext();
			while((nextLine = reader.readNext()) != null) {
				object2.add(nextLine);
				objectCounter++;
			}
			object=new String[objectCounter][object2.get(0).length];
			
			for(int i=0;i<objectCounter;i++) {
				for(int x=0;x<object2.get(0).length;x++) {
					object[i][x]=object2.get(i)[x];
				}
			}
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"Couldnt read csv file "+e.getMessage());
			System.err.println("Couldnt read csv file "+e.getMessage());
		}
		return object;
	}
}
