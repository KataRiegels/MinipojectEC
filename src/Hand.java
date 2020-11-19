

public class Hand extends Cards{

   public Hand(String label){
      super(label);
   }
   public Hand(){
   }

   // deal starting hand
   public void starter(Pile pile, int amount){
      clear();
      pile.deal(this, amount, pile.size()-1);
   }

   // simply draw
   public Cards draw(Pile pile){
      return pile.deal(this, 1, pile.size()-1);
   }
   public void play(Pile pile, int index){
      deal(pile, 1, index-1);
   }


   // prints hand
   public void printHand(){
      for (int i = 0; i < size(); i++){
         print(showCard(getCard(i)));
         if (i<size()-1){
            print(" - ");
         }
      }
      println();
   }






}
