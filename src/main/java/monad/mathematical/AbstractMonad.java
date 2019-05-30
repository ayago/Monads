package monad.mathematical;

import java.util.function.Function;

public abstract class AbstractMonad<F, T> implements Monad<F, T> {

    <U> AbstractMonad<F, U> yield(Function<T, U> f) {
        ApplicativeFunctor<F, Function<T, U>> ff = unit(f);
        return (AbstractMonad<F, U>) apply(ff);
    }

    <U> AbstractMonad<F, U> join() {
        return get();
    }

    public <U> AbstractMonad<F, U> bind(Function<T, ? extends Monad<F, U>> f) {
        return yield(f).join();
    }
}
