


public class NormConv extends Conversation {
   Game game;
   Output welcome,  wyn, yni, yniN, yniY, hyd, nth, ohno, igt, iag, intro, hobbies, ilcg, ihcg, fav,
           ywp, symbolCheck, askIfExplain, explain,
           startGame, clarifyAsk, stopGame, afterGame,
           symbolCheckY, symbolCheckN, symbolCheckWhat;
   Output output;
   Output[] possibleReplies, allReplies, aPR;
   String userName, input;

   public NormConv(){
      game = new Game();

      // starting message
      welcome = new Output("Welcome!");

      // first question: ask for name
      wyn = new Output("What's your first name?");
      wyn.setKeyword(a("dummy"));

      // reaction: ask if name correct
      yni = new Output("Your name is " + userName + "?"); // somehow filter all words that could not be names..
      yni.setKeyword(a("dummy"));
      yni.setNotKeywords(a("hello"), a("hi"), a("good", "day"), a("yes"));

      // reaction if name incorrect
      yniN = new Output("I am not good with names.. Write *nothing* but your name.");
      yniN.setKeyword(a("no"), a("it's", "not"));

      // reaction if name correct: Liza introduces herself
      intro = new Output("Nice to meet you, " + userName + "!");
      intro.setKeyword(a("yes"), a("it's"));
      intro.setNotKeywords(a("it's", "not"));
      intro.setAdditionalDisplay("My name is Liza.");

      // next question: ask how player is feeling
      hyd = new Output("How are you, " + userName + "?");
      hyd.setKeyword(a("dummy"));
      //hyd.setNotKeywords(a("not", "name"), a("not", "my"));

      // reaction if player feels good
      nth = new Output("Good to hear.");
      nth.setKeyword(a("good"), a("not", "bad"));
      nth.setNotKeywords(a("not", "good"));
      nth.setAdditionalDisplay("Do you like card games?");

      // reaction if player feels bad
      ohno = new Output("Sorry about that.");
      ohno.setKeyword(a("bad"), a("not", "good"));
      ohno.setNotKeywords(a("not", "bad"));
      //ohno.setAdditionalDisplay("Why are you feeling bad?");
      ohno.setAdditionalDisplay("Do you like card games?");

      // reaction if player feels good and asks how Liza is feeling
      iag = new Output("I'm good, thanks for asking!");
      iag.setKeyword(a("bad", "you"), a("not", "good", "you"));
      iag.setNotKeywords(a("not", "bad"));
      iag.setAdditionalDisplay("Do you like card games?");

      // reaction if player feels bad and asks how Liza is feeling
      igt = new Output("I'm good too. Thanks for asking!");
      igt.setKeyword(a("good", "you"), a("great", "you"));
      igt.setNotKeywords(a("not", "good"), a("not", "great"));
      igt.setAdditionalDisplay("Do you like card games?");

      // next question: ask for reason of feelings

      // next question: ask about interests
      hobbies = new Output("What are your interests?");
      hobbies.setKeyword(a("dummy"));

      // question: ask about card games
      //cg = new Output("Do you like card games?");
      //cg.setKeyword(a("dummy"));

      // reaction if player likes card games
      ilcg = new Output("Me too!");
      ilcg.setKeyword(a("yes"), a("do"), a("love"));
      ilcg.setNotKeywords(a("don't", "like"), a("dislike"));
      ilcg.setAdditionalDisplay("What is your favorite card game?");

      // reaction to player's favorite card game
      fav = new Output(input + "? That's a cool game!"); // look for right word
      fav.setKeyword(a("dummy"));
      fav.setAdditionalDisplay("Would you like to play the card game 31 with me?");

      // reaction if player doesn't like card games
      ihcg = new Output("Really? But they are so fun!");
      ihcg.setKeyword(a("don't"), a("no"), a("dislike"), a("hate"));
      ihcg.setNotKeywords(a("don't", "hate"), a("don't", "dislike"));
      ihcg.setAdditionalDisplay("Have you heard of the card game 31?");

      // reaction if player doesn't know 31
      // ask if explain?

      // reaction if player knows 31: ask about playing
      ywp = new Output("Would you like to play the card game 31 with me?");
      ywp.setKeyword(a("yes"), a("do"));

      // reaction if player doesn't want to play: more smalltalk?

      // reaction if player wants to play: ask about explaining the rules
      askIfExplain = new Output("Do you know the rules or would you like me to explain them?");
      askIfExplain.setKeyword(a("dummy"), a("yes"), a("ok"), a("sure"), a("let's", "do", "it"));

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
      //String[][] dff = {a("yo", "are"), a("jkljlk", "opi")};
      //yni.setNotKeywords(dff);
      yni.setPossibleOutputs(getaPR(), intro, yniN);                                     // <- Should lead to some "my name is"
      yniN.setPossibleOutputs(getaPR(), yni);
      intro.setPossibleOutputs(getaPR(), hyd);
      hyd.setPossibleOutputs(getaPR(),nth, ohno, iag, igt);
      nth.setPossibleOutputs(getaPR(), ihcg, ilcg);
      ohno.setPossibleOutputs(getaPR(), ihcg, ilcg);
      iag.setPossibleOutputs(getaPR(), ihcg, ilcg);
      igt.setPossibleOutputs(getaPR(), ihcg, ilcg);
      ilcg.setPossibleOutputs(getaPR(), fav);
      ihcg.setPossibleOutputs(getaPR(), ywp);
      fav.setPossibleOutputs(getaPR(), askIfExplain);
      ywp.setPossibleOutputs(getaPR(), askIfExplain);
      askIfExplain.setPossibleOutputs(getaPR(),explain, startGame);
      explain.setPossibleOutputs(getaPR(),clarifyAsk, startGame);
      afterGame.setPossibleOutputs(wyn);   // fix



   }


   public void startConv() {
      //settingPossibleOutputs();
      // this might be the loop (looping through Output objects, 'welcome' being the first)
      int counter = 0;



      output = welcome; //.copy();   // first output
      output.print();


      while(counter < 10 && !startGameT) {
         //loopingReplies();
         //updateReplies();
         String input = readString();
         normSpecialOutput(output, input);
         //Output previous = output.copy();
         output = output.getNext(input);
         normSpecialOutput(output, input);
         //updateReplies();
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
         //updateReplies();
         yni.setReply("Your name is " + userName + "?");
         intro.setReply("Nice to meet you, " + userName + "!");
         hyd.setReply("How are you " + userName + "?");

      }
   }

   public void updateReplies(){
      yni.setReply("Your name is " + userName + "?");
      intro.setReply("Nice to meet you, " + userName + "!");
      hyd.setReply("How are you " + userName + "?");
   }


   public String getUserName(){
      return userName;
   }




}
