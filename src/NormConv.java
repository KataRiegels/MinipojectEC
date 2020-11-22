import java.util.Scanner;

//public class NormConv extends Conversation {
   public class NormConv {
   Game game;
   Output welcome,  wyn, yni, yniN, yniY, hyd, nth, ohno, igt, iag, intro, ilcg, ihcg, fav,
           lp, ywp, symbolCheck, askIfExplain, explain, why, wsp,
           startGame, stopGame, afterGame,
           symbolCheckY, symbolCheckN, symbolCheckWhat;
   Output output;
   String userName, input;
   boolean startGameT, stopGameT, uni;

   public NormConv(){
      game = new Game();

      // starting message
      welcome = new Output("Welcome!");

      // first question: ask for name
      wyn = new Output("What's your first name?");
      wyn.setKeywords(null);

      // reaction: ask if name correct
      yni = new Output("Your name is " + userName + "?"); // somehow filter all words that could not be names..
      yni.setKeywords(a("dummy"));
      yni.setNotKeywords(a("i'm"), a("my"), a("name"), a("is"));

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

      // reaction if player feels good
      nth = new Output("Good to hear.");
      nth.setKeywords(a("good"), a("not", "bad"));
      nth.setNotKeywords(a("not", "good"));
      nth.setAdditionalDisplay("Do you like card games?");

      // reaction if player feels bad
      ohno = new Output("I'm sorry to hear that.");
      ohno.setKeywords(a("bad"), a("not", "good"));
      ohno.setNotKeywords(a("not", "bad"));
      ohno.setAdditionalDisplay("Why are you not feeling good?");

      // reaction if player feels bad and asks how Liza is feeling
      iag = new Output("I'm good, thanks for asking.");
      iag.setKeywords(a("bad", "you"), a("not", "good", "you"));
      iag.setNotKeywords(a("not", "bad"));
      iag.setAdditionalDisplay("Why are you not feeling good?");

      // reaction if player feels good and asks how Liza is feeling
      igt = new Output("I'm good too. Thanks for asking!");
      igt.setKeywords(a("good", "you"), a("great", "you"));
      igt.setNotKeywords(a("not", "good"), a("not", "great"));
      igt.setAdditionalDisplay("Do you like card games?");

      // next question: ask to play 31
      lp = new Output("That sounds rough.");
      lp.setKeywords(a("dummy"));
      lp.setAdditionalDisplay("Maybe a game of 31 would cheer you up?");

      // reaction if player likes card games
      ilcg = new Output("Me too!");
      ilcg.setKeywords(a("yes"), a("do"), a("love"));
      ilcg.setNotKeywords(a("don't", "like"), a("dislike"));
      ilcg.setAdditionalDisplay("What is your favorite card game?");

      // reaction to player's favorite card game
      fav = new Output(input + "? That sounds cool!"); // look for right word
      fav.setKeywords(null);
      fav.setAdditionalDisplay("Would you like to play the card game 31 with me?");

      // reaction if player doesn't like card games
      ihcg = new Output("Really? But they are so fun!");
      ihcg.setKeywords(a("don't"), a("no"), a("dislike"), a("hate"));
      ihcg.setNotKeywords(a("don't", "hate"), a("don't", "dislike"));
      ihcg.setAdditionalDisplay("Have you heard of the card game 31?");

      // reaction if player doesn't know 31: ask about playing
      wsp = new Output("It's my favorite card game.");
      wsp.setKeywords(a("haven't"), a("no"));
      wsp.setNotKeywords(a("yes"));
      wsp.setAdditionalDisplay("We should play it!");

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
      explain.setKeywords(a("don't","explain"));
      String rule1 = new String("You have a hand of 3 cards.");
      String rule2 = new String("Your goal is to get a total as close to 31 as possible.");
      String rule3 = new String("Only cards of the same suit count together.");
      String rule4 = new String("All face cards count as 10. Ace counts as 11.");
      String rule5 = new String("When it is your turn you can \"knock\" if you think you can beat the other player's score.");
      String rule6 = new String("After a \"knock\" the other player gets one more round.");
      String rule7 = new String("The player who is closer to 31 wins the round.");
      String question = new String("Are you ready to play?");
      explain.setAdditionalDisplay(rule1, rule2, rule3, rule4, rule5, rule6, rule7, question);

      // reaction if player not ready to play: ask why
      why = new Output("What's the problem?");
      why.setKeywords(a("no"), a("not"));
      // just play anyways


      // reaction if player ready to play: ask about symbols
      symbolCheck  = new Output("One more question before we start:");
      symbolCheck.setKeywords(a("yes"));
      symbolCheck.setAdditionalDisplay("Can you see these symbols or just three squares?: " + (char) 0x2805 + (char)0x235A + (char)0x2661);

      // reaction if player can see symbols
      symbolCheckY = new Output("Alright, thanks.");
      symbolCheckY.setKeywords(a("yes"),a("i", "can"), a("i", "do"), a("no", "square"), a("no", "squares"), a("symbols"));
      symbolCheckY.setNotKeywords(a("can", "not"));

      // reaction to ambiguous reply
      symbolCheckWhat = new Output("\"yes\" as in you don't see three squares?");
      symbolCheckWhat.setKeywords(a("yes"));

      // reaction if player cannot see symbols
      symbolCheckN = new Output("Good, thanks");
      symbolCheckN.setKeywords( a("no"), a("correct"), a("can't"), a("don't"), a("squares"), a("square"));
      symbolCheckN.setNotKeywords(a("no", "square"), a("no", "squares"));

      // starting the game
      startGame = new Output("Let's start the game then.");
      startGame.setKeywords( a("explain", "don't"), a("start", "game"), a("know", "rules"));

      afterGame = new Output("Well played!");
      uni = true;
      settingPossibleOutputs();
   }



   public void settingPossibleOutputs(){

      welcome.setPossibleOutputs(wyn);
      wyn.setPossibleOutputs(yni);
      yni.setPossibleOutputs(startGame, intro, yniN);
      yniN.setPossibleOutputs(startGame, yni);
      intro.setPossibleOutputs(startGame, hyd);
      hyd.setPossibleOutputs(startGame,nth, ohno, iag, igt);
      nth.setPossibleOutputs(startGame, ihcg, ilcg);
      ohno.setPossibleOutputs(startGame, lp);
      iag.setPossibleOutputs(startGame, lp);
      lp.setPossibleOutputs(askIfExplain);
      igt.setPossibleOutputs(startGame, ihcg, ilcg);
      ilcg.setPossibleOutputs(startGame, fav);
      ihcg.setPossibleOutputs(startGame, ywp, wsp);
      wsp.setPossibleOutputs(startGame, askIfExplain);
      fav.setPossibleOutputs(startGame, askIfExplain);
      ywp.setPossibleOutputs(startGame, askIfExplain);
      askIfExplain.setPossibleOutputs(startGame,explain, symbolCheck);
      explain.setPossibleOutputs(startGame,symbolCheck, why);
      symbolCheck.setPossibleOutputs(symbolCheckWhat, symbolCheckN,  symbolCheckY);
      symbolCheckWhat.setPossibleOutputs(symbolCheckY,symbolCheckN);
      symbolCheckN.setPossibleOutputs(startGame);
      symbolCheckY.setPossibleOutputs(startGame);
   }


   public void startConv() {
      int counter = 0;

      output = welcome;   // first output
      output.print();

      while(counter < 20 && !startGameT) {


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
            //specialOutput(output);
            normSpecialOutput(output, input);
            //
            //System.out.println(firstOut.isInPossibleOutputs(output));   // prints if the output is in the possible outputs of the first output (for testing i assume)
         } while (!output.equals(prevOutput.getErrOutput()) && !firstOut.isInPossibleOutputs(output));   // loop while the output is neither the error output of the previous output
         // nor in the possible outputs of the first output
         //return output;

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
         this.output = startGame;
         return;
      }
      if (output == symbolCheckY) {
         output.print();
         uni = true;
         this.output = startGame;
         return;
      }
      if (output == yni)         {
         userName = wyn.getPart(input);
         userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
      }
      if (output == startGame){
         startGameT = true;
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
