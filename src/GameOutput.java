import java.util.Scanner;

public class GameOutput extends Output {
   int trigger;


   public GameOutput(String reply){
      super(reply);
   }

   public GameOutput(){

   }

public GameOutput(String reply, int trigger){
      this.trigger = trigger;
}

   /*
   private void   printWait(long waitTime){
      int dots = 3;
      String dotChar;
      if (uni) dotChar = (char)0x26AC + "";
      else dotChar = ".";
      String delete = "\b";
      String dot;
      for (int n = 0; n <= dots*2; n++){
         delete += "\b";
      }
      for (int j = 0; j < waitTime; j++) {
         dot = "";
         for (int i = 0; i < dots; i++) {
            dot += dotChar + " ";
            waitingHalf(1);
            System.out.print(dot + "\r");

         }
         waitingHalf(1);
         System.out.print(delete);
      }
      waiting(1);
   }

    */



   // prints and scanner
   //private void print(String string){
      //System.out.print(string);
   //}
   private void println(String string){
      System.out.println(string);
   }
   private void println(){
      System.out.println();
   }
   private static String readString() {
      Scanner in = new Scanner(System.in);
      return in.nextLine();
   }

   @Override public void print() {
      printWait(1);
      System.out.println("\n" + this.reply);
   }


}
