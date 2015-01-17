package backend;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import models.Lift;
import models.Location;
import models.User;

import org.apache.commons.io.FileUtils;

import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import play.db.ebean.Model;
import utils.Util;

import com.avaje.ebean.Expr;

public final class Database {

	public static Lift getLift(String name) {
		return new Model.Finder<String, Lift>(String.class, Lift.class).where(Expr.eq("name", name)).findUnique();
	}

	public static User getUser(String usrname) {
		return new Model.Finder<String, User>(String.class, User.class).where(Expr.eq("username", usrname)).findUnique();
	}
	
	/**
	 * Reading the xls spreadsheet into the database.
	 */
	public static void readSpreadsheet() {
		try {
			File file = File.createTempFile("jdslk", "jskdl");
			FileUtils.copyURLToFile(new URL(Util.url_LiftXls), file);
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			// The order and type of cells is validated in the respective unit test
			for (int i = 3; i < sheet.getRows(); i++) { // read in tuples until end of table
				if (sheet.getCell(8, i).getContents().equalsIgnoreCase("nein")) { // filtering out lifts that are no longer active
					String name = sheet.getCell(0, i).getContents();
					int PLZ;
					if (sheet.getCell(2, i).getContents().equals("")) {
						PLZ = 0;
					} else {
						PLZ = Integer.parseInt(sheet.getCell(2, i).getContents()); // cell type in document is LABEL not NUMBER
					}
					String municipality = sheet.getCell(3, i).getContents();
					String type = sheet.getCell(10, i).getContents();
					int maxPersons;
					if (sheet.getCell(14, i).getContents().equals("")) {
						maxPersons = 0;
					} else {
						//better?
						maxPersons = (int) ((NumberCell) sheet.getCell(14, i)).getValue();
						/*
						maxPersons = Integer.parseInt(sheet.getCell(14, i).getContents());
						*/
					}
					Boolean seatHeating = sheet.getCell(15, i).getContents().equalsIgnoreCase("ja") ? true : false;
					String weatherProtection = sheet.getCell(16, i).getContents();

					Location location = new Location(PLZ, municipality);

					Lift oldVal = getLift(name);
					if (oldVal == null) {
						Lift lift = new Lift(name, location, type, maxPersons, seatHeating, weatherProtection);
						lift.save();
					} else {
						oldVal.name = name;
						oldVal.location = location;
						oldVal.type = type;
						oldVal.maxPersons = maxPersons;
						oldVal.seatHeating = seatHeating;
						oldVal.weatherProtection = weatherProtection;
						oldVal.update();
					}
				}
			}

		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}