import java.util.*;

public class Cards {
   //private Card[] cards = null;
   private int clubs = 0, diamond = 1, hearts = 2, spades = 3;

   String label;
   private ArrayList<Card> cards; // = new ArrayList<Card>(); //new ArrayList<Card>();


   public Cards() {
      this.cards = new ArrayList<>();
   }

   public Cards(String label) {
      this.cards = new ArrayList<>();
      this.label = label;
   }


   // Return: Cards of chosen suit.
   public Cards findGroup(int suit){
      Cards group = new Cards();
      for (int i = 0; i < size(); i++){
         if (getCard(i).getSuit() == suit) {
            group.addCard(getCard(i));
         }
      }
      return group;
   }

   // group of cards with the best suit
   public Cards bestGroup(){
      return findGroup(bestSuit());
   }
   public Cards worstGroup(){
      return findGroup(worstSuit());
   }

   // find points of some Cards
   public int points() {
      int maxP = 0;
      for (int i = 0; i < size(); i++) {
         maxP += getPoint(i);
      }
      return maxP;
   }

   // get point for Card i from Cards.
   public int getPoint(int i){
      return getCard(i).points();
   }

   // point from suit grouped Cards.
   public int groupPoint(int suit){
      return findGroup(suit).points();
   }

   // worst/best card from Cards
   public Card worstCard(){
      Card minC = getCard(0);
      int min = 31;
      for (int i = 0; i < size(); i++){
         if (getPoint(i) < min && getPoint(i) != 0) {
            min = getPoint(i);
            minC = getCard(i);
         }
      }
      return minC;
   }
   public Card worstCardAndSuit(){
      return worstGroup().worstCard();
   }

   // max or min point from groups
   public int maxPoints(){
      int maxP = groupPoint(clubs);
      for (int i = 1; i < 4; i++){
         if (groupPoint(i) > maxP){
            maxP = groupPoint(i);
         }
      }
      return maxP;
   }
   public int minPoints(){
      int minP = groupPoint(clubs);
      for (int i = 1; i < 4; i++){
         if (groupPoint(i) < minP){
            minP = groupPoint(i);
         }
      }
      return minP;
   }

   // Best or worst suit
   public int bestSuit(){
      int suit = 0;
      int maxP = groupPoint(clubs);
      for (int i = 1; i < 4; i++){
         if (groupPoint(i) > maxP){
               maxP = groupPoint(i);
               suit = i;
         }
      }
      return suit;
   }
   public int worstSuit(){
      int suit = 0;
      int minP = 100;
      for (int i = 0; i < 4; i++){
         if (groupPoint(i) < minP && groupPoint(i)!= 0){
            minP = groupPoint(i);
            suit = i;
         }
      }
      return suit;
   }

   // returns a Cards which appends two Cards
   public void appendCards(Cards c1, Card c2){
      Cards cs = new Cards();
      appendCard(c1);
      addCard(c2);
      //return cs;
   }

   // append Cards c to this.cards
   public void appendCard(Cards c){
      for (int i = 0; i < c.size(); i++){
         cards.add(c.getCard(i));
      }
   }

   // are there any cards with rank over ... in this.cards
   public boolean anyOver(int min){
      for (int i = 0; i < size(); i++){
         if (getCard(i).points() > min) return true;
      }
      return false;
   }
   public boolean anyUnder(int max){
      for (int i = 0; i < size(); i++){
         if (getCard(i).points() < max) return true;
      }
      return false;
   }

   // return index of Card in list
   public int getIndex(Card card){
      return cards.indexOf(card);
   }


   // takes "amount" card from this.cards and adds to cs cards
   public Cards deal(Cards receiver, int amount, int cardIndex){ //cardIndex is where form the cards. you take. it is 0 if top of a pile
      Cards cs = new Cards();
      for (int i = 0; i < amount; i++){
         Card card = takeCard(cardIndex);
         cs.addCard(card);
         receiver.addCard(card);
      }
      return cs;
   }


   // set card *i* to be *card*>
   public void setCard(int i, Card card){
      cards.set(i,card);
   }

   // returns the card at index *i*
   public Card getCard(int i){
      return cards.get(i);
   }

   //index of last card in Cards
   public int lastCard(){
      return size()-1;
   }

   // add card *c* to *cards.*
   public void addCard(Card c){
      cards.add(c);
   }


   // take card at index *i* from *cards.*. Return the card that was taken.
   public Card takeCard(int i){
      return cards.remove(i);
   }

   // swaps two cards
   public void swapCards(int card1, int card2){
      Card c2 = getCard(card2);
      setCard(card2, getCard(card1));
      setCard(card1, c2);
   }

   // is Cards empty?
   public boolean isEmpty(){
      if (size() == 0) return true;
      else return false;
   }

   // prints cards
   public void printCards(){
      for (int i = 0; i < size(); i++){
         println(getCard(i).show());
      }
   }

   // prints form lower to upper
   public void printCards(int lower, int upper){
      int i = lower;
      for (; i < upper; i++) {
         System.out.println(getCard(i).show());
      }
   }

   // shuffles cards
   public void shuffle(){
      Random r = new Random();
      for (int i = 0; i <size(); i++){
         swapCards(i, r.nextInt(size()));
      }
   }

   // number of cards in Cards
   public int size(){
      return cards.size();
   }

   // prints
   public void print(String string){
      System.out.print(string);
   }
   public void println(String string){
      System.out.println(string);
   }
   public void println(){
      System.out.println();
   }
}