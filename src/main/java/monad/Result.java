package monad;

import java.util.Objects;
import java.util.function.Supplier;

import static java.lang.String.format;

public abstract class Result<T> extends Monad<Result<T>, T> {
    /**
     * Check if result is erroneous
     * @return f result is erroneous
     */
    public abstract boolean isError();

    /**
     * Factory method to create a successful result
     * @param value contained value
     * @param <U> contained value type
     * @return a result instance of type U
     */
    public static <U> Result<U> ok(U value) {
        return new Success<>(value);
    }

    /**
     * Factory method to create an erroneous result
     * @param throwable cause of error
     * @param <U> target contained type
     * @return a result instance of type U containing the error cause
     */
    public static <U> Result<U> error(Throwable throwable) {
        return new Failure<>(throwable);
    }

    /**
     * Handles the side effect of an operation when
     * the execution resulted to an exception. Specifically
     * when this result object is erroneous, the supplier is invoked
     * to provide a default value when the result is erroneous
     *
     * @param other the supplier of alternative value
     * @return a Result of same type with instance equal to the supplied value
     */
    public abstract <U> U whenErrorThen(final Supplier<U> other);

    /**
     * Factory method to create a result based from the evaluation
     * of the provided supplier
     *
     * @param supplier supplier of contained data
     * @param <U> target contained type
     * @return result of type target type U
     */
    public static <U> Result <U> fallible(Supplier<U> supplier) {
        Objects.requireNonNull(supplier);
        try {
            return new Success<>(supplier.get());
        } catch (Throwable t) {
            return new Failure<>(t);
        }
    }

    @Override
    protected <U> Result<U> fail(RuntimeException e) {
        return new Failure<>(e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Result<U> bind(Function<T, ? extends Monad<?, U>> f) {
        return (Result<U>) super.bind(f);
    }

    private static class Success<T> extends Result<T> {
        private final T value;

        private Success(final T value){
            this.value = value;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public <U> U whenErrorThen(Supplier<U> other) {
            return get();
        }

        @Override
        protected <U> Result<U> unit(U value) {
            return new Success<>(value);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <U> U get() {
            return (U) value;
        }

        @Override
        public String toString() {
            return format("Success(%s)", value);
        }
    }

    private static class Failure<T> extends Result<T> {

        private final Throwable error;

        private Failure(Throwable error) {
            this.error = error;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public <U> U whenErrorThen(Supplier<U> other) {
            return other.get();
        }

        @Override
        public <U> U get() {
            throw new IllegalStateException(
                    "You should not use get for Result specially when it is erroneous", error);
        }

        @Override
        protected <U> Result<U> unit(U value) {
            return new Failure<>(this.error);
        }

        @Override
        protected <U> Result<U> yield(Function<T, U> f) {
            return new Failure<>(this.error);
        }

        @Override
        protected <U> Result<U> join() {
            return new Failure<>(this.error);
        }

        @Override
        public String toString() {
            return format("Failure(%s: %s)", error.getClass().getName(), error.getMessage());
        }
    }
}
