

import java.util.Random;

public class Bot extends Player {

   boolean trainingGame;

   public Bot(String name, boolean trainingGame){
      super(name);
      this.trainingGame = trainingGame;
   }


   // returns what pile bot chooses to draw from
   public Pile choosePile(Pile discard, Pile stock, boolean knocked, int gameTurn) {
      Card pileCard = discard.topCard();
      Pile choice = stock; int dCPoint = pileCard.points(); //discard pile point
      Cards HD = new Cards();
      HD.appendCards(hand, pileCard);
      String p = "";
      boolean justBetter, notBadCard, bestSuit_anyOver9,
              improveHandEnough, discCard_GT_minPoint,
              pileCard_GT_8, pileCard_GTE_10, badCardGoodSuit
              , betterCard, changeLowestCard, badSuitGoodCard, discCard_2GT_minPointSUIT, bestSuit_anyUnder10;

      justBetter           = HD.maxPoints() > (hand.maxPoints());
      //if (justBetter) p += "\n - Hand gets more points, ";
      notBadCard           = dCPoint > 5;    //hand.worstCardAndSuit().points();
      //if (notBadCard) p += "\n - points of card in pile is higher than 5, ";
      bestSuit_anyOver9    = HD.bestGroup().anyOver(9);
      //if (bestSuit_anyOver9) p+= "\n - the cards from the best suit in my hand are not just cards with points below 10 points, ";
      discCard_GT_minPoint = (dCPoint > hand.worstCardAndSuit().points()) ;
      //if (discCard_GT_minPoint) p += "\n - points of discard pile card is better than the points of the worst card in my hand";
      discCard_2GT_minPointSUIT =  dCPoint+2 > hand.bestGroup().worstCard().points();
      //if (discCard_2GT_minPointSUIT) p +="\n - points of discard pile card is at least 3 points better than my worst card of by best suit";
      pileCard_GT_8        = dCPoint > 8;
      //if (pileCard_GT_8)  p += "\n - the points of the discard pile card is better than 8";
      pileCard_GTE_10      = dCPoint >= 10;
      //if (pileCard_GTE_10) p += "\n - the points of the discard pile card is 10 or better";
      badCardGoodSuit      = hand.bestGroup().worstCard().points() > 4;
      betterCard           = dCPoint > hand.worstCard().points();
      changeLowestCard     = hand.worstCard().points() < 8;
      //if (changeLowestCard) p += "\n - the worst card in my hand is less than 8 points worth";
      badSuitGoodCard    = hand.worstGroup().anyOver(9);
      //if (badSuitGoodCard) p+= "\n - the cards with my worst suit has no card with points over 9";
      bestSuit_anyUnder10 = hand.bestGroup().anyUnder(10);
      //if (bestSuit_anyUnder10) p+= "\n - best suit group has a card below 10 points";
      Card temp = new Card(); int counter = 0;

      for (int i = 0; i < hand.bestGroup().size(); i++){
         if (hand.bestGroup().getCard(i).over10()) counter += 1;
      }

      //if (counter >= 2) p += "\n - i have two cards with points over 10 in my best suit group";

      if (knocked && justBetter) choice = discard;

      else if (notBadCard
              &&           ((justBetter && bestSuit_anyOver9
              &&           (discCard_GT_minPoint || discCard_2GT_minPointSUIT )
              && ((counter >= 2  || pileCard_GT_8))))){
      if (justBetter) p += "\n - Hand gets more points, ";
      if (notBadCard) p += "\n - points of card in pile is higher than 5, ";
      if (bestSuit_anyOver9) p+= "\n - the cards from the best suit in my hand are not just cards with points below 10 points, ";
      if (discCard_GT_minPoint) p += "\n - points of discard pile card is better than the points of the worst card in my hand";
      if (discCard_2GT_minPointSUIT) p +="\n - points of discard pile card is at least 3 points better than my worst card of by best suit";
      if (pileCard_GT_8)  p += "\n - the points of the discard pile card is better than 8";
      if (counter >= 2) p += "\n - i have two cards with points over 10 in my best suit group";
         System.out.println(p);choice = discard;}
      else if (pileCard_GTE_10 && changeLowestCard && !badSuitGoodCard && bestSuit_anyUnder10){
         p += "\n - the points of the discard pile card is 10 or better";
         p += "\n - the worst card in my hand is less than 8 points worth";
         p += "\n - the cards with my worst suit has a card with points over 9";
         p += "\n - best suit group has a card below 10 points";
         System.out.println(p);
         choice = discard;} // if discard pile card is 10 or 11, you wanna switch out with cards lower than 6 of best suit.
      else if ( shouldKnock(gameTurn, knocked, HD.removeCard(HD.worstCard())))
      {
         System.out.println(p);
         choice = discard;

      /*
          else if (notBadCard
                  &&           ((justBetter && bestSuit_anyOver9
                               &&           (discCard_GT_minPoint || discCard_2GT_minPointSUIT )
                                             && ((counter >= 2  || pileCard_GT_8)))

                  ||           (pileCard_GTE_10 && changeLowestCard && !badSuitGoodCard && hand.bestGroup().anyUnder(10))) // if discard pile card is 10 or 11, you wanna switch out with cards lower than 6 of best suit.
                  || shouldKnock(gameTurn, knocked, HD.removeCard(HD.worstCard())))
            {
               System.out.println(p);
         choice = discard;

       */
      }


      return choice;
   }

