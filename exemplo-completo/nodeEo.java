public class nodeEo extends nodeE {

   public char op; 
   public nodeE left, right;

   public void init (char o, nodeE l, nodeE r) {
      op=o;
      left=l;
      right=r;
      }

   public void visit (Visitor v) {
      v.visitEo (this);
      }

}