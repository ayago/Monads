package monad.mathematical.sub;

import monad.mathematical.Functor;
import monad.mathematical.Higher;

import java.util.function.Function;

public abstract class Monad<WITNESS, I> implements Higher<WITNESS, I>, Functor<WITNESS> {

    public <U> Monad<WITNESS, U> bind(Function<? super I, ? extends Monad<WITNESS, U>> f) {
        return fMap(f, this).join();
    }

    @Override
    public <T, U> Monad<WITNESS, U> fMap(Function<? super T, ? extends U> f, Higher<WITNESS, T> type) {
        Monad<WITNESS, T> narrowType = (Monad<WITNESS, T>) type;
        return narrowType.map(f);
    }

    abstract <U> Monad<WITNESS, U> join();

    abstract <U> Monad<WITNESS, U> map(Function<? super I, ? extends U> f);
}
