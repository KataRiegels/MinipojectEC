import java.util.*;

public class Main {

    public static void main(String[] args) {




        /*
        int ave = 0;
        int iter = 1;
        for (int i = 0; i < iter; i++) {
            Game game = new Game();
            game.playGame();
            //game.getTurnNr();
            ave += game.getTurnNr();
        }

        ave = ave/iter;

        System.out.println("average winning: " + ave);
        */


        // welcome/starting message
        System.out.println("Welcome to Blackjack!");
        System.out.println("Do you know the rules or would you like me to explain them?");
        /*
        String answer = readString();
        Case case1 = new Case(answer);
        Reply reply1 = new Reply(case1.getTrigger());
         */

        Output welcome = new Output("welcome");

        Output explain = new Output("Ok, I'll explain the rules then.");
        String explainTrigger1[] = {"explain"};
        String explainTrigger2[] = {"know", "rules"};
        String explainTriggers[][] = {explainTrigger1, explainTrigger2};
        explain.setKeyword(explainTriggers);

        Output noexplain = new Output("Let's start the game then.");
        String noexplainTrigger1[] = {"explain", "don't"};
        String noexplainTrigger2[] = {"don't", "know", "rules"};
        String noexplainTriggers[][] = {noexplainTrigger1, noexplainTrigger2};
        noexplain.setKeyword(noexplainTriggers);

        Output possibleReplies[] = {explain, noexplain};

        // this might be the loop (looping through Output objects, 'welcome' being the first)
        String input = readString();
        welcome.getNext(input, possibleReplies);

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