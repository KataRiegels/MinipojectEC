import java.util.Scanner;

public class Conversation {
      Game game;
   Output welcome,  wyn, hyd, nth, ohno, igt, iag,
           symbolCheck, askIfExplain, explain,
           startGame, clarifyAsk, stopGame;
   Output output;
   Output[] possibleReplies, allReplies, aPR;
   boolean startGameT, stopGameT, uni;

   public Conversation(){

          startGame = new Output("Let's start the game then.");
         String startGameTriggers[][] = {{"explain", "don't"}, {"know", "rules"}, {"start", "game"}};
         startGame.setKeyword(startGameTriggers);

         stopGame = new Output("Ok, let's stop");
         stopGame.setKeyword(a("stop"));
         aPR = a(startGame, stopGame);

         //Output possibleReplies[] = {wyn, hyd, iag, igt, ohno, nth, askIfExplain, noexplain, explain};
         allReplies = a(wyn, hyd, iag, igt, ohno, nth, askIfExplain, startGame, stopGame, explain, clarifyAsk);
         possibleReplies = allReplies;
         //welcome.setPossibleOutputs(possibleReplies);
         //System.out.println(Arrays.toString((welcome.defaultKeywords)));
   }


   public void loopingReplies(){
      // we need some other condition here
      String input = readString();

      //possibleReplies = welcome.getPossibleOutputs();

      //**** why is this in?****
      //output.setPossibleOutputs(possibleReplies);    // outside of the loop, distinct replies for each output

      output = output.getNext(input);
      // check which output the new output is


      //***** and why is this in?****
      /*
         for (Output r : allReplies) {
            if (output.equals(r)) {                         // get the possible replies based on output
               possibleReplies = r.getPossibleOutputs();   // update possibleReplies
            }
         }*/

      output.print();
      specialOutput(output);
   }



   public Output[] getaPR(){
      return aPR;
   }

   public void startConv(){
      // this might be the loop (looping through Output objects, 'welcome' being the first)
      int counter = 0;

      Output output = hyd.copy();   // first output
      output.print();
      while (counter < 6) {   // we need some other condition here
         String input = readString();
         //possibleReplies = welcome.getPossibleOutputs();
         output.setPossibleOutputs(possibleReplies);    // outside of the loop, distinct replies for each output
         output = output.getNext(input);
         // check which output the new output is
         for (Output r : allReplies) {
            if (output.equals(r)) {                         // get the possible replies based on output
               possibleReplies = r.getPossibleOutputs();   // update possibleReplies
            }
         }
         output.print();
         specialOutput(output);
         counter++;
      }
      // save current output

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

