

public class Pile extends Cards{


   public Pile(String label){
      super(label);
   }


   // Creates the stock from the given deck
   public void createStock(Cards deck){
      clear();
      deck.deal(this, deck.size(), 0);
   }

   // Turns top card of this and puts it on pile
   public void turnCard(Pile pile){
      clear();
      pile.deal(this, 1, 0);
   }

   // Prints top cards of this
   public void printTop(){
      print((char) 0x2B9A + " Discard pile: ");
      if (isEmpty()) println("|x|");
      else println(topCard().show());
   }

   // returns topCard of this
   public Card topCard(){
      return getCard(lastCard());
   }

}
