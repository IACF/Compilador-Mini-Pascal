/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author victor
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("ex.txt"));
        Scanner s = new Scanner(reader);
        Token t;
        List tokens =  new ArrayList();
            
        do{
            t = s.scan();
            tokens.add(t);
            //System.out.println(t.kind);
        }while(t.kind != 39); // diferente de EOF.

        System.out.println(tokens.size());
        
        for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
            Token next = (Token) iterator.next();
            System.out.println(next.spelling + " l = " +  Integer.toString(next.line) + " c = " + Integer.toString(next.collum) + " k = " + next.kind);
        }
    }
    
}
