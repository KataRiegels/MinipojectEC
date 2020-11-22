import java.util.*;

public class Output {
   boolean uni;
   String reply;
   ArrayList<String> additionalDisplay;
   Output errOutput = null;
   String[][] keywords; // keywords that trigger this particular output
   Output[] allOutputs;
   String[][] notKeywords;
   Output[] possibleOutputs;
   boolean anything = false;
   //ArrayList<Output> possibleOutputs;
   Output defaultOutput; // = new Output("starting the game");
   String[][] defaultKeywords;




   public Output(String reply){
      this.reply = reply;
      //setDefaultKeywords();
      uni = true;             // This is whether we use cool symbols or not
      notKeywords = null;

   }

   public Output(){}          // this is needed for my GameOutput class

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


   public void setReply(String string){
      reply = string;
   }

   // setKeyword("fine", "good")
   public void setKeyword(String[]... keywords) {
      this.keywords = keywords;
   }

   public String[][] getKeywords() {
      return keywords;
   }

   public String getKeyword(int i, int j){
      return keywords[i][j];
   }  // keywordS


   public void setNotKeywords(String[]... notKeywords){
      this.notKeywords = notKeywords;
   }

   public String[][] getNotKeywords() {
      return notKeywords;
   }


   public void addPossibleOutput(Output possibleOutput){
         Output[] newArr = new Output[possibleOutputs.length+1];
         for (int i = 0; i < possibleOutputs.length; i++){
            newArr[i] = possibleOutputs[i];
         }
         newArr[-1] = possibleOutput;
      possibleOutputs = newArr;
   }

   public Output[] getPossibleOutputs() {
      return possibleOutputs;
   }

   public String getReply(){
      return reply;
   }


   public boolean isInPossibleOutputs(Output output){
      for (Output o : possibleOutputs){
         if (output == o) return true;
      }
      return false;
   }


   // set possible outputs (method) - Output[] as input for this method
   // define the default Output, then the other possible Outputs
   // in getNext() check for default Output first, then the other Outputs
   //public void setPossibleOutputs(Output[] possibleOutputs) {
      /*public void setPossibleOutputs(Output... possibleOutputs) {
      defaultOutput = new Output("starting the game");
      this.possibleOutputs = possibleOutputs;
   }*/

   // setPossibleOutputs method with undefined number of Outputs as input
   public void setPossibleOutputs(Output... possibleOutputs) {
      defaultOutput = new Output("starting the game");

      /*if (possibleOutputs == null) {
         this.possibleOutputs = new Output[]{defaultOutput};
      } else {
         this.possibleOutputs = new Output[possibleOutputs.length];
         for (int i = 0; i < possibleOutputs.length; i++) {
            this.possibleOutputs[i] = possibleOutputs[i];
         }
      }*/
      if (possibleOutputs != null) {
         System.out.println(possibleOutputs.length);
         Output[] temp = new Output[possibleOutputs.length];
         for (int i = 0; i < possibleOutputs.length; i++) {
            temp[i] = possibleOutputs[i];
         }
         this.possibleOutputs = temp;
      }
      /*
      this.possibleOutputs = new Output[possibleOutputs.length];
      for (int i = 0; i < possibleOutputs.length; i++) {
         this.possibleOutputs[i] = possibleOutputs[i];
      }*/
   }

/*
   public Output setReply(String triggers, Output reply){
      // if keyword.... choose output
      return null;
   }
*/

   public void setErrOutput(Output output){
      errOutput = output;
   }

   public Output getErrOutput(){
      return errOutput;
   }


   public boolean equals(Output output){
      return (this == output);
   }

   public void setAnything(boolean b){
      anything = b;
   }

   public String getPart(String input) {
      String[] m;
      String string = null;
      String[] splitInput = split(input);
      if (possibleOutputs != null) {
         // if player doesn't want to start the game yet, get next output
         for (Output r : possibleOutputs){
            for (String i : splitInput) {
               for (String[] k : notKeywords)
                  for (String l : k) {
                     m = checkJ(l);
                     for (String n : m){
                        if (!i.equals(l) && !i.equals(n)) {
                           return i;
                        }
                     }
                  }
               }
            }
         }
      return "";
      }






