import java.util.*;

public class Output {
   String reply;
   String[][] keywords; // keywords that trigger this particular output
   Output[] possibleOutputs;
   //ArrayList<Output> possibleOutputs;
   Output defaultOutput; // = new Output("starting the game");
   String[][] defaultKeywords;



   public Output(String reply){
      this.reply = reply;
      setDefaultKeywords();
   }

   // standard answers that work for each output -> start game, method
   // are checked first (before keywords for other outputs) in getNext()
   // setting the default keywords that always lead to starting the game
   public void setDefaultKeywords() {
      String trigger1[] = {"start", "game"}; // more default triggers can be defined here
      String defaultKeywords[][] = {trigger1};
   }

   // setKeyword("fine", "good")
   public void setKeyword(String[][] keywords) {
      this.keywords = keywords;
   }

   public String[][] getKeywords() {
      return this.keywords;
   }

   // set possible outputs (method) - Output[] as input for this method
   // define the default Output, then the other possible Outputs
   // in getNext() check for default Output first, then the other Outputs
   public void setPossibleOutputs(Output[] possibleOutputs) {
      defaultOutput = new Output("starting the game");
      this.possibleOutputs = possibleOutputs;
   }

/*
   public Output setReply(String triggers, Output reply){
      // if keyword.... choose output
      return null;
   }
*/

   // get next output based on player's input
   // f.ex. getNext("yes")
   public Output getNext(String input){
      Output next = new Output("I'm confused.");

      // convert input sentence to String[]
      String[] splitInput = Main.split(input); // maybe we can split it in Main instead or move the method to this class
      System.out.println(Arrays.toString(splitInput));

      // check first if player wants to start game (default Output)
      if (containsTrigger(defaultKeywords, splitInput)) {
         return defaultOutput;
      }
      // if player doesn't want to start the game yet, get next output
      for (Output r : possibleOutputs) {
         if (containsTrigger(r.getKeywords(), splitInput)) {
            System.out.println("worked");
            return r;
         }
      }
      return next;
   }

   // contains method: checks if at least one of the trigger options is contained
   public boolean containsTrigger(String[][] triggers, String[] input) {
      List<String> list = Arrays.asList(input);
      for (String[] i : triggers) {
         int counter = 0;
         for (String j : i) {
            if (list.contains(j)) {
               counter++;
            }
         }
         if (i.length == counter) {
            return true;
         }
      }
      return false;
   }

   public void print() {
      System.out.println(this.reply);
   }
}
