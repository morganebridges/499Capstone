package com.zombie.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TODO: describe<br/>
 * <br/>
 * <em>Created on 6/18/16</em>
 *
 * @since 7.0
 */
@Service
public class AdminService {
	/**
	 * Imports n users from an Excel file and commits them to the database
	 *
	 * @param input an inputStreamSource containing an Excel file
	 * @return The number of added users
	 * @throws IllegalArgumentException If the source is not an Excel document or the data is invalid for a user
	 */
	public int importUserData (InputStreamSource input) throws IllegalArgumentException{
		int numOfUsers = 0;

		// Get the workbook and sheet
		XSSFWorkbook workbook = null;
		try (InputStream reader = input.getInputStream()){
			workbook = new XSSFWorkbook(reader);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Error reading document");
		}
		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.rowIterator();

		try {
			// Validate the header row
			if (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				//TODO: put these values somewhere global
				ArrayList<String> orderedUserFields = new ArrayList<>();
				orderedUserFields.add("name");
				orderedUserFields.add("total kills");
				orderedUserFields.add("kills");
				orderedUserFields.add("active");
				orderedUserFields.add("ammo");
				orderedUserFields.add("serum");
				orderedUserFields.add("last used serum");
				Iterator<String> userFieldIterator = orderedUserFields.iterator();
				while (userFieldIterator.hasNext()) {
					String expected = userFieldIterator.next();
					String actual = cellIterator.next().getStringCellValue();
					if (!expected.equals(actual)) {
						throw new IllegalArgumentException("Invalid format for column header " + actual);
					}
				}
				if (!rowIterator.hasNext()) {
					return 0;
				}
			}

			// Start pulling user rows
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				try {
					String name = cellIterator.next().getStringCellValue();
					double totalKills = cellIterator.next().getNumericCellValue();
					double kills = cellIterator.next().getNumericCellValue();
					boolean active = cellIterator.next().getBooleanCellValue();
					double ammo = cellIterator.next().getNumericCellValue();
					double serum = cellIterator.next().getNumericCellValue();
					Date lastUsedSerum = cellIterator.next().getDateCellValue();

					if (name == null) {
						throw new IllegalStateException("Column 1.");
					}
						// TODO: Turn cells into a user
						numOfUsers++;
				} catch (IllegalStateException e) {
					throw new IllegalArgumentException("Invalid cell in row " + (numOfUsers + 2) + ", " + e.getMessage());
				}
			}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Not enough columns");
		}
		// TODO: Commit database
		// TODO: logging? log.info("Data uploaded successfully! Number of users=" + numOfUsers);
		return numOfUsers;
	}
}
