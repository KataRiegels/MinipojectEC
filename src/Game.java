import java.util.*;

public class Game{
   private String comReply;  //0x2B9A
   private String userName;
   private Cards stock, discard;
   private Player winner, player, p1, p2;
   private Player[] ps = new Player[2];
   private int turnNr, gameNr;
   private boolean endGame, knocked, uni;

   private Output contin, secret;
   private Output notRollDie, rollDie, readyDie,
           notSeeWhoWon, seeWhoWon, seeWhoWonQ,
           playAgain, playAgainQ, notPlayAgain, stopGame;


   public Game() {
      stock   = new Cards("stock");
      discard = new Cards("discard");
      turnNr = 0; gameNr = 0;
      endGame = false;
      knocked = false;
      comReply = (char) 0x2B9A + " ";

      createOutputs();
      setOutputs();

   }

   public void playGame(){                               // The method used to call in main to play the game.
      p1 = new Bot("Liza");
      p2 = new Player(userName);
      ps[0] = p2; ps[1] = p1;
      uniCode();

      botReply("So, this is how it works: ",1);
      if (!uni) botReply("The cards are represented like C.10, which means 10 of clubs. D is diamonds, H is hearts and S is spades. ",1);
      botReply("If you want to knock, you need to do it when I ask what pile you want to draw from.",1);
      botReply("Enough chitty-chat now!",1);


      do {
         endGame = false;
         knocked = false;
         turnNr = 0;

         stock.createStock();
         stock.shuffle();
         p1.hand.starter(stock, 3);
         p2.hand.starter(stock, 3);
         discard.turnCard(stock);

         if (gameNr == 0) botReply("\nLet's get ready to play! We will decide who starts by rolling a die. \n", 1);
         else botReply("\nDie time \n", 1);
         waitingMilSec(500);

         player = whoStarts(p1, p2);
         if (endGame) break;
         waitingMilSec(1000);

         player = p2;

         printLine();
         println("\n              The game begins!");
         printLine();
         waitingMilSec(1000);

         turns();
         if (knocked) comparePoints();
         winner = playerWon();
         gameNr++;
         if (endGame) break;
      } while (playAgain());

      if (endGame) botReply("Okay, let's stop then", 1);

   }

   // Output related
   public void createOutputs(){                             // instantiation of the Outputs used here.
      readyDie     = new Output("Are you ready to roll the die?");
      rollDie      = new Output("ok, here");
      notRollDie   = new Output("Uhm, okay.. Do you want to stop playing?");
      seeWhoWonQ   = new Output("Are you ready to see who won?");
      seeWhoWon    = new Output("Alright. Let's see");
      notSeeWhoWon = new Output("Okay, shall we keep it a secret then?");
      secret       = new Output("We will keep it a secret then");
      playAgain    = new Output("Okay, let's play");
      playAgainQ   = new Output("...");
      notPlayAgain = new Output("Alright, let's stop");
      contin       = new Output("Alright, let's continue");
      stopGame     = new Output("Ok, let's stop");
   }
   public void setOutputs(){                                // setting keywords and possible outputs for the Outputs.
      rollDie.setKeywords(a("yes"));
      notRollDie.setKeywords(a("no"));
      seeWhoWon.setKeywords(a("yes"), a("do"));
      seeWhoWon.setNotKeywords(a("no"), a("dont"));
      notSeeWhoWon.setKeywords(a("no"), a("dont"));
      notSeeWhoWon.setNotKeywords(a("yes"));
      playAgain.setKeywords(a("yes"), a("play"));
      notPlayAgain.setKeywords(a("no"), a("not", "play"), a("not", "continue"));
      playAgain.setNotKeywords(a("no"), a("not", "play"), a("not", "continue"));
      contin.setKeywords(a("no"), a("continue"),a("don't", "stop"));
      secret.setKeywords(a("yes"));
      stopGame.setKeywords(a("stop"));
      readyDie.setPossibleOutputs(stopGame,rollDie, notRollDie);
      notRollDie.setPossibleOutputs(stopGame, contin, secret );
      playAgainQ.setPossibleOutputs(stopGame,playAgain, notPlayAgain);
      seeWhoWonQ.setPossibleOutputs(stopGame, seeWhoWon, notSeeWhoWon);
   }
   public Output useOutput(Output output){                   // For whenever we want to use our outputs. Will take an Output and find the next Output based on keywords and possible outputs.
      Output firstOut = output.copy();
      Output[] firstOutPoss = output.getPossibleOutputs();
      Output prevOutput;
      do {
         output.print();
         String input = readString();
         prevOutput = output.copy();
         output = output.getNext(input);
         output.setPossibleOutputs(firstOutPoss);
      } while (!output.equals(prevOutput.getErrOutput()) && !firstOut.isInPossibleOutputs(output));
      return output;
   }

   // getters and setters
   public void setUserName(String name){
      userName = name;
   }
   public void setUni(boolean uni){
      this.uni = uni;
   }
   public boolean stoppedGame(){
      return endGame;
   }

