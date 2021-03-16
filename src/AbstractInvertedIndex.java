import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Crates the format that the Inverted index should be.
 */
public abstract class AbstractInvertedIndex {

    /**
     * Creates the inverted index by using a container that fits word to vectors.
     *
     * @param listFiles training documents.
     */
    protected abstract void buildInvertedIndex(File[] listFiles);

    /**
     * according to the words given it manages the information retrieval process.
     *
     * @param query the required words.
     * @return Vector that consists the relevant documents names.
     */
    protected abstract TreeSet<String> runQuery(String query);

    /**
     * Part of the process of crating the data container.
     *
     * @param ap_list         a list of the lines of a given document.
     * @param word            the word we want to make as a key or update the vector.
     * @param insensitive_map the data container we use for the documents names.
     */
    protected static void fillMap(List<String> ap_list, String word, HashMap<String, TreeSet<String>> insensitive_map) {
        if (insensitive_map.containsKey(word)) {
            insensitive_map.get(word).add(Utils.substringBetween(ap_list.get(1), "<DOCNO> ", " </DOCNO>"));
        } else {
            TreeSet<String> tree_helper = new TreeSet<>();
            tree_helper.add(Utils.substringBetween(ap_list.get(1), "<DOCNO> ", " </DOCNO>"));
            insensitive_map.put(word, tree_helper);
        }
    }

    /**
     * Conducts an 'OR' operation between two vectors
     *
     * @param vec_stack the data frame for the operator.
     */
    protected static void operatorOr(Stack<TreeSet<String>> vec_stack) {
        TreeSet<String> vec1 = vec_stack.pop();
        TreeSet<String> vec2 = vec_stack.pop();
        vec1.addAll(vec2);
        vec_stack.push(vec1);
    }

    /**
     * Conducts an 'AND' operation between two vectors
     *
     * @param vec_stack the data frame for the operator.
     */
    protected static void operatorAnd(Stack<TreeSet<String>> vec_stack) {
        TreeSet<String> vec1 = vec_stack.pop();
        TreeSet<String> vec2 = vec_stack.pop();
        TreeSet<String> final_tree = new TreeSet<>();
        for (String word : vec1) {
            if (vec2.contains(word)) {
                final_tree.add(word);
            }
        }
        vec_stack.push(final_tree);
    }

    /**
     * Conducts an 'NOT' operation between two vectors
     *
     * @param vec_stack the data frame for the operator.
     */
    protected static void operatorNot(Stack<TreeSet<String>> vec_stack) {
        TreeSet<String> vec1 = vec_stack.pop();
        TreeSet<String> vec2 = vec_stack.pop();
        TreeSet<String> final_tree = new TreeSet<>();
        for (String word : vec2) {
            if (!vec1.contains(word)) {
                final_tree.add(word);
            }
        }
        vec_stack.push(final_tree);
    }

}
