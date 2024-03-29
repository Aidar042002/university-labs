public class Main {
    public static void main(String[] args) {
        double x1 = 1; // начальная первая точка
        double deltaX = 1; // величина шага
        double e1 = 0.00005; // точность для Fmin
        double e2 = 0.0001; // точность для x_min



        // Функция f(x) = x^4 + x^2 + x + 1
        Function f = (x) -> Math.pow(x, 4) + Math.pow(x, 2) + x + 1;

        int iteration = 0;
        double x2, x3, x_cp, Fmin, x_min, f_x1, f_x2, f_x3;

        do {
            // Шаг 2: Вычислить вторую точку x2
            x2 = deltaX + x1;

            // Шаг 3: Вычислить значения функции f(x1) и f(x2)
            f_x1 = f.apply(x1);
            f_x2 = f.apply(x2);

            // Шаг 4: Сравнить точки f(x1) и f(x2)
            if (f_x1 > f_x2) {
                x3 = x1 + 2 * deltaX; // a) если f(x1) > f(x2)
            } else {
                x3 = x1 - deltaX; // б) если f(x1) <= f(x2)
            }

            // Шаг 5: Вычислить f(x3)
            f_x3 = f.apply(x3);

            // Шаг 6: Найти Fmin и x_min
            if (f_x1 <= f_x2 && f_x1 <= f_x3) {
                Fmin = f_x1;
                x_min = x1;
            } else if (f_x2 <= f_x1 && f_x2 <= f_x3) {
                Fmin = f_x2;
                x_min = x2;
            } else {
                Fmin = f_x3;
                x_min = x3;
            }

            // Шаг 7: Найти x_cp и f(x_cp)
            double numerator = ((Math.pow(x2, 2) - Math.pow(x3, 2)) * f_x1 +
                    (Math.pow(x3, 2) - Math.pow(x1, 2)) * f_x2 +
                    (Math.pow(x1, 2) - Math.pow(x2, 2)) * f_x3);
            double denominator = ((x2 - x3) * f_x1 + (x3 - x1) * f_x2 + (x1 - x2) * f_x3);

            if (denominator != 0) {
                x_cp = 0.5 * numerator / denominator;
            } else {
                // Если знаменатель равен нулю, то результат итерации является прямой
                System.out.println("Знаменатель равен нулю. Итерация является прямой.");
                x1 = x_min;
                continue;
            }

            double f_x_cp = f.apply(x_cp);

            // Шаг 8: Проверка условий окончания расчета
            double condition1 = Math.abs((Fmin - f_x_cp) / f_x_cp);
            double condition2 = Math.abs((x_min - x_cp) / x_cp);

            if (condition1 < e1 && condition2 < e2) {
                System.out.println("Решение найдено:");
                System.out.println("x* = " + x_cp);
                System.out.println("f(x*) = " + f_x_cp);
                break;
            }


            // Переход к следующей итерации
            if (x_cp >= Math.min(x1, Math.min(x2, x3)) && x_cp <= Math.max(x1, Math.max(x2, x3))) {
                if (x_min <= x_cp) {
                    x1 = x_min;
                } else {
                    x1 = x_cp;
                }
            } else {
                x1 = x_cp;
            }

            iteration++;
        } while (true);
    }

    interface Function {
        double apply(double x);
    }
}
