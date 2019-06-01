package monad.mathematical;


import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public abstract class Optional<U> extends AbstractMonad<Optional, U> implements Monad<Optional, U> {
    
    public static <E> Optional<E> of(E value){
        if(isNull(value)) {
            return new Nothing<>();
        }

        return new Optional.Just<>(value);
    }

    public <V> Optional<V> fmap(Function<U, V> f) {
        U containedValue = containedValue();
        if(isNull(containedValue)) {
            return new Nothing<>();
        }
        return unit(f.apply(containedValue));
    }

    @Override
    public <V> Optional<V> unit(V value) {
        return of(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    <V> Optional<V> join(Monad<Optional, ? extends Monad<Optional, V>> v) {
        final Optional<Monad<Optional, V>> optional = (Optional<Monad<Optional, V>>) v;
        return optional.containedValue();
    }

    private static class Just<E> extends Optional<E> {

        private E value;

        private Just(E value){
            this.value = value;
        }

        @Override
        <V> V containedValue() {
            return (V) value;
        }

        @Override
        public String toString(){
            return format("Just %s", value);
        }
    }

    private static class Nothing<E> extends Optional<E> {

        @Override
        <T> T containedValue() {
            return null;
        }

        @Override
        public String toString(){
            return "Nothing";
        }

    }
}
