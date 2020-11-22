
public class Bot extends Player {
   private boolean knock;

   public Bot(String name){
      super(name);
   }

   @Override public boolean isUser(){
      return false;
   }

   // How bot takes their turn
   @Override public void drawTurn(Cards discard, Cards stock, boolean knocked, int gameTurn){
      if (shouldKnock(gameTurn, knocked, hand) && !knock) knock = true;
      else {
         Cards pileChoice = choosePile(discard, stock, knocked, gameTurn);
         announceDraw(pileChoice);
         hand.draw(pileChoice);
      }
   }
   @Override public void playTurn(Cards discard, boolean knocked){
      Card chosenCard = chooseCard(knocked);
      announcePlay(chosenCard);
      hand.play(discard, hand.getIndex(chosenCard)+1);
   }

   // Which pile to draw form, and which card to play.
   public Cards choosePile(Cards discard, Cards stock, boolean knocked, int gameTurn) {
      Card pileCard = discard.topCard();
      Cards choice = stock;
      int dCPoint = pileCard.points();                                                    //discard pile points
      Cards HD = new Cards();                                                             // HD is what the player's hand will look like if they pick up the discard pile's card
      HD.appendCards(hand, pileCard);
      boolean justBetter, notBadCard, bestSuit_anyOver9, discCard_GT_minPoint, pileCard_GT_8, pileCard_GTE_10, changeLowestCard, badSuitGoodCard, discCard_2GT_minPointSUIT, bestSuit_anyUnder10;

      justBetter                = HD.maxPoints() > (hand.maxPoints());                    // Picking the card will give more points.
      notBadCard                = dCPoint > 5;                                            // The card in the discard pile is better than a five.
      bestSuit_anyOver9         = HD.bestGroup().anyOver(9);                         // The cards with the suit summed to highest points has any cards giving 10 or 11 points
      discCard_GT_minPoint      = dCPoint > hand.worstGroup().worstCard().points() ;      // The card in the discard pile's points are higher than the points of the worst card from the worst suit in hand.
      discCard_2GT_minPointSUIT =  dCPoint+2 > hand.bestGroup().worstCard().points();     // Discard pile card gives at least 2 points more than the worst card from the best suit in hand.
      pileCard_GT_8             = dCPoint > 8;                                            // Discard pile card's points are 9 or more.
      pileCard_GTE_10           = dCPoint >= 10;                                          // Discard pile card's points are 10 or 11
      changeLowestCard          = hand.worstCard().points() < 8;                          // The worst card in hand gives less than 8 points.
      badSuitGoodCard           = hand.worstGroup().anyOver(9);                      // The worst suit's cards have any cards with points 10 or 11
      bestSuit_anyUnder10       = hand.bestGroup().anyUnder(10);                     // Best suit's cards have any cards with less than 10 points

      int counter = 0;
      for (int i = 0; i < hand.bestGroup().size(); i++)                                   // Counter for how many cards in hand have 10 or 11 points
         if (hand.bestGroup().getCard(i).over10()) counter += 1;

      if (knocked && justBetter) choice = discard;                                        // If the player knocked, Liza just wants the best hand no matter what
      else if (notBadCard
              &&  (justBetter && bestSuit_anyOver9
                &&  (discCard_GT_minPoint || discCard_2GT_minPointSUIT )
                  &&   ((counter >= 2  || pileCard_GT_8)))

              || (pileCard_GTE_10 && changeLowestCard && !badSuitGoodCard && bestSuit_anyUnder10))  // if discard pile card is 10 or 11, you wanna switch out with cards lower than 6 of best suit.
      choice = discard;
      else if (( shouldKnock(gameTurn, knocked, HD.removeCard(HD.worstCard())))) {               // If the bot get enough points to knock next by picking the discard pile card.
         knock = true;
         choice = discard;
      }

      return choice;
   }
   public Card  chooseCard(boolean knocked) {
      int counter = 0;
      for (int i = 0; i < hand.bestGroup().size(); i++)
         if (hand.bestGroup().getCard(i).over10()) counter += 1;

      boolean changeLowestCard = hand.worstGroup().anyOver(9);
      boolean noBadinBadSuit   = !hand.worstGroup().anyUnder(10);
      if    (!knocked && hand.worstCard().points() < 8
              && changeLowestCard && noBadinBadSuit
              && ((hand.anyUnder(6) && counter <2)
                 || (counter >= 2 && hand.anyUnder(5))))
         return hand.worstCard();
      else return hand.worstGroup().worstCard();
   }

   // Prints which cards were picked above.
   public void announceDraw(Cards pile){
   printWait(2);
      println("I draw from the " + pile.getLabel() + " pile.");
      waitingMilSec(1000);
   }
   public void announcePlay(Card chosen){
      printWait(2);
      println("I choose to play card nr. " + (hand.getIndex(chosen)+1) + ": " + hand.showCard(chosen));
      waitingMilSec(500);
      println();
   }

   // bot decides whether to knock or not.
   public boolean shouldKnock(int gameTurn, boolean knocked, Cards hand){
      boolean earlyGame, midGame, lateGame;
      int     lowerTurn, midTurn, lateTurn;
      lowerTurn = 4; midTurn = 8; lateTurn = 12;
      earlyGame  = gameTurn < lowerTurn &&                          hand.maxPoints() > diverseKnockChoices(23);
      midGame    = gameTurn > lowerTurn && gameTurn <= midTurn   && hand.maxPoints() > diverseKnockChoices(26);
      lateGame   = gameTurn > midTurn   && gameTurn <= lateTurn  && hand.maxPoints() > diverseKnockChoices(28);
      if ( !knocked && (earlyGame && gameTurn > 0 || midGame || lateGame  || hand.maxPoints() >= 30)){
         printWait(1);
         println("I want to knock");
         return true;
      }
      return false;
   }
   private int    diverseKnockChoices(int points){
      return (int)(Math.random() * ((points-1) - (points + 1) + 1) + points-1);
   }              // To make the sure it doesn't just always knock when at x points on turn y.

   // printers
   @Override public void printHand(){
      for (int i = 0; i < hand.size(); i++){
         print("|x|");
         if (i<hand.size()-1) print(" - ");
      }
      println();
   }
   public void printOpen(){
      hand.printHand();
   }
   public void printWait(long waitTime){
      int dots = 3;
      String dotChar = ".";
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
   } // Prints dots while Liza is writing


}
