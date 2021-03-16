public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {
    @Override
    public AbstractInvertedIndex createInvertedIndex(){
        return InvertedIndexCaseSensitive.getInstance();
    }
}
