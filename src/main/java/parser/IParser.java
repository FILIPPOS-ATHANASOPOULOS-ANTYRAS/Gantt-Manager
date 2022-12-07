package parser;

import dom.Diagram;

public interface IParser {
    
    Diagram parse(String fileName , String delimiter);

}
