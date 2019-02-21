public class nodeD {

   char name;
   nodeD next;

   public void visit  (Visitor v) {
      v.visitD(this);
      }

}