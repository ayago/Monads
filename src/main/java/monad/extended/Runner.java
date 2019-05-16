package monad.extended;

import java.util.function.Function;

import static java.lang.String.format;

public class Runner {

    public static void main(String[] args) {
        Function<Integer, Integer> addOne = s -> s + 7;
        ApplicativeFunctor<Integer> value = Optional.of(3).apply(Optional.of(addOne));

        System.out.println(format("Value is: %s", value));
    }
}
