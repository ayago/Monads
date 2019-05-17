package monad.extended;


import java.util.function.Function;


public interface Monad<F, T> extends ApplicativeFunctor<F, T>{

    default  <U> Monad<F, U> yield(Function<T, U> f) {
        ApplicativeFunctor<F, Function<T, U>> ff = unit(f);
        return (Monad<F, U>) apply(ff);
    }

    default <U> Monad<F, U> join() {
        return get();
    }

    default <U> Monad<F, U> bind(Function<T, ? extends Monad<F, U>> f) {
        return yield(f).join();
    }
}
