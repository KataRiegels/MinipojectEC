import java.util.*;

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




    }


}

/*
- why chosen subject
-
 */

