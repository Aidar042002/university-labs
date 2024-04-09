import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        double res = quadraticApproximationMethod();
        System.out.println(res);
    }

    static double f(double x) {
        return Math.pow(x,4) +Math.pow(x,2) +x+1;
    }

    static double quadraticApproximationMethod() {
        //шаг1
        double x1 = -1;
        double delta_x = 0.05;
        double epsilon = 0.0001;
        double epsilon1 = epsilon;
        double epsilon2 = epsilon;

        double[] x = new double[3];
        double[] f_x = new double[3];

        double x_result = 0.0;

        boolean can_continue = true;
        while (can_continue) {
            //шаг2
            double x2 = x1 + delta_x;
            double x3;
            //шаг3,4
            if (f(x1) > f(x2)) {
                x3 = x1 + 2 * delta_x;
            } else {
                x3 = x1 - delta_x;
            }

            while (true) {
                //шаг5
                double[] xs = {x1, x2, x3};
                for (int i = 0; i < xs.length; i++) {
                    x[i] = xs[i];
                    f_x[i] = f(xs[i]);
                }
                //Для хранения значений и соответствующих функций
                HashMap<Double, Double> map = new HashMap<>();
                for (int i = 0; i < x.length; i++) {
                    map.put(f_x[i], x[i]);
                }

                //шаг6
                double x_min = map.getOrDefault(min(f_x), x1);
                double F_min = f(x_min);

                double nominator = (x2*x2 - x3*x3) * f(x1) + (x3*x3 - x1*x1) * f(x2) + (x1*x1 - x2*x2) * f(x3);
                double denominator = 2 * ((x2 - x3) * f(x1) + (x3 - x1) * f(x2) + (x1 - x2) * f(x3));

                //шаг7 находим прбилижение x
                double x_ = nominator / denominator;
                //шаг8 проверка условия окончания
                boolean is_e1 = Math.abs((F_min - f(x_)) / f(x_)) < epsilon1;
                boolean is_e2 = Math.abs((x_min - x_) / x_) < epsilon2;

                if (denominator == 0) {
                    x1 = x_min;
                    break;
                }

                if (is_e1 && is_e2) {
                    x_result = x_;
                    can_continue = false;
                    break;
                } else {
                    if (x1 < x_ && x_ < x3) {
                        double x_mid;
                        if (f(x_min) < f(x_)) {
                            x_mid = x_min;
                        } else {
                            x_mid = x_;
                        }

                        double[] filtered_xs = filter(x, x_mid);
                        double x_left = max(filtered_xs[0], filtered_xs[1]);
                        double x_right = min(filtered_xs[2], filtered_xs[3]);

                        x1 = x_left;
                        x2 = x_mid;
                        x3 = x_right;
                    } else {
                        x1 = x_;
                        break;
                    }
                }
            }
        }
        return x_result;
    }

    static double min(double[] arr) {
        double min = Double.MAX_VALUE;
        for (double v : arr) {
            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    static double[] filter(double[] arr, double x_mid) {
        double[] filtered = new double[4];
        int idx = 0;
        for (double v : arr) {
            if (v < x_mid) {
                filtered[idx++] = v;
            }
        }
        filtered[idx++] = x_mid;
        for (double v : arr) {
            if (v > x_mid) {
                filtered[idx++] = v;
            }
        }
        return filtered;
    }

    static double max(double a, double b) {
        return Math.max(a, b);
    }

    static double min(double a, double b) {
        return Math.min(a, b);
    }
}
