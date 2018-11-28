/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Map<String, Integer> map = new HashMap<>()
        {{
            put(":=" , 0);
            put("true", 1);
            put("false", 2);
            put("begin", 3);
            put("end", 4);
            put("if", 5);
            put("then", 6);
            put("else", 7);
            put("var", 8);
            put(":", 9);
            put(";", 10);
            put("int-lit", 11);
            put("(", 12);
            put(")", 13);
            put(".", 14);
            put("id", 15);
            put("while", 16);
            put("do", 17);
            put(",", 18);
            put("+", 19);
            put("-", 20);
            put("or", 21);
            put("*", 22);
            put("/", 23);
            put("and", 24);
            put("<", 25);
            put(">", 26);
            put("<=", 27);
            put(">=", 28);
            put("=", 29);
            put("<>", 30);
            put("[", 31);
            put("]", 32);
            put("array", 33);
            put("..", 34);
            put("of", 35);
        }};
        
        Token t = new Token(map, 1, "while", 0 ,1);
        Scanner s;
        s = new Scanner();
        s.scan();
    }
    
}
