

public class Pile extends Cards{


   public Pile(String label){
      super(label);
   }

   public void createStock(Cards deck){
      deck.deal(this, deck.size(), 0);
      //return pile;
   }

   public void turnCard(Pile pile){
      pile.deal(this, 1, 0);
   }

   public void printTop(){
      System.out.print("Discard Pile: ");
      if (isEmpty()) System.out.println("|x|");
      else System.out.println(topCard().show());
   }

   public Card topCard(){
      return getCard(lastCard());
   }

   public void printAll(){
      System.out.print("Top card on discard pile: ");
      this.topCard();
   }

}
