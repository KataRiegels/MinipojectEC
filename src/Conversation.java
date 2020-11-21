import java.util.Scanner;

public class Conversation {
      Game game;
   Output welcome,  wyn, hyd, nth, ohno, igt, iag,
           symbolCheck, askIfExplain, explain,
           startGame, clarifyAsk, stopGame;
   Output[] possibleReplies, allReplies, aPR;

   public Conversation(){


         welcome = new Output("welcome");
         wyn = new Output("What's your name?");

         hyd = new Output("How are you?");
         String hydTriggers[][] = {{"hello"}, {"hi"}, {"good", "day"}};
         hyd.setKeyword(hydTriggers);

         nth = new Output("Good to hear. Let's play blackjack!");
         String nthTriggers[][] = {{"good"}, {"not", "bad"}};
         nth.setNotKeywords(a("not", "good"));
         nth.setKeyword(nthTriggers);

         ohno = new Output("Sorry about that. Let's play blackjack to cheer you up!");
         String ohnoTriggers[][] = {{"bad"}, {"not", "good"}};
         ohno.setNotKeywords(a("not", "bad"));
         ohno.setKeyword(ohnoTriggers);

         iag = new Output("I'm good, thanks for asking! Let's play blackjack to cheer you up!");
         String iagTriggers[][] = {{"bad", "you"}, {"not", "good", "you"}};
         iag.setNotKeywords(a("not", "bad"));
         iag.setKeyword(iagTriggers);

         igt = new Output("I'm good too. Thanks for asking! Let's play blackjack!");
         String igtTriggers[][] = {{"good", "you"}, {"great", "you"}};
         igt.setKeyword(igtTriggers);

         //Output ywp = new Output("Wanna play blackjack?");

         // Output: can you see this symbol: ... ?
         symbolCheck = new Output("Can you see this symbol: ?");

         askIfExplain = new Output("Do you know the rules or would you like me to explain them?");
         String askIfExplainTriggers[][] = {{"ok"}, {"sure"}, {"let's", "do", "it"}};
         askIfExplain.setKeyword(askIfExplainTriggers);

         explain = new Output("Ok, these are the rules:");
         String explainTriggers[][] = {{"explain"}, {"don't", "know", "rules"}};
         explain.setKeyword(explainTriggers);
         String rule1 = new String("The goal of blackjack is to beat the dealer's hand without going over 21.");
         String rule2 = new String("Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.");
         String question = new String("Do you need clarification?");
         explain.setAdditionalDisplay(rule1, rule2, question);

         startGame = new Output("Let's start the game then.");
         String startGameTriggers[][] = {{"explain", "don't"}, {"know", "rules"}, {"start", "game"}};
         startGame.setKeyword(startGameTriggers);

         clarifyAsk = new Output("Which rule would you like me to clarify?");
         String clarifyAskTriggers[][] = {{"yes"}, {"do"}};
         clarifyAsk.setKeyword(clarifyAskTriggers);


         stopGame = new Output("Ok, let's stop");
         stopGame.setKeyword(a("stop", "game"));
         aPR = a(startGame, stopGame);

         //Output rule1 = new Output("");

         //Output possibleReplies[] = {wyn, hyd, iag, igt, ohno, nth, askIfExplain, noexplain, explain};
         allReplies = a(wyn, hyd, iag, igt, ohno, nth, askIfExplain, startGame, stopGame, explain, clarifyAsk);
         possibleReplies = allReplies;
         //welcome.setPossibleOutputs(possibleReplies);

         wyn.setPossibleOutputs(aPR,hyd);
         hyd.setPossibleOutputs(aPR,nth, ohno, iag, igt);
         nth.setPossibleOutputs(aPR, askIfExplain);
         ohno.setPossibleOutputs(aPR,askIfExplain);
         iag.setPossibleOutputs(aPR,askIfExplain);
         igt.setPossibleOutputs(aPR,askIfExplain);
         //ywp.setPossibleOutputs(askIfExplain);
         askIfExplain.setPossibleOutputs(aPR,explain, startGame);
         explain.setPossibleOutputs(aPR,clarifyAsk, startGame);

         //System.out.println(Arrays.toString((welcome.defaultKeywords)));



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
      // only read new input if input needed, otherwise just get next output (?)
      // save current output, possible replies need to be updated according to output

   }


   public void specialOutput(Output output){
      if (output == startGame){
         game = new Game();
         game.playGame();
      }
      if (output == stopGame){
         game.stopGame();
         System.out.println("Game stopped");
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

}