   // Returns the worst card in hand
   public Card chooseCard(boolean knocked) {
      Card choice = new Card(); int counter = 0;
      for (int i = 0; i < hand.bestGroup().size(); i++){
         if (hand.bestGroup().getCard(i).over10()) counter += 1;
      }
      boolean justBetter, notBadCard, bestSuit_anyOver9,
              improveHandEnough, discCard_GT_minPoint,
              pileCard_GT_8, pileCard_GTE_10, badCardGoodSuit, betterCard, changeLowestCard, noBadinBadSuit;
      changeLowestCard = hand.worstGroup().anyOver(9);
      noBadinBadSuit = !hand.worstGroup().anyUnder(10);
      if (!knocked && hand.worstCard().points() < 8 && changeLowestCard && noBadinBadSuit && ((hand.anyUnder(6) && counter <2) || (counter >= 2 && hand.anyUnder(5)))) choice = hand.worstCard();
      else choice = hand.worstCardAndSuit();
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
      System.out.println("I choose to play card nr. " + (hand.getIndex(chosen)+1) + ": " + chosen.show() );
   }


   // prints bot's hand. Override: will print hidden cards.
   @Override
   public void printHand(){
      int handSize = hand.size();
      System.out.print(comReply + name + "'s hand: ");
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
   public boolean shouldKnock(int gameTurn, boolean knocked, Cards hand){
      boolean earlyGame, midGame, lateGame, neverKnock;
      int lowerTurn, midTurn, lateTurn;
      lowerTurn = 3; midTurn = 8; lateTurn = 12;
      earlyGame  = gameTurn < lowerTurn &&                          hand.maxPoints() > areaOfSurprise(25);
      midGame    = gameTurn > lowerTurn && gameTurn <= midTurn   && hand.maxPoints() > areaOfSurprise(26);
      lateGame   = gameTurn > midTurn   && gameTurn <= lateTurn  && hand.maxPoints() > areaOfSurprise(28);
      neverKnock = gameTurn > lateTurn;
      if ( !knocked && (earlyGame && gameTurn > 1 || midGame || lateGame  || hand.maxPoints() >= 30)){

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

   // dice roll
   @Override
   public int dieRoll(){
      return (int)(Math.random()*6+1);
   }

   // How bot takes their turn
   @Override
   public void drawTurn(Pile discard, Pile stock, boolean knocked, int gameTurn){
      printHand();
      if (shouldKnock(gameTurn, knocked, hand)){
         knock = true;
      } else {
         waiting(3);
         Pile pileChoice = choosePile(discard, stock, knocked, gameTurn);
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
      Card chosenCard = chooseCard(knocked);
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
