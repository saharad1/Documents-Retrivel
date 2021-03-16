/**
 * The abstract generic factory.
 */
public abstract class AbstractInvertedIndexFactory {
    /**
     * Creates the required Inverted index according to the factory that is used.
     *
     * @return The inverted retrieval object.
     */
    public abstract AbstractInvertedIndex createInvertedIndex();
}

