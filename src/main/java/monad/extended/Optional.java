package monad.extended;


import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public abstract class Optional<U> implements Monad<Optional<?>, U> {

    public static <E> Optional<E> of(E value){
        if(isNull(value)) {
            return new Nothing<>();
        }

        return new Just<>(value);
    }

    @Override
    public <U1> Optional<U1> fmap(Function<U, U1> f) {
        U containedValue = get();
        if(isNull(containedValue)) {
            return new Nothing<>();
        }
        return (Optional<U1>) unit(f.apply(containedValue));
    }

    @Override
    public <U1> ApplicativeFunctor<Optional<?>, U1> unit(U1 value) {
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

        @Override
        public <U> Optional<U> join() {
            return new Nothing<>();
        }

        @Override
        public <U> Optional<U> yield(Function<E, U> f) {
            return new Nothing<>();
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
