import java.util.*;

public class Main {

    public static void main(String[] args) {
        boolean gam, con, conC;
        gam = false; con = false; conC = false;
        Game game = new Game();
        conC = true;


        if (conC){
            NormConv conv = new NormConv();
            conv.startConv();
            if (conv.startedGame()) {
                game.setUserName(conv.getUserName());
                game.setuni(conv.getUni());
                game.playGame();
            }
            if (game.stoppedGame()) conv.endConv();
        }


        if (gam) {
            int ave = 0;
            int iter = 1;
            for (int i = 0; i < iter; i++) {
                Game games = new Game();
                games.playGame();
                //game.getTurnNr();
                ave += games.getTurnNr();
            }

            ave = ave / iter;

            System.out.println("average winning: " + ave);
        }


        // welcome/starting message
        //System.out.println("Welcome to Blackjack!");
        //System.out.println("Do you know the rules or would you like me to explain them?");
        /*
        String answer = readString();
        Case case1 = new Case(answer);
        Reply reply1 = new Reply(case1.getTrigger());
         */


        if (con) {
            Output welcome = new Output("welcome");

            Output wyn = new Output("What's your name?");

            Output hyd = new Output("How are you?");
            String hydTriggers[][] = {{"hello"}, {"hi"}, {"good", "day"}};
            hyd.setKeywords(hydTriggers);

            Output nth = new Output("Good to hear. Let's play blackjack!");
            String nthTriggers[][] = {{"good"}, {"not", "bad"}};
            nth.setNotKeywords(a("not", "good"));
            nth.setKeywords(nthTriggers);

            Output ohno = new Output("Sorry about that. Let's play blackjack to cheer you up!");
            String ohnoTriggers[][] = {{"bad"}, {"not", "good"}};
            ohno.setNotKeywords(a("not", "bad"));
            ohno.setKeywords(ohnoTriggers);

            Output iag = new Output("I'm good, thanks for asking! Let's play blackjack to cheer you up!");
            String iagTriggers[][] = {{"bad", "you"}, {"not", "good", "you"}};
            iag.setNotKeywords(a("not", "bad"));
            iag.setKeywords(iagTriggers);

            Output igt = new Output("I'm good too. Thanks for asking! Let's play blackjack!");
            String igtTriggers[][] = {{"good", "you"}, {"great", "you"}};
            igt.setKeywords(igtTriggers);

            //Output ywp = new Output("Wanna play blackjack?");

            // Output: can you see this symbol: ... ?
            Output symbolCheck = new Output("Can you see this symbol: ?");

            Output askIfExplain = new Output("Do you know the rules or would you like me to explain them?");
            String askIfExplainTriggers[][] = {{"ok"}, {"sure"}, {"let's", "do", "it"}};
            askIfExplain.setKeywords(askIfExplainTriggers);

            Output explain = new Output("Ok, these are the rules:");
            String explainTriggers[][] = {{"explain"}, {"don't", "know", "rules"}};
            explain.setKeywords(explainTriggers);
            String rule1 = new String("The goal of blackjack is to beat the dealer's hand without going over 21.");
            String rule2 = new String("Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.");
            String question = new String("Do you need clarification?");
            explain.setAdditionalDisplay(rule1, rule2, question);

            Output startGame = new Output("Let's start the game then.");
            String startGameTriggers[][] = {{"explain", "don't"}, {"know", "rules"}};
            startGame.setKeywords(startGameTriggers);

            Output clarifyAsk = new Output("Which rule would you like me to clarify?");
            String clarifyAskTriggers[][] = {{"yes"}, {"do"}};
            clarifyAsk.setKeywords(clarifyAskTriggers);

            //Output rule1 = new Output("");

            //Output possibleReplies[] = {wyn, hyd, iag, igt, ohno, nth, askIfExplain, noexplain, explain};
            Output allReplies[] = {wyn, hyd, iag, igt, ohno, nth, askIfExplain, startGame, explain, clarifyAsk};
            Output[] possibleReplies = allReplies;
            //welcome.setPossibleOutputs(possibleReplies);

            wyn.setPossibleOutputs(hyd);
            hyd.setPossibleOutputs(nth, ohno, iag, igt);
            nth.setPossibleOutputs(askIfExplain);
            ohno.setPossibleOutputs(askIfExplain);
            iag.setPossibleOutputs(askIfExplain);
            igt.setPossibleOutputs(askIfExplain);
            //ywp.setPossibleOutputs(askIfExplain);
            askIfExplain.setPossibleOutputs(explain, startGame);
            explain.setPossibleOutputs(clarifyAsk, startGame);

            //System.out.println(Arrays.toString((welcome.defaultKeywords)));


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
                counter++;
            }
            // only read new input if input needed, otherwise just get next output (?)
            // save current output, possible replies need to be updated according to output
        }
    }

    public static String[] a(String... strings){
        String[] a = new String[strings.length];
        for (int i = 0; i < strings.length; i++){
            a[i] = strings[i];
        }
        return a;
    }

    // method that reads input as a string
    public static String readString() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    // method that converts String into String[]
    public static String[] split(String s) {
        return s.toLowerCase().split(" ");
    }
}

// account for synonyms, dont = don't etc.
// add user's name --> store as variable



/*
- why chosen subject
-
 */

 /*O
 Output hi = new Output("hello")
   Output wyd = new Output("what are you doing")
   Output bye = new Output("ok goodbye")
   wyd.setKeywords("hello")
   bye.setKeywords("i dont like you", "go away")
   setReply("i dont like you") -> this.reply
   getNext("i dont like you Liza") -> "ok goodbye"

   example: How are you?
   Output hyd = new Output("how are you, *name*?")

      Output onw = new Output("oh no, why?)
      Output gth = new Output("Good to hear")


      (Main) hyd.setKeywords("good","fine",  "bad")           (all the possible answers from the player, String[]s)
      (Main) hyd.setTriggerKeys("hello")
      (Main) gth.setTriggerKeys("good", "fine", ["very", "good"])

    (Main) String[] of responses.
    (Main) String[] of correspondingTriggers


 public static method(Output current){
  loop starts
      Scanner in
      (Main) ArrayList <> option1 = hyd.setReply("good", "fine", "good to hear")

      (Main) Output onw = hyd.setReply(*triggers*"bad" , *response* "oh no, why?")

      (Main) hyd.getNext(input, Output onw, Output gth)   -> Output

  loop ends

      public Question getNext(String input, Output ... output ){
      Output reply = new ....
      keywords = what does input contain()
      ..... checks output[i].triggers
      if keywords contains output[i].triggers
        -> reply = ...
      reply.printReply()
      return reply


      }



      setReply{
      this.getTriggerKeys // e.g ("good", "fine")










    */