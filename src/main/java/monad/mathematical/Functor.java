package monad.mathematical;

import java.util.function.Function;

@FunctionalInterface
public interface Functor<WITNESS> {
    <T, U> Higher<WITNESS, U> fMap(Function<? super T, ? extends U> f, Higher<WITNESS, T> type);
}
