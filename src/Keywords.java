import java.util.ArrayList;

public class Keywords {
    private ArrayList<Keyword> keywords;
    // all the keywords we want
    // before game
    String[] keys = {"explain", "don't", "know", "rules"};

    public Keywords() {
        keywords = new ArrayList<Keyword>();
        for (String k : keys) {
            addKeyword(k);
        }
    }

    public void addKeyword(String s) {
        keywords.add(new Keyword(s));
    }

    public int length() {
        return keywords.size();
    }

    /**
    public Keyword get(int index) {
        return keywords.get(index);
    }
    */
    public String getAsString(int index) {
        return keywords.get(index).getString();
    }
}
