

public class Pile extends Cards{


   public Pile(String label){
      super(label);
   }



   // Creates the stock from the given deck
   public void createStock(Cards deck){

      clear();
      deck.deal(this, deck.size(), 0);
   }
   public void createStock(){
      clear();
      for (int suit = 0; suit < 4; suit++){
         for (int rank = 1; rank < 14; rank++) {
            Card card = new Card(rank,suit);
            this.addCard(card);
         }
      }
   }


   // Turns top card of this and puts it on pile
   public void turnCard(Pile pile){
      clear();
      pile.deal(this, 1, 0);
   }

   // Prints top cards of this
   public void printTop(){
      print("Discard pile: ");
      if (isEmpty()) println("|X|");
      else println(showCard(topCard()));
   }

   // returns topCard of this
   public Card topCard(){
      return getCard(size()-1);
   }

}
