

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
         System.out.print(getCard(i).show() );
         if (i<size()-1){
            System.out.print(" - ");
         }
      }
      System.out.println();
   }

   public void play(Pile pile, int index){
      this.deal(pile, 1, index-1);
   }





/*
   public int maxPoints(){
      int maxP = 0;
      int clubP, diamP, hearP, spadP;
      clubP = findGroup(clubs).points();
      diamP = findGroup(diamond).points();
      hearP = findGroup(hearts).points();
      spadP = findGroup(spades).points();


      if (clubP > maxP) maxP = clubP;
      if (diamP > maxP) maxP = diamP;
      if (hearP > maxP) maxP = hearP;
      if (spadP > maxP) maxP = spadP;

      System.out.println(maxP);
      return maxP;
   }

 */
      /*
      for (int i = 1; i < size(); i++){
         if (getCard(i).getSuit() == getCard(i-1).getSuit()){
            maxP += getPoint(i);
         } else if (getPoint(i) > getPoint(i-1))
            maxP = getPoint(i);


         if (getCard(i) == )
         sum += getCard(i).points();
        }
       */





}
