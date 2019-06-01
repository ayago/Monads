package monad.mathematical;

import java.util.function.Function;

/**
 * @param <I> The applicative implementation
 * @param <T> The contained type
 */
public interface ApplicativeFunctor<I, T> extends Functor<I, T> {
    <U> ApplicativeFunctor<I, U> unit(U value);

    <U> ApplicativeFunctor<I, U> apply(ApplicativeFunctor<I, Function<T, U>> f);
}
