package monad.mathematical;

import monad.mathematical.Optional.OptionalMorphism;
import monad.mathematical.Optional.OptionalObject;

import java.util.function.Function;

public class Runner {
    public static void main(String[] args) {
        Function<Integer, Integer> addSeven = s -> s + 7;
        Function<Integer, Integer> minusTwo = s -> s - 2;
        OptionalObject<Integer> optionalTwo = Optional.of(2);
        OptionalMorphism<Integer, Integer> optionalAddSeven = Optional.of(addSeven);

        OptionalObject<Integer> result = optionalAddSeven.apply(optionalTwo);
        System.out.println("Result is "+result);

        OptionalObject<Integer> optionalOfCompositionResult =
                Optional.of(addSeven.compose(minusTwo)).apply(optionalTwo);
        System.out.println("Optional of composition result is "+optionalOfCompositionResult);

        OptionalObject<Integer> composeResult =
                optionalAddSeven.compose(Optional.of(minusTwo)).apply(optionalTwo);
        System.out.println("Composition result is "+composeResult);
    }

}
