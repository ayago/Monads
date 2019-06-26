package monad.mathematical;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ReaderTest {

    private static final SomeConfig HYPOTHETICAL_CONFIG = new SomeConfig(2);

    private static Function<SomeConfig, String> given = c -> c.envVariable.toString();

    private static final Function<Function<SomeConfig, String>, Reader<SomeConfig, String>> f =
            fPrime -> Reader.unit(r -> r.envVariable.toString() + fPrime.apply(r));

    private static final Function<Function<SomeConfig, String>, Reader<SomeConfig, BigDecimal>> g =
            gPrime -> Reader.unit(r -> BigDecimal.valueOf(r.envVariable).add(new BigDecimal(gPrime.apply(r))));

    private static final Function<Function<SomeConfig, String>, Reader<SomeConfig, String>> javaCompileHelper =
            Reader::unit;

    //(unit a) >>= f = fa
    @Test
    public void leftIdentity(){
        Reader<SomeConfig, String> unitA = Reader.unit(given);

        Reader<SomeConfig, String> left = unitA.bind(f);
        Reader<SomeConfig, String> right = f.apply(given);

        String leftApplied = left.apply(HYPOTHETICAL_CONFIG);
        String rightApplied = right.apply(HYPOTHETICAL_CONFIG);
        assertEquals(leftApplied, rightApplied);
    }

    //m >>= unit = m
    @Test
    public void rightIdentity(){
        Reader<SomeConfig, String> m = Reader.unit(given);

        Reader<SomeConfig, String> left = m.bind(javaCompileHelper);

        String leftApplied = left.apply(HYPOTHETICAL_CONFIG);
        String rightApplied = m.apply(HYPOTHETICAL_CONFIG);
        assertEquals(leftApplied, rightApplied);
    }

    //(m >>= f) >>= g = m >>= (\x -> fx >>= g)
    @Test
    public void associativity(){
        Reader<SomeConfig, String> m = Reader.unit(given);
        Reader<SomeConfig, String> leftFirstOp = m.bind(f);
        Reader<SomeConfig, BigDecimal> left = leftFirstOp.bind(g);

        Function<Function<SomeConfig, String>, Reader<SomeConfig, BigDecimal>> rightFirstOp = x -> f.apply(x).bind(g);
        Reader<SomeConfig, BigDecimal> right = m.bind(rightFirstOp);

        BigDecimal leftApplied = left.apply(HYPOTHETICAL_CONFIG);
        BigDecimal rightApplied = right.apply(HYPOTHETICAL_CONFIG);
        assertEquals(leftApplied, rightApplied);
    }

    private static class SomeConfig {
        private final Integer envVariable;

        private SomeConfig(Integer envVariable) {
            this.envVariable = envVariable;
        }
    }
}
