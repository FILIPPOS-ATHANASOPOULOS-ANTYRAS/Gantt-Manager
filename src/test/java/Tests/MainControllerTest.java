package Tests;

import static org.junit.Assert.*;

//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;

import org.junit.*;
//import org.junit.Test;

import backend.MainController;
import backend.MainControllerFactory;
import backend.ReportType;
//import dom.GanttDiagram;
//import dom.Task;

public class MainControllerTest {
	
	static MainControllerFactory mcFactory;
	static MainController mainEngine;
	
	@BeforeClass
	public static void beforeClass() {
		mcFactory = new MainControllerFactory(); 
		mainEngine = (MainController) mcFactory.createMainController();
	}
	
	@Before
	public void setUp() {
		mainEngine.load("src/test/resources/input/Eggs.tsv", "\t");
	}
	
	@Test // Happy Day
	public void getByIdShouldReturnCorrectTask() {
		assertEquals("Task With Correct ID was not returned", "101", mainEngine.getTaskById(101).getValueAt(0,0));
	}
	
	@Test // No Match
	public void getByIdWithNoMatchShouldReturnNoTaskAndErrorMessage() {
		assertEquals("No Task was not returned", 0, mainEngine.getTaskById(5).getData().size());
		assertEquals("Column Name Was Incorrect", "Task with given ID not found", mainEngine.getTaskById(5).getColumnName(0));
	}
	
	@Test // Negative ID
	public void getByIdWithNegativeIdShouldReturnNoTaskAndErrorMessage() {
		assertEquals("No Task was not returned", 0, mainEngine.getTaskById(5).getData().size());
		assertEquals("Column Name Was Incorrect", "No Task Can Have ID Lesser Or Equal To Zero", mainEngine.getTaskById(-3).getColumnName(0));
	}
	
	@Test // Zero ID
	public void getByIdWithIdEqualToZeroShouldReturnNoTaskAndErrorMessage() {
		assertEquals("No Task was not returned", 0, mainEngine.getTaskById(5).getData().size());
		assertEquals("Column Name Was Incorrect", "No Task Can Have ID Lesser Or Equal To Zero", mainEngine.getTaskById(0).getColumnName(0));
	}
	
	@Test // Happy Day
	public void getTopLevelTasksShouldReturnOnlyTopLevelTasks() {
		assertEquals("First Top Level Was  Not Returned", "100", mainEngine.getTopLevelTasks().getValueAt(0,0));
		assertEquals("Second Top Level Was Not Returned", "200", mainEngine.getTopLevelTasks().getValueAt(1,0));
		assertEquals("Third Top Level Was Not Returned", "300", mainEngine.getTopLevelTasks().getValueAt(2,0));
		assertEquals("No more Top Level Not Returned", 3, mainEngine.getTopLevelTasks().getRowCount());
	}
	
	@Test // No Top Level Tasks Exist
	public void onlySimpleTaskFileShouldReturnNoTasks() {
		mainEngine.load("src/test/resources/input/NoTopLevelTaskTest.tsv", "\t");
		assertEquals("No Top Level Tasks Not Returned", 0, mainEngine.getTopLevelTasks().getRowCount());
	}
	
	@Test // Happy Day
	public void getByPrefixShouldReturnCorrectTask() {
		assertEquals("First Task Was Not Correct", "Prepare Fry", mainEngine.getTasksByPrefix("P").getValueAt(0,1));
		assertEquals("Second Task Was Not Correct", "Prepare the bread", mainEngine.getTasksByPrefix("P").getValueAt(1,1));
		assertEquals("Third Task Was Not Correct", "Put bread in plate", mainEngine.getTasksByPrefix("P").getValueAt(2,1));
		assertEquals("Fourth Task Was Not Correct", "Put eggs on bread", mainEngine.getTasksByPrefix("P").getValueAt(3,1));
		assertEquals("No more Tasks Not Returned", 4, mainEngine.getTasksByPrefix("p").getRowCount());
	}
	
	@Test // Capital And Lowercase
	public void capitalsShouldReturnSameAsLowercases() {
		assertEquals("Capital And Lowercase Don't Return Same Tasks", mainEngine.getTasksByPrefix("w").getValueAt(0,1), mainEngine.getTasksByPrefix("W").getValueAt(0,1));
	}
	
	@Test // Prefix Doesn't Match
	public void nonExistantPrefixShouldReturnNothing() {
		assertEquals("No Task Not Returned", 0, mainEngine.getTasksByPrefix("000").getRowCount());
	}
	
	@Test // Prefix Too Long
	public void longPrefixShouldShowMessage() {
		assertEquals("Task Were Returned", 0, mainEngine.getTasksByPrefix("goofyaaaaaaaaaaaahhhhhhhhhhhhhh").getRowCount());
	}
	
	@Test //No FileLoaded
	public void NoFileLoadedShouldReturnError() {
		mainEngine.setGanttDiagram(null);
		assertEquals("Get By ID null", "No File has been loaded", mainEngine.getTaskById(100).getColumnName(0));
		assertEquals("Get By Prefix null", "No File has been loaded", mainEngine.getTasksByPrefix("P").getColumnName(0));
		assertEquals("Get Top Level null", "No File has been loaded", mainEngine.getTopLevelTasks().getColumnName(0));
		assertEquals("Get Top Level null", -1, mainEngine.createReport("Doesn't Matter", ReportType.TEXT));
	}
	
	@Test //Happy Day of load
	public void happyDayLoad(){ //change name
		assertEquals("Eggs Not Loaded", 14, mainEngine.load("src/test/resources/input/Eggs.tsv", "\t").getRowCount());
	}
	
	//@Test
	//public void loadWithNoFileShouldReturnError() { //Do we have to do this???
	//	assertEquals("", "", mainEngine.load(null,"\t").getColumnName(0));
	//}
	
	@Test
	public void loadWithNotTsvFileShouldReturnError() { // check \t or .tsv???
		assertEquals("Couldn't load beacause delimiter is not tab", "Not a tsv file", mainEngine.load("src/test/resources/input/NotATSVfile.txt","\n").getColumnName(0));
	}
	
	 @Test
	 public void loadWithEmptyFileShouldReturnEmptyDiagram() {
		assertEquals("Empty Gantt Returned", "No Tasks Found", mainEngine.load("src/test/resources/input/Empty.tsv","\t").getColumnName(0));
	 }
	
	
	@Test
	public void loadWhileHavingLoadedShouldOverwriteOldDiagram() {
		assertEquals("Eggs Not Loaded", 14, mainEngine.load("src/test/resources/input/Eggs.tsv", "\t").getRowCount());
		assertEquals("Small Not Loaded", 6, mainEngine.load("src/test/resources/input/SmallFile.tsv", "\t").getRowCount());
	}
	

	
	// @Rule
	// public TemporaryFolder folder = new TemporaryFolder();
	
	// @Test
	// public void reportTxtShouldReportCorrectly() throws IOException {
	// 	//File file = new File("src/test/resources/output/eggs.txt");
	// 	mainEngine.createReport("src/test/resources/output/eggs.txt", ReportType.TEXT);
	// 	//assertTrue("File Not Created", file.exists());
		
		
	// }
	
	// @Test
	// public void reportMdShouldReportCorrectly() {
	// 	fail("Not Yet Implemented");
	// }
	
	// @Test
	// public void reportHtmlShouldReportCorrectly() {
	// 	fail("Not Yet Implemented");
	// }	
}
