package compilador;

import java.util.HashMap;
import java.util.Map;

public class Token {
    public int kind;
    public String spelling;
    public int line;
    public int collum;

    public Token(Map<String, Integer> map, int kind, String spelling, int line, int collum) {
        this.kind = kind;
        this.spelling = spelling;
        this.line = line;
        this.collum = collum;

        if(map.containsKey(spelling)){
            this.kind = map.get(spelling);
            System.out.println(this.kind);
        }    
    }
}
