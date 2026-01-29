import java.util.*;
import java.time.Duration;
import java.time.Instant;



public class Main {

    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        String[] texts = new String[25];
        List<Thread> threads = new ArrayList<>(); //Пустой список, где будут храниться ссылка на объекты потоков

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time
        for (String text : texts) {
            Thread thread = new Thread(() -> { //Создаем объект нового потока, и так для всей texts. Реализация интерфейса Runable
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
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
            });
            threads.add(thread); //добавляет новый поток в копилку
            thread.start(); //приказывает операционной системе: «Выдели под этот объект реальный системный поток и начни выполнять код внутри Runnable»
        }
        //Главный поток ждет, пока выполнятся все потоки
        for (Thread thread : threads) {
            try {
                thread.join(); // main встает на паузу, пока поток не умрет естественной смертью
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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