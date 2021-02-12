package meta;

public class Match<T,R> {
    private T one;
    private R two;

    public Match(T one, R two) {
        this.one = one;
        this.two = two;
    }

    public static <T,R> Match<T,R> of(T one, R second){
        return new Match<>(one,second);
    }

    public void setOne(T one) {
        this.one = one;
    }

    public void setTwo(R two) {
        this.two = two;
    }
}
