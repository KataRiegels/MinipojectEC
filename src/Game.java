import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game extends Conversation{
   String comReply = (char) 0x2B9A + " ";  //0x2B9A
   Pile stock, discard;
   Scanner in;
   Player winner, player, user, p1, p2;
   Bot bot, bot2;
   int turnNr, gameNr;
   boolean endGame, knocked;

   public Game() {
      Deck deck = new Deck("Deck");
      deck.shuffle();


      stock = new Pile("stock");
      stock.createStock(deck);

      discard = new Pile("discard");
      discard.turnCard(stock);

      //bot = new Bot("Liza");
      //bot.hand.starter(stock, 3);


      //user = new Player("User");
      //user.hand.starter(stock, 3);

      turnNr = 0; gameNr = 0;
      in = new Scanner(System.in);

      endGame = false;
      knocked = false;
   }

   public void playGame(){

      boolean trainingGame;
      trainingGame = true;

      p1 = new Bot("Liza", trainingGame);
      p2 = new Bot("bot2", true);


      do {

         endGame = false;
         knocked = false;
         Deck deck = new Deck("Deck");
         deck.shuffle();

         //stock = new Pile("stock");
         stock.createStock(deck);

         //discard = new Pile("discard");
         discard.turnCard(stock);

         turnNr = 0;
         in = new Scanner(System.in);

         p1.hand.starter(stock, 3);
         p2.hand.starter(stock, 3);

         println("Let's get ready to play! We will decide who starts by rolling a die. \n");
         waiting(1);

         player = whoStarts(p1, p2);

         printLine();
         println("\n              The game begins!\n");
         printLine();

         turns();
         if (knocked) comparePoints();
         if (p2.blitz()) player2Won();
         if (p1.blitz()) player1Won();
         gameNr++;
         System.out.println("Game nr " + gameNr);
      } while (playAgain());



   }

   public int getTurnNr(){
      return turnNr;
   }

   //prints hand and top discard card
   public void printState(){
      //System.out.println();
      waiting(1);
      discard.printTop();
      waiting(1);
      System.out.print(comReply + player.getName() + "'s hand:   ");
      player.printHand();

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
         //System.out.println(comReply + "Turn number: " + turnNr);
         turnNr ++;
         //discard.printCards();
      }
   }

   // Drawing part of the turn
   public void drawTurn(){
      waiting(2);
      printLine();

      System.out.print("\n               " + player.getName() + "'s turn\n");

      printLine();
      System.out.println();

      waiting(1);
      if (player.isUser()) waiting(1);
      printState();
      player.drawTurn(discard, stock, knocked, turnNr);
      if (player.isUser() && !player.hasKnocked()) printState();
   }

   // The playing part of a turn
   public void playTurn(){
      boolean checkKnock = player.hasKnocked();
      if (player.hasKnocked()) println(comReply +  player.getName() + " knocked");
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
      System.out.println(comReply + "Congratulations! You got a blitz and won the game!");
      p2.printHand();
      endGame = true;
   }
   public void player1Won(){
      System.out.println();
      System.out.println(comReply + "Bugger! Eliza got a blitz. You lost. ");
      p1.printHand();
      endGame = true;
   }


   // Checks if there has been a blitz
   public boolean endGame(){
      return (p1.blitz() || p2.blitz());
   }

   // Compares points after someone knocked.
   public void comparePoints(){
      Player[] ps = {p1,p2};
      println("\n\n=================================================\n");
      printWait(2);
      println("Are you ready to see who won?");
      String reply = in.nextLine();
        // IF THEY SAY SOMETHING ELSE THAN YES?!?!??!
      if (reply.contains("yes")) {

         for (Player p : ps) {
            print(comReply + p.getName() + "'s hand: ");
            p.printOpen();
            println(comReply + p.getName() + " had " + p.hand.maxPoints() + " points \n");

         }

         if (p2.hand.maxPoints() > p1.hand.maxPoints()) winner = p2;
         else if (p2.hand.maxPoints() == p1.hand.maxPoints()) {
            winner = null;
            println(comReply + "It was a tie!");
            return;
         } else winner = p1;
         println(comReply + winner.getName() + " had most points. " + winner.getName() + " won!");
         printWait(2);
         if (winner.isUser()){
            println("Congratulations, you played well");
         } else if (winner == null){
            println("I guess we were both too good ;-)");
         } else {
            println("Can't say I'm surprised I won.. ;-)");
         }

      }
   }



   // rolls a die to check who starts
   public Player whoStarts(Player p1, Player p2){
      String p = "";
      int die = 0;
      int die2 = 0;
      while (die == die2) {
         die = p1.dieRoll();

         println(comReply + p1.getName() + " rolled |" + diePrint(die) + "|");
         println("\nAlright, my turn.\n");

         die2 = p2.dieRoll();

         println(comReply + p2.getName() + " rolled |" + diePrint(die2) + "|\n");
         //System.out.println("first die " + die); System.out.println("second die " + die2);

         if (die == die2) println("Whoops, we rolled the same. Let's try again.\n");
      }


      if (die2 > die) {
         println(comReply + p2.getName() + " rolled highest. " + p2.getName() + " starts");
         return p2;
      }
      println(comReply + p1.getName() + " rolled highest. " + p1.getName() + " starts");
      return p1;
   }

   // prints the die all nicely :)
   public String diePrint(int die){
      String p;
      if (die == 1)      p = (char) 0x2802 + "";
      else if (die == 2) p = (char) 0x2801 + " " + (char) 0x2804;
      else if (die == 3) p = (char) 0x2801 + ""  + (char) 0x2802 + "" + (char) 0x2804;
      else if (die == 4) p = (char) 0x2805 + " " + (char) 0x2805;
      else if (die == 5) p = (char) 0x2805 + ""  + (char) 0x2802 + "" + (char) 0x2805;
      else               p = (char) 0x2807 + " " + (char) 0x2807;
      return p;
   }

   public void printLine(){
      print("\n=======================================================\n");
   }

   public void print(String string){
      System.out.print(string);
   }

   public void println(String string){
      System.out.println(string);
   }

   public boolean playAgain(){

      printLine();
      printWait(1);
      if (gameNr == 1) println("That was fun! Shall we play again?");
      if (gameNr == 2) println("Great! Shall we play again?");
      if (gameNr == 3) println("Alright. Do you want to play again?");
      if (gameNr == 4) {println("Nice! Are we done playing?");
         String reply = in.nextLine();
         if (reply.contains("no")) return true;
         else if (reply.contains("yes")) return false;
      }
      if (gameNr == 5) {
         println("Soo.. Shall we call it a night for the games?");
         String reply = in.nextLine();
         if (reply.contains("no")){
            println("Okay, but this will be our last.. ");
            return true;
         }
         else if (reply.contains("yes")) return false;
      }
      if (gameNr >= 5) return false;

      String reply = in.nextLine();
      if (reply.contains("yes")) return true;
      else if (reply.contains("no")) return false;
      return false;
   }

   // Makes a bit of waiting time between answers
   public void waiting(long seconds){// Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java

      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }

   }

   public void waitingHalf(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds*500);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }


   }

   public void printWait(long waitTime){
      int dots = 3;
      String delete = "\b";
      String dot;
      for (int n = 0; n <= dots*2; n++){
         delete += "\b";
      }
      for (int j = 0; j < waitTime; j++) {
         dot = "";
         for (int i = 0; i < dots; i++) {
            dot += (char)0x26AC + " ";
            waitingHalf(1);
            System.out.print(dot + "\r");

         }
         waitingHalf(1);
         System.out.print(delete);
      }
      waiting(1);
   }


}
