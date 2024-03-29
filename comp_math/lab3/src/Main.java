import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class SNAEFunctions {
    static double k = 0.4;
    static double a = 0.9;

    private static double first_function(List<Double> args) {
        return Math.sin(args.get(0));
    }

    private static double second_function(List<Double> args) {
        return (args.get(0) * args.get(1)) / 2;
    }

    private static double third_function(List<Double> args) {
        return Math.tan(args.get(0) * args.get(1) + k) - Math.pow(args.get(0), 2);
    }

    private static double fourth_function(List<Double> args) {
        return a * Math.pow(args.get(0), 2) + 2 * Math.pow(args.get(1), 2) - 1;
    }

    private static double fifth_function(List<Double> args) {
        return Math.pow(args.get(0), 2) + Math.pow(args.get(1), 2) + Math.pow(args.get(2), 2) - 1;
    }

    private static double six_function(List<Double> args) {
        return 2 * Math.pow(args.get(0), 2) + Math.pow(args.get(1), 2) - 4 * args.get(2);
    }

    private static double seven_function(List<Double> args) {
        return 3 * Math.pow(args.get(0), 2) - 4 * args.get(1) + Math.pow(args.get(2), 2);
    }

    private static double default_function(List<Double> args) {
        return 0.0;
    }

    public static List<Function<List<Double>, Double>> get_functions(int n) {
        switch (n) {
            case (1):
                return List.of(SNAEFunctions::first_function, SNAEFunctions::second_function);
            case (2):{
                SNAEFunctions.k = 0.4;
                SNAEFunctions.a = 0.9;
                return List.of(SNAEFunctions::third_function, SNAEFunctions::fourth_function);
            }
            case (3):{
                SNAEFunctions.k = 0.0;
                SNAEFunctions.a = 0.5;
                return List.of(SNAEFunctions::third_function, SNAEFunctions::fourth_function);
            }
            case (4):
                return List.of(SNAEFunctions::fifth_function, SNAEFunctions::six_function, SNAEFunctions::seven_function);
            default:
                return List.of(SNAEFunctions::default_function);
        }
    }
}


class Result {

    public static List<Double> solve_by_fixed_point_iterations(int system_id, int number_of_unknowns, List<Double> initial_approximations) {
        List<Function<List<Double>, Double>> functions = SNAEFunctions.get_functions(system_id);

        double epsilon = 1e-5;

        List<Double> x = new ArrayList<>(initial_approximations);
        List<Double> xPrev = new ArrayList<>(initial_approximations);

        boolean converged = false;
        int maxIterations = 100;

        for (int iter = 0; iter < maxIterations; iter++) {//1
            List<List<Double>> jacobian = new ArrayList<>();
            for (int i = 0; i < number_of_unknowns; i++) {//2
                List<Double> row = new ArrayList<>();
                for (int j = 0; j < number_of_unknowns; j++) {//3
                    double h = epsilon;
                    List<Double> xPlusH = new ArrayList<>(x);
                    xPlusH.set(j, x.get(j) + h);
                    double fPlusH = functions.get(i).apply(xPlusH);

                    double f = functions.get(i).apply(x);

                    row.add((fPlusH - f) / h);
                }
                jacobian.add(row);
            }

            List<Double> fValues = new ArrayList<>();
            for (Function<List<Double>, Double> function : functions) {
                fValues.add(function.apply(x));
            }

            List<Double> delta_x = linearSystem(jacobian, negate(fValues));

            for (int i = 0; i < number_of_unknowns; i++) {
                x.set(i, x.get(i) + delta_x.get(i));
            }

            converged = true;
            for (int i = 0; i < number_of_unknowns; i++) {
                if (Math.abs(x.get(i) - xPrev.get(i)) > epsilon) {
                    converged = false;
                    break;
                }
            }

            if (converged) {
                break;
            }

            xPrev = new ArrayList<>(x);
        }

        for (int i = 0; i < x.size(); i++) {
            x.set(i, Math.round(x.get(i) * 1e5) / 1e5);
        }

        return x;
    }

    private static List<Double> linearSystem(List<List<Double>> A, List<Double> b) {
        int n = A.size();
        List<Double> x = new ArrayList<>(Collections.nCopies(n, 0.0));

        for (int i = 0; i < n; i++) {
            double pivot = A.get(i).get(i);

            for (int j = i + 1; j < n; j++) {
                double ratio = A.get(j).get(i) / pivot;
                for (int k = i; k < n; k++) {
                    A.get(j).set(k, A.get(j).get(k) - ratio * A.get(i).get(k));
                }
                b.set(j, b.get(j) - ratio * b.get(i));
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A.get(i).get(j) * x.get(j);
            }
            x.set(i, (b.get(i) - sum) / A.get(i).get(i));
        }

        return x;
    }

    private static List<Double> negate(List<Double> list) {
        List<Double> negated = new ArrayList<>();
        for (Double num : list) {
            negated.add(-num);
        }
        return negated;
    }

}



class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int system_id = Integer.parseInt(bufferedReader.readLine().trim());

        int number_of_unknowns = Integer.parseInt(bufferedReader.readLine().trim());

        List<Double> initial_approximations = IntStream.range(0, number_of_unknowns).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(toList());

        List<Double> result = Result.solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );
        bufferedReader.close();
        bufferedWriter.close();
    }
}

