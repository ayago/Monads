package monad.mathematical;


import java.util.function.Function;


public interface Monad<F, T> extends ApplicativeFunctor<F, T> {

    <U> Monad<F, U> bind(Function<T, ? extends Monad<F, U>> f);
}
