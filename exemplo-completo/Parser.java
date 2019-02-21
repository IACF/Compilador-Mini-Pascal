public class Parser {

char token;
char buffer [] = new char [100];
int i,n;

void take(char c){
if (token==c) takeIt();
   else error();}

void takeIt(){
if (i<n) {
   i=i+1;
   if (i!=n) token=buffer[i];
      else token=' ';
   }
   else token=' ';
}

void error(){
System.out.println ("ERRO");
}

void init(String arg){
buffer=arg.toCharArray();
i=0;
n=arg.length();
token=buffer[i];
System.out.println("Analisando "+arg+" com "+n+" caracteres");
}

//P -> D C
nodeP parseP(){
nodeP p = new nodeP();
p.d=parseD();
p.c=parseC();
if (i!=n) error();
return p;
}

//D -> #I D | empty
nodeD parseD(){
   nodeD first, last, d;
   first = null;
   last = null;
   while (token=='#') {
      takeIt();                 
      d = new nodeD();
      d.name = parseI();
      d.next = null;
      if (first==null) first=d;
         else last.next=d;
      last=d;
      }
   return first;
}

//C -> $ I = E C | empty
nodeC parseC(){
   nodeC first, last, c;
   first = null;
   last = null;
   while (token=='$') {
      takeIt();
      c = new nodeC();
      c.name = parseI();
      take ('=');
      c.exp = parseE();
      c.next = null;
      if (first==null) first=c;
         else last.next=c;
      last=c;
   }
   return first;
}

// E -> T + E | T
   nodeE parseE(){
      nodeE last;
      last = parseT();
      while (token=='+'){
         takeIt();
         nodeEo current = new nodeEo();
         current.init ('+',last,parseT());
         last = current;
         }
      return last;
   }

// T -> F * T | F
   nodeE parseT(){
      nodeE last;
      last = parseF();
      while (token=='*'){
         takeIt();
         nodeEo current = new nodeEo();
         current.init ('*',last,parseF());
         last = current;
         }
      return last;
   }

// F -> (E) | I
   nodeE parseF(){
      nodeE e;
      if (token=='('){
            takeIt();
            e=parseE();
            take(')');
            }
         else {
              nodeEn ex = new nodeEn();
              ex.init (parseI());
              e=ex;
              }
      return e;
   }

//I -> letter
char parseI(){
char d=' ';
if ((token>='A')&&(token<='Z')) {
   d = token;
   takeIt();
   }
else error();
return d;
}

public nodeP parse(String arg){
nodeP p;
System.out.println();
System.out.println ("---> Iniciando analise sintatica");
init(arg);
p = parseP();
return p;
}

}