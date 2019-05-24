package monad.mathematical;

import java.util.function.Function;

public interface Monad<V, T> extends Functor<V, T> {
    <U> Monad<U, T> bind(Function<V, ? extends Monad<U, T>> f);
}
