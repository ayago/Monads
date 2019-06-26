package monad.mathematical;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

/**
 * Wraps value to give optional context
 * avoiding null values gracefully
 *
 * @param <T> type of value to be wrapped
 */
public abstract class Optional<T> {

    @SuppressWarnings("unchecked")
    public static <U> Optional<U> unit(U value){
        if(isNull(value)) {
            return Nothing.DEFAULT;
        }

        return new Just<>(value);
    }

    public <I, O> Function<Optional<I>, Optional<O>> fMap(Function<I, O> f) {
        return tOptional -> unit(f.apply(tOptional.get()));
    }

    public <U> Optional<U> map(Function<T, U> f) {
        return fMap(f).apply(this);
    }

    public abstract <U> Optional<U> bind(Function<T, Optional<U>> f);

    public static final class Just<I> extends Optional<I> {

        private final I value;

        private Just(I value) {
            this.value = value;
        }

        @Override
        public <U> Optional<U> bind(Function<I, Optional<U>> f) {
            return f.apply(value);
        }

        @Override
        I get() {
            return value;
        }

        @Override
        public I orElse(Supplier<I> alternative) {
            return value;
        }
    }

    public static final class Nothing<I> extends Optional<I> {

        private static final Optional DEFAULT = new Nothing<>();

        private Nothing(){}

        @SuppressWarnings("unchecked")
        @Override
        public <U> Optional<U> bind(Function<I, Optional<U>> f) {
            return DEFAULT;
        }

        @Override
        I get() {
            return null;
        }

        @Override
        public I orElse(Supplier<I> alternative) {
            return alternative.get();
        }
    }

    abstract T get();

    public abstract T orElse(Supplier<T> alternative);
}
