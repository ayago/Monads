package monad;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
          s -> Result.fallible(() -> s.concat(" GREATUS"));

  private static Function<String, Result<Integer>> getIdForName =
          s -> {
            Map<String, Integer> idRegistry = new HashMap<>();
            idRegistry.put("POUPING GREATUS", 1);
            idRegistry.put("POUPY GREATUS", 2);

            if(idRegistry.containsKey(s)) {
              return Result.fallible(() -> idRegistry.get(s));
            } else {
              return Result.error(new RuntimeException("Id does not exist for"+s));
            }
          };

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
    String fail = Result.fallible(() -> errorProducingString("Pouping",true))
            .bind(capitaliseResultString)
            .bind(finaliseResultString)
            .bind(getIdForName)
            .whenErrorThen(() -> "Failed Result");

    String anotherFail = Result.fallible(() -> errorProducingString("Adrian2",false))
            .bind(capitaliseResultString)
            .bind(finaliseResultString)
            .bind(getIdForName)
            .whenErrorThen(() -> "");

    Integer success = Result.fallible(() -> errorProducingString("Poupy", false))
            .bind(capitaliseResultString)
            .bind(finaliseResultString)
            .bind(getIdForName)
            .get();

    System.out.println("Result fail is: "+fail);
    System.out.println("Result fail is: "+anotherFail);
    System.out.println("Result success is: "+success);
  }

  private static String errorProducingString(String reflectName, boolean error) {
    if(error)
      throw new RuntimeException("HAHA");

    return reflectName;
  }

  private static void separator() {
    System.out.println("\n-----------\n");
  }
}
