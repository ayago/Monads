package monad.mathematical;

import java.util.function.Function;

public abstract class AbstractApplicativeFunctor<I, T> implements ApplicativeFunctor<I, T>{

    abstract <E> E containedValue();

    @Override
    public <U> ApplicativeFunctor<I, U> apply(ApplicativeFunctor<I, Function<T, U>> ff) {
        if(!(ff instanceof AbstractApplicativeFunctor)){
            throw new IllegalArgumentException("The applicative should be instance of AbstractApplicativeFunctor");
        }

        final Function<T, U> f = ((AbstractApplicativeFunctor<I, Function<T, U>> )ff).containedValue();
        return unit(f.apply(containedValue()));
    }
}
