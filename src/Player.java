import java.util.Scanner;

public class Player{
   private String comReply,name;
   private boolean isUser, knock, unicode;;
   Hand hand;
   GameOutput whichCard, whichPile;
   GameOutput discard, stock, cardNr, knocked;
   GameOutput card1, card2, card3, card4;

   public Player(String name){
      this.name = name;
      this.hand = new Hand();
      this.knock = false;
      this.isUser = true;
      this.comReply = (char) 0x2B9A + " ";
      unicode = true;
      whichCard = new GameOutput("Which card from the left do you want to play?");
      whichPile = new GameOutput("Which pile do you want to draw from?");
      discard = new GameOutput();
      stock = new GameOutput();
      knocked = new GameOutput("Oh no, you knocked. Let's see if I can beat you");
      card1 = new GameOutput();
      card2 = new GameOutput();
      card3 = new GameOutput();
      card4 = new GameOutput();
      setOutputs();
   }

   public void setOutputs(){
     whichCard.setNotKeywords();
     discard.setKeyword(a("discard"), a("open"));
     stock.setKeyword(a("stock"),a("closed"));
     knocked.setKeyword(a("knock"), a("knocked"));
     card1.setKeyword(a("1"));
     card2.setKeyword(a("2"));
     card3.setKeyword(a("3"));
     card4.setKeyword(a("4"));

     whichPile.setPossibleOutputs(discard, stock, knocked);
     whichCard.setPossibleOutputs(card1, card2, card3, card4);


   }


   public Player (){
      this.hand = new Hand();
      this.knock = false;
      this.isUser = true;
   }

   // getters and setters
   public String getName(){
      return name;
   }
   public void   setName(String name){
      this.name = name;
   }
   public void   setComReply(char c){
      comReply = c + " ";
   }
   public void   setUnicode(boolean r){
      unicode = r;
   }

   // what happens if player is the who to has to draw
   public void drawTurn(Pile discard, Pile stock, boolean knocked, int gameTurn){
      waiting(1);
      if (knocked) System.out.println("This is your last turn. Choose wisely");


      int patienceTaken = 0;

      while (whichPile(discard, stock) && gameTurn <= 2){
         waiting(1);
         //System.out.println("You cannot knock on the first turn! Finish your turn and wait until next turn.");
         knock = false;
         if (patienceTaken < 2) System.out.println("You cannot knock on the first turn! Finish your turn and wait until next turn.");
         if (patienceTaken > 2){
            System.out.println("Okay, now you are just annoying.. Do you want to stop, or what?");
            if ((new Scanner(System.in).nextLine().contains("no"))){
               waiting(1);
               System.out.println("Ok, good, let's continue");
            }
         }
         else if (patienceTaken > 1) System.out.println("I said you cannot knock yet");
         patienceTaken ++;
         waiting(1);
      }
   }
   public void playTurn(Pile discard, boolean knocked){
         whichCard(discard);
   }

   // asks user to choose pile
   public boolean whichPile(Pile discard, Pile stock){
      //Scanner in = new Scanner(System.in);
      //System.out.println();
      //System.out.println(comReply + "Do you want to draw from stock pile or discard pile?");

      Output output = useOutput(whichPile);

      Pile drawn;
      drawn = null;

      if      (output == this.discard) drawn = discard;
      else if (output == this.stock) drawn = stock;
      else if (output == this.knocked) knock = true;
      if (drawn != null) hand.draw(drawn);         // and then what?
      return knock;
   }
   public void    whichCard(Pile discard){
      waiting(1);int playAnswer;
      //System.out.println("\nWhich card number from left do you want to play?"); //playing card 0 means write 1!!
      //Scanner in = new Scanner(System.in);
      //Needs something for if player tries to knock..
      //int drawAnswer = in.nextInt();
      Output o = useOutput(whichCard);
      if (o == card1 || o == card2 || o == card3 || o==card4){
         playAnswer = Integer.parseInt(o.getKeyword(0,0));
      } else return;

      hand.play(discard,playAnswer);
   }


   // die roll
   public int dieRoll(){
      return (int)(Math.random()*6+1);
   }

   // registers if player has blitz (31 points)
   public boolean hasKnocked(){
      return knock;
   }
   public boolean blitz(){
      //return (hand.maxPoints() >= 31 && !hand.bestGroup().anyUnder(10) && hand.bestGroup().anyOver(10));
      return (hand.maxPoints() >= 9);
   }
   public boolean isUser(){
      return isUser;
   }

   // prints hand
   public void printHand(){
      hand.printHand();
   }
   public void printOpen(){
      hand.printHand();
   }

   //waiters
   public void waitingMilSec(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }


   }
   public void waiting(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }

   // move? its look for cases kind of thing
   public int lookForCase(){
      Scanner in = new Scanner(System.in);
      String drawAnswer = in.nextLine();

      if (drawAnswer.contains("discard")) return 1;
      if (drawAnswer.contains("stock")) return 0;
      if (drawAnswer.contains("knock"))   return 2;
      return -1;
   }

   // printers
   public void print(String string){
      System.out.print(string);
   }
   public void println(String string){
      System.out.println(string);
   }
   public void println(){
      System.out.println();
   }

   public Output[] a(Output... outputs){
      Output[] a = new Output[outputs.length];
      for (int i = 0; i < outputs.length; i++){
         a[i] = outputs[i];
      }
      return a;
   }
   public void aa(Output... outputs){

   }
   public String[] a(String... strings){
      String[] a = new String[strings.length];
      for (int i = 0; i < strings.length; i++){
         a[i] = strings[i];
      }
      return a;
   }
   public String[][] a(String[]... stringss){
      String[][] a = new String[stringss.length][];
      for (int i = 0; i < stringss.length; i++){
         a[i] = stringss[i];
      }
      return a;
   }
   public Output useOutput(Output welcome){
      welcome.print();
      String input = readString();

      welcome = welcome.getNext(input);
      return welcome;
      //welcome.print();
   }
   private static String readString() {
      Scanner in = new Scanner(System.in);
      return in.nextLine();
   }


}
