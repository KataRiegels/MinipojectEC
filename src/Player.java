import java.util.Scanner;

public class Player{
   String name;
   Hand hand;

   public Player(String name){
      this.name = name;
      this.hand = new Hand(name);
   }

   public Cards draw(Pile pile){
      return hand.draw(pile);
   }

   public void play(Pile discard, int cardNr){
      hand.play(discard, cardNr);
   }

   public void drawTurn(Pile drawn, Pile stock){
         System.out.println();
         System.out.println("Your turn!");
         whichPile(drawn);
   }

   public void playTurn(Pile discard){
         whichCard(discard);
         discard.printTop();
   }

   public void printHand(){
      hand.printHand();
   }

   public void whichCard(Pile discard){
      System.out.println("Which card number from left do you want to play?"); //playing card 0 means write 1!!
      Scanner in = new Scanner(System.in);
      int drawAnswer = in.nextInt();
      play(discard,drawAnswer);
   }

   public void whichPile(Pile drawn){
      System.out.println();
      System.out.println("Do you want to draw from stock pile or discard pile?");

      //Contains stuff must be changes when keyword stuff is done!!


      if (drawn!= null) draw(drawn);
   }

   public void botsHand(){
      int handSize = hand.size();
      System.out.print(this.name + "'s hand: ");
      for (int i = 0; i < handSize; i++){
         System.out.print("|x|");
         if (i<handSize-1){
            System.out.print(" - ");
         }
      }
      System.out.println();
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




}
