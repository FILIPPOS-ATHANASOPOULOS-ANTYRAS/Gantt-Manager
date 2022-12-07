package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ComplexTaskTest.class, GanttDiagramTest.class, MainControllerTest.class, SimpleReporterTest.class,
		SimpleTaskTest.class, SimpleTextParserTest.class })
public class AllTests {

}
