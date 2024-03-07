import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'interpolate_by_spline' function below.
     *
     * The function is expected to return a DOUBLE.
     * The function accepts following parameters:
     *  1. DOUBLE_ARRAY x_axis
     *  2. DOUBLE_ARRAY y_axis
     *  3. DOUBLE x
     */

    public static double interpolate_by_spline(List<Double> x_axis, List<Double> y_axis, double x) {
        int size = x_axis.size();
        double[] intervals = new double[size - 1];
        double[] derivatives = new double[size - 1];
        double[] diagonals = new double[size];
        double[] subDiagonals = new double[size - 1];
        double[] mainDiagonals = new double[size];
        double[] solution = new double[size];

        for (int i = 0; i < size - 1; i++) {
            intervals[i] = x_axis.get(i + 1) - x_axis.get(i);
        }

        for (int i = 1; i < size - 1; i++) {
            derivatives[i] = (3.0 / intervals[i]) * (y_axis.get(i + 1) - y_axis.get(i)) - (3.0 / intervals[i - 1]) * (y_axis.get(i) - y_axis.get(i - 1));
        }

        diagonals[0] = 1;
        subDiagonals[0] = 0;
        solution[0] = 0;

        for (int i = 1; i < size - 1; i++) {
            mainDiagonals[i] = 2.0 * (x_axis.get(i + 1) - x_axis.get(i - 1)) - intervals[i - 1] * subDiagonals[i - 1];
            subDiagonals[i] = intervals[i] / mainDiagonals[i];
            solution[i] = (derivatives[i] - intervals[i - 1] * solution[i - 1]) / mainDiagonals[i];
        }

        mainDiagonals[size - 1] = 1;
        solution[size - 1] = 0;
        double[] c = new double[size];
        double[] b = new double[size];
        double[] d = new double[size];

        for (int j = size - 2; j >= 0; j--) {
            c[j] = solution[j] - subDiagonals[j] * c[j + 1];
            b[j] = (y_axis.get(j + 1) - y_axis.get(j)) / intervals[j] - intervals[j] * (c[j + 1] + 2.0 * c[j]) / 3.0;
            d[j] = (c[j + 1] - c[j]) / (3.0 * intervals[j]);
        }

        int interval = 0;
        while (interval < size - 1 && x > x_axis.get(interval + 1)) {
            interval++;
        }

        double a_i = y_axis.get(interval);
        double b_i = b[interval];
        double c_i = c[interval];
        double d_i = d[interval];
        double x_i = x_axis.get(interval);

        double result = a_i + b_i * (x - x_i) + c_i * Math.pow((x - x_i), 2) + d_i * Math.pow((x - x_i), 3);

        return result;
    }

}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int axisCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Double> x_axis = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Double::parseDouble)
                .collect(toList());

        List<Double> y_axis = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Double::parseDouble)
                .collect(toList());

        double x = Double.parseDouble(bufferedReader.readLine().trim());

        double result =Result.interpolate_by_spline(x_axis, y_axis, x);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}