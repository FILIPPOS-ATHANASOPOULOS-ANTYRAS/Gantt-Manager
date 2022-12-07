package Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dom.GanttDiagram;
import dom.Task;
import dom.TaskFactory;

public class GanttDiagramTest {
	
	GanttDiagram gantt;
	static TaskFactory taskFactory;
	static ArrayList<Task> list;
	
	@BeforeClass
	public static void beforeClass() throws Exception{
		list = new ArrayList<Task>();
		ArrayList<Task> ComplexList = new ArrayList<Task>();
		taskFactory = new TaskFactory();
		list.add(taskFactory.createSimpleTask(201, "Heat bread in toaster", 200, 10, 12, 10));
		list.add(taskFactory.createSimpleTask(202, "Little bit of salt, galric spice to bread", 200, 12, 12, 10));
		ComplexList.add(taskFactory.createSimpleTask(201, "Heat bread in toaster", 200, 10, 12, 10));
		ComplexList.add(taskFactory.createSimpleTask(202, "Little bit of salt, galric spice to bread", 200, 12, 12, 10));
		list.add(0, taskFactory.createComplexTask(200, "Prepare the bread", 0, ComplexList));
		ComplexList.clear();
		list.add(taskFactory.createSimpleTask(301, "Put bread in plate", 300, 13, 13, 10));
		list.add(taskFactory.createSimpleTask(302, "Put eggs on bread", 300, 14, 14, 10));
		list.add(taskFactory.createSimpleTask(303, "Wash fry", 300, 15, 20, 10));
		ComplexList.add(taskFactory.createSimpleTask(301, "Put bread in plate", 300, 13, 13, 10));
		ComplexList.add(taskFactory.createSimpleTask(302, "Put eggs on bread", 300, 14, 14, 10));
		ComplexList.add(taskFactory.createSimpleTask(303, "Wash fry", 300, 15, 20, 10));
		list.add(3, taskFactory.createComplexTask(300, "Serve eggs", 0, ComplexList));
		ComplexList.clear();
	}
	
	@Before
	public void setUp() {
		gantt = new GanttDiagram(list);
	}

	@Test
	public void orderTasksShouldSortAllTasks() {
		// for this test ill make a diagram of the above tasks and each time shuffle it and sort it
		ArrayList<Task> copyOfList = new ArrayList<Task>();
		for (Task task : list)  copyOfList.add(task); // deep copy list
		for (int i = 0; i < 10; i++) {
			Collections.shuffle(list);
			gantt = new GanttDiagram(list);
			gantt = (GanttDiagram) gantt.orderTasks();
			for ( Task task : gantt.getAllTasks() ) assertEquals("Mismatch Index" , gantt.getAllTasks().indexOf(task), copyOfList.indexOf(task));			
		}
	}
	
	@Test
	public void orderTasksWithNoTasksShouldReturnEmptyList() {
		gantt = new GanttDiagram(new ArrayList<Task>());
		gantt.orderTasks();
		assertEquals(0, gantt.getAllTasks().size());
	}
	
//	@Test
//	public void orderTasksWithNoTopLevelTasksShouldReturnCorrectResult() {
//		gantt.orderTasks();
//		assertEquals(0, gantt.getAllTasks().size());
//	}

}
