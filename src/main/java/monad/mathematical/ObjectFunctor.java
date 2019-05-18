package monad.mathematical;


import java.util.Objects;

public interface ObjectFunctor<T> {
    T get();
}

interface FMap<A, B, I extends ObjectFunctor<A>, O extends ObjectFunctor<B>> {

    O apply(I inputObjectFunctor);

    default <X, V extends ObjectFunctor<X>> FMap<X, B, V, O> compose(FMap<X, A, ? super V, ? extends I> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }
}


