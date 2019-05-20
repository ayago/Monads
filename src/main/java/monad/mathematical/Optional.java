package monad.mathematical;

import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;


public abstract class Optional<T> implements Functor<T, Optional> {

    public static <U> Optional<U> of(U value) {
        if(isNull(value)) {
            return new Nothing<>();
        }

        return new Just<>(value);
    }

    public static <U, S> Function<Optional<S>, Optional<U>> lift(Function<S, U> f) {
        return new Nothing<S>().fmap(f);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Function<Optional<T>, Optional<U>> fmap(Function<T, U> f) {
        return i -> {
            T t = i.get();
            if(t instanceof Functor) {
                Object apply = ((Functor) t).fmap(f).apply(t);
                return (Optional<U>) of(apply);
            }

            return of(f.apply(i.get()));
        };
    }

    public <E, V extends Functor<? extends E, V>> Optional<V> compose(V before) {
        return of(before);
    }

    private static class Just<E> extends Optional<E> {

        private final E value;

        private Just(E value) {
            this.value = requireNonNull(value);
        }

        public E get() {
            return value;
        }

        @Override
        public String toString(){
            return format("Just %s", value);
        }
    }

    private static class Nothing<E> extends Optional<E> {

        private Nothing() {}

        public E get() {
            return null;
        }

        @Override
        public String toString(){
            return "Nothing";
        }
    }
}