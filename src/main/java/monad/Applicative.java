package monad;

public abstract class Applicative<F, T> implements Functor<F, T> {

  protected abstract <U> Applicative<?, U> unit(U value);

  final <U> Function<Applicative<F, T>, Applicative<?, U>> apply(
          final Applicative<Function<T, U>, U> ff) {

    return ft -> {
      Function<T, U> f = ff.get();
      T t = ft.get();
      return unit(f.apply(t));
    };
  }
}