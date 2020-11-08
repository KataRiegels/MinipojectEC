


public class Bot extends Player {

   public Bot(String name){
      super(name);
   }


   public Pile choosePile(Pile discard, Pile stock){
      Card pileCard = discard.topCard();
      Pile choice = stock; Cards HD = new Cards(); HD.appendCards(hand, pileCard);
      boolean justBetter, notBadCard, bestSuit_OKCards, improveHand,discCard_GT_minPoint, pileCard_GT_8, pileCard_GT_10, badCardGoodSuit;
      justBetter           = HD.maxPoints()    > (hand.maxPoints());
      notBadCard           = pileCard.points() > 5;    //hand.worstCardAndSuit().points();
      bestSuit_OKCards     = HD.bestGroup().anyOver(9);
      improveHand          = HD.maxPoints()    < hand.minPoints();
      discCard_GT_minPoint = pileCard.points() > hand.minPoints();
      pileCard_GT_8        = pileCard.points() > 8;
      pileCard_GT_10       = pileCard.points() >= 10;
      badCardGoodSuit      = hand.bestGroup().worstCard().points() < 4;

      if (justBetter && notBadCard
              && (( bestSuit_OKCards || improveHand)
              || (discCard_GT_minPoint && pileCard_GT_8)
              || (pileCard_GT_10 && badCardGoodSuit)

      )
      ){ choice = discard;
      }
      return choice;
   }

   public void announcePlay(){
      System.out.println();
      System.out.println(name + "plays card nr. " + (indexCard()) + ": " + chooseCard().show() );
   }

   public void announceDraw(Pile discard, Pile stock){
      System.out.println();
      System.out.println("Liza draws from the " + choosePile(discard, stock).label + " pile.");
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
   public void drawTurn(Pile discard, Pile stock){
      System.out.println(name + "'s turn!");
      printHand();
      announceDraw(discard, stock);
      choosePile(discard, stock);
      printHand();
   }

   @Override
   public void playTurn(Pile discard){
      announcePlay();
      play(discard, indexCard());
   }


}
