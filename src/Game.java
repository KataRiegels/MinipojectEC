import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game extends Conversation{
   Pile stock, discard;
   Scanner in;
   Player winner, player, user, p1, p2;
   Bot bot, bot2;
   int turnNr;
   boolean endGame, knocked;

   public Game() {
      Deck deck = new Deck("Deck");
      deck.shuffle();

      //discPile = new Case("discard", 1);
      //stockPile = new Case("stock", 0);

      stock = new Pile("stock");
      stock.createStock(deck);

      discard = new Pile("discard");
      discard.turnCard(stock);

      //bot = new Bot("Liza");
      //bot.hand.starter(stock, 3);


      user = new Player("User");
      user.hand.starter(stock, 3);





      turnNr = 0;
      in = new Scanner(System.in);

      endGame = false;
      knocked = false;
   }

   public void playGame(){

      boolean trainingGame;
      trainingGame = true;
      p1 = new Bot("Liza", trainingGame);
      p1.hand.starter(stock, 3);

      p2 = new Bot("bot2", true);
      p2.hand.starter(stock, 3);


      player = p1;

      turns();
      if (knocked) comparePoints();
      if (p2.blitz()) player2Won();
      if (p1.blitz()) player1Won();
   }


   //prints hand and top discard card
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

   // taking turns
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
         if (endGame()) return;
         System.out.println("Turn number: " + turnNr);
         turnNr ++;
      }
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
      discard.turnCard(stock);
   }

   // Returns non-current player
   public Player nextPlayer(Player current) {
      if (current == p1) {
         return p2 ;
      } else {
         return p1;
      }
   }

   // what to print if who wins.
   public void player2Won(){
      System.out.println();
      System.out.println("Congratulations! You got a blitz and won the game!");
      p2.printHand();
      endGame = true;
   }
   public void player1Won(){
      System.out.println();
      System.out.println("Bugger! Eliza got a blitz. You lost. ");
      p1.printHand();
      endGame = true;
   }


   // Checks if there has been a blitz
   public boolean endGame(){
      return (p1.blitz() || p2.blitz());
   }

   // Compares points after someone knocked.
   public void comparePoints(){
      print(p2.getName() + "'s hand: ");
      p2.printHand();
      println("You had " + p2.hand.maxPoints() + " points \n");
      print(p1.getName() + "'s hand: ");
      p1.printOpen();
      println("Liza had " + p1.hand.maxPoints() + " points \n");

      if (p2.hand.maxPoints() > p1.hand.maxPoints()) winner = p2;
      else if (p2.hand.maxPoints() == p1.hand.maxPoints()) {
         winner = null;
         println("It was a tie!");
         return;
      }
      else winner = p1;
      println(winner.getName() + " had most points. " + winner.getName() + " won!");


   }

   // Makes a bit of waiting time between answers
   public void waiting(long seconds){// Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      /*
      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
      */
   }


   public void print(String string){
      System.out.print(string);
   }

   public void println(String string){
      System.out.println(string);
   }


}
