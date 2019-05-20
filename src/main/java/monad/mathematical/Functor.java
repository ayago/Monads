package monad.mathematical;

import java.util.function.Function;

public interface Functor<T, F> {
    T get();
    <U> Functor<U, F> fmap(Function<T, U> f);
    <E, V extends Functor<? extends E, V>> Functor<? super V, F> compose(V before);
}

interface FMap<A, B, F, I extends Functor<A, F>, O extends Functor<B, F>> extends Function<I, O> {

}


