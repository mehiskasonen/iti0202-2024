package ee.taltech.iti0202.stream.basics;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.SequencedCollection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
//import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.lang.Integer.MAX_VALUE;

/**
 * This task requires Java version 21 or higher, because of the {@link SequencedCollection} interface.
 * Otherwise, you can use {@link Collection} interface instead of SequencedCollection.
 * Stream API has been available since Java 8.
 * <p>
 * StreamBasics class has different methods to work with collections.
 * All methods should be implemented using the Stream API.
 * The goal is to use the Stream API to solve the problems, not just to solve the problems.
 * Please do not use any loops (`for`, `while`) to solve these tasks.
 * <p>
 * The {@link #main(String[])} method demonstrates the usage of these methods.
 * You can run the main method to see the output of the methods.
 * The output is already provided for you.
 * The output should not be changed.
 * You can add more tests to the main method if you want.
 * <p>
 * Please note that the Stream API is not always the best solution.
 * Also, {@link Stream} objects are one-time use only. The Stream is consumed after the terminal operation is executed.
 * If you want to use the same Stream multiple times, you have to create it again.
 * In real app, do not leave the Stream unconsumed. It may cause memory leaks.
 * <p>
 * You should not worry about handling the exceptions. You can assume that the input data is always valid,
 * and no null values are passed to the methods, unless the method's description states otherwise.
 */
public final class StreamBasics {

    /**
     * Private constructor. No need to create an instance of this class.
     * All methods are static and can be called without an instance.
     */
    private StreamBasics() {
        throw new IllegalStateException("Non-instantiable class");
    }

    /**
     * Returns a sequenced collection of numbers from `start` (inclusive) to `end` (exclusive).
     * Please use the {@link IntStream#range(int, int)} method and `boxed` method to convert `IntStream` to `Stream`.
     */
    public static SequencedCollection<Integer> generateNumbersExclusive(final int start, final int end) {
        return IntStream.range(start, end).boxed().toList();
    }

    /**
     * Returns a collection of numbers from `start` to `end` (inclusive).
     * Please use the {@link LongStream#rangeClosed(long, long)} method, `asDoubleStream` method and `boxed` method.
     * <p>
     * asDoubleStream() is used to convert {@link LongStream} to {@link java.util.stream.DoubleStream}.
     * Actually, it is used to demonstrate the usage of different types of streams.
     */
    public static SequencedCollection<Double> generateNumbersInclusive(final long start, final long end) {
        List<Double> doubleList = IntStream.rangeClosed(Math.toIntExact(start),
                Math.toIntExact(end)).boxed().map(Integer::doubleValue).toList();
        return doubleList.stream().toList();
    }

    /**
     * Collects the collection into a list.
     * Please use the `toList` collector.
     * <p>
     * T is a generic type, think of it as a type of the collection, like Integer, String, etc.
     */
    public static <T> List<T> convertToList(final SequencedCollection<T> collection) {
        return collection.stream().collect(Collectors.toList());
    }

