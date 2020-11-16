

public class Hand extends Cards{

   public Hand(String label){
      super(label);
   }

   public Hand(){

   }

   // simply draw
   public Cards draw(Pile pile){
      return pile.deal(this, 1, pile.lastCard());
   }

   //simple plays
   public void play(Pile pile, int index){
      deal(pile, 1, index-1);
   }



   // deal starting hand
   public void starter(Pile pile, int amount){
      clear();
      pile.deal(this, amount, (pile.lastCard()));
   }

   public void printWorstCard(){
      System.out.print(worstCard().show() );
   }

   // prints hand
   public void printHand(){
      for (int i = 0; i < size(); i++){
         print(getCard(i).show() );
         if (i<size()-1){
            System.out.print(" - ");
         }
      }
      println();
   }






}
