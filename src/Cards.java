import java.util.*;

public class Cards {
   private int clubs = 0, diamond = 1, hearts = 2, spades = 3;
   private boolean uni;                      // The boolean deciding what kind of symbols to print.
   private String label;
   private ArrayList<Card> cards;

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


   public Cards  deal(Cards receiver, int amount, int cardIndex){    // Takes a card from this Cards and adds to another. Such as dealing a hand.
      Cards cs = new Cards();
      for (int i = 0; i < amount; i++){
         Card card = takeCard(cardIndex);
         if (cardIndex == size()) cardIndex -= 1;
         cs.addCard(card);
         receiver.addCard(card);
      }
      return cs;
   }
   public void   shuffle(){
      Random r = new Random();
      for (int i = 0; i <size(); i++){
         swapCards(i, r.nextInt(size()));
      }
   }
   public String showCard(Card card){           // Prints a card.
      return card.show(uni);
   }

   // Methods that concerns grouping and searching cards depending on their suit and points.
   public Cards   findGroup(int suit){          // Returns Cards of chosen suit.
      Cards group = new Cards();
      for (int i = 0; i < size(); i++){
         if (getCard(i).getSuit() == suit) {
            group.addCard(getCard(i));
         }
      }
      return group;
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
   public Cards   bestGroup(){                     // The arraylist of cards in the best suit (based on points)
      return findGroup(bestSuit());
   }
   public Cards   worstGroup(){
      return findGroup(worstSuit());
   }
   public int     bestSuit(){                       // The suits whose cards give most points.
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
   public boolean anyOver(int min){                // checks if there are any cards with points over min
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

   // for finding points
   public int points() {                               // the sum of points.
      int maxP = 0;
      for (int i = 0; i < size(); i++) {
         maxP += getPoint(i);
      }
      return maxP;
   }
   public int getPoint(int i){                        // gets the points for a specific card
      return getCard(i).points();
   }
   public int groupPoint(int suit){                      // Gets the points within a suit.
      return findGroup(suit).points();
   }
   public int maxPoints(){                                     // The maximum amount of points the Cards can achieve, where points are restricted to suits.
      int maxP = groupPoint(clubs);
      for (int i = 1; i < 4; i++){
         if (groupPoint(i) > maxP){
            maxP = groupPoint(i);
         }
      }
      return maxP;
   }

   // Methods primarily for piles
   public void createStock(Cards deck){                   // Just deals all cards from "Cards deck" to this.
      clear();
      deck.deal(this, deck.size(), 0);
   }
   public void createStock(){                                     // Creates a pile from 52 fresh cards.
      clear();
      for (int suit = 0; suit < 4; suit++){
         for (int rank = 1; rank < 14; rank++) {
            Card card = new Card(rank,suit);
            this.addCard(card);
         }
      }
   }
   public void turnCard(Cards pile){                              // Meant to deal from stock pile to discard.
      clear();
      pile.deal(this, 1, 0);
   }
   public void printTop(){                                  // To print the top card in the discard pile
      print("Discard pile: ");
      if (isEmpty()) println("|X|");
      else println(showCard(topCard()));
   }
   public Card topCard(){                                    // Gets the top (last) card.
      return getCard(size()-1);
   }

   // Methods primarily for hands
   public void  starter(Cards pile, int amount){                  // Dealing the starting hand.
      clear();
      pile.deal(this, amount, pile.size()-1);
   }
   public Cards draw(Cards pile){                                 // drawing from Cards pile and adds to this
      return pile.deal(this, 1, pile.size()-1);
   }
   public void  play(Cards pile, int index){                     // deals card nr index to Cards pile.
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