



public class Output {
   String reply;
   String[][] keywords; // keywords that trigger this particular output


   public Output(String reply){
      this.reply = reply;
   }

   // nice.

   // setKeyword("fine", "good")


   public void setKeyword(String[][] keywords) {
      this.keywords = keywords;
   }

   public String[][] getKeywords() {
      return this.keywords;
   }

/*
   public Output setReply(String triggers, Output reply){
      // if keyword.... choose output

      return null;
   }
*/
   // getNext("yes")
   public Output getNext(String input, Output[] possibleReplies){
      //contains
      //keywords
      Output next = new Output("I'm confused.");

      // convert input sentence to String[]
      String[] splitInput = Main.split(input); // maybe we can split it in Main instead or move the method to this class

      // check for trigger -> needs access to keywords of other Outputs
      for (Output r : possibleReplies) {     // loop through all possible replies
         String[][] keys = r.getKeywords();  // get keywords for each reply
         for (String[] k : keys) {           // loop through keywords
            if (k.equals(splitInput)) {      // compare input to keywords
               next = r;                     // find reply
            }
         }
      }

      // return new Output
      return next;
   }
}
