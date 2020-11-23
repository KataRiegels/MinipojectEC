import java.util.*;

public class Output {
   private boolean uni;
   private String reply;
   private ArrayList<String> additionalDisplay;
   private Output errOutput = null;
   private String[][] keywords, notKeywords;       // keywords that trigger this particular output
   private Output[] possibleOutputs;
   private boolean anything = false;
   private String[] words         = a("do not", "can not", "will not", "are not", "is not", "you are", "i am", "it is");
   private String[] contractioned = a("don't",  "can't",   "won't",    "aren't",  "isn't",  "you're",  "i'm",  "it's");
   private ArrayList<Output> previous;
   boolean dummy;
   String[] ignore;

   public Output(String reply){
      this.reply = reply;
      uni = true;             // This is whether we use cool symbols or not
      notKeywords = null;
      dummy = false;
   }




   // getters and setters
   public void setIgnore(String... words){
      ignore = words;
   }

   public void setDummy(boolean b){
      dummy = b;
   }

   public String[][] getKeywords() {
      return keywords;
   }

   public String     getKeyword(int i, int j){
      return keywords[i][j];
   }

   public String[][] getNotKeywords() {
      return notKeywords;
   }

   public Output[]   getPossibleOutputs() {
      return possibleOutputs;
   }

   public Output     getErrOutput(){
      return errOutput;
   }

   public String getReply(){
      return reply;
   }

   public void       setReply(String string){
      reply = string;
   }

   public void       setKeywords(String[]... keywords) {
      this.keywords = keywords;
   }

   public void       setNotKeywords(String[]... notKeywords){
      this.notKeywords = notKeywords;
   }

   public void       setPossibleOutputs(Output... possibleOutputs) {
      if (possibleOutputs != null) {
         Output[] temp = new Output[possibleOutputs.length];
         for (int i = 0; i < possibleOutputs.length; i++)
            temp[i] = possibleOutputs[i];
         this.possibleOutputs = temp;
      }
   }

   public void       setErrOutput(Output output){
      errOutput = output;
   }

   public void setAdditionalDisplay(String ... strings){
      additionalDisplay = new ArrayList<String>();
      for (String s : strings) {
         additionalDisplay.add(s);
      }
   }

   public void addPossibleOutput(Output possibleOutput){
         Output[] newArr = new Output[possibleOutputs.length+1];
         for (int i = 0; i < possibleOutputs.length; i++){
            newArr[i] = possibleOutputs[i];
         }
         newArr[-1] = possibleOutput;
      possibleOutputs = newArr;
   }

   public boolean isInPossibleOutputs(Output output){
      for (Output o : possibleOutputs){
         if (output == o) return true;
      }
      return false;
   }

   public boolean equals(Output output){
      return (this == output);
   }

   public String getPart(String input) {
      String[] syn = null;
      contractions(input);
      String[] splitInput = split(input);
      String ig = "";
      if (possibleOutputs != null) {
         for (Output o : possibleOutputs){
            for (String igno : o.ignore){
               ig += igno;
            }
            for (String i : splitInput){
               for (String ign : o.ignore){
                     syn = checkJ(ign);
                     for (String synword : syn){

                        if (!(ig.contains(i) && i.contains(synword))) {
                           return i;
                        }
                  }
      }}}}
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
         }
         errOutput.setPossibleOutputs(errOutput);
         Output next = errOutput;
         next.setPossibleOutputs(errOutput);

         // convert input sentence to String[]

         input.toLowerCase();
         contractions(input);
         String[] splitInput = split(input);
      if (possibleOutputs != null){
         for (Output r : possibleOutputs) {
            if (r.dummy) return r;
            //System.out.println(Arrays.deepToString(r.getKeywords()));
            //if (r.getKeyword(0,0).equals("dummy")) return r;
            //if (r.keywords == null) return r;
            if (containsTrigger(r.getKeywords(), splitInput) && !containsTrigger(r.getNotKeywords(), splitInput) ) {
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
   } // Print the dots while Liza is writing
   private void waitingMilSec(long seconds){
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

   public String[] a(String... strings){
      String[] a = new String[strings.length];
      for (int i = 0; i < strings.length; i++){
         a[i] = strings[i];
      }
      return a;
   }


}
