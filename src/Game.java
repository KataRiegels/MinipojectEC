import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {
   Player user;

   Pile stock, discard;
   Scanner in;
   Player winner, player;
   Bot bot;
   int turnNr;
   boolean endGame;
   boolean knocked;

   public Game() {
      Deck deck = new Deck("Deck");
      deck.shuffle();

      //discPile = new Case("discard", 1);
      //stockPile = new Case("stock", 0);

      stock = new Pile("stock");
      stock.createStock(deck);

      discard = new Pile("discard");
      discard.turnCard(stock);

      bot = new Bot("Liza");
      bot.hand.starter(stock);


      user = new Player("User");
      user.hand.starter(stock);

      player = bot;
      turnNr = 0;
      in = new Scanner(System.in);

      endGame = false;
      knocked = false;
   }

   public void playGame(){




      turns();
      if (knocked) comparePoints();
      if (user.blitz()) userWon();
      if (bot.blitz()) botWon();
   }



   public void printState(){
      //System.out.println();
      waiting(1);
      discard.printTop();

      if (player == user){
         //waiting(1);
         System.out.print("Your hand:   ");
         user.printHand();
      }
   }

   public void turns(){
      while (!endGame()){
         if (stock.isEmpty()) reshuffle();
         Player previous = nextPlayer(player);
         if (previous.hasKnocked()) knocked = true;
         drawTurn();
         playTurn();
         //takeTurns(player);
         player = nextPlayer(player);
         if (player.hasKnocked()) return;
         turnNr ++;
      }
   }

   public void takeTurns(Player player){





   }

   // Drawing part of the turn
   public void drawTurn(){
      waiting(2);
      println("\n================================================ ");

      System.out.println("\n               " + player.getName() + "'s turn\n");

      println("================================================ \n");

      waiting(2);
      if (player.isUser()) waiting(1);
      printState();
      player.drawTurn(discard, stock, knocked, turnNr);
      if (player.isUser() && !player.hasKnocked()) printState();
   }

   // The playing part of a turn
   public void playTurn(){
      boolean checkKnock = player.hasKnocked();
      if (player.hasKnocked()) println(player.getName() + " knocked");
      if (!checkKnock){
         player.playTurn(discard, knocked);
         printState();
         //discard.printTop();
      }

   }

   // Reshuffles the deck
   public void reshuffle(){
      discard.shuffle();
      stock.createStock(discard);
   }

   // Returns non-current player
   public Player nextPlayer(Player current) {
      if (current == bot) {
         return user;
      } else {
         return bot;
      }
   }

   // what to print if who wins.
   public void userWon(){
      System.out.println();
      System.out.println("Congratulations! You got a blitz and won the game!");
      user.printHand();
      endGame = true;
   }
   public void botWon(){
      System.out.println();
      System.out.println("Bugger! Eliza got a blitz. You lost. ");
      bot.printHand();
      endGame = true;
   }


   // Checks if there has been a blitz
   public boolean endGame(){
      return (bot.blitz() || user.blitz());
   }

   // Compares points after someone knocked.
   public void comparePoints(){
      print("Your hand: ");
      user.printHand();
      println("You had " + user.hand.maxPoints() + " points \n");
      print("Liza's hand: ");
      bot.printOpen();
      println("Liza had " + bot.hand.maxPoints() + " points \n");

      if (user.hand.maxPoints() > bot.hand.maxPoints()) winner = user;
      else if (user.hand.maxPoints() == bot.hand.maxPoints()) {
         winner = null;
         println("It was a tie!");
         return;
      }
      else winner = bot;
      println(winner.getName() + " had most points. " + winner.getName() + " won!");


   }

   // Makes a bit of waiting time between answers
   public void waiting(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }


   public void print(String string){
      System.out.print(string);
   }

   public void println(String string){
      System.out.println(string);
   }


}
