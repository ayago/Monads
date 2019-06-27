package monad.mathematical.sub;

import monad.mathematical.Functor;

public interface Joinable<WITNESS> extends Functor<WITNESS> {
    Functor<WITNESS> join();
}
