package ru.javaops.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 3, 2, 3 };
        System.out.println(minValue(array));

        array = new int[]{9, 8};
        System.out.println(minValue(array));

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(5);
        list.add(12);
        list.add(10);
        list.add(1);
        list.add(3);
        list = oddOrEven(list);
        System.out.println(list);
    }

    private static int minValue(int[] values) {
        final int[] index = {1};
        return Arrays.stream(values)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .distinct()
                .map(integer -> {
                    int result = integer * index[0];
                    index[0] *= 10;
                    return result;
                })
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers
                .stream()
                .filter(val -> integers.stream().mapToInt(Integer::intValue).sum() % 2 != val % 2)
                .collect(Collectors.toList());
    }
}
