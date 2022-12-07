package reporter;

import java.io.IOException;

import backend.ReportType;
import dom2app.SimpleTableModel;

public interface IReporter {
    void report(String path, ReportType type, SimpleTableModel table) throws IOException;
}