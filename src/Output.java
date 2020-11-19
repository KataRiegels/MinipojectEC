import java.util.*;

public class Output {
   String reply;
   String[][] keywords; // keywords that trigger this particular output
   Output[] possibleOutputs;
   //ArrayList<Output> possibleOutputs;
   Output defaultOutput = new Output("starting the game");
   String[][] defaultKeywords;



   public Output(String reply){
      this.reply = reply;
      //defaultOutput = new Output("starting game");
      defaultKeywords = setDefaultKeywords();
      defaultOutput.setKeyword(defaultKeywords);
   }

   // setting the default keywords that always lead to starting the game
   public String[][] setDefaultKeywords() {  // more default triggers can be defined here
      String trigger1[] = {"start", "game"};
      String triggers[][] = {trigger1};
      return triggers;
   }

   // nice.

   // setKeyword("fine", "good")

   // standard answers that work for each output -> start game, method
   // should be checked first -> method that gives boolean


   public void setKeyword(String[][] keywords) {
      this.keywords = keywords;
   }

   public String[][] getKeywords() {
      return this.keywords;
   }

   // set possible outputs (method) - Output[] as input for this method
   // -> use in getNext
   // add the default Output, then the other possible Outputs
   // OR: in getNext check for default Output first, then the other Outputs
   public void setPossibleOutputs(Output[] possibleOutputs) {
      //this.possibleOutputs = new Output[possibleOutputs.length+1
      //this.possibleOutputs.add(defaultOutput);
      this.possibleOutputs = possibleOutputs;
   }

/*
   public Output setReply(String triggers, Output reply){
      // if keyword.... choose output

      return null;
   }
*/
   // getNext("yes")
   public Output getNext(String input){
      Output next = new Output("I'm confused.");

      // convert input sentence to String[]
      String[] splitInput = Main.split(input); // maybe we can split it in Main instead or move the method to this class
      System.out.println(Arrays.toString(splitInput));

      /*
      // check for trigger -> needs access to keywords of other Outputs
      for (Output r : possibleReplies) {     // loop through all possible replies
         String[][] keys = r.getKeywords();  // get keywords for each reply
         System.out.println(1);
         for (String[] k : keys) {           // loop through keywords
            System.out.println(Arrays.toString(k));
            System.out.println(2);
            // contains method should be in here somehow
            // compare the words (strings) instead of the array
            // check every string in k, all the keywords need to be contained
            if (k.equals(splitInput)) {      // compare input to keywords
               next = r;                     // find reply -> doesn't work for some reason :/
               System.out.println(3);
               return next;
            }
         }
      }


      ArrayList<String> contained = new ArrayList<>();
      for (Output o : this.possibleOutputs) {   // loop through possible Outputs
         String[][] triggers = o.getKeywords();
         String[] t = triggers[0];
         // contains method that checks which keywords are contained
         // compare each string in input array to each string in t
         for (int i = 0; i < t.length; i++) {
            for (String word : splitInput) {
               if (word.equals(t[i])) {
                  contained.add(t[i]);
               }
            }
         }
      }
      for (String c : contained) {
         System.out.println(c);
      }

      // tests
      System.out.println();
      // return new Output
      return next;
       */


      // check first if player wants to start game
      if (containsTrigger(defaultKeywords, splitInput)) {
         return defaultOutput;
      }

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
