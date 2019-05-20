package monad.mathematical;

import java.util.function.Function;

import static java.lang.String.format;

public abstract class Result<S> implements Functor<S, Result>{

    public static <U> Result<U> of(U value) {
        if(value instanceof Throwable) {
            return new Failure<>((Throwable) value);
        }

        return new Success<>(value);
    }

    public static <U, S> Function<Result<S>, Result<U>> lift(Function<S, U> f) {
        return new Success<S>(null).fmap(f);
    }

    @Override
    public S get() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Function<Result<S>, Result<U>> fmap(Function<S, U> f) {
        return i -> {
            S t = i.get();
            if(t instanceof Functor) {
                Object apply = ((Functor) t).fmap(f).apply(t);
                return (Result<U>) of(apply);
            }

            return of(f.apply(i.get()));
        };
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
