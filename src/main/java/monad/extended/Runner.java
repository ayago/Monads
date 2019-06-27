package monad.extended;

import java.util.function.Function;

import static java.lang.String.format;

public class Runner {

    public static void main(String[] args) {
        Function<Integer, Integer> addSeven = s -> s + 7;
        ApplicativeFunctor<Optional<?>, Integer> applyResult = Optional.of(3).apply(Optional.of(addSeven));

        System.out.println(format("Applied Value is: %s", applyResult));

        Integer hey = 2;
        Functor<Integer> newValue = Optional.of(hey).fmap(addSeven);
        System.out.println(format("Mapped value is: %s", newValue));

        Function<Integer, Integer> addOne = s -> s + 1;
        Function<Integer, Integer> minusTwo = s -> s - 2;

        Optional<Function<Integer, Integer>> optionalComposed = Optional.of(minusTwo.compose(addOne));
        Optional<Function<Integer, Integer>> optionalAddOne = Optional.of(addOne);
        Optional<Function<Integer, Integer>> optionalMinusTwo = Optional.of(minusTwo);

        Function<Integer, Optional<Integer>> addOneFmap = s -> optionalAddOne.fmap(f -> f.apply(s));
        Function<Optional<Integer>, Optional<Integer>> minusTwoFmap = s -> optionalMinusTwo.fmap(f -> f.apply(s.get()));

        System.out.println("\nProving that Functors preserve composition of morphisms");
        System.out.println(format("OptionalFunctor composed: %s", optionalComposed.fmap(s -> s.apply(10))));
        System.out.println(format("OptionalFunctor separate gof: %s", minusTwoFmap.compose(addOneFmap).apply(10)));

        System.out.println("\nPlaying with Monads");
        Function<Integer, Optional<Integer>> optionalAddSeven = s -> Optional.of(s + 7);
        Integer heyEmpty = null;
        Monad<Optional<?>, Integer> nothing = Optional.of(heyEmpty).bind(optionalAddSeven);
        System.out.println(format("Nothing monad: %s", nothing));

        Monad<Optional<?>, Integer> optionalValue = Optional.of(3).bind(optionalAddSeven);
        System.out.println(format("Just monad: %s", optionalValue));
    }
}
