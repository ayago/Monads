package monad.mathematical;

import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public abstract class Result<T> implements Functor<T, Result>{

    public static <U> Result<U> of(U value) {
        if(value instanceof Throwable) {
            return new Failure<>((Throwable) value);
        }

        return new Success<>(value);
    }

    public static <A, B> Function<Result<A>, Result<B>> lift(Function<A, B> f) {
        return i -> {
            A containedValue = i.get();
            if(containedValue instanceof Throwable) {
                return new Failure<>((Throwable) containedValue);
            }
            return of(f.apply(containedValue));
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Functor<U, Result> fmap(Function<T, U> f) {
        T t = get();
        if(t instanceof Functor) {
            Functor apply = ((Functor) t).fmap(f);
            return (Result<U>) of(apply);
        }

        return lift(f).apply(this);
    }

    @Override
    public <E, V extends Functor<? extends E, V>> Result<V> compose(V before) {
        return of(before);
    }

    private static class Success<E> extends Result<E>{

        private final E value;

        private Success(E value) {
            this.value = value;
        }

        @Override
        public E get() {
            return value;
        }

        @Override
        public String toString(){
            return format("Success %s", value);
        }
    }

    private static class Failure<S> extends Result<S>{

        private final Throwable value;

        private Failure(Throwable value) {
            this.value = value;
        }

        @Override
        public S get() {
            throw new UnsupportedOperationException(value);
        }

        @Override
        public String toString(){
            return value.getMessage();
        }
    }
}
