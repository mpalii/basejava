package ru.javaops.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 9, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        ArrayList<Integer> array = new ArrayList<>();
        array.add(2);
        array.add(9);
        array.add(3);
        array.add(3);
        array.add(2);
        array.add(3);
        System.out.println(oddOrEven(array));
    }

    /*
     * реализовать метод через стрим int minValue(int[] values).
     * Метод принимает массив цифр от 1 до 9, надо выбрать уникальные
     * и вернуть минимально возможное число, составленное из этих
     * уникальных цифр. Не использовать преобразование в строку и обратно.
     * Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
     */
    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((left, right) -> left * 10 + right)
                .orElseGet(null);
    }

    /*
     * реализовать метод List<Integer> oddOrEven(List<Integer> integers)
     * если сумма всех чисел нечетная - удалить все нечетные, если четная
     * - удалить все четные. Сложность алгоритма должна быть O(N).
     * Optional - решение в один стрим.
     */
    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers
                .stream()
                .filter(val -> sum % 2 != val % 2)
                .collect(Collectors.toList());
    }

}