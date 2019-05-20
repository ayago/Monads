package monad.mathematical;

import java.util.function.Function;

public class Runner {
    public static void main(String[] args) {
        Function<Integer, Integer> addSeven = s -> s + 7;
        Function<Integer, Integer> minusTwo = s -> s - 2;
        Optional<Integer> optionalTwo = Optional.of(2);

        Optional<Integer> optionalTwoPlusSeven = Optional.lift(addSeven).apply(optionalTwo);
        System.out.println("optionalTwoPlusSeven is "+optionalTwoPlusSeven);
        System.out.println("Optional of Null is "+Optional.of(null));

        Function<Optional<Integer>, Optional<Integer>> lift = Optional.lift(addSeven.compose(minusTwo));
        System.out.println("Optional of composition result is "+lift.apply(Optional.of(2)));

        Function<Optional<Integer>, Optional<Integer>> compose1 =
                Optional.lift(minusTwo).compose(Optional.lift(addSeven));
        System.out.println("Composition result is "+compose1.apply(Optional.of(2)));

        Optional<Result<Integer>> resultOptional = Optional.of(Result.of(2));
        System.out.println("Optional Result is: "+resultOptional);

        Optional<Result> resultOptional2 = Optional.of(2).compose(Result.of(2));
        System.out.println("Composite Optional Result is: "+resultOptional2);
    }

}
