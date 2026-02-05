import java.util.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        // Создаем пул потоков (количество потоков равно количеству ядер процессора)
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Список для хранения объектов Future (наши "квитанции" на получение результата)
        List<Future<Integer>> futures = new ArrayList<>();

        long startTs = System.currentTimeMillis(); // start time

        for (String text : texts) {
            // Используем Callable вместо Runnable, чтобы вернуть значение
            Callable<Integer> task = ()-> { //Создаем объект нового потока, и так для всей texts. Реализация интерфейса Runable
                //Код в лямбдке не выполняется прямо сейчас. Он упаковывается в объект потока как инструкция
                int maxSize = 0;
                for (int i = 0; i < text.length(); i++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                return maxSize; // Возвращаем результат для конкретной строки
            };
            // submit() отправляет задачу и сразу возвращает Future
            futures.add(threadPool.submit(task));
        }
        //Главный поток ждет, пока выполнятся все потоки
        int globalMaxSize = 0;
        for (Future<Integer> future : futures) {
            // get() блокирует main и ждет, пока поток закончит вычисления
            int result = future.get();
            if (result > globalMaxSize) {
                globalMaxSize = result;
            }
        }

        threadPool.shutdown();

        System.out.println("Все потоки завершили работу!");

        long endTs = System.currentTimeMillis();

        System.out.println("=== ВСЕ ПОТОКИ ЗАВЕРШИЛИ РАБОТУ ===");
        System.out.println("Общее время выполнения: " + (endTs - startTs) + " ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}