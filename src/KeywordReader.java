import java.util.ArrayList;

public class KeywordReader {
    // instance variables
    private String inputSentence;   // what the player types
    public Keywords keywords;     // all the keywords to check for
    public int trigger;            // trigger for the corresponding response
    public String[] input;

    // constructor
    public KeywordReader(String s, Keywords keys) {
        this.inputSentence = s;
        this.keywords = keys;
        this.input = split(inputSentence);
        this.trigger = computeTrigger();
    }

    // methods
    // splitting sentence/string into string[]
    public static String[] split(String s) {
        String[] words = s.toLowerCase().split(" ");
        return words;
    }

    // checking which keywords are contained
    public ArrayList<Integer> containsKeywords() {
        ArrayList<Integer> indices = new ArrayList<>();       // create ArrayList to store results
        for (int i = 0; i < keywords.length(); i++) {
            for (String word : input) {
                if (word.equals(keywords.getAsString(i))) {                      // adding the index of a keyword when found
                    indices.add(i);
                }
            }
        }
        return indices;
    }

    // method that prints which keywords are contained in the player's response
    public void showContainedKeywords() {                                   // for testing mainly i think
        ArrayList<Integer> indices = new ArrayList<Integer>();
        indices = this.containsKeywords();
        System.out.print("The player's response contains the keywords:");
        for (int index : indices) {
            System.out.print(" " + keywords.getAsString(index));
        }
        System.out.println(".");
    }

    // method that computes trigger for response/reply

    public int computeTrigger() {
        int t = 0;
        // get a trigger map
        TriggerMap map = new TriggerMap();
        map.show();
        // look for which condition is fulfilled
        int[] condition = toIntArray(containsKeywords());
        for (int i = 0; i < map.getConditions().length; i++) {
            if (map.getCondition(i) == condition) {
                t = map.getTrigger(i);                              // for some reason this is not executed
            }
        }
        return t;
    }



    // method that converts an integer arraylist to an int[]
    public static int[] toIntArray(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
