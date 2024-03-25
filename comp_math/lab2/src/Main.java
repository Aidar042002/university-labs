import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int matrixRows = n;
        int matrixColumns = n + 1;

        List<List<Double>> matrix = new ArrayList<>();

        IntStream.range(0, matrixRows).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Double::parseDouble)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Double> result = Result.solveByGauss(n, matrix);

        if (Result.isSolutionExists) {
            // Output solution
            for (Double value : result) {
                bufferedWriter.write(value + "\n");
            }
        } else {
            // Output error message
            bufferedWriter.write(Result.errorMessage + "\n");
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
}

class Result {

    public static boolean isSolutionExists = true;
    public static String errorMessage = "The system has no roots of equations or has an infinite set of them.";

    public static List<Double> solveByGauss(int n, List<List<Double>> matrix) {
        List<Double> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            result.add(0.0);
        }

        for (int i = 0; i < n; i++) {
            int maxRow = i;
            double maxValue = Math.abs(matrix.get(i).get(i));
            for (int k = i + 1; k < n; k++) {
                double absValue = Math.abs(matrix.get(k).get(i));
                if (absValue > maxValue) {
                    maxValue = absValue;
                    maxRow = k;
                }
            }

            if (Math.abs(maxValue) == 0) {
                isSolutionExists = false;
                return result;
            }

            if (maxRow != i) {
                List<Double> temp = matrix.get(i);
                matrix.set(i, matrix.get(maxRow));
                matrix.set(maxRow, temp);
            }

            for (int k = i + 1; k < n; k++) {
                double coef = matrix.get(k).get(i) / matrix.get(i).get(i);
                for (int j = i; j < n + 1; j++) {
                    matrix.get(k).set(j, matrix.get(k).get(j) - coef * matrix.get(i).get(j));
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix.get(i).get(j) * result.get(j);
            }
            double x = (matrix.get(i).get(n) - sum) / matrix.get(i).get(i);
            result.set(i, x);
        }

        List<Double> residuals = new ArrayList<>();
        for (List<Double> row : matrix) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += row.get(j) * result.get(j);
            }
            residuals.add(row.get(n) - sum);
        }

        for (int i = 0; i < n; i++) {
            if (Math.abs(result.get(i)) < 1e-10 && Math.abs(matrix.get(i).get(n)) > 1e-10) {
                isSolutionExists = false;
                return new ArrayList<>();
            }
        }
        result.addAll(residuals);

        return result;
    }
}
