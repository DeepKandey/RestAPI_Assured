package com.qa.util;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUtil {

	public final static int RESPONSE_CODE_200 = 200; // successful API request OK
	public final static int RESPONSE_CODE_201 = 201; // created data
	public final static int RESPONSE_CODE_401 = 401; // Unauthorized
	public final static int RESPONSE_CODE_404 = 404; // not found
	public final static int RESPONSE_CODE_400 = 400; // Bad request

	public static String TESTDATA_SHEET_PATH = "C:/Users/deepa/Downloads/APITestData.xlsx";
	public static String WEATHER_SHEET_NAME = "WeatherInfo";

	public static String[][] getExcelData(String filePath, String sheetName) {
		String[][] arrayData = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);

			int totalNoRows = sheet.getLastRowNum();
			int totalNoCols = sheet.getRow(0).getLastCellNum();
			arrayData = new String[totalNoRows][totalNoCols];

			for (int i = 0; i < totalNoRows; i++) {
				for (int j = 0; j < totalNoCols; j++) {
					arrayData[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayData;
	}
}