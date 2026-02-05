import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        int threadsCount = 1000;
        Thread[] threads = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = countR(route);

                synchronized (sizeToFreq) {
                    sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        printStatistics();
    }
    public static String generateRoute(String letters, int length) {
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(
                    ThreadLocalRandom.current().nextInt(letters.length())
            ));
        }
        return route.toString();
    }

    public static int countR(String route) {
        int count = 0;
        for (char c : route.toCharArray()) {
            if (c == 'R') {
                count++;
            }
        }
        return count;
    }

    public static void printStatistics() {
        int maxFreq = 0;
        int mostCommonSize = 0;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mostCommonSize = entry.getKey();
            }
        }

        System.out.println(
                "Самое частое количество повторений " +
                        mostCommonSize +
                        " (встретилось " +
                        maxFreq +
                        " раз)"
        );

        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != mostCommonSize) {
                System.out.println("- " + entry.getKey() +
                        " (" + entry.getValue() + " раз)");
            }
        }
    }
}
