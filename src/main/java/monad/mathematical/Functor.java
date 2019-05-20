package monad.mathematical;


import java.util.function.Function;

public interface Functor<T, F extends Functor> {

    T get();

    <U> Function<? extends Functor<T, F>, ? extends Functor<U, F>> fmap(Function<T, U> f);

    <E, V extends Functor<? extends E, V>> Functor<? super V, F> compose(V before);

}

