public class nodeP {

   nodeD d;
   nodeC c;

   public void visit  (Visitor v){
      v.visitP(this);
      }

}