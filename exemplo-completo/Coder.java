public class Coder implements Visitor {

   int m=0;

   public void visitP (nodeP p) {
      if (p!=null) {
         if (p.d!=null) p.d.visit(this);
         if (p.c!=null) p.c.visit(this);
         if (m!=0) System.out.println ("POP "+m);
         System.out.println ("HALT");
         }
   }

   public void visitD (nodeD d) {
      if (d!=null) {
         System.out.println ("PUSH 1");
         m++;
         if (d.next!=null) d.next.visit (this);
         }
   }

   public void visitC (nodeC c) {
      if (c!=null) {
         c.exp.visit(this);
         System.out.println ("STORE "+c.name);
         if (c.next!=null) c.next.visit (this);
         }         
   }

   public void visitE (nodeE e) {
      if (e!=null) e.visit (this);
   }

   public void visitEo (nodeEo e) {
      e.left.visit (this);
      e.right.visit (this);
      System.out.println ("CALL "+e.op);

   }

   public void visitEn (nodeEn e) {
      System.out.println ("LOAD "+e.name);
   }
 
   public void code (nodeP p) {
     System.out.println ("---> Iniciando geracao de codigo");
     p.visit (this);
   }

}