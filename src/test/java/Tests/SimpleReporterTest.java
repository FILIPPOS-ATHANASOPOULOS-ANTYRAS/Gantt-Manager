package Tests;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Test;
import backend.MainController;
import backend.MainControllerFactory;
import backend.ReportType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SimpleReporterTest {
	
	static MainControllerFactory mcFactory;
	static MainController mainEngine;

	
	@BeforeClass
	public static void beforeClass() {
		mcFactory = new MainControllerFactory(); 
		mainEngine = (MainController) mcFactory.createMainController();
	}
	
	@Before
	public void before(){
		mainEngine.load("src/test/resources/input/Eggs.tsv", "\t");
	}
	
	
	@Test //HappyDayTXT
	public void reportTxtShouldReportCorrectly() throws NoSuchElementException{
		mainEngine.createReport("bin/output/eggsOut.txt", ReportType.TEXT);
		File expected = new File("bin/output/eggs.txt");
		File actual = new File("bin/output/eggsOut.txt");
		assertTrue(actual.exists());
		Scanner expectedIn = null, actualIn = null;
		try {
			expectedIn = new Scanner(expected);
			actualIn = new Scanner(actual);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try{
			while (expectedIn.hasNext()) assertEquals(expectedIn.next(),actualIn.next());
		} catch (NoSuchElementException e){
			e.printStackTrace();
			fail("Exception Thrown");
		}
		
	}
	
	@Test //HappyDayMD
	public void reportMdShouldReportCorrectly() throws FileNotFoundException {
		mainEngine.createReport("bin/output/eggsOut.md", ReportType.MD);
		File expected = new File("bin/output/eggs.md");
		File actual = new File("bin/output/eggsOut.md");
		assertTrue(actual.exists());
		Scanner expectedIn = null, actualIn = null;
		try {
			expectedIn = new Scanner(expected);
			actualIn = new Scanner(actual);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try{
			while (expectedIn.hasNext()) assertEquals(expectedIn.next(),actualIn.next());
		} catch (NoSuchElementException e){
			e.printStackTrace();
			fail("Exception Thrown");
		}
	}
	
	
	@Test //HappyDayHTML
	public void reportHtmlShouldReportCorrectly() {
		mainEngine.createReport("bin/output/eggsOut.html", ReportType.HTML);
		File expected = new File("bin/output/eggs.html");
		File actual = new File("bin/output/eggsOut.html");
		assertTrue(actual.exists());
		Scanner expectedIn = null, actualIn = null;
		try {
			expectedIn = new Scanner(expected);
			actualIn = new Scanner(actual);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try{
			while (expectedIn.hasNext()) assertEquals(expectedIn.next(),actualIn.next());
		} catch (NoSuchElementException e){
			e.printStackTrace();
			fail("Exception Thrown");
		}
	}
}
