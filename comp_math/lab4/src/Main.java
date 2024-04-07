import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.sin;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        double a = Double.parseDouble(bufferedReader.readLine().trim());

        double b = Double.parseDouble(bufferedReader.readLine().trim());

        int f = Integer.parseInt(bufferedReader.readLine().trim());

        double epsilon = Double.parseDouble(bufferedReader.readLine().trim());

        double result = Result.calculate_integral(a, b, f, epsilon);
        if(!Result.has_discontinuity){
            bufferedWriter.write(String.valueOf(result));
        } else {
            bufferedWriter.write(String.valueOf(Result.error_message));
        }
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}


class Result {
    public static String error_message = "Integrated function has discontinuity or does not defined in current interval";
    public static boolean has_discontinuity = false;

    private static double first_function(double x) {
        return 1 / x;
    }

    private static double second_function(double x) {
        return sin(x) / x;
    }

    private static double third_function(double x) {
        return x * x + 2;
    }

    private static double fourth_function(double x) {
        return 2 * x + 2;
    }

    private static double five_function(double x) {
        return log(x);
    }

    /*
     * How to use this function:
     *    Function<Double, Double> func = get_function(4);
     *    func.apply(0.0001);
     */
    private static Function<Double, Double> get_function(int n) {
        switch (n) {
            case (1):
                return Result::first_function;
            case (2):
                return Result::second_function;
            case (3):
                return Result::third_function;
            case (4):
                return Result::fourth_function;
            case (5):
                return Result::five_function;
            default:
                throw new UnsupportedOperationException("Function " + n + " not defined.");
        }
    }


    /*
     * Complete the 'calculate_integral' function below.
     *
     * The function is expected to return a DOUBLE.
     * The function accepts following parameters:
     *  1. DOUBLE a
     *  2. DOUBLE b
     *  3. INTEGER f
     *  4. DOUBLE epsilon
     */

    public static double calculate_integral(double a, double b, int f, double epsilon) {
        Function<Double, Double> function = get_function(f);
        double h = (b - a) / 2;
        double integral = 0.5 * (function.apply(a) + function.apply(b));

        if (a > b) {
            h = -h;
        }

        double previous;
        do {
            previous = integral;
            h /= 2;
            double sum = 0;

            if (a > b) {
                for (double x = b + h; x < a; x += 2 * h) {
                    sum += function.apply(x);
                }
            } else {
                for (double x = a + h; x < b; x += 2 * h) {
                    sum += function.apply(x);
                }
            }

            integral = 0.5 * integral + h * sum;
        } while (Math.abs(integral - previous) > epsilon);

        if (Double.isNaN(integral) || Double.isInfinite(integral)) {
            has_discontinuity = true;
            return 0;
        }

        if (a > b) {
            integral = -integral;
        }

        return integral;
    }

}

/*
задание
Реализуйте метод трапеций для вычисления интеграла от выбранной функции на интервале от a до b.
• Если функция имеет разрыв второго рода или "скачок", или если функция не определена какой-либо частью в интервале от a до b, то вам следует указать переменные error_message и hasDiscontinuity.
• Сообщение об ошибке, которое вы должны указать: "Integrated function has discontinuity or does not defined in current interval".
• Если функция имеет устранимый разрыв первого рода, то вы должны уметь вычислить интеграл.
• Если a > b, то интеграл должен иметь отрицательное значение.
Формат ввода:
a
b
f
epsilon
, где a и b - границы интеграла, f - номер функции, epsilon - максимальная разница между двумя вашими итерациями (итерация - это некоторое разбиение на отрезки).
Формат вывода:
I
, где I - ваш вычисленный интеграл для текущего количества разбиений.
 */