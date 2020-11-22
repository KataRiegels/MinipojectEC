import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game extends Conversation{
   String comReply;  //0x2B9A
   String userName;
   Pile stock, discard;
   Player winner, player, p1, p2;
   Player[] ps = new Player[2];
   int turnNr, gameNr;
   boolean endGame, knocked, uni, stop;

   Output contin, secret;
   GameOutput notRollDie, rollDie, readyDie,
           notSeeWhoWon, seeWhoWon, seeWhoWonQ,
           playAgain, playAgainQ, notPlayAgain;

   String[] yes, do_;
   String[] no, dont;

   public Game() {
      stock   = new Pile("stock");
      discard = new Pile("discard");
      turnNr = 0; gameNr = 0;
      endGame = false;
      knocked = false;
      comReply = (char) 0x2B9A + " ";
      stopGameT = false;


      readyDie     = new GameOutput("Are you ready to roll the die??");
      rollDie      = new GameOutput("ok, here");
      notRollDie   = new GameOutput("Uhm, okay.. Do you want to stop playing?");
      seeWhoWonQ   = new GameOutput("Are you ready to see who won?");
      seeWhoWon    = new GameOutput("Alright. Let's see");
      notSeeWhoWon = new GameOutput("Okay, shall we keep it a secret then?");
      secret       = new Output("We will keep it a secret then");
      playAgain    = new GameOutput("Okay, let's play");
      playAgainQ   = new GameOutput("...");
      notPlayAgain = new GameOutput("Alright, let's stop");
      contin = new Output("Alright, let's continue");

      no   = a(("no"));
      yes  = a("yes");
      dont = a("don't");
      do_  = a("do");
      setOutputs();

   }

   public void setOutputs(){
      rollDie.setKeyword(yes);
      notRollDie.setKeyword(no);
      seeWhoWon.setKeyword(yes, do_);
      seeWhoWon.setNotKeywords(no, dont);
      notSeeWhoWon.setKeyword(no,dont);
      notSeeWhoWon.setNotKeywords(yes);
      playAgain.setKeyword(yes, a("play"));
      playAgain.setNotKeywords(no, a("not", "play"), a("not", "continue"));
      notPlayAgain.setKeyword(no, a("not", "play"), a("not", "continue"));
      contin.setKeyword(no, a("continue"),a("don't", "stop"));
      secret.setKeyword(yes);

      readyDie.setPossibleOutputs(aPR,rollDie, notRollDie);
      notRollDie.setPossibleOutputs(aPR, contin, secret );
      playAgainQ.setPossibleOutputs(aPR,playAgain, notPlayAgain);
      seeWhoWonQ.setPossibleOutputs(aPR, seeWhoWon, notSeeWhoWon);
   }



   public void setUserName(String name){
       userName = name;
   }

   public String[] a(String... strings){
      String[] a = new String[strings.length];
      for (int i = 0; i < strings.length; i++){
         a[i] = strings[i];
      }
      return a;
   }


   public Output useOutput(Output output){
      Output firstOut = output.copy();
      Output[] firstOutPoss = output.getPossibleOutputs();
      Output prevOutput;
      do {
         output.print();
         String input = readString();
         prevOutput = output.copy();
         output = output.getNext(input);
         output.setPossibleOutputs(firstOutPoss);
         System.out.println(firstOut.isInPossibleOutputs(output));
      } while (!output.equals(prevOutput.getErrOutput()) && !firstOut.isInPossibleOutputs(output));
      return output;
   }

   public void setuni(boolean uni){
      this.uni = uni;
   }

   public boolean stoppedGame(){
      return stopGameT;
   }

   public void playGame(){
      p1 = new Bot("Liza");
      //p2 = new Bot("bot2");
      p2 = new Player(userName);
      ps[0] = p2; ps[1] = p1;
      gameNr = 0;
      stopGameT = false;
      //uni = true;                     // <-- fix!



      do {
         stopGameT = false;
         endGame = stopGameT;
         knocked = false;

         stock.createStock();
         stock.shuffle();
         p1.hand.starter(stock, 3);
         p2.hand.starter(stock, 3);

         discard.turnCard(stock);
         uniCode();
         turnNr = 0;
         //in = new Scanner(System.in);

         if (gameNr == 0) botReply("\nLet's get ready to play! We will decide who starts by rolling a die. \n", 1);
         else botReply("\nDie time \n", 1);
         //waiting(1);


         player = whoStarts(p1, p2);
         if (stopGameT) break;
         //waiting(2);

         player = p2;
/*
         printLine();
         println("\n              The game begins!");
         printLine();
         waiting(1);

*/
         turns();
         if (knocked) comparePoints();
         winner = playerWon();
         gameNr++;
         if (stopGameT) break;
         //if (!(playAgain() || !stopGameT)) break;
      } while (playAgain());

      if (stopGameT) botReply("Okay, let's stop then", 1);

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
            Output output = null;
            do {
            if (p.isUser()) output = useOutput(readyDie);
            if (output == stopGame){
               stopGameT = true;
               return null;
            }

               if (output == rollDie || !p.isUser()) {
                  //dieRoll(p);
                  if (!p.isUser()) botReply("I'll roll\n", 1);
                  dice[i] = p.dieRoll();
                  waiting(1);
                  println(comReply + p.getName() + " rolled |" + diePrint(dice[i]) + "|");
                  waiting(1);
               } else if (output == notRollDie) {

                  output = useOutput(notRollDie);
                  if (output == stopGame) {
                     stopGameT = true;
                     return null;
                  } else if (output == contin) continue;
               }
            }while (output != rollDie && p.isUser() ) ;


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
      while (!stopGameT){
         playerWon();
         if (endGame) return;
         if (stock.isEmpty()) reshuffle();
         Player previous = nextPlayer(player);
         if (previous.hasKnocked()) knocked = true;
         if (player.getStop()) return;
            drawTurn();
            //printHand();
            playTurn();

         //printHand();
         player = nextPlayer(player);
         if (player.hasKnocked() || endGame) return;
         //System.out.println(comReply + "Turn number: " + turnNr);
         turnNr ++;
         //if (turnNr > 2) return;
         //discard.printCards();
      }
   }

   // Drawing and playing (in turns)
   public void drawTurn(){
      if (player.getStop()) {
         stopGameT = true;
         return;
      }
      printLine();
      System.out.print("\n               " + player.getName() + "'s turn\n");
      printLine();
      System.out.println();

      waiting(1);
      if (player.isUser()) waiting(1);
      printState();
      player.drawTurn(discard, stock, knocked, turnNr);
      if (player.isUser() && !player.hasKnocked() && !player.getStop()) printState();
   }
   public void playTurn(){
      if (player.getStop()) {
         stopGameT = true;
         return;

      }
      boolean checkKnock = player.hasKnocked();
      if (player.hasKnocked()) {
         waitingMilSec(500);
         println(comReply +  player.getName() + " knocked");
      }
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

   public void dieRoll(Player p){
      if (p.isUser()) {
         useOutput(readyDie);
            printDieWait();

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
      Output output = useOutput(seeWhoWonQ);
      do {
         if (output == stopGame){
            stopGameT = true;
            return;
         }
         if (output == seeWhoWon) {
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
            if (winner.isUser()) {
               println("\nCongratulations, you played well");
            } else if (winner == null) {
               println("I guess we were both too good ;-)");
            } else {
               println("Can't say I'm surprised I won.. ;-)");
            }
         } else if (output == notSeeWhoWon) {
            output = useOutput(notSeeWhoWon);
         }
      } while (output != secret);
   }// compares points after someone knocked
   public boolean playAgain(){
      printLine();
      printWait(1);
      Output o;
      //if (gameNr == 0) playAgainQ.setReply("That was fun! Shall we play again?");
      if (gameNr == 1) playAgainQ.setReply("That was fun! Shall we play again?");
      if (gameNr == 2) playAgainQ.setReply("Great! Shall we play again?");
      if (gameNr == 3) playAgainQ.setReply("Alright. Do you want to play again?");
      if (gameNr == 4 || gameNr == 5) {
         if (gameNr == 4) {
            playAgainQ.setReply("Nice! Are we done playing?");
            playAgain.setKeyword(no, a("not"), a("aren't"));
            notPlayAgain.setKeyword(yes, a("are", "done"));
            notPlayAgain.setNotKeywords(no, a("not"));
         } else {
            playAgainQ.setReply("Soo.. Shall we call it a night for the games?");
         }
      }
      if (gameNr >= 5) {
         println("We played enough now");
         return false;
      }

      o = useOutput(playAgainQ);

      if (o == playAgain) return true;
      else if (o == notPlayAgain) return false;

      return false;
   }


   public void uniCode(){

         p1.hand.setUni(uni);
         p2.hand.setUni(uni);
         for (Player p : ps){
            if (p.isUser() || !uni) p.setComReply((char)45);
            p.setUnicode(uni);
         }
         stock.setUni(uni);
         discard.setUni(uni);
         if (!uni) comReply = "- ";

   }



   // Methods that are simply concerned with how to print certain things. Are only used within other methods.
   public void    printState(){
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
            waitingMilSec(300);
            System.out.print(dot + "\r");

         }
         waitingMilSec(300);
         System.out.print(delete);
      }
      waitingMilSec(300);
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
   public String readString() {
      Scanner in = new Scanner(System.in);
      return in.nextLine();
   }



}
