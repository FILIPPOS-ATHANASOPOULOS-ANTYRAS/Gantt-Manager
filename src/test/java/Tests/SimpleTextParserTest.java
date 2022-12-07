package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

import dom.GanttDiagram;
import dom.Task;
import dom.TaskFactory;
import parser.*;


public class SimpleTextParserTest  {

	private static ParserFactory parserFactory;
	SimpleTextParser parser;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		parserFactory = new ParserFactory();
	}
	
	@Before
	public void setUp() throws Exception {
		parser = (SimpleTextParser) parserFactory.createParser();
	}
	
	@Test
	public void parserFactoryShouldReturnParser() {
		assertNotNull(parser);
		assertTrue(parser instanceof SimpleTextParser);
	}
	
	@Test
	public void ParsingShouldReturnTasksWithFileOrder() throws Exception {
		//prep
		ArrayList<Task> list = new ArrayList<Task>();
		TaskFactory taskFactory;
		taskFactory = new TaskFactory();
		list.add(taskFactory.createSimpleTask(101, "Turn on burner (low)", 100, 1, 1, 10));
		list.add(taskFactory.createSimpleTask(102, "Break eggs and pour into fry", 100, 2, 4, 10));
		list.add(taskFactory.createSimpleTask(103, "Steer mixture to avoid sticking", 100, 5, 10, 10));
		list.add(taskFactory.createSimpleTask(104, "Throw yellow cheese into fry", 100, 6, 12, 10));
		list.add(taskFactory.createSimpleTask(105, "Salt, pepper", 100, 5, 5, 10));
		list.add(taskFactory.createComplexTask(100, "Prepare Fry", 0, list));
		
		
		GanttDiagram diagram = (GanttDiagram) parser.parse("src/test/resources/input/SmallFile.tsv", "\t");
		assertNotNull(diagram);
		for (int i = 0; i<diagram.getAllTasks().size(); i++) assertEquals(list.get(i).getID(), diagram.getAllTasks().get(i).getID());
	}

}
