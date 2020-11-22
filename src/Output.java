import java.util.*;

public class Output {
   private boolean uni;
   private String reply;
   private ArrayList<String> additionalDisplay;
   private Output errOutput = null;
   private String[][] keywords; // keywords that trigger this particular output
   private Output[] allOutputs;
   private String[][] notKeywords;
   private Output[] possibleOutputs;
   private boolean anything = false;
   private String[] words         = a("do not", "can not", "will not", "are not", "is not", "you are", "i am", "it is");
   private String[] contractioned = a("don't",  "can't",   "won't",    "aren't",  "isn't",  "you're",  "i'm",  "it's");
   //ArrayList<Output> possibleOutputs;
   //private Output defaultOutput; // = new Output("starting the game");
   //private String[][] defaultKeywords;
   private ArrayList<Output> previous;

   public Output(String reply){
      this.reply = reply;
      //setDefaultKeywords();
      uni = true;             // This is whether we use cool symbols or not
      notKeywords = null;

   }

   public Output(){}          // this is needed for my GameOutput class



   public void setPrevious(Output current) {
      //previous.add(current);
   }

   // setKeyword("fine", "good")
   public void setReply(String string){
      reply = string;
   }
   public void       setKeywords(String[]... keywords) {
      this.keywords = keywords;
   }
   public String[][] getKeywords() {
      return keywords;
   }
   public String     getKeyword(int i, int j){
      return keywords[i][j];
   }  // keywordS
   public void       setNotKeywords(String[]... notKeywords){
      this.notKeywords = notKeywords;
   }
   public String[][] getNotKeywords() {
      return notKeywords;
   }
   public Output[]   getPossibleOutputs() {
      return possibleOutputs;
   }
   public void       setPossibleOutputs(Output... possibleOutputs) {
      if (possibleOutputs != null) {
         Output[] temp = new Output[possibleOutputs.length];
         for (int i = 0; i < possibleOutputs.length; i++)
            temp[i] = possibleOutputs[i];
         this.possibleOutputs = temp;
      }
   }
   public void       setPossibleOutputs(Output[] output, Output... outputs){
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
   public void       setErrOutput(Output output){
      errOutput = output;
   }
   public Output     getErrOutput(){
      return errOutput;
   }



   public void addPossibleOutput(Output possibleOutput){
         Output[] newArr = new Output[possibleOutputs.length+1];
         for (int i = 0; i < possibleOutputs.length; i++){
            newArr[i] = possibleOutputs[i];
         }
         newArr[-1] = possibleOutput;
      possibleOutputs = newArr;
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

/*
   public Output setReply(String triggers, Output reply){
      // if keyword.... choose output
      return null;
   }
*/




   public boolean equals(Output output){
      return (this == output);
   }

   public String getPart(String input) {
      String[] syn;

      // insert contractions

      contractions(input);
      String[] splitInput = split(input);
      if (possibleOutputs != null) {
         // if player doesn't want to start the game yet, get next output
         for (Output o : possibleOutputs)
            for (String i : splitInput)
               for (String[] notK : o.notKeywords)
                  for (String word : notK) {
                     syn = checkJ(word);
                     for (String synword : syn)
                        if (!i.equals(word) && !i.equals(synword))
                           return i;
                  }
      }
        return "";
   }

   private String contractions(String input) {
      for (int i = 0; i < words.length; i++)
         if (input.contains(words[i]))
            input = input.replace(words[i], contractioned[i]);

      return input;
   }


   public Output getNext(String input){
         if (errOutput == null) {
            errOutput = new Output("I'm confused. Please clarify");
            /*Output originalQ = errOutput.findOriginalQuestion();
            String[][] previousKeys = originalQ.getKeywords();
            errOutput.setKeyword(previousKeys);
            errOutput.setPossibleOutputs(originalQ.possibleOutputs);*/
         }
         errOutput.setPossibleOutputs(errOutput);
         Output next = errOutput;
         next.setPossibleOutputs(errOutput);

         // convert input sentence to String[]
         input.toLowerCase();




         String[] splitInput = split(input); // maybe we can split it in Main instead or move the method to this class
         //System.out.println(Arrays.toString(splitInput));

         // check first if player wants to start game (default Output)



      if (possibleOutputs != null){
      // if player doesn't want to start the game yet, get next output
         for (Output r : possibleOutputs) {
            //System.out.println(Arrays.deepToString(r.getKeywords()));
            //if (r.getKeywords().contains(a(a("dummy")))) return r;
            // check if there are any triggers
            /*if (this.getKeywords().length == 0) {
               return r;
            }*/
            //if (containsTrigger(r.getKeywords(), splitInput)) {
            if (containsTrigger(r.getKeywords(), splitInput) && !containsTrigger(r.getNotKeywords(), splitInput) || !containsTrigger(r.getNotKeywords(), a("dummy"))) {
               //System.out.println("worked");
               return r;
            }
         }
      }
      return next;
   }

   // contains method: checks if at least one of the trigger options is contained
   private boolean containsTrigger(String[][] triggers, String... input) {
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
      newOutput.setKeywords(getKeywords());
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
            printWait(1);
            System.out.println(s);
         }
      }
   }

   private void printWait(long waitTime){
      int dots = 3;
      String dotChar;
      dotChar = ".";
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
   private void waitingMilSec(long seconds){
      // Taken from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
      try {
         Thread.sleep(seconds);
      }
      catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }



   private String[] checkJ(String j){
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
   private String[] split(String s) {
      return s.toLowerCase().split(" ");
   }




   public Output findOriginalQuestion() {
      Output originalQ = null;
      if (errOutput != null) {
         if (previous.contains(errOutput)) {
            int indexOriginalQ = previous.indexOf(errOutput) - 1;
            originalQ = previous.get(indexOriginalQ);
         }
      }
      return originalQ;
   }

   public String[] a(String... strings){
      String[] a = new String[strings.length];
      for (int i = 0; i < strings.length; i++){
         a[i] = strings[i];
      }
      return a;
   }


}
