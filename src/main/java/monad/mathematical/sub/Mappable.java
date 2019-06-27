package monad.mathematical.sub;

import java.util.function.Function;

public interface Mappable<T> {
    <U> Mappable<U> map(Function<? super T, ? extends U> f);
}
