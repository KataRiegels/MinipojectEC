


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


      wyn = new Output("Welcome. What's your first name?");
      wyn.setKeywords(a("dummy"));
      // first question: ask for name
      wyn = new Output("What's your first name?");

      // reaction: ask if name correct
      yni = new Output("Your name is " + userName + "?"); // somehow filter all words that could not be names..
      yni.setKeywords(null);
      //yni.setKeywords(a("dummy"));
      yni.setNotKeywords(a("hello"), a("hi"), a("good", "day"), a("yes"));

      // reaction if name incorrect
      yniN = new Output("I am not good with names.. Write *nothing* but your name.");
      yniN.setKeywords(a("no"), a("it's", "not"));

      // reaction if name correct: Liza introduces herself
      intro = new Output("Nice to meet you, " + userName + "!");
      intro.setKeywords(a("yes"), a("it's"));
      intro.setNotKeywords(a("it's", "not"));
      intro.setAdditionalDisplay("My name is Liza.");

      // next question: ask how player is feeling
      hyd = new Output("How are you, " + userName + "?");
      hyd.setKeywords(null);
      //hyd.setNotKeywords(a("not", "name"), a("not", "my"));

      // reaction if player feels good
      nth = new Output("Good to hear.");
      nth.setKeywords(a("good"), a("not", "bad"));
      nth.setNotKeywords(a("not", "good"));
      nth.setAdditionalDisplay("Do you like card games?");

      // reaction if player feels bad
      ohno = new Output("Sorry about that.");
      ohno.setKeywords(a("bad"), a("not", "good"));
      ohno.setNotKeywords(a("not", "bad"));
      //ohno.setAdditionalDisplay("Why are you feeling bad?");
      ohno.setAdditionalDisplay("Do you like card games?");

      // reaction if player feels good and asks how Liza is feeling
      iag = new Output("I'm good, thanks for asking!");
      iag.setKeywords(a("bad", "you"), a("not", "good", "you"));
      iag.setNotKeywords(a("not", "bad"));
      iag.setAdditionalDisplay("Do you like card games?");

      // reaction if player feels bad and asks how Liza is feeling
      igt = new Output("I'm good too. Thanks for asking!");
      igt.setKeywords(a("good", "you"), a("great", "you"));
      igt.setNotKeywords(a("not", "good"), a("not", "great"));
      igt.setAdditionalDisplay("Do you like card games?");

      // next question: ask for reason of feelings

      // next question: ask about interests
      hobbies = new Output("What are your interests?");
      hobbies.setKeywords(a("dummy"));

      // question: ask about card games
      //cg = new Output("Do you like card games?");
      //cg.setKeywords(a("dummy"));

      // reaction if player likes card games
      ilcg = new Output("Me too!");
      ilcg.setKeywords(a("yes"), a("do"), a("love"));
      ilcg.setNotKeywords(a("don't", "like"), a("dislike"));
      ilcg.setAdditionalDisplay("What is your favorite card game?");

      // reaction to player's favorite card game
      fav = new Output(input + "? That's a cool game!"); // look for right word
      fav.setKeywords(null);
      fav.setAdditionalDisplay("Would you like to play the card game 31 with me?");

      // reaction if player doesn't like card games
      ihcg = new Output("Really? But they are so fun!");
      ihcg.setKeywords(a("don't"), a("no"), a("dislike"), a("hate"));
      ihcg.setNotKeywords(a("don't", "hate"), a("don't", "dislike"));
      ihcg.setAdditionalDisplay("Have you heard of the card game 31?");

      // reaction if player doesn't know 31: explain the rules
      //expl = new Output("Don't worry, I will explain them to you.");

      // reaction if player knows 31: ask about playing
      ywp = new Output("Would you like to play the card game 31 with me?");
      ywp.setKeywords(a("yes"), a("i","have"));
      ywp.setNotKeywords(a("haven't"));

      // reaction if player doesn't want to play: more smalltalk?

      // reaction if player wants to play: ask about explaining the rules
      askIfExplain = new Output("Do you know the rules or would you like me to explain them?");
      askIfExplain.setKeywords(a("dummy"), a("yes"), a("ok"), a("sure"), a("let's", "do", "it"));

      // explain the rules
      explain = new Output("Ok, these are the rules:");
      explain.setKeywords(a("don't", "know", "rules"), a("explain"));
      String rule1 = new String("You have a hand of 3 cards.");
      String rule2 = new String("Your goal is to get a total as close to 31 as possible.");
      String rule3 = new String("Only cards of the same suit count together.");
      String rule4 = new String("All face cards count as 10. Ace counts as 11.");
      String rule5 = new String("When it is your turn you can \"knock\" if you think you can beat the other player's score.");
      String rule6 = new String("After a \"knock\" the other player gets one more round.");
      String rule7 = new String("The player who is closer to 31 wins the round.");
      String question = new String("Are you ready to play?");
      explain.setAdditionalDisplay(rule1, rule2, rule3, rule4, rule5, rule6, rule7, question);

      // reaction if player ready to play: ask about symbols
      symbolCheck  = new Output("One more question before we start:");
      symbolCheck.setKeywords(a("yes"));
      symbolCheck.setAdditionalDisplay("Can you see these symbols or just three squares?: " + (char) 0x2805 + (char)0x235A + (char)0x2661);

      // Output: can you see this symbol: ... ?

      symbolCheckY = new Output("Alright, thanks.");
      symbolCheckY.setKeywords(a("yes"),a("i", "can"), a("i", "do"), a("no", "square"), a("no", "squares"), a("symbols"));
      symbolCheckY.setNotKeywords(a("can", "not"));

      symbolCheckWhat = new Output("\"yes\" as in you don't see three squares?");
      symbolCheckWhat.setKeywords(a("yes"));

      symbolCheckN = new Output("Good, thanks");
      symbolCheckN.setKeywords( a("no"), a("correct"), a("can't"), a("don't"), a("squares"), a("square"));
      symbolCheckN.setNotKeywords(a("no", "square"), a("no", "squares"));


      /*clarifyAsk = new Output("Which rule would you like me to clarify?");
      String clarifyAskTriggers[][] = {{"yes"}, {"do"}};
      clarifyAsk.setKeywords(clarifyAskTriggers);*/

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
      ihcg.setPossibleOutputs(getaPR(), ywp, explain);
      fav.setPossibleOutputs(getaPR(), askIfExplain);
      ywp.setPossibleOutputs(getaPR(), askIfExplain);
      askIfExplain.setPossibleOutputs(getaPR(),explain, symbolCheck);
      explain.setPossibleOutputs(getaPR(),symbolCheck);
      symbolCheck.setPossibleOutputs(getaPR(), symbolCheckY, symbolCheckWhat, symbolCheckN);
      afterGame.setPossibleOutputs(wyn);   // fix



   }


   public void startConv() {
      //settingPossibleOutputs();
      // this might be the loop (looping through Output objects, 'welcome' being the first)
      int counter = 0;



      output = welcome; //.copy();   // first output
      output.print();


      while(counter < 6 && !startGameT) {


         Output firstOut = output.copy();                         // creates a copy of the current output called firstOut
         Output[] firstOutPoss = output.getPossibleOutputs();     // gets the possible outputs of the current output
         Output prevOutput;                                       // previous output (?)
         do {
            output.setPossibleOutputs(firstOutPoss);
            updateReplies();
                                                  // prints the current output
            String input = readString();                          // reads player's input
            prevOutput = output.copy();                           // saves current output as previous output
            output = output.getNext(input);                       // updates the current output based on player's input

            normSpecialOutput(output, input);


            updateReplies();
                         // sets the new output's possible outputs to the possible outputs of the first output

            output.print();
            specialOutput(output);

            //System.out.println(firstOut.isInPossibleOutputs(output));   // prints if the output is in the possible outputs of the first output (for testing i assume)
         } while (!output.equals(prevOutput.getErrOutput()) && !firstOut.isInPossibleOutputs(output));   // loop while the output is neither the error output of the previous output
         // nor in the possible outputs of the first output
         //return output;


         /*
         updateReplies();
         String input = readString();
         normSpecialOutput(output, input);
         //Output previous = output.copy();
         output = output.getNext(input);

         //output.setPrevious(previous);
         output.print();

         specialOutput(output);
*/
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
      intro.setReply("Nice to meet you, " + userName + "!");
      hyd.setReply("How are you " + userName + "?");
   }


   public String getUserName(){
      return userName;
   }

   public boolean startedGame(){
      return startGameT;
   }

}
