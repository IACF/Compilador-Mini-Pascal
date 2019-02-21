public class Checker implements Visitor {

   identificationTable t = new identificationTable();

   public void visitP (nodeP p) {
      if (p!=null) {
         if (p.d!=null) p.d.visit(this);
         if (p.c!=null) p.c.visit(this);
         }
   }

   public void visitD (nodeD d) {
      if (d!=null) {
         t.enter (d.name);
         if (d.next!=null) d.next.visit (this);
         }
   }

   public void visitC (nodeC c) {
      if (c!=null) {
         t.retrieve (c.name);
         c.exp.visit(this);
         if (c.next!=null) c.next.visit (this);
         }         
   }

   public void visitE (nodeE e) {
      if (e!=null) e.visit (this);
   }

   public void visitEo (nodeEo e) {
      e.left.visit (this);
      e.right.visit (this);
   }

   public void visitEn (nodeEn e) {
      t.retrieve (e.name);
   }
 
   public void check (nodeP p) {
     System.out.println ("---> Iniciando identificacao de nomes");
     p.visit (this);
   }

}