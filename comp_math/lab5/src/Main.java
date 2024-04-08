import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.sin;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
class Result {

    private static double first_function(double x, double y) {
        return sin(x);
    }

    private static double second_function(double x, double y) {
        return (x * y)/2;
    }

    private static double third_function(double x, double y) {
        return y - (2 * x)/y;
    }

    private static double fourth_function(double x, double y) {
        return x + y;
    }

    private static double default_function(double x, double y) {
        return 0.0;
    }

    /*
     * How to use this function:
     *    BiFunction<Double, Double, Double> func = get_function(4);
     *    func.apply(0.0001);
     */
    private static BiFunction<Double, Double, Double> get_function(int n) {
        switch (n) {
            case (1):
                return Result::first_function;
            case (2):
                return Result::second_function;
            case (3):
                return Result::third_function;
            case (4):
                return Result::fourth_function;
            default:
                return Result::default_function;
        }
    }

    /*
     * Complete the 'solveByEuler' function below.
     *
     * The function is expected to return a DOUBLE.
     * The function accepts following parameters:
     *  1. INTEGER f
     *  2. DOUBLE epsilon
     *  3. DOUBLE a
     *  4. DOUBLE y_a
     *  5. DOUBLE b
     */
    public static double solveByEuler(int f, double epsilon, double a,  double y_a, double b) {
        BiFunction<Double, Double, Double> func = get_function(f);

        double h = 0.1;
        double t = epsilon / 10.0;

        while (a < b) {
            double dydx = func.apply(a, y_a);
            double next_y = y_a + h * dydx;

            while (Math.abs(next_y - y_a) > t) {
                h /= 2;
                next_y = y_a + h * dydx;
            }

            y_a = next_y;
            a += h;

            if (a + h > b) {
                h = b - a;
            }
        }

        return y_a;
    }

}
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int f = Integer.parseInt(bufferedReader.readLine().trim());

        double epsilon = Double.parseDouble(bufferedReader.readLine().trim());

        double a = Double.parseDouble(bufferedReader.readLine().trim());

        double y_a = Double.parseDouble(bufferedReader.readLine().trim());

        double b = Double.parseDouble(bufferedReader.readLine().trim());

        double result = Result.solveByEuler(f, epsilon, a, y_a, b);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

/*
задание
Title: Метод Эйлера

Description: [ДИСКЛЕЙМЕР: Это не всё, что вам нужно сделать для сдачи лабораторной работы по курсу, но этого достаточно, чтобы получить вариант на следующую работу. Проверьте требования и вариант в методическом пособии в файлах Teams.]

Реализуйте метод Эйлера для решения обыкновенных дифференциальных уравнений по начальному значению (задача Коши) в интервале от a до b [a,b].
f
epsilon
a
y(a)
b
f - номер уравнения, где уравнение в виде y'=f(x,y). Вы должны получить функцию по номеру из входных данных в методе get_function.
Вы должны определить и пересчитать шаг h самостоятельно.
Вы должны вычислить и вернуть y(b) с разницей, не превышающей epsilon.
 */