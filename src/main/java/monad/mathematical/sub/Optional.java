package monad.mathematical.sub;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public abstract class Optional<I> extends Monad<Optional.mu, I>  {

    public abstract I orElse(Supplier<I> alternative);

    public <U> Optional<U> bind(Function<? super I, ? extends Monad<Optional.mu, U>> f) {
        return (Optional<U>) super.bind(f);
    }

    public static class Just<T> extends Optional<T> {
        private final T value;

        Just(T value) {
            this.value = value;
        }

        @Override
        public T orElse(Supplier<T> alternative) {
            return value;
        }

        @Override
        public <U> Optional<U> map(Function<? super T, ? extends U> f) {
            return new Just<>(f.apply(value));
        }

        @Override
        <U> Optional<U> join() {
            return (Optional<U>) value;
        }
    }

    public static class Nothing<T> extends Optional<T> {

        private static final Optional NOTHING = new Nothing<>();

        @Override
        <U> Optional<U> join() {
            return NOTHING;
        }

        @Override
        public T orElse(Supplier<T> alternative) {
            return alternative.get();
        }

        @Override
        public <U> Optional<U> map(Function<? super T, ? extends U> f) {
            return NOTHING;
        }
    }

    static class mu {}



    public static void main(String[] args) {
        Optional<Integer> hey = new Just<>(2);
        Function<Integer, Optional<Integer>> hey2 = i -> new Just<>(i * 2);
        Integer value = hey.bind(hey2).bind(hey2).orElse(() -> 10000);

        System.out.println(value);
    }
}
