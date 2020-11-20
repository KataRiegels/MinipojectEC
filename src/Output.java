import java.util.*;

public class Output {
   String reply;
   ArrayList<String> additionalDisplay;
   String[][] keywords; // keywords that trigger this particular output
   Output[] allOutputs;
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
      this.keywords = new String[0][0];
      String trigger1[] = {"start", "game"}; // more default triggers can be defined here
      ArrayList<String[]> triggers = new ArrayList<>();
      triggers.add(trigger1);
      defaultKeywords = triggers.toArray(new String[0][0]);
   }

   // setKeyword("fine", "good")
   public void setKeyword(String[][] keywords) {
      this.keywords = keywords;
   }

   public String[][] getKeywords() {
      return this.keywords;
   }

   public Output[] getPossibleOutputs() {
      return possibleOutputs;
   }

   // set possible outputs (method) - Output[] as input for this method
   // define the default Output, then the other possible Outputs
   // in getNext() check for default Output first, then the other Outputs
   /*public void setPossibleOutputs(Output[] possibleOutputs) {
      defaultOutput = new Output("starting the game");
      this.possibleOutputs = possibleOutputs;
   }*/

   // setPossibleOutputs method with undefined number of Outputs as input
   public void setPossibleOutputs(Output ... possibleOutputs) {
      defaultOutput = new Output("starting the game");

      /*if (possibleOutputs == null) {
         this.possibleOutputs = new Output[]{defaultOutput};
      } else {
         this.possibleOutputs = new Output[possibleOutputs.length];
         for (int i = 0; i < possibleOutputs.length; i++) {
            this.possibleOutputs[i] = possibleOutputs[i];
         }
      }*/

      this.possibleOutputs = new Output[possibleOutputs.length];
      for (int i = 0; i < possibleOutputs.length; i++) {
         this.possibleOutputs[i] = possibleOutputs[i];
      }
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
      Output next = new Output("I'm confused. Please clarify what you mean.");

      // convert input sentence to String[]
      String[] splitInput = Main.split(input); // maybe we can split it in Main instead or move the method to this class
      //System.out.println(Arrays.toString(splitInput));

      // check first if player wants to start game (default Output)
      if (containsTrigger(defaultKeywords, splitInput)) {
         return defaultOutput;
      }

      // if player doesn't want to start the game yet, get next output
      for (Output r : possibleOutputs) {
         // check if there are any triggers
         /*if (this.getKeywords().length == 0) {
            return r;
         }*/
         if (containsTrigger(r.getKeywords(), splitInput)) {
            //System.out.println("worked");
            return r;
         }
      }
      return next;
   }

   // contains method: checks if at least one of the trigger options is contained
   public boolean containsTrigger(String[][] triggers, String[] input) {
      List<String> list = Arrays.asList(input);
      //System.out.println("triggers: "+Arrays.toString(triggers));
      //System.out.println(Arrays.toString(list.toArray()));
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
      //System.out.println("no keywords contained");
      return false;
   }

   // method that adds/performs any actions specific to the output
   // define actions

   public void setAdditionalDisplay(String ... strings){
      additionalDisplay = new ArrayList<String>();
      for (String s : strings) {
         additionalDisplay.add(s);
      }
   }

   // copy method that copies Output object with all its attributes etc.
   public Output copy() {
      Output newOutput = new Output(this.reply);
      newOutput.setKeyword(this.getKeywords());
      newOutput.setPossibleOutputs(this.possibleOutputs);
      return newOutput;
   }

   public void print() {
      System.out.println(this.reply);
      if (additionalDisplay != null) {
         for (String s : additionalDisplay) {
            System.out.println(s);
         }
      }
   }
}
