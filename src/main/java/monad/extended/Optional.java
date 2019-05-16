package monad.extended;


import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public abstract class Optional<U> implements ApplicativeFunctor<U> {

    public static <E> Optional<E> of(E value){
        if(isNull(value)) {
            return new Nothing<>();
        }

        return new Just<>(value);
    }

    @Override
    public <T> ApplicativeFunctor<T> unit(T value) {
        return of(value);
    }

    @Override
    public <U1> Functor<U1> fmap(Function<U, U1> f) {
        if(isNull(get())) {
            return new Nothing<>();
        }
        return unit(f.apply(get()));
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
        public E get() {
            return null;
        }

        @Override
        public String toString(){
            return "Nothing";
        }
    }
}
