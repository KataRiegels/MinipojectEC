import java.util.*;


public class Card {
   private int rank;
   private int suit;

   public Card(){

   }

   public Card(int rank, int suit){
      this.rank = rank;
      this.suit = suit;
   }

   public int getSuit(){
      return suit;
   }

   public int getRank() {
      return rank;
   }

   public String name(){
      String[] str_rank = {null, "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
      //String[] str_suit = {"Clubs", "Diamonds", "Hearts", "Spades"};
      char[] str_suit = {(char)0x2660, (char)1, (char)1, (char)1};
      //str_suit = {(char)0x2660,}
      String cardName = (str_rank[this.rank] + " of " + str_suit[this.suit]);

      return cardName;
   }

   public String show(){
      String[] str_rank = {null, "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
      char[] str_suit = {(char)0x2660, (char)0x2661, (char)0x2662, (char)0x2663};
      String cardName = ("|" + str_suit[this.suit] + str_rank[this.rank] + "|");
      return cardName;
   }


   public boolean equals(Card c1){
      boolean result = false;
      if (this.rank == c1.rank && this.suit == c1.suit) result = true;
      return result;
   }

   public int points() {
      int points;
      int r = this.rank;
      if (2 <= r && r <= 9) {
         return points = r;
      } else if (10 <= r && r <= 13) {
         return points = 10;
      } else if (r == 1) {
         return points = 11;
      } else {
         return -1;                             //Fix error
      }
   }

   public boolean over10(){
      return (points() >= 10);
   }

}
