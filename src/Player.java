

public class Player {
   private String name;
   Hand hand;


   //BotChoices bot = new BotChoices();
   //BotChoices bot;

   public Player(String name){
      this.name = name;
      this.hand = new Hand(name);
      //pileCard = discard.getCard(discard.lastCard());
      //HD.appendCards(hand, pileCard);
      //bot = new BotChoices();
   }

   public Cards draw(Pile pile){
      return hand.draw(pile);
   }

   public void play(Pile pile, int cardNr){
      hand.play(pile, cardNr);
   }

   public void printHand(){
      hand.printHand();
   }

   public void botsHand(){
      int handSize = hand.size();
      System.out.print(this.name + "'s hand: ");
      for (int i = 0; i < handSize; i++){
         System.out.print("|x|");
         if (i<handSize-1){
            System.out.print(" - ");
         }
      }
      System.out.println();
   }

   public Pile choosePile(Pile discard, Pile stock){
      Card pileCard = discard.topCard();
      Pile choice = stock; Cards HD = new Cards(); HD.appendCards(hand, pileCard);
      boolean justBetter           = HD.maxPoints()    > (hand.maxPoints());
      boolean notBadCard           = pileCard.points() > 5;    //hand.worstCardAndSuit().points();
      boolean bestSuit_OKCards     = HD.bestGroup().anyOver(9);
      boolean improveHand          = HD.maxPoints()    < hand.minPoints();
      boolean discCard_GT_minPoint = pileCard.points() > hand.minPoints();
      boolean pileCard_GT_8        = pileCard.points() > 8;
      boolean pileCard_GT_10       = pileCard.points() >= 10;
      boolean badCardGoodSuit      = hand.bestGroup().worstCard().points() < 4;

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
      return hand.worstCardAndSuit();
   }

   public int indexCard(){
      return hand.getIndex(ChooseCard())+1;
   }

   public boolean blitz(){
      return (hand.maxPoints() >= 31 && !hand.bestGroup().anyUnder(10) && hand.bestGroup().anyOver(10));

   }

   public boolean knock(){
      boolean result = true;
      return result;
   }

   public void knocked(){
      System.out.println();
      System.out.println(name + " knocked");
      System.out.println();
   }

   // ========= Eliza smartness ==========



}
