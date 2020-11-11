import java.util.ArrayList;
import java.util.*;

public class Replies {
   private String[] keywords = null; // = {"no", "yes", "maybe"};
   private String[] answers = null;
   private int instructions = 0;


   // "no" and "maybe" -> "fuck you"
   public Replies(String[] keywords, String[] answers){
      this.keywords = keywords;
      this.answers = answers;
   }

   public Replies(String[] keywords, int instructions){
      this.keywords = keywords;
      this.instructions = instructions;
   }

   public void reply(){

   }

   public static String[] split(String userInput) {
      return userInput.toLowerCase().split(" ");
   }



   public ArrayList<Integer> containsKeywords(String[] userInput) {
      ArrayList<Integer> indices = new ArrayList<>();       // create ArrayList to store results
      for (int i = 0; i < keywords.length; i++) {
         for (String word : userInput) {
            if (word.equals(keywords[i])) {                      // adding the index of a keyword when found
               indices.add(i);
            }
         }
      }
      return indices;
   }







   public String[] getKeywords() {
      return keywords;
   }

   public void setKeywords(String[] keywords) {
      this.keywords = keywords;
   }

   public String[] getAnswers() {
      return answers;
   }

   public void setAnswers(String[] answers) {
      this.answers = answers;
   }

   public int getInstructions() {
      return instructions;
   }

   public void setInstructions(int instructions) {
      this.instructions = instructions;
   }
}
