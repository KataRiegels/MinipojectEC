


public class BotChoices extends Game {
   //Player Eliza;
   //Pile discard;
   //Pile stock;
   //Card pileCard;
   //Pile choice = stock;
   Cards HD;
   Hand h;


   public BotChoices(){
      Eliza = new Player("Eliza");
      h = Eliza.hand;
      Card pileCard = discard.getCard(discard.lastCard());
      HD.appendCards(Eliza.hand, pileCard);
   }

   public boolean justBetter(){
      if (HD.maxPoints() > h.maxPoints()) return true;
      return false;
   }

   public Pile choosePile(Pile discards, Pile stocks){
      Card pileCard = discard.topCard();
      Pile choice = stock; Cards HD = new Cards(); HD.appendCards(h, pileCard);
      boolean justBetter           = HD.maxPoints()    > (h.maxPoints());
      boolean notBadCard           = pileCard.points() > 5;    //hand.worstCardAndSuit().points();
      boolean bestSuit_OKCards     = HD.bestGroup().anyOver(9);
      boolean improveHand          = HD.maxPoints()    < h.minPoints();
      boolean discCard_GT_minPoint = pileCard.points() > h.minPoints();
      boolean pileCard_GT_8        = pileCard.points() > 8;
      boolean pileCard_GT_10       = pileCard.points() >= 10;
      boolean badCardGoodSuit      = h.bestGroup().worstCard().points() < 4;

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
      System.out.println("Eliza plays card nr. " + (indexCard()) + ": " + ChooseCard().show() );
   }

   public void announceDraw(Pile discard,Pile stock){
      System.out.println();
      System.out.println("Eliza draws from the " + choosePile(discard, stock).label + " pile.");
   }

   public Card ChooseCard(){
      //Card choice = new Card();
      return h.worstCardAndSuit();
   }

   public int indexCard(){
      return h.getIndex(ChooseCard())+1;
   }



}
