import java.util.Scanner;

public class Conversation {
      Game game;
   Output welcome,  wyn, hyd, nth, ohno, igt, iag,
           symbolCheck, askIfExplain, explain,
           startGame, clarifyAsk, stopGame,
            youAre;
   Output output;
   Output[] possibleReplies, allReplies, aPR;
   boolean startGameT, stopGameT, uni;

   public Conversation(){

          startGame = new Output("Let's start the game then.");
         String startGameTriggers[][] = {{"explain", "don't"}, {"know", "rules"}, {"start", "game"}};
         startGame.setKeywords(startGameTriggers);


         stopGame = new Output("Ok, let's stop");
         stopGame.setKeywords(a("stop"));
         aPR = a(startGame, stopGame);

         //Output possibleReplies[] = {wyn, hyd, iag, igt, ohno, nth, askIfExplain, noexplain, explain};
         allReplies = a(wyn, hyd, iag, igt, ohno, nth, askIfExplain, startGame, stopGame, explain, clarifyAsk);
         possibleReplies = allReplies;
         //welcome.setPossibleOutputs(possibleReplies);
         //System.out.println(Arrays.toString((welcome.defaultKeywords)));
   }

   public Output[] getaPR(){
      return aPR;
   }

   public boolean startedGame(){
      return startGameT;
   }

   public void specialOutput(Output output){
      if (output == startGame){
         startGameT = true;
      }
      if (output == stopGame){
         stopGameT = true;
      }

   }


   public String[] a(String... strings){
      String[] a = new String[strings.length];
      for (int i = 0; i < strings.length; i++){
         a[i] = strings[i];
      }
      return a;
   }

   // method that reads input as a string
   public String readString() {
      Scanner in = new Scanner(System.in);
      return in.nextLine();
   }

   public Output[] a(Output... outputs){
      Output[] a = new Output[outputs.length];
      for (int i = 0; i < outputs.length; i++){
         a[i] = outputs[i];
      }
      return a;
   }

   public boolean getUni(){
      return uni;
   }


}

