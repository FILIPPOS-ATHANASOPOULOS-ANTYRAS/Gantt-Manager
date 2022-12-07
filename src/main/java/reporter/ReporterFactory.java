package reporter;

public class ReporterFactory {
    public IReporter createReporter(String type){
        if(type.equals("SimpleReporter")) return new SimpleReporter();
        // else if(type.equals("TestReporter")) return new TestReporter();

        return null;
    }
}
