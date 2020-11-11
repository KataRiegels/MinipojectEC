import java.util.Arrays;

// for defining which answers trigger which reply
public class TriggerMap {
    // array of all the conditions with a response
    //String[] keys = {"explain", "don't", "know", "rules"};
    public int[][] conditions;
    int[] explain = new int[]{0};
    int[] explain_dont = new int[]{0,1};
    int[] explain_rules = new int[]{0,3};
    int[] dont_know_rules = new int[]{1,2,3};
    int[] explain_dont_rules = new int[]{0,1,3};
    // array for the corresponding triggers
    public int[] trigger;

    public TriggerMap() {
        this.conditions = new int[][]{explain, explain_dont, explain_rules, dont_know_rules, explain_dont_rules};
        trigger = new int[this.conditions.length];
        for (int i = 0; i < this.conditions.length; i++) {
            if      (conditions[i] == explain)            trigger[i] = 1;
            else if (conditions[i] == explain_dont)       trigger[i] = 2;
            else if (conditions[i] == explain_rules)      trigger[i] = 1;
            else if (conditions[i] == dont_know_rules)    trigger[i] = 1;
            else if (conditions[i] == explain_dont_rules) trigger[i] = 2;
            else trigger[i] = 0;
        }
    }

    public int[][] getConditions() {
        return conditions;
    }

    public int[] getCondition(int i) {
        return conditions[i];
    }

    public int getTrigger(int i) {
        return trigger[i];
    }

    public void show() {
        System.out.println("This is the condition -> trigger map:");
        for (int i = 0; i < conditions.length; i++) {
            //System.out.println(Arrays.toString(conditions[i]) + " " + trigger[i]);
            System.out.printf("%10s", Arrays.toString(conditions[i]));
            System.out.printf("%4d", trigger[i]);
            System.out.println();
        }
    }
}
