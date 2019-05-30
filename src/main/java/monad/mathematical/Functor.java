package monad.mathematical;


import java.util.function.Function;

/**
 * @param <T> The contained type
 */
public interface Functor<T> {
    <U> Functor<U> fmap(Function<T, U> f);
    <E> E get();
}