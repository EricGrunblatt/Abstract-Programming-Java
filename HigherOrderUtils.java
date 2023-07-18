import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HigherOrderUtils {

    interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        String name();
    }

    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "add";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble + aDouble2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "diff";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble - aDouble2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "mult";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble * aDouble2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "div";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            if(aDouble2 == 0)
                throw new ArithmeticException();
            int x = aDouble.intValue() / aDouble2.intValue();
            return (double) x;
        }
    };

    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions) {
        if(args.size() < bifunctions.size() + 1) {
            throw new IllegalArgumentException();
        }
        else {
            IntStream.range(0, bifunctions.size())
                    .mapToObj(i -> args.set(i+1, bifunctions.get(i).apply(args.get(i), args.get(i + 1))))
                    .collect(Collectors.toList());
            return args.get(bifunctions.size());
        }
    }

    static class FunctionComposition<T, U, R> {
        public BiFunction<Function<T, U>, Function<U, R>, Function<T, R>> composition = new BiFunction<Function<T, U>, Function<U, R>, Function<T, R>>() {
            @Override
            public Function<T, R> apply(Function<T, U> function1, Function<U, R> function2) {
                return function1.andThen(function2);
            }
        };
    }
}
