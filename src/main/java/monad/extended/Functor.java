package monad.extended;


import java.util.function.Function;

public interface Functor<T> {
    <U> Functor<U> fmap(Function<T, U> f);
    T get();
}