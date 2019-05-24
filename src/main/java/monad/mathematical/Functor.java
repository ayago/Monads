package monad.mathematical;

import java.util.function.Function;

public interface Functor<V, T> {
    V get();
    <U> Functor<U, T> fmap(Function<V, U> f);
}


