


public class BotChoices {
   Player Eliza;
   Pile discard;
   Pile stock;
   Card pileCard;
   //Pile choice = stock;
   Cards HD;
   Hand hand;


   public BotChoices(){
      Eliza = new Player("Eliza");
      hand = Eliza.hand;
      pileCard = discard.getCard(discard.lastCard());
      HD.appendCards(Eliza.hand, pileCard);
   }

   public boolean justBetter(){
      if (HD.maxPoints() > hand.maxPoints()) return true;
      return false;
   }





}
