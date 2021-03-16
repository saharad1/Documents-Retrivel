import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;

/**
 * The class is designed by the Singleton Design Pattern.
 * The class's aim is to conduct the process of the information retrieval.
 * The documents are insensitive to Uppercase chars.
 */
public class InvertedIndexCaseInsensitive extends AbstractInvertedIndex {
    protected static InvertedIndexCaseInsensitive my_inverted_Insensitive;
    protected HashMap<String, TreeSet<String>> insensitive_map = new HashMap<>();

    /**
     * The constructor used for the Singleton pattern
     */
    protected InvertedIndexCaseInsensitive() {
    }

    /**
     * Responsible to create only one object.
     *
     * @return The inverted index object.
     */
    public static InvertedIndexCaseInsensitive getInstance() {
        if (my_inverted_Insensitive == null) {
            my_inverted_Insensitive = new InvertedIndexCaseInsensitive();
            System.out.println("New CaseInsensitive index is created");
        } else {
            System.out.println("You already have a CaseInsensitive index");
        }
        return my_inverted_Insensitive;
    }

    /**
     * Also converts the words to lowercase.
     *
     * @param listFiles training documents.
     */
    @Override
    protected void buildInvertedIndex(File[] listFiles) {
        for (File file : listFiles) {
            List<String> ap_list = Utils.readLines(file);
            for (String line : ap_list) {
                String[] array_words = Utils.splitBySpace(line);
                for (String word : array_words) {
                    word = word.toLowerCase();
                    fillMap(ap_list, word, insensitive_map);
                }
            }
        }
    }

    /**
     * Also converts the words to lowercase.
     *
     * @param query the required words.
     * @return
     */
    @Override
    protected TreeSet<String> runQuery(String query) {
        Stack<TreeSet<String>> vec_stack = new Stack<>();
        String[] words = Utils.splitBySpace(query);
        for (String word : words) {
            switch (word) {
                case "OR":
                    operatorOr(vec_stack);
                    break;
                case "AND":
                    operatorAnd(vec_stack);
                    break;
                case "NOT":
                    operatorNot(vec_stack);
                    break;
                default:
                    word = word.toLowerCase();
                    vec_stack.push(insensitive_map.get(word));
            }
        }
        return vec_stack.pop();
    }
}



