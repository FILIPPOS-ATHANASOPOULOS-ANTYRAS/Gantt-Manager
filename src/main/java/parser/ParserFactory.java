package parser;

public class ParserFactory {
    public IParser createParser(){
        return new SimpleTextParser();
    }
}
