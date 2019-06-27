package monad.mathematical.other;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public abstract class Optional<I> extends Monad<Optional.mu, I> {

    @SuppressWarnings("unchecked")
    public static <U> Optional<U> unit(U value){
        if(isNull(value)) {
            return Nothing.NOTHING;
        }

        return new Just<>(value);
    }

    static class mu{}

    public abstract I orElse(Supplier<I> alternative);

    @Override
    public <U> Optional<U> bind(Function<I, ? extends Monad<mu, U>> f) {
        return (Optional<U>) super.bind(f);
    }

    @SuppressWarnings("unchecked")
    public static class Just<T> extends Optional<T> {

        private final T value;

        private Just(T value) {
            this.value = value;
        }

        @Override
        public T orElse(Supplier<T> alternative) {
            return value;
        }

        @Override
        <U> Optional<U> join() {
            return (Optional<U>) value;
        }

        @Override
        <U> Optional<U> map(Function<T, U> f) {
            return new Just<>(f.apply(value));
        }
    }

    @SuppressWarnings("unchecked")
    public static class Nothing<T> extends Optional<T> {

        private static final Optional NOTHING = new Nothing<>();

        private Nothing(){}

        @Override
        <U> Optional<U> join() {
            return NOTHING;
        }

        @Override
        public T orElse(Supplier<T> alternative) {
            return alternative.get();
        }

        @Override
        public <U> Optional<U> map(Function<T, U> f) {
            return NOTHING;
        }
    }
}
