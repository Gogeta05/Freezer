package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseTest {

	private static Workbook workbook;
	private static Sheet sheet;
	
	@BeforeClass
	public static void setUp() throws Exception {
		try {
			File file = File.createTempFile("jdslk", "jskdl");
			FileUtils.copyURLToFile(new URL("https://gis.tirol.gv.at/ogd/sport_freizeit/Aufstiegshilfen.xls"), file);
			workbook = Workbook.getWorkbook(file);
		    sheet = workbook.getSheet(0); 
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Tests for the xls spreadsheet:

	@Test
	public void checkConsistencyOfSpreadsheet() {
		//Check row names
		assertEquals("Error: column 'Name' not at the expected position", sheet.getCell(0, 2).getContents(), "Name");
		assertEquals("Error: column 'ID' not at the expected position", sheet.getCell(1, 2).getContents(), "ID");
		assertEquals("Error: column 'PLZ' not at the expected position", sheet.getCell(2, 2).getContents(), "PLZ");
		assertEquals("Error: column 'Gemeinde' not at the expected position", sheet.getCell(3, 2).getContents(), "Gemeinde");
		assertEquals("Error: column 'aufgelassen' not at the expected position", sheet.getCell(8, 2).getContents(), "aufgelassen");
		assertEquals("Error: column 'Typ' not at the expected position", sheet.getCell(10, 2).getContents(), "Typ");
		assertTrue("Error: column 'Personen pro Aufhängung' not at the expected position", sheet.getCell(14, 2).getContents().startsWith("Personen"));
		assertEquals("Error: column 'Sitzplatzheizung' not at the expected position", sheet.getCell(15, 2).getContents(), "Sitzplatzheizung");
		assertEquals("Error: column 'Wetterschutz' not at the expected position", sheet.getCell(16, 2).getContents(), "Wetterschutz");
		
		//Check cell types
		int j;
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'Name' - fields have the wrong type", sheet.getCell(0, j).getType() == CellType.LABEL);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'ID' - fields have the wrong type", sheet.getCell(1, j).getType() == CellType.LABEL);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'PLZ' - fields have the wrong type", sheet.getCell(2, j).getType() == CellType.LABEL);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'Gemeinde' - fields have the wrong type", sheet.getCell(3, j).getType() == CellType.LABEL);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'aufgelassen' - fields have the wrong type", sheet.getCell(8, j).getType() == CellType.LABEL);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'Typ' - fields have the wrong type", sheet.getCell(10, j).getType() == CellType.LABEL);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'Personen pro Aufhängung' - fields have the wrong type", sheet.getCell(14, j).getType() == CellType.NUMBER);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'Sitzplatzheizung' - fields have the wrong type", sheet.getCell(15, j).getType() == CellType.LABEL);
		j = 3;
		while(sheet.getCell(0, 3).getType() == CellType.EMPTY && j <= sheet.getColumns())
			j++;
		assertTrue("Error: column 'Wetterschutz' - fields have the wrong type", sheet.getCell(16, j).getType() == CellType.LABEL);
	}

}
