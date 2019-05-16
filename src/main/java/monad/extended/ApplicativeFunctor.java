package monad.extended;

import java.util.function.Function;

public interface ApplicativeFunctor<T> extends Functor<T>{

    <U> ApplicativeFunctor<U> unit(U value);

    default <U> ApplicativeFunctor<U> apply(final ApplicativeFunctor<Function<T, U>> ff) {
        Function<T, U> f = ff.get();
        return unit(f.apply(get()));
    }
}
