package com.thomassmithyman.topbloc.thomas_smithyman;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

public class ExcelReader {

	public static final String DATA1_FILE_PATH = "/Users/Thomas/Documents/Data1.xlsx";
	public static final String DATA2_FILE_PATH = "/Users/Thomas/Documents/Data2.xlsx";

	public static void main(String[] args) throws IOException,
			InvalidFormatException {

		Workbook data1 = WorkbookFactory.create(new File(DATA1_FILE_PATH));
		Workbook data2 = WorkbookFactory.create(new File(DATA2_FILE_PATH));

		Sheet sheet1 = data1.getSheetAt(0);
		Sheet sheet2 = data2.getSheetAt(0);

		// Create a DataFormatter to format and get each cell's value as a
		// String
		DataFormatter dataFormatter = new DataFormatter();

		int amountOfRows = sheet1.getLastRowNum();

		int[] data1NumberSet1 = new int[amountOfRows];
		int[] data1NumberSet2 = new int[amountOfRows];
		String[] data1WordSet = new String[amountOfRows];

		int[] data2NumberSet1 = new int[amountOfRows];
		int[] data2NumberSet2 = new int[amountOfRows];
		String[] data2WordSet = new String[amountOfRows];

		// Create and initialize values to fill data1 arrays
		int j = 0, k = 0, l = 0, column = 1;

		// Fill the arrays for the data from Data1.xlsx
		for (int i = 1; i < amountOfRows + 1; i++) {

			Row row = sheet1.getRow(i);

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				if (column == 1) {
					data1NumberSet1[j] = Integer.parseInt(cellValue);
					j++;
					column++;
				} else if (column == 2) {
					data1NumberSet2[k] = Integer.parseInt(cellValue);
					k++;
					column++;
				} else {
					data1WordSet[l] = cellValue;
					l++;
					column = 1;
				}
			}
		}

		// Reset the counts for initializing the new data2 arrays
		j = 0;
		k = 0;
		l = 0;

		// Fill the arrays for the data from Data2.xlsx
		for (int i = 1; i < amountOfRows + 1; i++) {

			Row row = sheet2.getRow(i);

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				if (column == 1) {
					data2NumberSet1[j] = Integer.parseInt(cellValue);
					j++;
					column++;
				} else if (column == 2) {
					data2NumberSet2[k] = Integer.parseInt(cellValue);
					k++;
					column++;
				} else {
					data2WordSet[l] = cellValue;
					l++;
					column = 1;
				}
			}
		}

		// Multiply data1NumberSet1 and data2NumberSet1 together and store back
		// into data1NumberSet1
		for (int i = 0; i < data1NumberSet1.length; i++) {
			data1NumberSet1[i] = data1NumberSet1[i] * data2NumberSet1[i];
		}

		// Divide data1NumberSet2 and data2NumberSet2 together and store back
		// into data1NumberSet2
		for (int i = 0; i < data1NumberSet2.length; i++) {
			data1NumberSet2[i] = data1NumberSet2[i] / data2NumberSet2[i];
		}

		// Concatenate data1WordSet and data2WordSet together and store back
		// into data1WordSet
		for (int i = 0; i < data1WordSet.length; i++) {
			data1WordSet[i] = data1WordSet[i] + " " + data2WordSet[i];
		}

		// Closing the workbooks
		data1.close();
		data2.close();

		// Perform the JSON request
		JSONRequest JSONrequest = new JSONRequest();
		JSONrequest.createJSONRequest(data1NumberSet1, data1NumberSet2,
				data1WordSet);

	}
}
