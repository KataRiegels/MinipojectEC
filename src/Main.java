public class Main {

    public static void main(String[] args) {
        Game game = new Game();
            NormConv conv = new NormConv();
            conv.startConv();

            if (conv.startedGame()) {
                game.setUserName(conv.getUserName());
                game.setUni(conv.getUni());
                game.playGame();
                conv.endConv();
            }


    }

}