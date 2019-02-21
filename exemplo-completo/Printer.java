public class Printer implements Visitor {

   int i=0;

   void indent() {
      for (int j=0; j<i; j++) 
         System.out.print ("|");
   }

   public void visitP (nodeP p) {
      if (p!=null) {
         if (p.d!=null) p.d.visit(this);
         if (p.c!=null) p.c.visit(this);
         }
   }

   public void visitD (nodeD d) {
      if (d!=null) {
         System.out.println ("#"+d.name);
         if (d.next!=null) {
            i++;
            indent();
            d.next.visit (this);
            i--;
            }
         }
   }

   public void visitC (nodeC c) {
      if (c!=null) {
         System.out.println ("$"+c.name);
         c.exp.visit(this);
         if (c.next!=null) {
            i++;
            indent();
            c.next.visit (this);
            i--;
            }
         }         
   }

   public void visitE (nodeE e) {
      if (e!=null) e.visit (this);
   }

   public void visitEo (nodeEo e) {
      i++;
      indent();
      System.out.println (e.op);
      e.left.visit (this);
      e.right.visit (this);
      i--;
   }

   public void visitEn (nodeEn e) {
      i++;
      indent();
      System.out.println (e.name);
      i--;
   }
 
   public void print (nodeP p) {
      System.out.println ("---> Iniciando impressao da arvore");
      p.visit (this);
   }

}