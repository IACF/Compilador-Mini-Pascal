public class Compiler{

public static void main(String args[]){
   nodeP p; 
   Parser parser = new Parser();
   Printer printer = new Printer();
   Checker checker = new Checker();
   Coder coder = new Coder();
   p = parser.parse(args[0]);
   printer.print(p);
   checker.check(p);
   coder.code(p);
   }

}