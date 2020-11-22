import java.util.*;

public class Cards {
   //private Card[] cards = null;
   private int clubs = 0, diamond = 1, hearts = 2, spades = 3;
   private boolean uni;
   //Cards cs;
   private String label;
   private ArrayList<Card> cards; // = new ArrayList<Card>(); //new ArrayList<Card>();

   // Constructors
   public Cards() {
      this.cards = new ArrayList<>();
   }
   public Cards(String label) {
      cards = new ArrayList<>();
      uni = true;
      this.label = label;
   }

   // Getters and setters
   public void   setUni(boolean b){
      uni = b;
   }
   public void   setCard(int i, Card card){
      cards.set(i,card);
   }
   public int    getIndex(Card card){
      return cards.indexOf(card);
   }
   public Card   getCard(int i){
      return cards.get(i);
   }
   public String getLabel() {
      return label;
   }


   // the typical ArrayList functions
   public void    appendCards(Cards c1, Card c2){
      appendCards(c1);
      addCard(c2);
   }
   public void    appendCards(Cards c){
      for (int i = 0; i < c.size(); i++){
         cards.add(c.getCard(i));
      }
   }
   public void    addCard(Card c){
      cards.add(c);
   }
   public Card    takeCard(int i){
      return cards.remove(i);
   }
   public void    swapCards(int card1, int card2){
      Card c2 = getCard(card2);
      setCard(card2, getCard(card1));
      setCard(card1, c2);
   }
   public Cards   removeCard(Card card){
      cards.remove(getIndex(card));
      return this;
   }
   public int     size(){
      return cards.size();
   }
   public void    clear(){
      while(!isEmpty()){
         cards.remove(0);
      }
   }
   public boolean isEmpty(){
      return (size() == 0);
   }

   // takes "amount" card from this.cards and adds to cs cards
   public Cards deal(Cards receiver, int amount, int cardIndex){ //cardIndex is where form the cards. you take. it is 0 if top of a pile
      Cards cs = new Cards();
      for (int i = 0; i < amount; i++){
         Card card = takeCard(cardIndex);
         if (cardIndex == size()) cardIndex -= 1;
         cs.addCard(card);
         receiver.addCard(card);
      }
      return cs;
   }
   public void  shuffle(){
      Random r = new Random();
      for (int i = 0; i <size(); i++){
         swapCards(i, r.nextInt(size()));
      }
   }


   // Methods that concerns grouping and searching cards depending on their suit and points.
   public Cards   findGroup(int suit){
      Cards group = new Cards();
      for (int i = 0; i < size(); i++){
         if (getCard(i).getSuit() == suit) {
            group.addCard(getCard(i));
         }
      }
      return group;
   }                                // Return: Cards of chosen suit.
   public Cards   bestGroup(){
      return findGroup(bestSuit());
   }           // group of cards with the best suit
   public Cards   worstGroup(){
      return findGroup(worstSuit());
   }
   public Card    worstCard(){
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
   public int     bestSuit(){
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
   public int     worstSuit(){
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

   //public Card  worstCardAndSuit(){
   //return worstGroup().worstCard();
   //}


   // for finding points
   public int points() {
      int maxP = 0;
      for (int i = 0; i < size(); i++) {
         maxP += getPoint(i);
      }
      return maxP;
   }
   public int getPoint(int i){
      return getCard(i).points();
   }
   public int groupPoint(int suit){
      return findGroup(suit).points();
   }
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


   // Methods primarily for piles
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
   public void turnCard(Cards pile){
      clear();
      pile.deal(this, 1, 0);
   }
   public void printTop(){
      print("Discard pile: ");
      if (isEmpty()) println("|X|");
      else println(showCard(topCard()));
   }
   public Card topCard(){
      return getCard(size()-1);
   }

   // Methods primarily for hands
   public void  starter(Cards pile, int amount){
      clear();
      pile.deal(this, amount, pile.size()-1);
   }
   public Cards draw(Cards pile){
      return pile.deal(this, 1, pile.size()-1);
   }
   public void  play(Cards pile, int index){
      deal(pile, 1, index-1);
   }
   public void  printHand(){
      for (int i = 0; i < size(); i++){
         print(showCard(getCard(i)));
         if (i<size()-1){
            print(" - ");
         }
      }
      println();
   }
   
   
   public void printCards(){
      for (int i = 0; i < size(); i++){
         print(showCard(getCard(i)) + " - ");
      }
   }
   public String showCard(Card card){
      return card.show(uni);
   }
   public void printCards(int lower, int upper){
      int i = lower;
      for (; i < upper; i++) {
         System.out.println(showCard(getCard(i)));
      }
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