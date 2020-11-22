


public class NormConv extends Conversation {
   Game game;
   Output welcome,  wyn, yni, yniN, yniY, hyd, nth, ohno, igt, iag,
           symbolCheck, askIfExplain, explain,
           startGame, clarifyAsk, stopGame, afterGame,
            symbolCheckY, symbolCheckN;
   Output output;
   Output[] possibleReplies, allReplies, aPR;
   String userName;

   public NormConv(){
      game = new Game();

      welcome = new Output("welcome");


      wyn = new Output("Welcome. What's your first name?");
      wyn.setKeyword(a("dummy"));

      yni = new Output("Your name is " + userName + "?"); // somehow filter all words that could not be names..
      yni.setKeyword(a("dummy"));
      String asd[][] = {{"hello"}, {"hi"}, {"good", "day"}, {"yes"}};
      yni.setNotKeywords(asd);
      yniN = new Output("I am not good with names.. Write *nothing* but your name.");


      hyd = new Output("How are you " + userName + "?");
      String hydTriggers[][] = {{"hello"}, {"hi"}, {"good", "day"}, {"yes"}};
      hyd.setKeyword(hydTriggers);
      hyd.setNotKeywords(a("not", "name"), a("not", "my"));

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
      symbolCheck  = new Output("Can you see these symbols or are some just squares?: " + (char) 0x2805 + (char)0x235A + (char)0x2661);
      symbolCheckY = new Output("Alright, thanks.");
      symbolCheck.setKeyword(a("yes"), a("i", "can"), a("i", "do"));
      symbolCheck.setNotKeywords(a("can", "not"));


      symbolCheckN = new Output("Good, thanks");
      symbolCheckN.setKeyword(a("no"), a("can't"), a("don't"), a("squares"), a("square"));



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

      clarifyAsk = new Output("Which rule would you like me to clarify?");
      String clarifyAskTriggers[][] = {{"yes"}, {"do"}};
      clarifyAsk.setKeyword(clarifyAskTriggers);

      afterGame = new Output("Well played!");
      settingPossibleOutputs();
   }



   public void settingPossibleOutputs(){
      Output afterSym = hyd;
      symbolCheckN.setPossibleOutputs(getaPR(), afterSym);
      symbolCheckY.setPossibleOutputs((symbolCheckN.getPossibleOutputs()));
      //wyn.setPossibleOutputs(getaPR(), hyd);
      welcome.setPossibleOutputs(wyn);
      wyn.setPossibleOutputs(yni);
      String[][] dff = {a("yo", "ka"), a("jkljlk", "opi")};
      yni.setNotKeywords(dff);
      yni.setPossibleOutputs(getaPR(), yniY, yniN);
      hyd.setPossibleOutputs(getaPR(),nth, ohno, iag, igt);
      nth.setPossibleOutputs(getaPR(), askIfExplain);
      ohno.setPossibleOutputs(getaPR(),askIfExplain);
      iag.setPossibleOutputs(getaPR(),askIfExplain);
      igt.setPossibleOutputs(getaPR(),askIfExplain);
      //ywp.setPossibleOutputs(askIfExplain);
      askIfExplain.setPossibleOutputs(getaPR(),explain, startGame);
      explain.setPossibleOutputs(getaPR(),clarifyAsk, startGame);
      afterGame.setPossibleOutputs(wyn);   // fix



   }


   public void startConv() {
      //settingPossibleOutputs();
      // this might be the loop (looping through Output objects, 'welcome' being the first)
      int counter = 0;



      output = wyn; //.copy();   // first output
      output.print();


      while(counter < 6 && !startGameT) {
         //loopingReplies();

         String input = readString();
         normSpecialOutput(output, input);
         output = output.getNext(input);
         output.print();

         specialOutput(output);

         counter++;
      }
   }

   public void endConv(){
      output = afterGame.copy();
      output.print();
   }

   public void normSpecialOutput(Output output, String input){
      if (output == symbolCheckN) uni = false;
      if (output == symbolCheckY) uni = true;
      if (output == wyn)         {
         userName = wyn.getPart(input);
         //System.out.println(userName);
         yni.setReply("Your name is " + userName + "?");
      }
   }






}
