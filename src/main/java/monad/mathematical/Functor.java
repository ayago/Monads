package monad.mathematical;


import java.util.function.Function;

/**
 * @param <T> The contained type
 * @param <I> The functor implementation
 */
public interface Functor<I, T> {
    <U> Functor<I, U> fmap(Function<T, U> f);
}