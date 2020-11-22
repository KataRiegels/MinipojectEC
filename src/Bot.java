
public class Bot extends Player {
   private String comReply;
   private String name;
   //Hand hand;
   private boolean isUser, knock, unicode;;

   public Bot(String name){
      super(name);
      this.isUser = false;
      unicode = true;
   }


   @Override public boolean isUser(){
      return false;
   }

   // How bot takes their turn
   @Override public void drawTurn(Pile discard, Pile stock, boolean knocked, int gameTurn){
      //printHand();
      if (shouldKnock(gameTurn, knocked, hand) && !knock){
         knock = true;
      } else {
         //waiting(3);
         Pile pileChoice = choosePile(discard, stock, knocked, gameTurn);
         //waiting(1);
         announceDraw(pileChoice);
         hand.draw(pileChoice);
         //System.out.print(comReply + name + "'s hand");
         //printHand();
      }
   }
   @Override public void playTurn(Pile discard, boolean knocked){
      //waiting(3);
      Card chosenCard = chooseCard(knocked);
      announcePlay(chosenCard);
      hand.play(discard, hand.getIndex(chosenCard)+1);
   }


   // Which pile to draw form, and which card to play.
   public Pile choosePile(Pile discard, Pile stock, boolean knocked, int gameTurn) {
      Card pileCard = discard.topCard();
      Pile choice = stock;
      int dCPoint = pileCard.points(); //discard pile point
      Cards HD = new Cards();
      HD.appendCards(hand, pileCard);
      boolean justBetter, notBadCard, bestSuit_anyOver9, discCard_GT_minPoint,
              pileCard_GT_8, pileCard_GTE_10, changeLowestCard, badSuitGoodCard,
              discCard_2GT_minPointSUIT, bestSuit_anyUnder10;

      justBetter                = HD.maxPoints() > (hand.maxPoints());
      notBadCard                = dCPoint > 5;    //hand.worstCardAndSuit().points();
      bestSuit_anyOver9         = HD.bestGroup().anyOver(9);
      discCard_GT_minPoint      = (dCPoint > hand.worstGroup().worstCard().points()) ;
      discCard_2GT_minPointSUIT =  dCPoint+2 > hand.bestGroup().worstCard().points();
      pileCard_GT_8             = dCPoint > 8;
      pileCard_GTE_10           = dCPoint >= 10;
      changeLowestCard          = hand.worstCard().points() < 8;
      badSuitGoodCard           = hand.worstGroup().anyOver(9);
      bestSuit_anyUnder10       = hand.bestGroup().anyUnder(10);
      int counter = 0;

      for (int i = 0; i < hand.bestGroup().size(); i++){
         if (hand.bestGroup().getCard(i).over10()) counter += 1;
      }

      if (knocked && justBetter) choice = discard;
      else if (notBadCard
              &&           ((justBetter && bestSuit_anyOver9
              &&           (discCard_GT_minPoint || discCard_2GT_minPointSUIT )
              &&           ((counter >= 2  || pileCard_GT_8))))){
         choice = discard;
      }
      else if (pileCard_GTE_10 && changeLowestCard && !badSuitGoodCard && bestSuit_anyUnder10){
         choice = discard;   // if discard pile card is 10 or 11, you wanna switch out with cards lower than 6 of best suit.
      }
      else if ( shouldKnock(gameTurn, knocked, HD.removeCard(HD.worstCard()))) {
         //System.out.println("will knock nexy huhu");
         knock = true;
         choice = discard;
      }
      return choice;
   }
   public Card chooseCard(boolean knocked) {
      Card choice; int counter = 0;
      for (int i = 0; i < hand.bestGroup().size(); i++){
         if (hand.bestGroup().getCard(i).over10()) counter += 1;
      }
      boolean changeLowestCard = hand.worstGroup().anyOver(9);
      boolean noBadinBadSuit   = !hand.worstGroup().anyUnder(10);
      if    (!knocked && hand.worstCard().points() < 8 && changeLowestCard && noBadinBadSuit && ((hand.anyUnder(6) && counter <2) || (counter >= 2 && hand.anyUnder(5)))) choice = hand.worstCard();
      else  choice = hand.worstGroup().worstCard();
      return choice;
   }

   // Prints which cards were picked above.
   public void announceDraw(Pile pile){
   printWait(2);
      println("I draw from the " + pile.getLabel() + " pile.");
      waitingMilSec(1000);
      //println();
   }
   public void announcePlay(Card chosen){
      printWait(2);
      println("I choose to play card nr. " + (hand.getIndex(chosen)+1) + ": " + hand.showCard(chosen));
      waitingMilSec(500);
      println();
   }


   // prints bot's hand. Override: will print hidden cards.
   @Override public void printHand(){
      //int handSize = hand.size();
      //System.out.print(comReply + name + "'s hand: ");

      for (int i = 0; i < hand.size(); i++){
         System.out.print("|x|");
         if (i<hand.size()-1){
            System.out.print(" - ");
         }
      }


      //hand.printHand();
      System.out.println();
   }

   // bot decides whether to knock or not.
   public boolean shouldKnock(int gameTurn, boolean knocked, Cards hand){
      boolean earlyGame, midGame, lateGame;
      int lowerTurn, midTurn, lateTurn;
      lowerTurn = 4; midTurn = 8; lateTurn = 12;
      earlyGame  = gameTurn < lowerTurn &&                          hand.maxPoints() > areaOfSurprise(23);
      midGame    = gameTurn > lowerTurn && gameTurn <= midTurn   && hand.maxPoints() > areaOfSurprise(26);
      lateGame   = gameTurn > midTurn   && gameTurn <= lateTurn  && hand.maxPoints() > areaOfSurprise(28);
      if ( !knocked && (earlyGame && gameTurn > 0 || midGame || lateGame  || hand.maxPoints() >= 30)){
         //waiting(2);
         printWait(1);
         System.out.println("I want to knock");
         //return false;
         return true;
      }
      return false;
   }
   private int areaOfSurprise(int points){


      return (int)Math.random() * ((points-1) - (points + 1) + 1) + points-1;
   }              // To make the sure it doesn't just always knock when at x points on turn y.


   // printers
   public void printOpen(){
      hand.printHand();
   }
   public void printWait(long waitTime){
      int dots = 3;
      String dotChar;
      if (unicode) dotChar = (char)0x26AC + "";
      else dotChar = ".";
      String delete = "\b";
      String dot;
      println();
      for (int n = 0; n <= dots*2; n++){
         delete += "\b";
      }
      for (int j = 0; j < waitTime; j++) {
         dot = "";
         for (int i = 0; i < dots; i++) {
            dot += dotChar + " ";
            waitingMilSec(300);
            print(dot + "\r");

         }
         waitingMilSec(500);
         print(delete);
      }
      waitingMilSec(500);
   } // Print the bubbles while Liza is writing


}