   public Player whoStarts(Player p1, Player p2){                 // Rolls a die for each player and prints result.
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
               endGame = true;
               return null;
            }
               if (output == rollDie || !p.isUser()) {
                  //dieRoll(p);
                  if (!p.isUser()) botReply("I'll roll", 1);
                  dice[i] = p.dieRoll();
                  printDieWait();

                  println(comReply + p.getName() + " rolled |" + diePrint(dice[i]) + "|");
                  waitingMilSec(1000);
               } else if (output == notRollDie) {
                  output = useOutput(notRollDie);
                  if (output == stopGame) {
                     endGame = true;
                     return null;
                  } else if (output == contin) continue;
               }
            }while (output != rollDie && p.isUser() ) ;
         }
         if (dice[0] == dice[1]) {
            println("Whoops, we rolled the same. Let's try again.\n");
            waitingMilSec(1000);
         }
      }
      if    (dice[0] > dice[1]) startingPlayer = ps[0];
      else  startingPlayer = ps[1];
      println(comReply + startingPlayer.getName() + " rolled highest. " + startingPlayer.getName() + " starts");
      return startingPlayer;
   }

   // taking turns
   public void turns(){
      while (!endGame){
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
   public void   drawTurn(){                          // The part of a turn that is concerned with drawing
      if (player.getStop()) {
         endGame = true;
         return;
      }
      printLine();
      System.out.print("\n               " + player.getName() + "'s turn\n");
      printLine();
      System.out.println();

      waitingMilSec(1000);
      if (player.isUser()) waitingMilSec(1);
      printState();
      player.drawTurn(discard, stock, knocked, turnNr);
      if (player.isUser() && !player.hasKnocked() && !player.getStop()) printState();
   }
   public void   playTurn(){                                // The part of the turn concerned with playing a card.
      if (player.getStop()) {
         endGame = true;
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
   public Player nextPlayer(Player current) {                        // returns non-current player
      if (current == p1) {
         return p2 ;
      } else {
         return p1;
      }
   }
   public void   reshuffle(){
      stock.createStock(discard);
      discard.shuffle();
      discard.turnCard(stock);
   }

   public Player  playerWon(){                              // checks and prints if someone won by blitz
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
   }
   public void    comparePoints(){                          // compares points after someone knocked
      Player[] ps = {p1,p2};
      printLine();
      printWait(2);
      Output output = useOutput(seeWhoWonQ);
      do {
         if (output == stopGame){
            endGame = true;
            return;
         }
         if (output == seeWhoWon) {
            for (Player p : ps) {
               print(comReply + p.getName() + "'s hand: ");
               p.printOpen();
               println(comReply + p.getName() + " had " + p.hand.maxPoints() + " points \n");
               waitingMilSec(300);
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
               botReply("\nCongratulations, you played well",1);
            } else if (winner == null) {
               botReply("I guess we were both too good ;-)",1);
            } else {
               botReply("Can't say I'm surprised I won.. ;-)",1);
            }
            break;
         } else if (output == notSeeWhoWon) {
            output = useOutput(notSeeWhoWon);
         }
      } while (output != secret);
   }
   public boolean playAgain(){                     // Asks whether they should play agian
      printLine();
      printWait(1);
      Output o;
      if (gameNr == 1) playAgainQ.setReply("That was fun! Shall we play again?");
      if (gameNr == 2) playAgainQ.setReply("Great! Shall we play again?");
      if (gameNr == 3) playAgainQ.setReply("Alright. Do you want to play again?");
      if (gameNr == 4 || gameNr == 5) {
         if (gameNr == 4) {
            playAgainQ.setReply("Nice! Are we done playing?");
            playAgain.setKeywords((a("no")), a("not"), a("aren't"));
            notPlayAgain.setKeywords(a("yes"), a("are", "done"));
            notPlayAgain.setNotKeywords(a("no"), a("not"));
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
      return false;
   }
   public void    uniCode(){                          // Sets all necessary things depending on the user's respond to symbols.
      p1.hand.setUni(uni);
      p2.hand.setUni(uni);
      stock.setUni(uni);
      discard.setUni(uni);
      if (!uni) comReply = "- ";

   }


   // Methods that are simply concerned with how to print certain things. Are only used within other methods.
   public void    printState(){                                   // Prints top card in discard pile and hand of the current player.
      print(comReply);
      discard.printTop();
      waitingMilSec(1000);
      System.out.print(comReply + player.getName() + "'s hand:   ");
      player.printHand();

   }
   public void    botReply(String string, int waitTime){                                // This just prints something with "writing bubbles" first.
      printWait(waitTime);
      println(string);

   }
   private void   printLine(){                                 // Just prints a line of =
      print("\n=======================================================\n");
   }
   private void   printWait(long waitTime){                 // The part that prints "writing bubbles"
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
   private void   printDieWait(){                              // Prints a small die roll.
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
      waitingMilSec(1000);
      print(delete);

   }
   private String diePrint(int die){                  // Prints the die neatly.
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


   // waiting method. This will cause a small pause in the console.
   private void waitingMilSec(long seconds){// Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java

      try {
         Thread.sleep(seconds);
      }
      catch(InterruptedException ex) {
         println("There was a problem :( ");
         //Thread.currentThread().interrupt();
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
   public String[] a(String... strings){
      String[] a = new String[strings.length];
      System.arraycopy(strings, 0, a, 0, strings.length);
      return a;
   }

}
