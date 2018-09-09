package com.qa.util;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUtil {

	public static int RESPONSE_CODE_200 = 200; // successful API request OK
	public static int RESPONSE_CODE_201 = 201; // created data
	public static int RESPONSE_CODE_401 = 401; // Unauthorized
	public static int RESPONSE_CODE_404 = 404; // not found
	public static int RESPONSE_CODE_400 = 400; // Bad request

	public static String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayData = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
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
