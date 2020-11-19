import java.util.Scanner;

public class Player{
   private String comReply,name;
   private boolean isUser, knock, unicode;;
   Hand hand;

   public Player(String name){
      this.name = name;
      this.hand = new Hand();
      this.knock = false;
      this.isUser = true;
      this.comReply = (char) 0x2B9A + " ";
      unicode = true;
   }
   public Player (){
      this.hand = new Hand();
      this.knock = false;
      this.isUser = true;
   }

   // getters and setters
   public String getName(){
      return name;
   }
   public void   setName(String name){
      this.name = name;
   }
   public void   setComReply(char c){
      comReply = c + " ";
   }
   public void   setUnicode(boolean r){
      unicode = r;
   }

   // what happens if player is the who to has to draw
   public void drawTurn(Pile discard, Pile stock, boolean knocked, int gameTurn){
      waiting(1);
      if (knocked) System.out.println("This is your last turn. Choose wisely");


      int patienceTaken = 0;

      while (whichPile(discard, stock) && gameTurn <= 2){
         waiting(1);
         //System.out.println("You cannot knock on the first turn! Finish your turn and wait until next turn.");
         knock = false;
         if (patienceTaken < 2) System.out.println("You cannot knock on the first turn! Finish your turn and wait until next turn.");
         if (patienceTaken > 2){
            System.out.println("Okay, now you are just annoying.. Do you want to stop, or what?");
            if ((new Scanner(System.in).nextLine().contains("no"))){
               waiting(1);
               System.out.println("Ok, good, let's continue");
            }
         }
         else if (patienceTaken > 1) System.out.println("I said you cannot knock yet");
         patienceTaken ++;
         waiting(1);
      }
   }
   public void playTurn(Pile discard, boolean knocked){
         whichCard(discard);
   }

   // asks user to choose pile
   public boolean whichPile(Pile discard, Pile stock){
      //Scanner in = new Scanner(System.in);
      System.out.println();
      System.out.println(comReply + "Do you want to draw from stock pile or discard pile?");

      Pile drawn;
      drawn = null;
      int      reply = lookForCase();
      if      (reply == 1) drawn = discard;
      else if (reply == 0) drawn = stock;
      else if (reply == 2) knock = true;

      if (drawn!= null) hand.draw(drawn);
      return knock;
   }
   public void    whichCard(Pile discard){
      waiting(1);
      System.out.println("\nWhich card number from left do you want to play?"); //playing card 0 means write 1!!
      Scanner in = new Scanner(System.in);
      //Needs something for if player tries to knock..
      int drawAnswer = in.nextInt();
      hand.play(discard,drawAnswer);
   }


   // die roll
   public int dieRoll(){
      return (int)(Math.random()*6+1);
   }

   // registers if player has blitz (31 points)
   public boolean hasKnocked(){
      return knock;
   }
   public boolean blitz(){
      return (hand.maxPoints() >= 31 && !hand.bestGroup().anyUnder(10) && hand.bestGroup().anyOver(10));

   }
   public boolean isUser(){
      return isUser;
   }

   // prints hand
   public void printHand(){
      hand.printHand();
   }
   public void printOpen(){
      hand.printHand();
   }

   //waiters
   public void waitingMilSec(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }


   }
   public void waiting(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }

   // move? its look for cases kind of thing
   public int lookForCase(){
      Scanner in = new Scanner(System.in);
      String drawAnswer = in.nextLine();

      if (drawAnswer.contains("discard")) return 1;
      if (drawAnswer.contains("stock")) return 0;
      if (drawAnswer.contains("knock"))   return 2;
      return -1;
   }

   // printers
   public void print(String string){
      System.out.print(string);
   }
   public void println(String string){
      System.out.println(string);
   }
   public void println(){
      System.out.println();
   }


}
