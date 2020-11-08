import java.util.*;


public class Deck extends Cards{

   //public Cards cards;
/*
   public Deck (int n){
      this.cards = new Cards();
   }
*/
   public Deck(String label){
      super(label);
      int i = 0;
      for (int suit = 0; suit < 4; suit++){
         for (int rank = 1; rank < 14; rank++) {
            Card card = new Card(rank,suit);
            addCard(card);
            i++;
         }
      }
   }



   public void printDeck(){
      printCards();

   }




}
