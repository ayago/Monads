package monad;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

  private static Function<Integer, Monad<List<Integer>, Integer>> repeatNumber =
          arg -> ListMonad.instance(Collections.nCopies(arg, arg));

  static Function<Integer, ListMonad<Object>> listFail = arg -> {
    throw new RuntimeException();
  };

  private static Function<String, MaybeMonad<Integer>> hashCode =
          arg -> MaybeMonad.instance(arg.hashCode());

  static Function<Integer, MaybeMonad<Object>> maybeFail = arg -> {
    throw new RuntimeException();
  };

  private static Either<UnsupportedEncodingException, String> encode2(String str) {
    try {
      return Right.instance(URLEncoder.encode(str, "utf-8"));
    } catch (UnsupportedEncodingException e) {
      return Left.instance(e);
    }
  }

  static Function<String, String> toUpper = String::toUpperCase;

  private static Function<Integer, StateMonad<Integer, String>> increment =
          arg -> new StateMonad<Integer, String>() {
            @Override protected ValueWithState<Integer, String> getValueWithState(String s) {
              return new ValueWithState<>(arg + 1, s + "\n Increment");
            }
          };

  private static Function<Integer, StateMonad<Integer, String>> multiply =
          arg -> new StateMonad<Integer, String>() {
            @Override protected ValueWithState<Integer, String> getValueWithState(String s) {
              return new ValueWithState<>(arg * 3, s + "\n Multiply");
            }
          };

  private static Function<String, Result<String>> capitaliseResultString =
          s -> Result.fallible(s::toUpperCase);

  private static Function<String, Result<String>> finaliseResultString =
          s -> Result.fallible(() -> s.concat(" YAGO"));

  public static void main(String... args) {
    ListMonad comprehension =
        (ListMonad) ListMonad.instance(
            Arrays.asList(1, 2, 3, 4)).bind(repeatNumber).map(arg -> arg * 2);//.bind(listFail);
    System.out.println(comprehension);

    separator();

    MaybeMonad<Object> failSafe = MaybeMonad.instance("Frankie").bind(hashCode).bind(maybeFail);//.bind(maybeFail);

    System.out.println(failSafe);

    separator();

    String encoding = encode2("will always succeed").left()
            .map(Throwable::getMessage).get();

    System.out.println(encoding);

    separator();

    ValueWithState<Integer, Object> valueWithState = StateMonad.instance(2)
        .bind(increment)
        .bind(multiply)
        .getValueWithState("First state\n");

    System.out.println(valueWithState.getValue());
    System.out.println(valueWithState.getState());

    separator();

    @SuppressWarnings("ConstantConditions")
    String fail = Result.fallible(() -> errorProducingString(true))
            .bind(capitaliseResultString)
            .bind(finaliseResultString)
            .whenErrorThen(() -> "Default Result");

    String success = Result.fallible(() -> errorProducingString(false))
            .bind(capitaliseResultString)
            .bind(finaliseResultString)
            .whenErrorThen(() -> "Default Result");

    System.out.println("Result fail is: "+fail);
    System.out.println("Result success is: "+success);
  }

  private static String errorProducingString(boolean error) {
    if(error)
      throw new RuntimeException("HAHA");

    return "Adrian";
  }

  private static void separator() {
    System.out.println("\n-----------\n");
  }
}
