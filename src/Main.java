import java.util.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {



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



        /*
        // welcome/starting message
        System.out.println("Welcome to Blackjack!");
        System.out.println("Do you know the rules or would you like me to explain them?");

        String answer = readString();
        Case case1 = new Case(answer);
        Reply reply1 = new Reply(case1.getTrigger());

         */

    }

    // method that reads input as a string
    public static String readString() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
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