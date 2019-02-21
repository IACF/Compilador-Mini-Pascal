public class identificationTable {

   int i;
   int last=-1;
   char t [] = new char [100];

   public void enter (char c) {
      i=0;
      while ((i<=last)&&(t[i]!=c)) i++;
      if (i>last) {
            last=last+1;
            t[last]=c;
            }
         else System.out.println ("Identificador "+c+" ja declarado");
   }

   public void retrieve (char c) {
      i=0;
      while ((i<=last)&&(t[i]!=c)) i++;
      if (i>last) System.out.println ("Identificador "+c+" nao declarado");
      }

   }