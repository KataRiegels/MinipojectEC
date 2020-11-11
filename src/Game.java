import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {
   Player user;
   //Player Eliza;
   Pile stock;
   Pile discard;
   Scanner in;
   Player winner;
   Player player;
   Bot bot;
   int turnNr;
   Case discPile;
   Case stockPile;
   boolean endGame;
   boolean knocked;
   boolean userKnocked;
   boolean ElizaKnocked;

   public Game() {
      Deck deck = new Deck("Deck");
      deck.shuffle();

      discPile = new Case("discard", 1);
      stockPile = new Case("stock", 0);

      stock = new Pile("stock");
      stock.createStock(deck);

      discard = new Pile("discard");
      discard.turnCard(stock);

      bot = new Bot("Liza");
      bot.hand.starter(stock);


      user = new Player("User");
      user.hand.starter(stock);


      player = user;
      turnNr = 0;
      in = new Scanner(System.in);

      endGame = false;
      knocked = false;
      userKnocked = false;
      ElizaKnocked = false;
   }

   public void playGame(){

      turns();
      if (knocked) comparePoints();
   }




   public void printState(){
      System.out.println();
      discard.printTop();
      if (player == user){
      System.out.print("Your hand:   ");
      user.printHand();
      }
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

   public void turns(){
      while (!endGame()){
         if (stock.isEmpty()) reshuffle();
         takeTurns(player);
         player = nextPlayer(player);
         if (player.getKnock()) return;
         turnNr ++;
      }

//      if (Eliza.blitz()) ElizaWon();
//      if (user.blitz()) userWon();
//      if (knocked) user.knocked();
   }

   public void takeTurns(Player player){
      Player previous = nextPlayer(player);
      if (previous.getKnock()) knocked = true;
      drawTurn();
      playTurn();

   }

   public void drawTurn(){
      System.out.println(player.getName() + "'s turn");
      //if (player == user)
      printState();
      player.drawTurn(discard,stock, knocked, turnNr);
      if (player == user && !player.getKnock()) printState();
   }

   public void playTurn(){
      boolean checkKnock = player.getKnock();
      if (player.getKnock()) System.out.println(player.getName() + " knocked");
      if (!checkKnock){
         player.playTurn(discard, knocked);
         discard.printTop();
      }

   }

   public void reshuffle(){
      discard.shuffle();
      stock.createStock(discard);
   }



   public Player nextPlayer(Player current) {
      if (current == bot) {
         return user;
      } else {
         return bot;
      }
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

   public void userWon(){
      System.out.println();
      System.out.println("Congratulations! You got a blitz and won the game!");
      user.printHand();
      endGame = true;
   }

   public void ElizaWon(){
      System.out.println();
      System.out.println("Bugger! Eliza got a blitz. You lost. ");
      bot.printHand();
      endGame = true;
   }


   public boolean checkBot(Player player){
      return player.name.equals("Liza");
   }

   public boolean endGame(){
      return (bot.blitz() || user.blitz());
   }


   public void typeOfEnd(){
      if (bot.blitz()) ElizaWon();
      if (user.blitz()) userWon();
      if (userKnocked) user.knocked();
      if (ElizaKnocked) bot.knocked();
   }

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



   public void print(String string){
      System.out.print(string);
   }

   public void println(String string){
      System.out.println(string);
   }

}
