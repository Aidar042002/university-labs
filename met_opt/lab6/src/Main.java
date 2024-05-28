import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    private static final double MUTATION_VALUE = 0.01;

    private static List<Integer> shortestRoute;
    private static int shortestRouteLength;

    public static void main(String[] args) {
        int size = 5;
        int step=3;
        int[][] matrix = {
                {0, 1, 1, 5, 3},
                {1, 0, 3, 1, 5},
                {1, 3, 0, 11, 1},
                {5, 1, 11, 0, 1},
                {3, 5, 1, 1, 0}
        };

        List<Integer> result = generation(size, matrix, 4, step);
        int length = routeLength(result, matrix);


        System.out.printf("Итераций = %d. Значение функции изменилось с %d на " +
                        "%d.%n",
                step, firstLength, length);

        shortestRoute = result;
        shortestRouteLength = routeLength(result, matrix);

        System.out.println("Кратчайший путь: " + shortestRoute);
        System.out.println("Длина кратчайшего пути: " + shortestRouteLength);
    }

    private static int firstLength;
    private static double firstAverage;
    private static int firstSum;
    private static int finalLength;
    private static double finalAverage;
    private static int finalSum;

    private static int routeLength(List<Integer> route, int[][] matrix) {
        int length = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            length += matrix[route.get(i)][route.get(i + 1)];
        }
        length += matrix[route.get(route.size() - 1)][route.get(0)];
        return length;
    }

    private static List<Integer> makeChild(List<Integer> p1, List<Integer> p2, int[] splits) {
        List<Integer> child = new ArrayList<>(Collections.nCopies(splits[0], null));
        child.addAll(p2.subList(splits[0], splits[1]));
        child.addAll(Collections.nCopies(p1.size() - splits[1], null));
        int i = 0;
        int j = splits[0] + 1;
        boolean exit = false;
        while (!exit) {
            if (child.get(i) != null) {
                i++;
                if (i >= p1.size()) {
                    exit = true;
                }
                continue;
            }
            while (!exit) {
                if (child.contains(p1.get(j))) {
                    j++;
                    if (j >= p1.size()) {
                        j = 0;
                    }
                    if (j == splits[0] + 1) {
                        exit = true;
                    }
                    continue;
                }
                child.set(i, p1.get(j));
                break;
            }
        }
        return child;
    }

    private static boolean mutateChild(List<Integer> child) {
        if (Math.random() < MUTATION_VALUE) {
            int[] splits = {new Random().nextInt(child.size()), new Random().nextInt(child.size())};
            Collections.swap(child, splits[0], splits[1]);
            return true;
        }
        return false;
    }

    private static List<Integer> makeChildren(List<Integer> p1, List<Integer> p2, int c, int[][] matrix) {
        while (true) {
            int[] splits = {new Random().nextInt(c + 1), new Random().nextInt(c + 1)};
            if (2 <= splits[1] - splits[0] && splits[1] - splits[0] < c - 1) {
                List<Integer> c1 = makeChild(p1, p2, splits);
                List<Integer> c2 = makeChild(p2, p1, splits);
                String par1 = p1.subList(0, splits[0]) + "|" + p1.subList(splits[0], splits[1]) + "|" + p1.subList(splits[1], p1.size());
                String par2 = p2.subList(0, splits[0]) + "|" + p2.subList(splits[0], splits[1]) + "|" + p2.subList(splits[1], p2.size());
                String ch1 = c1.subList(0, splits[0]) + "|" + c1.subList(splits[0], splits[1]) + "|" + c1.subList(splits[1], c1.size());
                String ch2 = c2.subList(0, splits[0]) + "|" + c2.subList(splits[0], splits[1]) + "|" + c2.subList(splits[1], c2.size());

                System.out.printf("%s  | %s | %d%n", par1, ch1, routeLength(c1, matrix));
                System.out.printf("%s  | %s | %d%n", par2, ch2, routeLength(c2, matrix));

                return c1;
            }
        }
    }

    private static List<Integer> generation(int c, int[][] matrix, int p, int g) {
        firstLength = 0;
        firstAverage = 0;
        firstSum = 0;
        finalLength = 0;
        finalAverage = 0;
        finalSum = 0;

        List<Integer> ogCities = new ArrayList<>();
        for (int i = 0; i < c; i++) {
            ogCities.add(i);
        }

        List<List<Integer>> population = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            List<Integer> route = new ArrayList<>(ogCities);
            Collections.shuffle(route);
            population.add(route);
        }

        for (int i = 0; i < g; i++) {
            System.out.printf("Поколение %d%n", i + 1);
            List<Integer> lengths = new ArrayList<>();
            for (List<Integer> route : population) {
                int length = routeLength(route, matrix);
                lengths.add(length);
            }

            if (i == 0) {
                firstLength = lengths.get(0);
                firstSum = lengths.stream().mapToInt(Integer::intValue).sum();
                firstAverage = (double) firstSum / lengths.size();
            }

            List<Double> probabilities = new ArrayList<>();
            for (int length : lengths) {
                probabilities.add(1.0 / length);
            }
            double totalProbability = probabilities.stream().mapToDouble(Double::doubleValue).sum();
            for (int j = 0; j < probabilities.size(); j++) {
                probabilities.set(j, probabilities.get(j) / totalProbability);
            }

            if (i != 0 && i < g - 1) {
                System.out.println("Код   | Значение целевой функции | Вероятность участия в процессе размножении");
                for (int j = 0; j < population.size(); j++) {
                    List<Integer> code = population.get(j);
                    int length = lengths.get(j);
                    double prob = probabilities.get(j);
                    System.out.printf("%s | %d | %f%n", code, length, prob);
                }
            } else {
                System.out.println("Код   |  Значение целевой функции");
                for (int j = 0; j < population.size(); j++) {
                    List<Integer> code = population.get(j);
                    int length = lengths.get(j);
                    System.out.printf("%s | %d%n", code, length);
                }
            }
            System.out.println();

            List<List<Integer>> allPairs = new ArrayList<>();
            for (int j = 0; j < p; j++) {
                for (int k = j + 1; k < p; k++) {
                    List<Integer> pair = new ArrayList<>();
                    pair.add(j);
                    pair.add(k);
                    allPairs.add(pair);
                }
            }
            Collections.shuffle(allPairs);
            List<List<Integer>> pairs = allPairs.subList(0, p / 2);
            StringBuilder pairsStr = new StringBuilder();
            for (List<Integer> pair : pairs) {
                pairsStr.append("(").append(pair.get(0) + 1).append(", ").append(pair.get(1) + 1).append(") и ");
            }
            pairsStr.delete(pairsStr.length() - 2, pairsStr.length());
            System.out.printf("Выбраны пары для скрещивания: %s%n", pairsStr);
            System.out.println("Родители | Потомки | Значение целевой функции для потомков");
            for (int j = 0; j < pairs.size(); j++) {
                List<Integer> pair = pairs.get(j);
                List<Integer> p1 = population.get(pair.get(0));
                List<Integer> p2 = population.get(pair.get(1));
                List<Integer> child = makeChildren(p1, p2, c, matrix);
                population.add(child);
            }
            System.out.println();
            population.sort((route1, route2) -> Integer.compare(routeLength(route1, matrix), routeLength(route2, matrix)));
            population = population.subList(0, p);
            System.out.println();
        }
        System.out.println("Итоговое поколение");
        List<Integer> lengths = new ArrayList<>();
        for (List<Integer> route : population) {
            int length = routeLength(route, matrix);
            lengths.add(length);
        }
        finalLength = lengths.get(0);
        finalSum = lengths.stream().mapToInt(Integer::intValue).sum();
        finalAverage = (double) finalSum / lengths.size();
        System.out.println("Код   | Значение целевой функции");
        for (int i = 0; i < population.size(); i++) {
            List<Integer> code = population.get(i);
            int length = lengths.get(i);
            System.out.printf("%s | %d%n", code, length);
        }
        System.out.println();
        return population.get(0);
    }
}
