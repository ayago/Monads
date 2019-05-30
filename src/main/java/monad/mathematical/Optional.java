package monad.mathematical;


import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public abstract class Optional<U> extends AbstractMonad<Optional<?>, U> {
    
    public static <E> Optional<E> of(E value){
        if(isNull(value)) {
            return new Optional.Nothing<>();
        }

        return new Optional.Just<>(value);
    }

    public <V> Optional<V> fmap(Function<U, V> f) {
        U containedValue = get();
        if(isNull(containedValue)) {
            return new Optional.Nothing<>();
        }
        return (Optional<V>) unit(f.apply(containedValue));
    }

    @Override
    public <V> ApplicativeFunctor<Optional<?>, V> unit(V value) {
        return of(value);
    }

    private static class Just<E> extends Optional<E> {

        private E value;

        private Just(E value){
            this.value = value;
        }

        @Override
        public E get() {
            return value;
        }

        @Override
        public String toString(){
            return format("Just %s", value);
        }
    }

    private static class Nothing<E> extends Optional<E> {

        private static final Nothing NOTHING = new Nothing<>();

        @Override
        public <U> Optional<U> join() {
            return NOTHING;
        }

        @Override
        public <U> Optional<U> yield(Function<E, U> f) {
            return NOTHING;
        }

        @Override
        public <T> T get() {
            return null;
        }

        @Override
        public String toString(){
            return "Nothing";
        }
    }
}
