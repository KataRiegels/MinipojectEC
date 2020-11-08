import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {
   Player user;
   Player Eliza;
   Pile stock;
   Pile discard;
   Scanner in;
   Timer timer;
   TimerTask task;
   boolean endGame;
   boolean knocked;
   boolean userKnocked;
   boolean ElizaKnocked;

   public Game() {
      Deck deck = new Deck("Deck");
      deck.shuffle();

      stock = new Pile("stock");
      stock.createStock(deck);

      discard = new Pile("discard");
      discard.turnCard(stock);

      user = new Player("User");
      user.hand.starter(stock);

      Eliza = new Player("Eliza");
      Eliza.hand.starter(stock);

      in = new Scanner(System.in);
      timer = new Timer();
      endGame = false;
      knocked = false;
      userKnocked = false;
      ElizaKnocked = false;
   }

   public void playGame(){




      do{ turns();
      } while (!endGame);

   }

   public void printState(){
      System.out.println();
      discard.printTop();
      System.out.print("Your hand:   ");
      user.printHand();
      //System.out.print("Eliza's hand: ");
      //Eliza.botsHand();
   }

   public void draw(Player p, Pile pile){
      Cards drawn = new Cards();
      drawn = p.draw(pile);
      if (p == user){
         System.out.print("You drew: ");
         drawn.printCardsSym();
         System.out.print("Your hand:   ");
         p.printHand();
      }
   }

   public void play(Player p, int cardNr){
      p.play(discard,cardNr);
   }

   public void turn(){

   }

   public void userTurn(){
      if (endGame()) typeOfEnd();
      //waiting(2);
      System.out.println();
      System.out.println("Your turn!");
      //waiting(1);
      printState();
      //waiting(2);
      whichPile();
      if (!knocked) {whichCard();
      discard.printTop();
      printState();}
   }

   public void ElizaTurn(){
      if (endGame()) typeOfEnd();
      //waiting(2);
      System.out.println("Eliza's turn!");
      //waiting(2);

      discard.printTop();
      //waiting(1);
      Eliza.printHand();
       //waiting(3);
      Eliza.announceDraw(discard, stock);
      Eliza.draw(Eliza.choosePile(discard,stock));
      //waiting(1);
      Eliza.printHand();
      //Eliza.botsHand();

      //waiting(4);
      Eliza.announcePlay();
      Eliza.play(discard, Eliza.indexCard());
      //waiting(1);
      discard.printTop();

      //printState();
   }

   public void whichPile(){
      System.out.println();
      System.out.println("Do you want to draw from stock pile or discard pile?");
      Scanner in = new Scanner(System.in);
      String drawAnswer = in.nextLine();
      Pile drawn = new Pile("");
      //Contains stuff must be changes when keyword stuff is done!!
      drawn = null;
      if (drawAnswer.contains("stock")) drawn = stock;
      else if (drawAnswer.contains("discard")) drawn = discard;
      else if (drawAnswer.contains("knock")) {
         knocked = user.knock();
         userKnocked = true;
      }

      if (drawn!= null) draw(user, drawn);
   }

   public void whichCard(){
      System.out.println("Which card number from left do you want to play?"); //playing card 0 means write 1!!
      int drawAnswer = in.nextInt();
      play(user,drawAnswer);
   }

   public void reshuffle(){
      discard.shuffle();
      stock.createStock(discard);
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

   public void turns(){
      while (!endGame()){
         if (stock.isEmpty()) reshuffle();
         userTurn();
         if (stock.isEmpty()) reshuffle();
         ElizaTurn();
      }


//      if (Eliza.blitz()) ElizaWon();
//      if (user.blitz()) userWon();
//      if (knocked) user.knocked();
   }

   public void userWon(){
      System.out.println();
      System.out.println("Congratulations! You got a blitz and won the game!");
      user.printHand();
      endGame = true;
   }

   public void ElizaWon(){
      System.out.println();
      System.out.println("Bugger! Eliza got a blitz. You lost. ");
      Eliza.printHand();
      endGame = true;
   }

   public boolean endGame(){
      return (knocked || Eliza.blitz() || user.blitz());
   }

   public void knocked(){
      //System.out.println("\n SOMEONE  knocked \n");
   }

   public void typeOfEnd(){
      if (Eliza.blitz()) ElizaWon();
      if (user.blitz()) userWon();
      if (userKnocked) user.knocked();
      if (ElizaKnocked) Eliza.knocked();
   }
}
