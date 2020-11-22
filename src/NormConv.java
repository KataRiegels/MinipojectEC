


public class NormConv extends Conversation {
   Game game;
   Output welcome,  wyn, yni, yniN, yniY, hyd, nth, ohno, igt, iag,
           symbolCheck, askIfExplain, explain,
           startGame, clarifyAsk, stopGame, afterGame,
           symbolCheckY, symbolCheckN, symbolCheckWhat;
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
      yniN.setKeyword(a("no"), a("it's", "not"));

      //yniY = new Output("Welcome " + userName + "!");
      //yniY.setKeyword(a("yes"));

      hyd = new Output("How are you, " + userName + "?");
      //String hydTriggers[][] = {{"hello"}, {"hi"}, {"good", "day"}, {"yes"}};
      //hyd.setKeyword(hydTriggers);
      hyd.setKeyword(a("yes"));
      hyd.setNotKeywords(a("not", "name"), a("not", "my"));

      nth = new Output("Good to hear.");
      nth.setKeyword(a("good"), a("not", "bad"));
      nth.setNotKeywords(a("not", "good"));
      nth.setAdditionalDisplay("Let's play 31.");

      ohno = new Output("Sorry about that.");
      ohno.setKeyword(a("bad"), a("not", "good"));
      ohno.setNotKeywords(a("not", "bad"));
      ohno.setAdditionalDisplay("Let's play 31.");

      iag = new Output("I'm good, thanks for asking!");
      iag.setKeyword(a("bad", "you"), a("not", "good", "you"));
      iag.setNotKeywords(a("not", "bad"));
      iag.setAdditionalDisplay("Let's play 31.");

      igt = new Output("I'm good too. Thanks for asking!");
      String igtTriggers[][] = {{"good", "you"}, {"great", "you"}};
      igt.setKeyword(igtTriggers);
      igt.setAdditionalDisplay("Let's play 31.");

      //Output ywp = new Output("Wanna play blackjack?");

      // Output: can you see this symbol: ... ?
      symbolCheck  = new Output("Can you see these symbols or just three squares?: " + (char) 0x2805 + (char)0x235A + (char)0x2661);
      symbolCheckY = new Output("Alright, thanks.");
      symbolCheckY.setKeyword(a("yes"),a("i", "can"), a("i", "do"), a("no", "square"), a("no", "squares"), a("symbols"));
      symbolCheckY.setNotKeywords(a("can", "not"));

      symbolCheckWhat = new Output("\"yes\" as in you don't see three squares?");
      symbolCheckWhat.setKeyword(a("yes"));

      symbolCheckN = new Output("Good, thanks");
      symbolCheckN.setKeyword( a("no"), a("correct"), a("can't"), a("don't"), a("squares"), a("square"));
      symbolCheckN.setNotKeywords(a("no", "square"), a("no", "squares"));



      askIfExplain = new Output("Do you know the rules or would you like me to explain them?");
      String askIfExplainTriggers[][] = {{"ok"}, {"sure"}, {"let's", "do", "it"}};
      askIfExplain.setKeyword(askIfExplainTriggers);
      askIfExplain.setKeyword(a("dummy"));

      explain = new Output("Ok, these are the rules:");
      String explainTriggers[][] = {{"explain"}, {"don't", "know", "rules"}};
      explain.setKeyword(explainTriggers);
      String rule1 = new String("You have a hand of 3 cards.");
      String rule2 = new String("Your goal is to get a total as close to 31 as possible.");
      String rule3 = new String("Only cards of the same suit count together.");
      String rule4 = new String("All face cards count as 10. Ace counts as 11.");
      String rule5 = new String("When it is your turn you can \"knock\" if you think you can beat the other player's score.");
      String rule6 = new String("After a \"knock\" the other player gets one more round.");
      String rule7 = new String("The player who is closer to 31 wins the round.");
      String question = new String("Do you need clarification?");
      explain.setAdditionalDisplay(rule1, rule2, rule3, rule4, rule5, rule6, rule7);

      clarifyAsk = new Output("Which rule would you like me to clarify?");
      String clarifyAskTriggers[][] = {{"yes"}, {"do"}};
      clarifyAsk.setKeyword(clarifyAskTriggers);

      afterGame = new Output("Well played!");
      uni = true;
      settingPossibleOutputs();
   }



   public void settingPossibleOutputs(){
      symbolCheck.setPossibleOutputs(symbolCheckWhat, symbolCheckN,  symbolCheckY);
      symbolCheckWhat.setPossibleOutputs(symbolCheckY,symbolCheckN);
      Output afterSym = hyd;
      symbolCheckN.setPossibleOutputs(getaPR(), afterSym);
      symbolCheckY.setPossibleOutputs((symbolCheckN.getPossibleOutputs()));
      //wyn.setPossibleOutputs(getaPR(), hyd);
      welcome.setPossibleOutputs(wyn);
      wyn.setPossibleOutputs(yni);
      String[][] dff = {a("yo", "are"), a("jkljlk", "opi")};
      yni.setNotKeywords(dff);
      yni.setPossibleOutputs(getaPR(), hyd, yniN);                                     // <- Should lead to some "my name is"
      yniN.setPossibleOutputs(getaPR(), yni);
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
         updateReplies();
         String input = readString();
         normSpecialOutput(output, input);
         //Output previous = output.copy();
         output = output.getNext(input);
         normSpecialOutput(output, input);
         updateReplies();
         //output.setPrevious(previous);
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
      if (output == symbolCheckN) {
         uni = false;
         output.print();
         this.output = wyn;                                                   // <-- FIX
         return;
      }
      if (output == symbolCheckY) {
         output.print();
         uni = true;
         this.output = wyn;
         return;
      }
      if (output == yni)         {
         userName = wyn.getPart(input);
         userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
         //yni.setReply("Your name is " + userName + "?");
         //hyd.setReply("How are you " + userName + "?");
      }
   }

   public void updateReplies(){
      yni.setReply("Your name is " + userName + "?");
      hyd.setReply("How are you " + userName + "?");
   }


   public String getUserName(){
      return userName;
   }




}
