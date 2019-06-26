package monad.mathematical;

import java.util.function.Function;

/**
 * AKA function monad
 * @param <R> values to be passed
 * @param <A> resulting value upon application of instance of R
 */
public final class Reader<R, A> {

    private final Function<R, A> g;

    /**
     * Wrap the function as a reader
     * @param g the function
     */
    private Reader(Function<R, A> g) {
        this.g = g;
    }

    /**
     * Lift the function as a reader
     * @param f the function
     * @param <E> the type of input which will be the type of env variables
     * @param <O> the output to be resolved once E is applied
     * @return the resulting reader
     */
    public static <E, O> Reader<E, O> unit(Function<E, O> f){
        return new Reader<>(f);
    }

    /**
     * Flat map operations of reader as a functor. In FP
     * Functions are first classed which is equivalent to objects here in Java;
     * however the f here is the function that relates the resulting output of this
     * reader to result of the new reader. The same env should be applied to the resulting
     * reader
     * @param f the function relating the this reader to the new reader
     * @param <O> the resulting value of the resulting reader upon application of env
     * @return the elevated function f
     */
    public <I, O> Function<Reader<R, I>, Reader<R, O>> fMap(Function<Function<R, I>, Function<R, O>> f){
        return r -> Reader.unit(f.apply(r.g));
    }

    /**
     * Perform fMap operation on this instance
     * @param f the function relating the this reader to the new reader
     * @param <B> the resulting value of the resulting reader upon application of env
     * @return the resulting reader
     */
    public <B> Reader<R, B> map(Function<Function<R, A>, Function<R, B>> f) {
        return fMap(f).apply(this);
    }

    /**
     * Bind operations of reader as a monad. Allows chain operations
     * of function that returns a reader that uses the same env variables.
     * Allows implicit passing of the env variable
     *
     * @param f function to be bind
     * @param <V> new resolution type
     * @return the resulting reader
     */
    public <V> Reader<R, V> bind(Function<Function<R, A>, Reader<R, V>> f){
        return new Reader<>(r -> f.apply(g).apply(r));
    }

    /**
     * Applying the env variable results to the resolution of the elevated function g
     * @param r the env variable
     * @return the result of g applied with the env variable r
     */
    public A apply(R r) {
        return g.apply(r);
    }
}
