package monad.mathematical;

import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public final class Optional {

    public static <U> OptionalObject<U> of(U value){
        if(isNull(value)){
            return new OptionalObject.Nothing<>();
        }

        return new OptionalObject.Just<>(value);
    }

    public static <INPUT, OUTPUT> OptionalMorphism<INPUT, OUTPUT> of(Function<INPUT, OUTPUT> sourceMorphism) {
        return new OptionalMorphism<>(sourceMorphism);
    }

    public static abstract class OptionalObject<T> implements ObjectFunctor<T>{

        private static class Just<T> extends OptionalObject<T> {

            private T value;

            private Just(T value){
                this.value = value;
            }

            @Override
            public T get() {
                return value;
            }

            @Override
            public String toString(){
                return format("Just %s", value);
            }
        }

        static class Nothing<T> extends OptionalObject<T> {

            @Override
            public T get() {
                return null;
            }

            @Override
            public String toString(){
                return "Nothing";
            }
        }
    }

    public static class OptionalMorphism<A, B> implements FMap<A, B, OptionalObject<A>, OptionalObject<B>>{

        private Function<A, B> sourceMorphism;

        private OptionalMorphism(Function<A, B> sourceMorphism){
            this.sourceMorphism = requireNonNull(sourceMorphism);
        }

        @Override
        public OptionalObject<B> apply(OptionalObject<A> inputObjectFunctor) {
            A containedValue = inputObjectFunctor.get();
            if(isNull(containedValue)) {
                return new OptionalObject.Nothing<>();
            }
            return of(sourceMorphism.apply(containedValue));
        }
    }
}
