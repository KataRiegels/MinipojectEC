import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {
   Player user;
   Player Eliza;
   Pile stock;
   Pile discard;
   Scanner in;
   Bot bot;
   Player player;
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

      player = user;
      in = new Scanner(System.in);

      bot = new Bot("Liza");

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
         drawn.printCards();
         System.out.print("Your hand:   ");
         p.printHand();
      }
   }

   /*
   public void play(Player p, int cardNr){
      p.play(discard,cardNr);
   }
   public void turn(){

   }
*/

   public void takeTurns(Player player){
      if (knocked){
         if (player.name.equals("Eliza")){
            player.drawTurn(discard, stock);
            player.ifKnocked();
         }
      } else {
         player.drawTurn(drawWhat(),stock);
         player.playTurn(discard);
      }
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

   public Player nextPlayer(Player current) {
      if (current == bot) {
         return user;
      } else {
         return bot;
      }
   }

   public void turns(){
      while (!endGame()){
         if (stock.isEmpty()) reshuffle();
         takeTurns(player);
         player = nextPlayer(player);

         /*
         userTurn();
         if (stock.isEmpty()) reshuffle();
         ElizaTurn();

          */
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

   public Pile drawWhat(){
      Scanner in = new Scanner(System.in);
      String drawAnswer = in.nextLine();
      Pile drawn;
      drawn = null;
      if (drawAnswer.contains("stock")) drawn = stock;
      else if (drawAnswer.contains("discard")) drawn = discard;
      else if (drawAnswer.contains("knock")) {
         knocked = true;
      }
      return drawn;
   }


   public boolean endGame(){
      return (knocked || Eliza.blitz() || user.blitz());
   }


   public void typeOfEnd(){
      if (Eliza.blitz()) ElizaWon();
      if (user.blitz()) userWon();
      if (userKnocked) user.knocked();
      if (ElizaKnocked) Eliza.knocked();
   }
}
