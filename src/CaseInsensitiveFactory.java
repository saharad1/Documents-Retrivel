public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {
    @Override
    public AbstractInvertedIndex createInvertedIndex() {
        return InvertedIndexCaseInsensitive.getInstance();
    }
}
