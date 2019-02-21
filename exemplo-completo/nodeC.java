public class nodeC {

   char name;
   nodeE exp;
   nodeC next;

   public void visit (Visitor v) {
      v.visitC(this);
      }

}