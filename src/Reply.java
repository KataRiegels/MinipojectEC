public class Reply {
    // list of possible replies
    private String[] replies = new String[] {
            "I don't know what to say.",
            "Okay, I will explain the rules.",
            "Let's start the game then."
    };

    private String reply;
    private int trigger;

    public Reply(int caseNumber) {
        trigger = caseNumber;
        printReply(trigger);
    }

    // method that prints reply for case
    public void printReply(int t) {
        reply = replies[t];
        System.out.println(reply);
    }
}