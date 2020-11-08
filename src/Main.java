import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int handsize = 3;

        Game game = new Game();

        game.playGame();

        /*

        Deck deck    = new Deck("deck");
        Hand player  = new Hand("Your hand");
        Hand Eliza   = new Hand("Eliza's hand");
        Pile stock   = new Pile("Stock");
        Pile discard = new Pile("Discard");


        //Cards deck = new Cards();



        //deck.shuffle();
        stock.createStock(deck);

        player.starter(stock);
        Eliza.starter(stock);

        System.out.print("Top card in discard pile: ");
        discard.turnCard(stock);
        discard.topCard();

        System.out.print("Your hand is: ");
        player.printHand();
        System.out.println("You do you want to draw from stock or discard?");
        String drawAnswer = in.nextLine();



        if (drawAnswer.contains("stock")){
            Cards Draw = player.draw(stock);
            System.out.print("You drew: ");
            Draw.printCardsSym();

        } else if (drawAnswer.contains("stock")) {
            System.out.print("You drew: ");
            player.draw(stock).printCardsSym();
        }



        System.out.print("Your hand: ");
        player.printHand();

        System.out.println();







        //System.out.println(stock.size());
        printAll(discard, player, Eliza);

        Eliza.draw(stock);
        //stock.printCardsSym();
        //player.printHand();
        //Eliza.printHand();

        player.play(discard, 1);
        player.printHand();
    //    stock.printCardsSym();



    }

    public void printAll(Pile discard, Hand player, Hand Eliza){
        System.out.print("Top card on discard pile: ");
        discard.topCard();
        System.out.print("Eliza's hand: ");
        Eliza.printHand();
        System.out.print("Player's hand: ");
        player.printHand();


         */
    }




}














        /*
        public static String cardFinder(String[] keywords){
            //keyword = "123woods";
            for (int i = 0; i > keywords.length; i++) {
                Boolean found = Arrays.asList(string.split(" ")).contains(keywords[i]);
                if (found) {
                    System.out.println("Keyword matched the string");
                    int foundIndex = i;
                }
            }
            return keywords[i];
    */




/*
- Find a way to read and recognize Strings
- if *a word* and *a word* then *sentence*
- Error response
- keyword rank stack
- read numbers
- class for sentences. And array of possible answers. Array that you search through.
- detect repeat
- construct sentence containing keyword

- Keyword class
    String word
    int sentence *(index)*

 Keyword k = new Keyword ("sleep",2) .... so if we get keyword "sleep", we reply with Sentence 2
    k getIndex()

- first: show dialog - receive data from dialog - print out data in new dialog
- start with static sentences
- keywords has references to relevant sentences.

Sentence[] answers = new Sentence(MAX);

private final int max = 20;


PDF hand in!


 */