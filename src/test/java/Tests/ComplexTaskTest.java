package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;
import org.junit.Test;

import dom.*;
import dom2app.SimpleTableModel;

public class ComplexTaskTest {
	ComplexTask task;
	static TaskFactory taskFactory;
	static ArrayList<Task> list;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		taskFactory = new TaskFactory();
		list = new ArrayList<Task>();
		list.add(taskFactory.createSimpleTask(201, "First", 200, 10, 12, 10));
		list.add(taskFactory.createSimpleTask(202, "Second", 200, 12, 12, 10));
	}
	
	@Before
	public void setUp() throws Exception{
		task = taskFactory.createComplexTask(200, "Complex", 0, list);
	}

	
	@Test // ID = 0
	public void IDzeroShouldThrowException() throws Exception{
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createComplexTask(0, "Complex", 0, list);
			      }
			    );
			    assertEquals("Invalid ID", exception.getMessage());
	}
	
	@Test // ID < 0
	public void IdNegativeShouldThrowException() throws Exception {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createComplexTask(-5, "Complex", 0, list);
			      }
			    );
			    assertEquals("Invalid ID", exception.getMessage());
	}
	
	@Test // MamaID = ID
	public void mamaIdIsSameAsIDShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createComplexTask(5, "Complex", 5, list);
			      }
			    );
			    assertEquals("Invalid ID", exception.getMessage());
	}
	
	
	@Test // Name = null
	public void nameIsNullShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createComplexTask(200, null, 0, list);
			      }
			    );
			    assertEquals("Invalid name", exception.getMessage());
	}
	
	@Test // No subtasks
	public void sizeZeroShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createComplexTask(200, "Complex", 0, new ArrayList<Task>());
			      }
			    );
			    assertEquals("Can't Create Complex Task Without Subtasks", exception.getMessage());
	}


}
