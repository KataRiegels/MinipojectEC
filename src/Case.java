import java.util.*;

public class Case {
    // list of keywords
    private String[] keywords = {"explain", "don't", "know", "rules"};
    private ArrayList<Integer> explain, explain_dont, explain_rules, dont_know_rules, explain_dont_rules, know_rules;

    // list of cases
    private ArrayList<ArrayList<Integer>> conditions;

    private String input;
    private String[] splitInput;
    private ArrayList<Integer> containedKeywords;
    private int[] caseNumbers;
    private int trigger;

    public Case(String s) {
        // initializations
        input = s;
        splitInput = split(input);
        conditions = defineCases();
        defineCaseNumbers();

        // actions / computations
        containedKeywords = contains();
        trigger = findCaseNumber();

        // tests
        //System.out.println(keywords.length);
        //printStringArray(splitInput);
        //System.out.println(conditions);
        //System.out.println(contains());
        //System.out.println(trigger);
    }

    public int getTrigger() {
        return trigger;
    }

    // method that converts String into String[]
    public static String[] split(String s) {
        return s.toLowerCase().split(" ");
    }

    // method that defines cases
    public ArrayList<ArrayList<Integer>> defineCases(){
        // maybe loop through all the keywords and create all possible combinations
        // defining each case
        explain = defineCase("explain");
        explain_dont = defineCase("explain", "don't");
        explain_rules = defineCase("explain", "rules");
        dont_know_rules = defineCase("don't", "know", "rules");
        explain_dont_rules = defineCase("explain", "don't", "rules");
        know_rules = defineCase("know", "rules");

        // creating a list of all conditions
        conditions = new ArrayList<>();
        conditions.add(explain);
        conditions.add(explain_dont);
        conditions.add(explain_rules);
        conditions.add(dont_know_rules);
        conditions.add(explain_dont_rules);

        return conditions;
    }

    // method for defining a case
    public ArrayList<Integer> defineCase(String ...strings) {
        ArrayList<Integer> definition = new ArrayList<>();
        for (String s : strings) {
            definition.add(indexOf(s));
        }
        return definition;
    }

    // method that returns index of a keyword
    public int indexOf(String s) {
        return Arrays.asList(keywords).indexOf(s);
    }

    // method that checks for keywords, i.e. case
    public ArrayList<Integer> contains() {
        ArrayList<Integer> indices = new ArrayList<>();       // create ArrayList to store results
        for (int i = 0; i < keywords.length; i++) {
            for (String word : splitInput) {
                if (word.equals(keywords[i])) {                // adding the index of a keyword when found
                    indices.add(i);
                }
            }
        }
        return indices;
    }

    // method that defines case number
    public void defineCaseNumbers() {
        caseNumbers = new int[conditions.size()];
        for (int i = 0; i < conditions.size(); i++) {
            if (conditions.get(i) == explain) caseNumbers[i] = 1;
            else if (conditions.get(i).equals(explain_rules)) caseNumbers[i] = 1;
            else if (conditions.get(i).equals(dont_know_rules)) caseNumbers[i] = 1;
            else if (conditions.get(i).equals(explain_dont)) caseNumbers[i] = 2;
            else if (conditions.get(i).equals(explain_dont_rules)) caseNumbers[i] = 2;
            else if (conditions.get(i).equals(know_rules)) caseNumbers[i] = 2;
        }
        //System.out.println(Arrays.toString(caseNumbers));
    }

    // method that finds case number
    public int findCaseNumber() {
        int n = 0;
        for (int i = 0; i < conditions.size(); i++) {
            if (containedKeywords.equals(conditions.get(i))) {
                n = caseNumbers[i];
            }
        }
        return n;
    }

    // method that prints each word in a string[] in a separate line
    public static void printStringArray(String[] words) {
        for (String word : words){
            System.out.println(word);
        }
    }
}
