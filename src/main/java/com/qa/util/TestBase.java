package com.qa.util;

import java.io.FileInputStream;
import java.util.Properties;

public class TestBase {
	public static Properties prop;

	// Method to load the configuration properties file
	public static void init() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}