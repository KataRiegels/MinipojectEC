

import java.util.Random;

public class Bot extends Player {
   boolean trainingGame;

   public Bot(String name, boolean trainingGame){
      super(name);
      this.trainingGame = trainingGame;
   }


   // returns what pile bot chooses to draw from
   public Pile choosePile(Pile discard, Pile stock, boolean knocked) {
      Card pileCard = discard.topCard();
      Pile choice = stock; int dCPoint = pileCard.points(); //discard pile point
      Cards HD = new Cards();
      HD.appendCards(hand, pileCard);
      String p;
      boolean justBetter, notBadCard, bestSuit_OKCards,
              improveHandEnough, discCard_GT_minPoint,
              pileCard_GT_8, pileCard_GTE_10, badCardGoodSuit
              , betterCard, changeLowestCard;
      justBetter           = HD.maxPoints() > (hand.maxPoints());
      notBadCard           = dCPoint > 5;    //hand.worstCardAndSuit().points();
      bestSuit_OKCards     = HD.bestGroup().anyOver(9);
      //improveHandEnough    = (HD.maxPoints() * 0.7) < hand.minPoints(); // complicated. Image having D6, S7 and C8 with S5 on the table.
      discCard_GT_minPoint = dCPoint > hand.minPoints();
      pileCard_GT_8        = dCPoint > 8;
      pileCard_GTE_10      = dCPoint >= 10;
      badCardGoodSuit      = hand.bestGroup().worstCard().points() > 4;
      betterCard           = true;
      changeLowestCard     = hand.worstCard().points() > 6;

      if (knocked && justBetter) choice = discard;
      /*
      else if (justBetter && notBadCard
              &&  (discCard_GT_minPoint && pileCard_GT_8)
                  || (pileCard_GTE_10 && badCardGoodSuit) )     {

       */
          else if (notBadCard
                  &&           (justBetter))





            {
         choice = discard;
      }
      return choice;
   }

   // prints chosen draw
   public void announceDraw(Pile pile){
      //System.out.println();
      //System.out.println(name + " draws from the " + pile.label + " pile.");
      System.out.println("I draw from the " + pile.label + " pile.");
      waiting(1);
   }

   // prints chosen card to play
   public void announcePlay(Card chosen){
      //System.out.println(name + " plays card nr. " + hand.getIndex(chosen) + ": " + chosen.show() );
      System.out.println("I choose to play card nr. " + hand.getIndex(chosen) + ": " + chosen.show() );
   }

   // Returns the worst card in hand
   public Card chooseCard(){
      return hand.worstCardAndSuit();
   }

   // prints bot's hand. Override: will print hidden cards.
   @Override
   public void printHand(){
      int handSize = hand.size();
      System.out.print((char) 0x2B9A + name + "'s hand: ");
      for (int i = 0; i < handSize; i++){
         System.out.print("|x|");
         if (i<handSize-1){
            System.out.print(" - ");
         }
      }
      hand.printHand();
      System.out.println();
   }

   // bot decides whether to knock or not.
   public boolean shouldKnock(int gameTurn){
      boolean earlyGame, midGame, lateGame, neverKnock;
      int lowerTurn, midTurn, lateTurn;
      lowerTurn = 3; midTurn = 8; lateTurn = 12;
      earlyGame  = gameTurn < lowerTurn &&                          hand.maxPoints() > areaOfSurprise(25);
      midGame    = gameTurn > lowerTurn && gameTurn <= midTurn   && hand.maxPoints() > areaOfSurprise(26);
      lateGame   = gameTurn > midTurn   && gameTurn <= lateTurn  && hand.maxPoints() > areaOfSurprise(28);
      neverKnock = gameTurn > lateTurn;
      if ( earlyGame && gameTurn > 1 || midGame || lateGame  || hand.maxPoints() >= 30){

         waiting(2);
         //return false;
         return true;
      }
      return false;
   }


   // creates random number in interval of 5 around points.
   // to make bot not just always knock when at _ points.
   public int areaOfSurprise(int points){
      return (int)Math.random() * ((points-1) - (points + 2) + 1) + points-1;
   }

   // prints bot's hand like normally
   public void printOpen(){
      hand.printHand();
   }

   // How bot takes their turn
   @Override
   public void drawTurn(Pile discard, Pile stock, boolean knocked, int gameTurn){
      printHand();
      if (shouldKnock(gameTurn)){
         knock = true;
      } else {
         waiting(3);
         Pile pileChoice = choosePile(discard, stock, knocked);
         waiting(1);
         announceDraw(pileChoice);
         hand.draw(pileChoice);
         printHand();
      }
   }

   // how bot plays their turn
   @Override
   public void playTurn(Pile discard, boolean knocked){
      waiting(3);
      Card chosenCard = chooseCard();
      announcePlay(chosenCard);
      hand.play(discard, hand.getIndex(chosenCard)+1);
   }

   // waiting method
   public void waiting(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      /*
      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }

       */
   }

   // returns that player is not user.
   public boolean isUser(){
      return false;
   }




}
