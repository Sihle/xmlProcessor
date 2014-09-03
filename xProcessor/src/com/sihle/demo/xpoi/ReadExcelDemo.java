package com.sihle.demo.xpoi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

public class ReadExcelDemo {
	static int count;
	static int rmHeader = 0;

	public static void main(String[] args) {
		try {
			FileInputStream file = new FileInputStream(new File(
			"excelRead_demo.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				String S_SERIAL_NO = "";
				String S_X_ID = "";
				String SIM = "";
				count = 0;

				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				if (rmHeader != 0) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						// Check the cell type and format accordingly
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							// System.out.print(cell.getStringCellValue() +
							// "\t");
							if (count == 0) {
								S_SERIAL_NO = cell.getStringCellValue();
							} else if (count == 1) {
								S_X_ID = cell.getStringCellValue();
							} else if (count == 2) {
								SIM = cell.getStringCellValue();
							}
							break;
						}
						count++;
					}
					// System.out.println(" ===="+S_SERIAL_NO+" "+S_X_ID+" "+SIM);

					// Check if that file is in that directory
					// If it is then process it.
					if (validate(System.getProperty("user.dir") + "\\in\\"
							+ S_SERIAL_NO + ".xml")) {
						parse(S_X_ID, SIM, S_SERIAL_NO);
					} else {
						System.out.println("************* The file is not in that directory *************");
					}
				}
				rmHeader++;
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Parse each XML file.
	static void parse(String vBirthDate, String vSIMCardNumber, String xmlName)
			throws TransformerException, TransformerConfigurationException,
			FileNotFoundException, IOException {
		// Use the static TransformerFactory.newInstance() method to instantiate
		// a TransformerFactory. The javax.xml.transform.TransformerFactory
		// system property setting determines the actual class to instantiate --
		// org.apache.xalan.transformer.TransformerImpl.
		TransformerFactory tFactory = TransformerFactory.newInstance();

		// Use the TransformerFactory to instantiate a Transformer that will
		// work with
		// the stylesheet you specify. This method call also processes the
		// stylesheet
		// into a compiled Templates object.
		Transformer transformer = tFactory.newTransformer(new StreamSource(
				"xslScript.xsl"));

		// Set the parameter. I can't get non-null namespaces to work!!
		transformer.setParameter("pBirthDate", /* parameter name */
				vBirthDate /* parameter value */);
		transformer.setParameter("pSIMCardNumber", /* parameter name */
				vSIMCardNumber /* parameter value */);
		// Use the Transformer to apply the associated Templates object to an
		// XML document
		// (./in/foo.xml) and write the output to a file (./out/foo.xml).
		transformer.transform(new StreamSource(System.getProperty("user.dir")
				+ "\\in\\" + xmlName + ".xml"), new StreamResult(
				new FileOutputStream(System.getProperty("user.dir") + "\\out\\"
						+ xmlName + ".xml")));

		System.out.println("************* The result is in "
				+ System.getProperty("user.dir") + "\\out\\" + xmlName + ".xml"
				+ " *************");
	}

	static boolean validate(String fDir) throws FileNotFoundException,
	IOException, ParserConfigurationException, SAXException {
		boolean result = false;
		File dir = new File(fDir);

		if (dir.isFile()) // Just checking one file.
		{
			System.out.println("************* Looking for a file at this point. *************");
			result = true;
		} else if (dir.isDirectory()) // Checking the contents of a directory.
		{
			System.out.println("************* Not looking for a directory at this point. *************");
		}
		return result;
	}
}
