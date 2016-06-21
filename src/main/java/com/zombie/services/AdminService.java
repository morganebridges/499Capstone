package com.zombie.services;

import com.zombie.models.User;
import com.zombie.utility.Globals;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A service for administrating the server
 * <br/>
 * <em>Created by Corin Dennison on 6/18/16</em>
 *
 * @since 7.0
 */
@Service
public class AdminService {
	@Autowired
	private UserService userService;

	public byte[] exportUserData() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		Iterator<User> userIterator = userService.getAllUsers().iterator();
		Iterator<String> userFieldIterator = Globals.getOrderedUserFields().iterator();

		//Write the header row
		Row headerRow = sheet.createRow(0);
		int headerColumn = 0;
		while(userFieldIterator.hasNext()) {
			Cell cell = headerRow.createCell(headerColumn);
			cell.setCellValue(userFieldIterator.next());
			headerColumn++;
		}

		//Write user rows
		int rowNum = 1;
		while(userIterator.hasNext()) {
			int column = 0;
			Row row = sheet.createRow(rowNum);
			User user = userIterator.next();
			Iterator<Object> fieldIterator = user.getAllFields();
			while (fieldIterator.hasNext()) {
				Object field = fieldIterator.next();
				System.out.println("before loop");
				System.out.println("column");
				Cell cell = row.createCell(column);
				if (field instanceof String) {
					cell.setCellValue((String)field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer)field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double)field);
				} else if (field instanceof Date) {
					cell.setCellValue((Date)field);
				} else if (field instanceof Boolean) {
					cell.setCellValue((Boolean)field);
				} else {
					if(column == 0) {

						cell.setCellValue("");
					}
					else if(column == 6){
						cell.setCellValue("");
					}

				}

				column++;
			}

			rowNum++;
		}

		try (ByteArrayOutputStream out = new ByteArrayOutputStream())
		{
			//Write the workbook to output stream
			workbook.write(out);
			byte[] bytes = out.toByteArray();
			out.close();
			return bytes;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return new byte[0];
		}
	}

	/**
	 * Imports n users from an Excel file and commits them to the database
	 *
	 * @param input an inputStreamSource containing an Excel file
	 * @return The number of added users
	 * @throws IllegalArgumentException If the source is not an Excel document or the data is invalid for a user
	 */
	public int importUserData(InputStreamSource input) throws IllegalArgumentException{
		int numOfUsers = 0;

		// Get the workbook and sheet
		XSSFWorkbook workbook = null;
		try (InputStream reader = input.getInputStream()){
			workbook = new XSSFWorkbook(reader);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Error reading document");
		}
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.rowIterator();

		try {
			// Validate the header row
			if (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				Iterator<String> userFieldIterator = Globals.getOrderedUserFields().iterator();
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
					int totalKills = (int)(cellIterator.next().getNumericCellValue());
					int kills = (int)(cellIterator.next().getNumericCellValue());
					boolean active = cellIterator.next().getBooleanCellValue();
					int ammo = (int)(cellIterator.next().getNumericCellValue());
					int serum = (int)(cellIterator.next().getNumericCellValue());
					Date lastUsedSerum = cellIterator.next().getDateCellValue();

					if (name == null) {
						throw new IllegalStateException("Column 1.");
					}
					User newUser = new User(name, totalKills, kills, active, ammo, serum, lastUsedSerum);
					userService.save(newUser);
					numOfUsers++;
				} catch (IllegalStateException e) {
					e.printStackTrace();
					throw new IllegalArgumentException("Invalid cell in row " + (numOfUsers + 2) + ", " + e.getMessage());
				}
			}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Not enough columns");
		}
		return numOfUsers;
	}
}
