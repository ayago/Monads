package monad.mathematical;

import java.util.function.Function;

public class Runner {
    public static void main(String[] args) {
        Function<Integer, Integer> addSeven = s -> s + 7;
        Function<Integer, Integer> minusTwo = s -> s - 2;
        Optional<Integer> optionalTwo = Optional.of(2);
        Function<Optional<Integer>, Optional<Integer>> optionalAddSeven = Optional.lift(addSeven);

        Optional<Integer> result = optionalAddSeven.apply(optionalTwo);
        System.out.println("Result is "+result);

        Optional<Integer> optionalOfCompositionResult =
                Optional.lift(addSeven.compose(minusTwo)).apply(optionalTwo);
        System.out.println("Optional of composition result is "+optionalOfCompositionResult);

        Optional<Integer> composeResult =
                optionalAddSeven.compose(Optional.lift(minusTwo)).apply(optionalTwo);
        System.out.println("Composition result is "+composeResult);

        Function<Optional<Integer>, Optional<Integer>> lift = Optional.lift(addSeven.compose(minusTwo));
        System.out.println("Optional of composition result is "+lift.apply(Optional.of(2)));

        Function<Optional<Integer>, Optional<Integer>> compose1 =
                Optional.lift(minusTwo).compose(Optional.lift(addSeven));
        System.out.println("Composition result is "+compose1.apply(Optional.of(2)));

        Optional<Result<Integer>> resultOptional = Optional.of(Result.of(2));
        System.out.println("Optional Result is: "+resultOptional);

        Optional<Result> compose = Optional.of(2).compose(Result.of(2));
        System.out.println("Composite Optional Result is: "+compose);

        Result<Integer> actualResult = compose.get();
        System.out.println("Composite Actual Result is: "+actualResult);
    }

}
