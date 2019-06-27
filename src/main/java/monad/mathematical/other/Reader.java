package monad.mathematical.other;

import java.util.function.Function;

public class Reader<E, O> extends Monad<Reader.mu, O> {

    public static <C, U> Reader<C, U> unit(Function<C, U> f){
        return new Reader<>(f);
    }

    private final Function<E, O> delegate;

    private Reader(final Function<E, O> f) {
        this.delegate = f;
    }

    public O apply(E env){
        return delegate.apply(env);
    }

    @Override
    public <U> Reader<E, U> bind(Function<O, ? extends Monad<Reader.mu, U>> f) {
        return (Reader<E, U>) super.bind(f);
    }

    @Override
    <U> Monad<mu, U> join() {
        Function<E, Reader<E, U>> equiv = (Function<E, Reader<E, U>>) delegate;
        return new Reader<E, U>(e -> equiv.apply(e).apply(e));
    }

    @Override
    <U> Monad<mu, U> map(Function<O, U> f) {
        return new Reader<>(f.compose(delegate));
    }

    static class mu{}

}
