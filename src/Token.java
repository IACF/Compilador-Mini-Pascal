import java.util.HashMap;
import java.util.Map;

public class Token {
    public byte kind;
    public String spelling;
    public int line;
    public int collum;

    public Token(byte kind, String spelling, int line, int collum) {
        this.kind = kind;
        this.spelling = spelling;
        this.line = line;
        this.collum = collum;

        Map<String, String> map = new HashMap<>();
        

    }
}
