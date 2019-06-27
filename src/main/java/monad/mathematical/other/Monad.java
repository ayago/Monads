package monad.mathematical.other;

import java.util.function.Function;

public abstract class Monad<WITNESS, I> implements Higher<WITNESS, I>, Functor<WITNESS> {

    public <U> Monad<WITNESS, U> bind(Function<I, ? extends Monad<WITNESS, U>> f) {
        return fMap(f).apply(this).join();
    }

    @Override
    public <T, U> Function<Monad<WITNESS, T>, Monad<WITNESS, U>> fMap(Function<T, U> f) {
        return t -> t.map(f);
    }

    abstract <U> Monad<WITNESS, U> join();

    abstract <U> Monad<WITNESS, U> map(Function<I, U> f);
}
