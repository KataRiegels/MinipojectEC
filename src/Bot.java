import java.util.Random;

public class Bot extends Player {

   public Bot(String name){
      super(name);
   }


   public Pile choosePile(Pile discard, Pile stock, boolean knocked){
      Card pileCard = discard.topCard();
      Pile choice = stock; Cards HD = new Cards(); HD.appendCards(hand, pileCard);
      boolean justBetter, notBadCard, bestSuit_OKCards, improveHand,discCard_GT_minPoint, pileCard_GT_8, pileCard_GTE_10, badCardGoodSuit;
      justBetter           = HD.maxPoints()    > (hand.maxPoints());
      notBadCard           = pileCard.points() > 5;    //hand.worstCardAndSuit().points();
      bestSuit_OKCards     = HD.bestGroup().anyOver(9);
      improveHand          = HD.maxPoints()    < hand.minPoints();
      discCard_GT_minPoint = pileCard.points() > hand.minPoints();
      pileCard_GT_8        = pileCard.points() > 8;
      pileCard_GTE_10       = pileCard.points() >= 10;
      badCardGoodSuit      = hand.bestGroup().worstCard().points() > 4;

      if (knocked && justBetter && notBadCard) choice = discard;
      else if (justBetter && notBadCard
              && (( bestSuit_OKCards || improveHand)
              || (discCard_GT_minPoint && pileCard_GT_8)
              || (pileCard_GTE_10 && badCardGoodSuit)
      )){ choice = discard; }
      return choice;
   }

   public void announcePlay(){
      System.out.println();
      System.out.println(name + " plays card nr. " + (indexCard()) + ": " + chooseCard().show() );
   }

   public void announceDraw(Pile pile){
      System.out.println();
      System.out.println("Liza draws from the " + pile.label + " pile.");
   }

   @Override
   public void ifKnocked(){
      System.out.println();
   }

   public int indexCard(){
      return hand.getIndex(chooseCard())+1;
   }

   public Card chooseCard(){
      //Card choice = new Card();
      return hand.worstCardAndSuit();
   }

   @Override
   public void printHand(){
      int handSize = hand.size();
      System.out.print(this.name + "'s hand: ");
      for (int i = 0; i < handSize; i++){
         System.out.print("|x|");
         if (i<handSize-1){
            System.out.print(" - ");
         }
      }
      hand.printHand();
      System.out.println();
   }

   public int areaOfSurprise(int points){
      return (int)Math.random() * ((points-2) - (points + 2) + 1) + points-2;
   }


   public boolean shouldKnock(int gameTurn){
      boolean earlyGame, midGame, lateGame, neverKnock;
      int lowerTurn, midTurn, lateTurn;
      lowerTurn = 3; midTurn = 8; lateTurn = 12;
      earlyGame  = gameTurn < lowerTurn &&                         hand.maxPoints() > areaOfSurprise(17);
      midGame    = gameTurn > lowerTurn && gameTurn <= midTurn   && hand.maxPoints() > areaOfSurprise(23);
      lateGame   = gameTurn > midTurn   && gameTurn <= lateTurn  && hand.maxPoints() > areaOfSurprise(27);
      neverKnock = gameTurn > lateTurn;
      if ( earlyGame || midGame || lateGame) return true;
      return false;
   }


   public void printOpen(){
      hand.printHand();
   }

   @Override
   public void drawTurn(Pile discard, Pile stock, boolean knocked, int gameTurn){
      printHand();
      if (shouldKnock(gameTurn)){
         knock = true;
      } else {
      Pile pileChoice = choosePile(discard, stock, knocked);
      announceDraw(pileChoice);
      draw(pileChoice);
      printHand();
      }
   }

   @Override
   public void playTurn(Pile discard, boolean knocked){
      announcePlay();
      play(discard, indexCard());
   }

}