   // get next output based on player's input
   // f.ex. getNext("yes")
   public Output getNext(String input){
         if (errOutput == null) errOutput = new Output("I'm confused. Please clarify");
         errOutput.setPossibleOutputs(errOutput);
         Output next = errOutput;
         next.setPossibleOutputs(errOutput);

         // convert input sentence to String[]
         input.toLowerCase();
         String[] words = a("do not", "can not", "will not", "are not", "is not", "you are", "i am", "it is");
         String[] contractioned = a("don't", "can't", "won't", "aren't", "isn't", "you're", "i'm", "it's");

         for (int i = 0; i < words.length; i++) {
            if (input.contains(words[i])) input = input.replace(words[i], contractioned[i]);
         }


         String[] splitInput = split(input); // maybe we can split it in Main instead or move the method to this class
         //System.out.println(Arrays.toString(splitInput));

         // check first if player wants to start game (default Output)
         if (containsTrigger(defaultKeywords, splitInput)) {
            return defaultOutput;
         }



      if (possibleOutputs != null){
      // if player doesn't want to start the game yet, get next output
      for (Output r : possibleOutputs) {
         if (r.getKeywords().equals("dummy")) return r;
         // check if there are any triggers
         /*if (this.getKeywords().length == 0) {
            return r;
         }*/
         //if (containsTrigger(r.getKeywords(), splitInput)) {
         if (containsTrigger(r.getKeywords(), splitInput) && !containsTrigger(r.getNotKeywords(), splitInput)) {
            //System.out.println("worked");
            return r;
         }
      }
      }
      return next;
   }

   // contains method: checks if at least one of the trigger options is contained
   public boolean containsTrigger(String[][] triggers, String... input) {
      if (triggers == null) return false;
      List<String> list = Arrays.asList(input);
      //System.out.println("triggers: "+Arrays.toString(triggers));
      //System.out.println(Arrays.toString(list.toArray()));
      for (String[] i : triggers) {
         int counter = 0;
         for (String j : i) {
            String [] k = checkJ(j);
            for (String l : k){              // checks if there is a synonym of the current keyword
               if (list.contains(l)) {
                  counter++;
               }
            }
            if (list.contains(j)) {
               counter++;
            }
            if (j.equals("dummy")) return true;
         }
         if (i.length == counter) {  // if all of the keywords were contained
            return true;
         }
      }
      //System.out.println("no keywords contained");
      return false;
   }

   //public void print() {
     // System.out.println(this.reply);
   //}

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
      Output newOutput = new Output(reply);
      newOutput.setKeyword(getKeywords());
      if (possibleOutputs == null) newOutput.setPossibleOutputs(errOutput);
         newOutput.setPossibleOutputs(possibleOutputs);
      //}
      return newOutput;
   }

   public void print() {
      printWait(1);
      System.out.println(reply);
      if (additionalDisplay != null) {
         for (String s : additionalDisplay) {
            System.out.println(s);
         }
      }
   }

   public void printWait(long waitTime){
      int dots = 3;
      String dotChar;
      if (uni) dotChar = (char)0x26AC + "";
      else dotChar = ".";
      String delete = "\b";
      String dot;
      for (int n = 0; n <= dots*2; n++){
         delete += "\b";
      }
      for (int j = 0; j < waitTime; j++) {
         dot = "";
         for (int i = 0; i < dots; i++) {
            dot += dotChar + " ";
            waitingMilSec(300);
            System.out.print(dot + "\r");

         }
         waitingMilSec(500);
         System.out.print(delete);
      }
      waitingMilSec(500);
   } // Print the bubbles while Liza is writing
   private void waiting(long seconds){// Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds*1000);
      }
      catch(InterruptedException ex) {
         System.out.println("There was a problem :( ");
         //Thread.currentThread().interrupt();
      }
   }
   private void waitingMilSec(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }

   public String[] checkJ(String j){
      String [] k;
      String[] synWords;
      synWords = a("yes", "no", "good", "bad",
              "don't", "can't", "aren't", "won't");


         if      (j.equals("yes"))  return a("sure", "totally", "yeah", "yup");
         else if (j.equals("good")) return a("great", "awesome", "fine", "alright", "well");
         else if (j.equals("no"))   return a("nope", "nah");
         else if (j.equals("bad"))  return a("terrible", "awful", "horrible", "poor");
         else if (j.equals("1"))    return a("first", "one","1st");
         else if (j.equals("2"))    return a("second", "two","2nd");
         else if (j.equals("3"))    return a("third", "three","3rd");
         else if (j.equals("4"))    return a("fourth", "four","4th");


      else k = a("");

      return k;
   }

   public String checkContractions(String input){
      String[] words        = a("do not", "can not", "will not", "are not", "is not", "you are", "i am");
      String[] contractioned = a("don't", "can't",   "won't",    "aren't",  "isn't", "you're", "i'm");

      for (int i = 0; i < words.length; i++){
         if (input.contains(words[i])) return contractioned[i];
      }
      return null;
   }


   public  String[] split(String s) {
      return s.toLowerCase().split(" ");
   }


   public void setPossibleOutputs(Output[] output, Output... outputs){
      Output[] temp = new Output[output.length+outputs.length];
      int k = 0;
      for (int i = 0; i < output.length; i++){
         temp[i] = output[i];
      }
      for (int j = output.length; j < temp.length; j++){
         temp[j] = outputs[k];
         k++;
      }
      possibleOutputs = temp;
   }

   public String[] a(String... strings){
      String[] a = new String[strings.length];
      for (int i = 0; i < strings.length; i++){
         a[i] = strings[i];
      }
      return a;
   }

}
