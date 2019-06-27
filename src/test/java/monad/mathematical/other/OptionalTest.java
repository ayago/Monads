package monad.mathematical.other;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class OptionalTest {

    private Integer given = 2;

    private Supplier<Integer> alternative = () -> 0;

    private Supplier<String> alternativeStr = () -> "ZERO";

    private static final Function<Integer, Optional<Integer>> f =
            i -> Optional.unit(i + 2);

    private static final Function<Integer, Optional<String>> g =
            i -> Optional.unit(i.toString());

    //(unit a) >>= f = fa
    @Test
    public void leftIdentity(){
        Optional<Integer> unitA = Optional.unit(given);

        Optional<Integer> left = unitA.bind(f);
        Optional<Integer> right = f.apply(given);

        Integer leftApplied = left.orElse(alternative);
        Integer rightApplied = right.orElse(alternative);

        assertEquals(leftApplied, rightApplied);
    }

    //m >>= unit = m
    @Test
    public void rightIdentity(){
        Optional<Integer> m = Optional.unit(given);

        Optional<Integer> left = m.bind(Optional::unit);

        Integer leftApplied = left.orElse(alternative);
        Integer rightApplied = m.orElse(alternative);
        assertEquals(leftApplied, rightApplied);
    }

    //(m >>= f) >>= g = m >>= (\x -> fx >>= g)
    @Test
    public void associativity(){
        Optional<Integer> m = Optional.unit(given);
        Optional<String> left = m.bind(f).bind(g);

        Function<Integer, Optional<String>> rightFirstOp = x -> f.apply(x).bind(g);
        Optional<String> right = m.bind(rightFirstOp);

        String leftApplied = left.orElse(alternativeStr);
        String rightApplied = right.orElse(alternativeStr);
        assertEquals(leftApplied, rightApplied);
    }
}
