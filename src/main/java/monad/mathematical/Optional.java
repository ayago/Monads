package monad.mathematical;

import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public abstract class Optional<T> implements Functor<T, Optional> {

    public static <U> Optional<U> of(U value){
        if(isNull(value)){
            return new Nothing<>();
        }

        return new Just<>(value);
    }

    public static <A, B> Function<Optional<A>, Optional<B>> lift(Function<A, B> f) {
        return i -> {
            A containedValue = i.get();
            if(isNull(containedValue)) {
                return new Nothing<>();
            }
            return of(f.apply(containedValue));
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Functor<U, Optional> fmap(Function<T, U> f) {
        T t = get();
        if(t instanceof Functor) {
            Functor apply = ((Functor) t).fmap(f);
            return (Optional<U>) of(apply);
        }

        return lift(f).apply(this);
    }

    @Override
    public <V extends Functor<?, V>> Optional<V> compose(V before) {
        return of(before);
    }

    private static class Just<T> extends Optional<T> {

        private T value;

        private Just(T value){
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public String toString(){
            return format("Just %s", value);
        }
    }

    private static class Nothing<T> extends Optional<T> {

        @Override
        public T get() {
            return null;
        }

        @Override
        public String toString(){
            return "Nothing";
        }
    }
}
