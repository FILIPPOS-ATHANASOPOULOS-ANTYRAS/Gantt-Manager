package Tests;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Test;

import dom.*;

public class SimpleTaskTest {
	SimpleTask task;
	static TaskFactory taskFactory;
	
	@BeforeClass
	public static void beforeClass() {
		taskFactory = new TaskFactory();
	}
	
	@Before
	public void setUp() throws Exception {
		task = taskFactory.createSimpleTask(103, "Test SimpleTask with this task" , 100, 3, 7, 13);
	}
	
	@Test // Getters
	public void gettersShouldReturnCorrectId() {
		assertEquals(103, task.getID());
		assertEquals("Test SimpleTask with this task", task.getName());
		assertEquals(100, task.getMamaID());
		assertEquals(3, task.getStart());
		assertEquals(7, task.getEnd());
		assertEquals(13.0 , task.getCost(), 0.00000000001);
	}

	
	@Test // ID = 0
	public void IDzeroShouldThrowException() throws Exception{
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createSimpleTask(0, "Test SimpleTask with this task" , 100, 3, 7, 13);
			      }
			    );
			    assertEquals("Invalid ID", exception.getMessage());
	}
	
	@Test // ID < 0
	public void IdNegativeShouldThrowException() throws Exception {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createSimpleTask(-4, "Test SimpleTask with this task" , 101, 3, 7, 13);
			      }
			    );
			    assertEquals("Invalid ID", exception.getMessage());
	}
	
	@Test // MamaID = ID
	public void mamaIdIsSameAsIDShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createSimpleTask(100, "Test SimpleTask with this task" , 100, 3, 7, 13);
			      }
			    );
			    assertEquals("Invalid ID", exception.getMessage());
	}
	
	@Test // End < Start
	public void endIsLesserThanStartShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createSimpleTask(101, "Test SimpleTask with this task" , 100, 7, 3, 13);
			      }
			    );
			    assertEquals("Invalid duration", exception.getMessage());
	}
	

	@Test // Cost = 0
	public void costZeroShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createSimpleTask(101, "Test SimpleTask with this task" , 100, 3, 7, 0);
			      }
			    );
			    assertEquals("Invalid cost", exception.getMessage());
	}
	
	@Test // Cost < 0
	public void costNegativeShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createSimpleTask(101, "Test SimpleTask with this task" , 100, 3, 7, -8);
			      }
			    );
			    assertEquals("Invalid cost", exception.getMessage());
	}
	
	@Test // Name = nulls
	public void nameIsNullShouldThrowException() {
		Throwable exception = assertThrows(
			      Exception.class, 
			      () -> {
			    	  task = taskFactory.createSimpleTask(101, null , 100, 3, 7, 13);
			      }
			    );
			    assertEquals("Invalid name", exception.getMessage());
	}

}
