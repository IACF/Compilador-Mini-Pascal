public class nodeEn extends nodeE {

   public char name; 

   public void init (char n) {
      name=n;
      }

   public void visit (Visitor v) {
      v.visitEn (this);
      }

}