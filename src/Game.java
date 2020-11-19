import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game extends Conversation{
   String comReply;  //0x2B9A
   Pile stock, discard;
   Scanner in;
   Player winner, player, p1, p2;
   Player[] ps = new Player[2];
   int turnNr, gameNr;
   boolean endGame, knocked, uni;

   public Game() {
      stock   = new Pile("stock");
      discard = new Pile("discard");
      turnNr = 0; gameNr = 0;
      in = new Scanner(System.in);
      endGame = false;
      knocked = false;
      comReply = (char) 0x2B9A + " ";
   }

   public void playGame(){
      p1 = new Bot("Liza");
      //p2 = new Bot("bot2");
      p2 = new Bot("Kata");
      ps[0] = p1; ps[1] = p2;


      uni = true;                     // <-- fix!



      do {

         endGame = false;
         knocked = false;

         stock.createStock();
         stock.shuffle();
         p1.hand.starter(stock, 3);
         p2.hand.starter(stock, 3);



         discard.turnCard(stock);
         uniCode();
         turnNr = 0;
         in = new Scanner(System.in);



         println("Let's get ready to play! We will decide who starts by rolling a die. \n");
         waiting(1);


         //player = whoStarts(p1, p2);
         //waiting(2);

         player = p1;

         printLine();
         println("\n              The game begins!");
         printLine();
         waiting(1);


         turns();
         if (knocked) comparePoints();
         winner = playerWon();
         return;
         //gameNr++;
         //System.out.println("Game nr " + gameNr);  // <-- remove
      } while (playAgain());
   }

   // getters and setters
   public int    getTurnNr(){
      return turnNr;
   }
   public Player getWinner(){
      return winner;
   }
   public void   setComReply(String c){
      comReply = c + "";
   }


   public Player whoStarts(Player p1, Player p2){
      //String p = "";
      Player p; Player startingPlayer;
      Player[] ps = {p1,p2};
      int[] dice = new int[2];
      while (dice[0] == dice[1]) {
         for (int i = 0; i < ps.length; i++){
            p = ps[i];

            dice[i] = p.dieRoll();
            dieRoll(p);
            waiting(1);
            println(comReply + p.getName() + " rolled |" + diePrint(dice[i]) + "|\n");
            waiting(1);
         }
         if (dice[0] == dice[1]) {
            println("Whoops, we rolled the same. Let's try again.\n");
            waiting(1);
         }
      }
      if    (dice[0] > dice[1]) startingPlayer = ps[0];
      else  startingPlayer = ps[1];
      println(comReply + startingPlayer.getName() + " rolled highest. " + startingPlayer.getName() + " starts");
      return startingPlayer;
   }   // Rolls a die for each player and prints result.

   // taking turns
   public void turns(){
      while (!endGame){
         if (stock.isEmpty()) reshuffle();
         Player previous = nextPlayer(player);
         if (previous.hasKnocked()) knocked = true;
         drawTurn();
         //printHand();
         playTurn();
         //printHand();
         player = nextPlayer(player);
         if (player.hasKnocked() || endGame) return;
         //System.out.println(comReply + "Turn number: " + turnNr);
         turnNr ++;
         if (turnNr > 2) return;
         //discard.printCards();
      }
   }

   // Drawing and playing (in turns)
   public void drawTurn(){
      waiting(1);
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
   public void playTurn(){
      boolean checkKnock = player.hasKnocked();
      if (player.hasKnocked()) println(comReply +  player.getName() + " knocked");
      if (!checkKnock){
         player.playTurn(discard, knocked);
         printState();
         //discard.printTop();
      }

   }


   public Player nextPlayer(Player current) {
      if (current == p1) {
         return p2 ;
      } else {
         return p1;
      }
   }           // returns non-current player
   public void   reshuffle(){
      stock.createStock(discard);
      discard.shuffle();
      discard.turnCard(stock);
   }
   public void   dieRoll(Player p){
      if (p.isUser()) {
         Scanner in = new Scanner(System.in);
         botReply("Are you ready to roll your die?",1);
         String input = in.nextLine();
         if (input.contains("yes")) {
            printDieWait();

         }
      } else {
         botReply("I'll roll now",1);
         printDieWait();

      }
   }                   // the print (or asking) before rolling die


   public Player  playerWon(){
      for (Player p : ps) {
         if (p.blitz()) {
            println();
            println(comReply + p.getName() + " got a blitz and won the game.");
            p.printHand();
            endGame = true;
            return p;
         }
      }
      return null;
   }                          // checks and prints if someone one by blitz
   public void    comparePoints(){
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
   }                     // compares points after someone knocked
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


   public void uniCode(){

         p1.hand.setUni(uni);
         p2.hand.setUni(uni);
         for (Player p : ps){
            if (p.isUser()) p.setComReply((char)45);
            p.setUnicode(uni);
         }
         stock.setUni(uni);
         discard.setUni(uni);
         comReply = "- ";

   }



   // Methods that are simply concerned with how to print certain things. Are only used within other methods.
   public void    printState(){
      waiting(1);
      print(comReply);
      discard.printTop();
      waiting(1);
      System.out.print(comReply + player.getName() + "'s hand:   ");
      player.printHand();

   }
   public void    botReply(String string, int waitTime){
      printWait(waitTime);
      println(string);

   }
   private void   printLine(){
      print("\n=======================================================\n");
   }
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
   private void   printHand(){
      print(comReply + player.getName() + "'s hand");
      player.printHand();
   }
   private void   printDieWait(){
      if (!uni){
         println();
         waitingMilSec(500);
         return;
      }
      int dots = 12;
      String delete = "\b";
      String dot; String bb = "";
      for (int n = 0; n <= dots*2; n++){
         delete += "\b";
      }
      for (int i = 0; i < dots; i++) {
         bb += " ";
         if (i%2 == 0) dot = bb + (char)0x235A + " ";
         else dot= bb+ (char)0x25FB + " ";
         waitingMilSec(250);
         print(dot + "\r");
      }
      dot = bb + (char)0x25FB + " ";
      println(dot);
      waiting(1);
      print(delete);

   }
   private String diePrint(int die){
      if (!uni) return Integer.toString(die);
      String p;
      if (die == 1)      p = (char) 0x2802 + "";
      else if (die == 2) p = (char) 0x2801 + " " + (char) 0x2804;
      else if (die == 3) p = (char) 0x2801 + ""  + (char) 0x2802 + "" + (char) 0x2804;
      else if (die == 4) p = (char) 0x2805 + " " + (char) 0x2805;
      else if (die == 5) p = (char) 0x2805 + ""  + (char) 0x2802 + "" + (char) 0x2805;
      else               p = (char) 0x2807 + " " + (char) 0x2807;
      return p;
   }


   // Makes a bit of waiting time between answers
   private void waiting(long seconds){// Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java

      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         println("There was a problem :( ");
         //Thread.currentThread().interrupt();
      }

   }
   private void waitingHalf(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds*500);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }


   }
   private void waitingMilSec(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }


   }

   // prints and scanner
   private void print(String string){
      System.out.print(string);
   }
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

}
