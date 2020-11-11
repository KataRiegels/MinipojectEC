

public class Hand extends Cards{

   public Hand(String label){
      super(label);
   }

   public Cards draw(Pile pile){
      Cards drawn = pile.deal(this, 1, pile.lastCard());
      return drawn;
   }

   public void starter(Pile pile){
      pile.deal(this, 3, 0);
   }

   public void printWorstCard(){
      System.out.print(worstCard().show() );
   }

   public void printHand(){
      for (int i = 0; i < size(); i++){
         print(getCard(i).show() );
         if (i<size()-1){
            System.out.print(" - ");
         }
      }
      println();
   }

   public void play(Pile pile, int index){
      this.deal(pile, 1, index-1);
   }






}
