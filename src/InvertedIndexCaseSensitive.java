import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;

/**
 * The class is designed by the Singleton Design Pattern.
 * The class's aim is to conduct the process of the information retrieval.
 * The documents are sensitive to Uppercase chars.
 */
public class InvertedIndexCaseSensitive extends AbstractInvertedIndex {
    protected static InvertedIndexCaseSensitive my_inverted;
    protected HashMap<String, TreeSet<String>> sensitive_map = new HashMap<>();

    /**
     * The constructor used for the Singleton pattern
     */
    protected InvertedIndexCaseSensitive() {
    }

    /**
     * Responsible to create only one object.
     *
     * @return The inverted index object.
     */
    public static InvertedIndexCaseSensitive getInstance() {
        if (my_inverted == null) {
            my_inverted = new InvertedIndexCaseSensitive();
            System.out.println("New CaseSensitive index is created");
        } else {
            System.out.println("You already have a CaseSensitive index");
        }
        return my_inverted;
    }

    /**
     * Sensitive to uppercase in the document.
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
                    InvertedIndexCaseInsensitive.fillMap(ap_list, word, sensitive_map);
                }
            }
        }
    }

    /**
     * Sensitive to uppercase in the query.
     *
     * @param query the required words.
     * @return
     */
    @Override
    protected TreeSet<String> runQuery(String query) {
        Stack<TreeSet<String>> my_stack = new Stack<>();
        String[] words = Utils.splitBySpace(query);
        for (String word : words) {
            switch (word) {
                case "OR":
                    operatorOr(my_stack);
                    break;
                case "AND":
                    operatorAnd(my_stack);
                    break;
                case "NOT":
                    operatorNot(my_stack);
                    break;
                default:
                    my_stack.push(sensitive_map.get(word));
            }
        }
        return my_stack.pop();
    }
}
