import java.util.Scanner;

public class Player{
   String name;
   Hand hand;
   Reply cardPlay, pileChoice;
   //Case discPile;
   //Case stockPile;
   Case discPile = new Case("discard", 1);
   Case stockPile = new Case("stock", 0);
   Case knocked = new Case("knock", 2);
   boolean knock;


   public Player(String name){
      this.name = name;
      this.hand = new Hand(name);
      this.knock = false;
   }

   public boolean getKnock(){
      return knock;
   }

   public String getName(){
      return name;
   }
   public Cards draw(Pile pile){
      return hand.draw(pile);
   }

   public void play(Pile discard, int cardNr){
      hand.play(discard, cardNr);
   }

   public void drawTurn(Pile discard, Pile stock, boolean knocked, int gameTurn){
      if (knocked) System.out.println("This is your last turn. Choose wisely");
      whichPile(discard, stock);

   }


   public void playTurn(Pile discard, boolean knocked){
         whichCard(discard);
         discard.printTop();
   }




   public void printHand(){
      hand.printHand();
   }

   public boolean whichPile(Pile discard, Pile stock){
      //Scanner in = new Scanner(System.in);
      System.out.println();
      System.out.println("Do you want to draw from stock pile or discard pile?");

      Pile drawn;
      drawn = null;
      int reply = lookForCase();
      if (reply == 1) drawn = discard;
      else if (reply == 0) drawn = stock;
      else if (reply == 2) knock = true;

      if (drawn!= null) draw(drawn);
      return knock;
   }

   public void whichCard( Pile discard){
      System.out.println("Which card number from left do you want to play?"); //playing card 0 means write 1!!
      Scanner in = new Scanner(System.in);
      //Needs something for if player tries to knock..
      int drawAnswer = in.nextInt();
      play(discard,drawAnswer);
   }

   public void playTurn(Pile discard, Pile stock){
      Scanner in = new Scanner(System.in);


      //String drawAnswer = in.nextLine();


      //return drawn;
   }


   public boolean blitz(){
      return (hand.maxPoints() >= 31 && !hand.bestGroup().anyUnder(10) && hand.bestGroup().anyOver(10));

   }

   public boolean knock(){
      boolean result = true;
      return result;
   }

   public void knocked(){
      System.out.println();
      System.out.println(name + " knocked");
      System.out.println();
   }

   public void ifKnocked(){
      System.out.println("hi");
   }

   public int lookForCase(){
      Scanner in = new Scanner(System.in);
      String drawAnswer = in.nextLine();
      Pile drawn;
      drawn = null;
      if (drawAnswer.contains(discPile.getInput())) return discPile.getKeyMap();
      if (drawAnswer.contains(stockPile.getInput())) return stockPile.getKeyMap();
      if (drawAnswer.contains(knocked.getInput())) return knocked.getKeyMap();
      return -1;
   }


}
