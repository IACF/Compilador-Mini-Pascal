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
        int aux = 4;
        Parser parser = new Parser();
        Printer printer = new Printer();
        Checker checker = new Checker();
        Coder coder = new Coder();
        Programa programa;
         
        switch(aux){
            case 4:
                programa = parser.parse("ex.txt");
                printer.print(programa);
                checker.check(programa);
                coder.code(programa);
                break;
            case 3:
                programa = parser.parse("ex.txt");
                printer.print(programa);
                checker.check(programa);
                break;
            case 2:
                programa = parser.parse("ex.txt");
                printer.print(programa);
                break;
            case 1:
                programa = parser.parse("ex.txt");
                break;
            case 0:
                Scanner s = new Scanner(reader);
                s.scanTodos();
                break;
            default:
                throw new Error("Argumento invalido.");
                
        }
            
    }
    
}
