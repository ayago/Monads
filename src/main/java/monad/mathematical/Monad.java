package monad.mathematical;


import java.util.function.Function;

/**
 * @param <I> The applicative implementation
 * @param <T> The contained type
 */
public interface Monad<I, T> extends ApplicativeFunctor<I, T> {
    <U> Monad<I, U> unit(U value);
    <U> Monad<?, U> bind(Function<T, ? extends Monad<I, U>> f);
}
