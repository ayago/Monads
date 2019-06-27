package monad.mathematical.other;

import java.util.function.Function;

@FunctionalInterface
public interface Functor<WITNESS> {
    <T, U> Function<? extends Higher<WITNESS, T>, ? extends Higher<WITNESS, U>> fMap(Function<T, U> f);
}
