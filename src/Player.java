import java.util.Scanner;

public class Player{
   private String comReply,name;
   private boolean isUser, knock, unicode;;
   Hand hand;
   GameOutput whichCard, whichPile, knockedCon, stopped, lastTurn, errCard, errPile;
   GameOutput discard, stock, cardNr, knocked;
   GameOutput card1, card2, card3, card4;
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
     //knockedCon.setKeyword(a("no"), a("continue"), a("not", "stop"));
     stopped.setKeyword(a("stop"));
     stopped.setNotKeywords(a("don't", "stop"));
     card1.setKeyword(a("1"));
     card2.setKeyword(a("2"));
     card3.setKeyword(a("3"));
     card4.setKeyword(a("4"));
     knocked.setPossibleOutputs(knockedCon, stopped);
     whichPile.setErrOutput(errPile);
     whichPile.setPossibleOutputs(discard, stock, knocked);
     whichCard.setPossibleOutputs(card1, card2, card3, card4);
     whichCard.setErrOutput(errCard);
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
      whichPile(discard, stock, gameTurn);
   }
   public void playTurn(Pile discard, boolean knocked){
         whichCard(discard);
   }

   // asks user to choose pile
   public boolean whichPile(Pile discard, Pile stock, int gameTurn){
      Output output = useOutput(whichPile);
      Pile drawn;
      drawn = null;

      if (output == this.knocked) {
         if (gameTurn <= 1) {
            knocked.setReply("You cannot knock yet. Wait until your next turn");
            knocked.print();
            output = useOutput(whichPile);
         } else knock = true;
      }

      if (output == this.discard) drawn = discard;
      else if (output == this.stock) drawn = stock;
      if (drawn != null) hand.draw(drawn);         // and then what?
      return knock;
   }
   public void    whichCard(Pile discard){
      waiting(1);int playAnswer;

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
   public Output useOutput(Output output){                     // output = current output
      Output firstOut = output.copy();                         // creates a copy of the first output
      Output[] firstOutPoss = output.getPossibleOutputs();     // gets the possible outputs of the first output
      Output prevOutput;                                       // previous output (?)
      do {
         output.print();                                       // prints the output
         String input = readString();                          // reads player's input
         prevOutput = output.copy();                           // saves current output as previous output
         output = output.getNext(input);                       // gets the next output based on player's input
         output.setPossibleOutputs(firstOutPoss);              // sets the output's possible outputs to the possible outputs of the first output (?)
         System.out.println(firstOut.isInPossibleOutputs(output));   // prints if the output is in the possible outputs of the first output (?)
      } while (!output.equals(prevOutput.getErrOutput()) && !firstOut.isInPossibleOutputs(output));   // loop while the output is not the error output of the previous output
                                                                                                      // and the output is not in the possible outputs of the first output
      return output;
   }
   private static String readString() {
      Scanner in = new Scanner(System.in);
      return in.nextLine();
   }

}
