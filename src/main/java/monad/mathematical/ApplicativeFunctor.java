package monad.mathematical;

import java.util.function.Function;

/**
 * @param <F> The applicative type
 * @param <T> The contained type
 */
public interface ApplicativeFunctor<F, T> extends Functor<T> {

    <U> ApplicativeFunctor<F, U> unit(U value);

    default <U> ApplicativeFunctor<F, U> apply(final ApplicativeFunctor<F, Function<T, U>> ff) {
        Function<T, U> f = ff.get();
        return unit(f.apply(get()));
    }
}
