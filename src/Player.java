import java.util.Scanner;

public class Player{
   private String comReply,name;
   private boolean isUser, knock, unicode;;
   Hand hand;
   GameOutput whichCard, whichPile, knockedCon, stopped, lastTurn, errCard, errPile;
   GameOutput discard, stock, cardNr, knocked;
   GameOutput card1, card2, card3, card4;
   Output cannotKnock;
   boolean stop;

   public Player(String name){
      stop = false;
      this.name = name;
      this.hand = new Hand();
      this.knock = false;
      this.isUser = true;
      this.comReply = (char) 0x2B9A + " ";
      unicode = true;
      whichCard = new GameOutput("Which card from the left do you want to play?");
      whichPile = new GameOutput("Which pile do you want to draw from?");
      discard = new GameOutput("disc");
      stock = new GameOutput("stock");
      stopped = new GameOutput("Okay, let's stop them");
      knocked = new GameOutput("");
      cannotKnock = new Output("You can only knock before you draw. Wait until next turn.");
      lastTurn = new GameOutput("This is your last turn. Choose wisely");
      errPile = new GameOutput("You need to tell whether you want to draw from the discard pile or the stock pile.");
      errCard = new GameOutput("You need to tell me which card you want to play. So if you want to play the card most to the left, you write \"1\"");
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
     cannotKnock.setKeyword(a("knock"), a("knocked"));
     //knockedCon.setKeyword(a("no"), a("continue"), a("not", "stop"));
     stopped.setKeyword(a("stop"));
     stopped.setNotKeywords(a("don't", "stop"));
     card1.setKeyword(a("1"));
     card2.setKeyword(a("2"));
     card3.setKeyword(a("3"));
     card4.setKeyword(a("4"));
     knocked.setPossibleOutputs(knockedCon, stopped);
     whichPile.setErrOutput(errPile);
     whichPile.setPossibleOutputs(discard, stock, knocked, stopped);
     whichCard.setPossibleOutputs(card1, card2, card3, card4, cannotKnock, stopped);
     whichCard.setErrOutput(errCard);
     cannotKnock.setPossibleOutputs(whichCard);
   }


   public Player (){
      this.hand = new Hand();
      this.knock = false;
      this.isUser = true;
   }



   // getters and setters
   public boolean getStop(){
      return stop;
   }
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
      //waiting(1);
      if (knocked) lastTurn.print();
      Output o = useOutput(whichPile);
      Pile drawn = null;
      while (o != stopped) {
         if (o == this.knocked) {
            if (gameTurn <= 1) {
               while (o == this.knocked) {
                  this.knocked.setReply("You cannot knock yet. Wait until your next turn");
                  this.knocked.print();
                  o = useOutput(whichPile);
               }
            } else knock = true;
         }
         if (o == this.discard) drawn = discard;
         else if (o == this.stock) drawn = stock;
         if (drawn != null) hand.draw(drawn);         // and then what?
      }
      stop = true;
   }
   public void playTurn(Pile discard, boolean knocked){
      int playAnswer;
      waiting(1);
      Output o = useOutput(whichCard);
      while (!(o == card1 || o == card2 || o == card3 || o==card4)) {
         if (o == cannotKnock) {
            cannotKnock.print();
            o = useOutput(whichCard);
         }
         if (o == stopped) {
            stop = true;
            return;
         }
      } playAnswer = Integer.parseInt(o.getKeyword(0, 0));


      hand.play(discard,playAnswer);
   }

   // asks user to choose pile
   public boolean whichPile(Pile discard, Pile stock, int gameTurn){
      Output o = useOutput(whichPile);
      Pile drawn;
      drawn = null;

      while (o != stopped) {
         if (o == this.knocked) {
            if (gameTurn <= 1) {
               while (o == knocked) {
                  knocked.setReply("You cannot knock yet. Wait until your next turn");
                  knocked.print();
                  o = useOutput(whichPile);
               }
            } else knock = true;
         }
         if (o == this.discard) drawn = discard;
         else if (o == this.stock) drawn = stock;
         if (drawn != null) hand.draw(drawn);         // and then what?
         return knock;
      }
      stop = true;
      return stop;
   }
   public void    whichCard(Pile discard){
      waiting(1);int playAnswer = 1;

      Output o = useOutput(whichCard);
      while (!(o == card1 || o == card2 || o == card3 || o==card4)) {
         if (o == cannotKnock) {
            cannotKnock.print();
            o = useOutput(whichCard);
         }
         if (o == stopped) {
            stop = true;
            return;
         }
      } playAnswer = Integer.parseInt(o.getKeyword(0, 0));


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
      return (hand.maxPoints() >= 31 && !hand.bestGroup().anyUnder(10) && hand.bestGroup().anyOver(10));
      //return (hand.maxPoints() >= 9);
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
   public Output useOutput(Output output){
      Output firstOut = output.copy();
      Output[] firstOutPoss = output.getPossibleOutputs();
      Output prevOutput;
      do {
         output.print();
         String input = readString();
         prevOutput = output.copy();
         output = output.getNext(input);
         output.setPossibleOutputs(firstOutPoss);
         System.out.println(firstOut.isInPossibleOutputs(output));
      } while (!output.equals(prevOutput.getErrOutput()) && !firstOut.isInPossibleOutputs(output));
      return output;
   }
   private static String readString() {
      Scanner in = new Scanner(System.in);
      return in.nextLine();
   }

}
