package monad.mathematical;

import java.util.function.Function;

public abstract class AbstractMonad<I, T> extends AbstractApplicativeFunctor<I, T> implements Monad<I, T> {

    abstract <U> Monad<I, U> join(Monad<I, ? extends Monad<I, U>> v);

    @SuppressWarnings("unchecked")
    public <U> Monad<I, U> bind(Function<T, ? extends Monad<I, U>> f) {
        Monad<I, ? extends Monad<I, U>> fmap = (Monad<I, ? extends Monad<I, U>>) fmap(f);
        return join(fmap);
    }
}