    /**
     * Collects the collection into a list of distinct values.
     * Please use the `distinct` method.
     */
    public static <T> SequencedCollection<T> getDistinctValues(final SequencedCollection<T> collection) {
        return collection.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Collects the collection into a set of distinct values.
     * Please use the `collect` method with {@link Collectors#toUnmodifiableSet} collector.
     */
    public static <T> Set<T> convertToUnmodifiableSet(final Collection<T> collection) {
        return collection.stream().collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Returns the maximum value in the collection.
     * Please use the `max` method.
     * <p>
     * T is a generic type that extends Comparable, so you can use the `T.compareTo` method
     * to compare the values of the collection of type T.
     */
    public static <T extends Comparable<? super T>> Optional<T> findMaxValue(final Collection<T> collection) {
        return collection.stream().max(T::compareTo);
    }

    /**
     * Returns the minimum value in the collection.
     * Please use the `min` method and `T.compareTo` method.
     */
    public static <T extends Comparable<? super T>> Optional<T> findMinValue(final Collection<T> collection) {
        return collection.stream().min(T::compareTo);
    }

    /**
     * Returns a list of non-negative values from the input collection.
     * Please use the `filter` method.
     * You may use the {@link Number#doubleValue()} method to convert {@link Number} to `double`.
     */
    public static <T extends Number> SequencedCollection<T> filterNonNegativeValues(
            final SequencedCollection<T> collection) {
        return collection.stream().filter(num -> num.doubleValue() >= 0).collect(Collectors.toList());
    }

    /**
     * Returns a list of negative values from the input collection.
     * If there are no negative numbers, return an empty {@link Optional}.
     * Please use the `filter` and `findAny` methods.
     */
    public static <T extends Number> Optional<T> findAnyNegativeNumber(final Collection<T> numbers) {
        return numbers.stream().filter(num -> num.doubleValue() < 0).toList().stream().findAny();
    }

    /**
     * Returns the first negative number from the input collection.
     * If there are no negative numbers, return {@link Integer#MAX_VALUE}.
     * Please use the `filter`. `findAny` and `orElse` methods.
     */
    public static int findAnyNegativeNumberOrElse(final SequencedCollection<Integer> numbers) {
        return numbers.stream()
                .filter(num -> num.doubleValue() < 0)
                .findAny()
                .orElse(MAX_VALUE);
    }

    /**
     * Returns the first negative number from the input collection.
     * If there are no negative numbers, return {@link Integer#MAX_VALUE}.
     * Please use the `filter`, `findFirst` and `orElseGet` methods.
     * <p>
     * orElseGet() is more efficient in terms of performance. It is only called when the Optional is empty.
     * orElse() is called every time, even if the `Optional` is not empty.
     * In this case, it does not matter because the default value is a constant.
     */
    public static int findFirstNegativeNumberOrElseGet(final SequencedCollection<Integer> numbers) {
        return numbers.stream().filter(nr -> nr.doubleValue() < 0).findFirst().orElseGet(() -> MAX_VALUE);
    }

    /**
     * Returns a list of string representations of the input collection.
     * Please use the `map` method.
     */
    public static SequencedCollection<String> convertToStringList(final SequencedCollection<?> collection) {
        return collection.stream().map(Object::toString).collect(Collectors.toList());
    }

    /**
     * Returns a single string of all positive numbers from the input collection.
     * Please use the `filter` method and `collect` method with `joining` collector with delimiter, prefix, and suffix.
     * Do not use String concatenation ('+').
     * The result should be formatted as "String[1, 2, 3, 4, 5]".
     */
    public static String convertPositivesToSingleString(final SequencedCollection<? extends Number> collection) {
        return collection.stream().filter(nr -> nr.doubleValue() > 0)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "String[", "]"));
    }

    /**
     * Returns a list of the first `amount` values from the input collection.
     * Please use the `limit` method.
     * If the `amount` is greater than the size of the collection, return all values.
     */
    public static <T> SequencedCollection<T> getLimitedList(final SequencedCollection<T> collection,
                                                            final int amount) {
        return collection.stream().limit(amount).collect(Collectors.toList());
    }

    /**
     * Returns a list of values from the input collection, starting from the `amount`th value.
     * Please use the `skip` method. The only line of code should be the return statement.
     * If the `amount` is greater than the size of the collection, return an empty list.
     */
    public static <T> SequencedCollection<T> getListFromIndex(final SequencedCollection<T> collection,
                                                              final int amount) {
        return collection.stream().skip(amount).collect(Collectors.toList());
    }

    /**
     * Returns a list of squared values of the input collection.
     * Please use the `map` method.
     */
    public static SequencedCollection<Double> getSquaredValues(
            final SequencedCollection<? extends Number> collection) {
        return collection.stream().map(nr -> (nr.doubleValue() * nr.doubleValue())).collect(Collectors.toList());
    }

    /**
     * Divides each value in the collection by the given value `divisor` and returns an array of the results.
     * If the `divisor` is less than 1, throw an {@link IllegalArgumentException}.
     * Please use the `mapToDouble` and `toArray` methods.
     */
    public static double[] divideValues(final SequencedCollection<? extends Number> collection, final Number divisor) {
        if (divisor.doubleValue() < 1) {
            throw new IllegalArgumentException();
        }

        return collection.stream().mapToDouble(nr -> nr.doubleValue() / divisor.doubleValue()).toArray();
    }

    /**
     * Count all values in the collection.
     * Please use the `count` method.
     */
    public static long countValues(final Collection<?> collection) {
        return collection.stream().count();
    }

    /**
     * Count all values in the collection.
     * Please use the `stream` and `forEach` methods.
     */
    public static int countValuesUsingForEach(final SequencedCollection<?> collection) {
        final int[] counter = new int[1];
        collection.forEach(nr -> counter[0]++);
        return counter[0];
    }

    /**
     * Count all values in the collection.
     * Please use the `stream` and `forEachOrdered` methods.
     * <p>
     * This method guarantees to respect the encounter order in a stream.
     * Most likely, you will not notice the difference in this non-parallel case.
     */
    public static int countValuesUsingForEachOrdered(final SequencedCollection<?> collection) {
        final int[] counter = new int[1];

        collection.stream().forEachOrdered(nr -> counter[0]++);
        return counter[0];
    }

    /**
     * Count all values in the collection.
     * Please use the {@link SequencedCollection#parallelStream()} and `forEach` methods.
     * <p>
     * Please notice that parallelStream will (usually) result in a wrong count, even with synchronized.
     */
    public static synchronized int countValuesUsingParallelForEach(final SequencedCollection<?> collection) {
        final int[] counter = new int[1];
        collection.parallelStream().forEach(nr -> counter[0]++);
        return counter[0];
    }

    /**
     * Count all values in the collection.
     * Please use the `parallelStream` and `forEachOrdered` methods.
     * <p>
     * This method guarantees to respect the encounter order in a stream.
     * Please notice that the issue from above is fixed, but you lose the parallelism.
     * But it is still useful in cases. You can perform operations in parallel first (map, etc.)
     * and then print the result in order (forEachOrdered).
     */
    public static int countValuesUsingParallelForEachOrdered(final SequencedCollection<?> collection) {
        final int[] counter = new int[1];

        collection.parallelStream().forEachOrdered(nr -> counter[0]++);
        return counter[0];
    }

    /**
     * Returns the sum of all values in the collection.
     * Please use the `reduce` method and `sum` method of `Integer` class.
     */
    public static Optional<Integer> calculateSumUsingReduce(final Collection<Integer> collection) {
        return collection.stream().reduce(Integer::sum);
    }

    /**
     * Returns the sum of all values in the collection.
     * Please use the `mapToInt` and `sum` methods, and `intValue` method of `Number` class.
     */
    public static Number calculateSumUsingIntMap(final Collection<? extends Number> collection) {
        return collection.stream().mapToInt(Number::intValue).sum();
    }

    /**
     * Returns the average of all values in the collection.
     * Please use the `mapToInt` and `average` methods, and `intValue` method of `Number` class.
     */
    public static OptionalDouble calculateAverageUsingIntMap(final Collection<? extends Number> collection) {
        return collection.stream().mapToInt(Number::intValue).average();
    }

    /**
     * Returns the average of all values in the collection.
     * Please use the `collect` method with `averagingInt` collector, and `intValue` method of `Number` class.
     */
    public static double calculateAverageUsingCollect(final Collection<? extends Number> collection) {
        return collection.stream().collect(Collectors.averagingInt(Number::intValue));
    }

    /**
     * Generates a list of even integers.
     * If the seed is not even, throw an {@link IllegalArgumentException}.
     * Please use the `iterate` method of `Stream` class.
     */
    public static SequencedCollection<Integer> generateEvenIntegers(final int seed, final int amount) {
        if (seed % 2 != 0) {
            throw new IllegalArgumentException();
        }
        return Stream.iterate(seed, n -> n + 2).limit(amount).collect(Collectors.toList());
    }

    /**
     * Generate a list of random integers.
     * Please use the `generate` method of `Stream` class
     * and `nextInt` method of {@link ThreadLocalRandom} or {@link java.util.Random} class.
     */
    public static Collection<Integer> generateRandomIntegers(final int origin, final int bound, final int amount) {
        return Stream.generate(() -> ThreadLocalRandom.current().nextInt(origin, bound)).limit(amount)
                .collect(Collectors.toList());

    }

    /**
     * Generate a list of random numbers.
     * Please use the `ints` method of {@link ThreadLocalRandom} or {@link java.util.Random} class
     * and the `boxed` method of `IntStream` class.
     */
    public static SequencedCollection<Integer> getRandomIntegers(final int origin, final int bound, final int amount) {
        return ThreadLocalRandom.current().ints(origin, bound).limit(amount)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of numbers from the input collection.
     * If the number is not self-dividable (0) (throws an {@link ArithmeticException} when divided by itself),
     * the result list should contain `null` instead of the number.
     * Please use the `map` method and handle the exception inside.
     */
    public static SequencedCollection<Integer> getSelfDividableNumbersOrNull(
            final SequencedCollection<Integer> collection) {
        return collection.stream().map(nr -> {
            try {
                if (nr == 0 || nr / nr != 1) {
                    throw new ArithmeticException();
                }
                return nr;
            } catch (ArithmeticException e) {
                return null;
            }
        }).collect(Collectors.toList());
    }

    /**
     * Flattens the collection of collections into a single collection.
     * Please use the `flatMap` method.
     */
    public static <T> SequencedCollection<T> flattenCollections(
            final SequencedCollection<SequencedCollection<T>> collections) {
        return collections.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Sorts the collection in natural order.
     * Please use the `sorted` method.
     */
    public static <T extends Comparable<? super T>> SequencedCollection<T> sortInNaturalOrder(
            final Collection<T> collection) {
        return collection.stream().sorted().collect(Collectors.toList());
    }

    /**
     * Sorts the collection in reverse order.
     * Please use the `sorted` method and {@link Comparator#reverseOrder()}.
     * <p>
     * Please note that sorting in reverse order is not always the same as sorting
     * in natural order and then reversing the result.
     */
    public static <T extends Comparable<? super T>> SequencedCollection<T> sortInReverseOrder(
            final Collection<T> collection) {
        return collection.stream().sorted().collect(Collectors.toList()).reversed();
    }

    /**
     * Partitions the collection into two lists - true (even) and false (odd).
     * Please use the `partitioningBy` method.
     */
    public static Map<Boolean, List<Integer>> partitionByEvenness(final SequencedCollection<Integer> collection) {
        return collection.stream().collect(Collectors.partitioningBy(num -> num % 2 == 0));
    }

    /**
     * Collects the collection into a {@link Map} where the keys are the sign of the numbers
     * and the values are the numbers themselves.
     * The sign can be "negative", "positive" or "zero".
     * Please use the `groupingBy` method and a custom function.
     * You can use `doubleValue` method of `Number` class.
     */
    public static <T extends Number> Map<String, List<T>> groupByNumberSign(final SequencedCollection<T> collection) {
        return collection.stream().collect(Collectors.groupingBy(nr -> {
            double value = nr.doubleValue();
            if (value < 0) {
                return "negative";
            } else if (value > 0) {
                return "positive";
            } else {
                return "zero";
            }
        }));
    }

    /**
     * Collects the collection into a `map` where the keys are the original numbers and the values are number plus one.
     * If the key already exists, the values are multiplied.
     * Please use the `Collectors.toMap` method.
     */
    public static Map<Integer, Integer> mapWithMergedValues(final Collection<Integer> collection) {
        return collection.stream().collect(Collectors.toMap(nr -> nr, nr -> nr + 1,
                (existing, replacement) -> existing * replacement));
    }

    /**
     * Returns true if the collection contains at least one negative number.
     * Please use the `anyMatch` method and `doubleValue` method of `Number` class.
     */
    public static boolean containsAnyNegative(final Collection<? extends Number> collection) {
        return collection.stream().anyMatch(nr -> nr.doubleValue() < 0);
    }

    /**
     * Returns true if all the numbers in the collection are positive.
     * Please use the `allMatch` method and `doubleValue` method of `Number` class.
     */
    public static boolean areAllPositive(final Collection<? extends Number> collection) {
        return collection.stream().allMatch(nr -> nr.doubleValue() > 0);
    }

    /**
     * Returns true if none of the numbers in the collection is zero.
     * Please use the `noneMatch` method and `doubleValue` method of `Number` class.
     */
    public static boolean containsNoZero(final Collection<? extends Number> collection) {
        return collection.stream().noneMatch(nr -> nr.doubleValue() == 0);
    }

    /**
     * Concatenates two collections into a single collection.
     * Please use the `concat` method of `Stream` class.
     */
    public static <T> SequencedCollection<T> concatenateCollections(final SequencedCollection<T> collection1,
                                                                    final SequencedCollection<T> collection2) {
        return Stream.concat(collection1.stream(), collection2.stream()).collect(Collectors.toList());
    }

    /**
     * Returns a list of positive numbers from the input collection.
     * The list should end when the first non-positive number is encountered.
     * Please use the `takeWhile` method and `doubleValue` method of `Number` class.
     */
    public static <T extends Number> SequencedCollection<T> takeWhileNumberIsPositive(
            final SequencedCollection<T> collection) {
        return collection.stream().takeWhile(nr -> nr.doubleValue() > 0).collect(Collectors.toList());
    }

    /**
     * Returns a list of numbers from the input collection.
     * The list should start when the first non-positive number is encountered.
     * Please use the `dropWhile` method and `doubleValue` method of `Number` class.
     */
    public static <T extends Number> SequencedCollection<T> dropWhileNumberIsPositive(
            final SequencedCollection<T> collection) {
        return collection.stream().dropWhile(nr -> nr.doubleValue() > 0).collect(Collectors.toList());
    }

    /**
     * The main method.
     * <p>
     * You may find {@link Stream#peek(Consumer)} method useful for debugging.
     */
    public static void main(final String[] args) {

        // Do not worry about this data structure. It is just a collection. You can use it as any other collection.
        final SequencedCollection<Integer> numbers = new CopyOnWriteArrayList<>(List.of(2, 2, -3, 0, 2));

        /*System.out.println(generateNumbersExclusive(2, 5)); // [2, 3, 4]
        System.out.println(generateNumbersInclusive(2, 5)); // [2.0, 3.0, 4.0, 5.0]
        System.out.println();

        System.out.println(convertToList(numbers)); // [2, 2, -3, 0, 2]
        System.out.println(getDistinctValues(numbers)); // [2, -3, 0]
        System.out.println(convertToUnmodifiableSet(numbers)); // [0, 2, -3]
        System.out.println(findMaxValue(numbers)); // Optional[2]
        System.out.println(findMinValue(numbers)); // Optional[-3]
        System.out.println();

        System.out.println(filterNonNegativeValues(numbers)); // [2, 2, 0, 2]
        System.out.println(findAnyNegativeNumber(numbers)); // Optional[-3]
        System.out.println(findAnyNegativeNumberOrElse(numbers)); // -3
        System.out.println(findAnyNegativeNumberOrElse(filterNonNegativeValues(numbers))); // Integer.MAX_VALUE
        System.out.println(findFirstNegativeNumberOrElseGet(numbers)); // -3
        System.out.println(convertToStringList(numbers)); // [2, 2, -3, 0, 2] (strings)
        System.out.println(convertPositivesToSingleString(numbers)); // String[2, 2, 2]
        System.out.println();

        System.out.println(getLimitedList(numbers, 3)); // [2, 2, -3]
        System.out.println(getLimitedList(numbers, 0)); // []
        System.out.println(getLimitedList(numbers, numbers.size() * 2)); // [2, 2, -3, 0, 2]
        System.out.println();

        System.out.println(getListFromIndex(numbers, 3)); // [0, 2]
        System.out.println(getListFromIndex(numbers, 0)); // [2, 2, -3, 0, 2]
        System.out.println(getListFromIndex(numbers, Byte.MAX_VALUE)); // []
        System.out.println();

        System.out.println(getSquaredValues(numbers)); // [4.0, 4.0, 9.0, 0.0, 4.0]
        System.out.println(Arrays.toString(divideValues(numbers, 2.0))); // [1.0, 1.0, -1.5, 0.0, 1.0]
        System.out.println();

        System.out.println(countValues(numbers)); // 5
        System.out.println(countValuesUsingForEach(numbers)); // 5
        System.out.println(countValuesUsingForEachOrdered(numbers)); // 5
        //System.out.println(countValuesUsingParallelForEach(IntStream.range(0, 10_000_000)
        .boxed().toList())); // !=? 10000000
        //System.out.println(countAllValuesParallelForEachOrdered(IntStream.range(0, 10_000_000)
        .boxed().toList())); // 10000000
        System.out.println();


        System.out.println(calculateSumUsingReduce(numbers)); // Optional[3]
        System.out.println(calculateSumUsingIntMap(numbers)); // 3
        System.out.println(calculateAverageUsingIntMap(numbers)); // OptionalDouble[0.6]
        System.out.println(calculateAverageUsingCollect(numbers)); // 0.6
        System.out.println();

        System.out.println(generateEvenIntegers(2, 5)); // [2, 4, 6, 8, 10]
        try {
            generateEvenIntegers(3, 5); // Is not even, IllegalArgumentException
            System.out.println("This line should not be printed");
        } catch (final IllegalArgumentException ignored) {
            System.out.println("IllegalArgumentException was thrown (as expected)");
        }
        System.out.println();

        System.out.println(generateRandomIntegers(-9, 9, 5)); // [*, *, *, *, *]
        System.out.println(getRandomIntegers(-9, 9, 5)); // [*, *, *, *, *]
        System.out.println();

        System.out.println(getSelfDividableNumbersOrNull(numbers)); // [2, 2, -3, null, 2]
        System.out.println(flattenCollections(List.of(
        numbers, numbers.reversed()))); // [2, 2, -3, 0, 2, 2, 0, -3, 2, 2]
        System.out.println();

        System.out.println(sortInNaturalOrder(numbers)); // [-3, 0, 2, 2, 2]
        System.out.println(sortInReverseOrder(numbers)); // [2, 2, 2, 0, -3]
        System.out.println();

        System.out.println(partitionByEvenness(numbers)); // {false=[-3], true=[2, 2, 0, 2]}
        System.out.println(groupByNumberSign(numbers)); // {zero=[0], negative=[-3], positive=[2, 2, 2]}
        System.out.println(mapWithMergedValues(numbers)); // {0=1, -3=-2, 2=27} (for 2: (2 + 1)(2 + 1)(2 + 1) = 27)
        System.out.println();

        System.out.println(containsAnyNegative(numbers)); // true
        System.out.println(areAllPositive(numbers)); // false
        System.out.println(containsNoZero(numbers)); // false
        System.out.println();

        System.out.println(concatenateCollections(numbers, numbers.reversed())); // [2, 2, -3, 0, 2, 2, 0, -3, 2, 2]
        System.out.println(takeWhileNumberIsPositive(numbers)); // [2, 2]
        System.out.println(dropWhileNumberIsPositive(numbers)); // [-3, 0, 2]*/
    }
}
