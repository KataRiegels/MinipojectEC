
public class Card {
   private int rank;
   private int suit;
   private String[] str_suit = new String[4];

   public Card(int rank, int suit){
      this.rank = rank;
      this.suit = suit;
      str_suit[0] = (char)0x2660+""; str_suit[1] = (char)0x2661+""; str_suit[2] = (char)0x2662+""; str_suit[3] = (char)0x2663+"";
   }

   public int getSuit(){
      return suit;
   }
   public String show(boolean uni){          // A string of the card
      String[] str_rank = {null, "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
      if (!uni) {
         str_suit[0] = "C.";
         str_suit[1] = "D.";
         str_suit[2] = "H.";
         str_suit[3] = "S.";
      }
      return ("|" + str_suit[suit] + str_rank[rank] + "|");
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
