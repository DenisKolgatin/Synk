public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runnable logic = () -> {
            while (true) {
                System.out.println("Hello World thread");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    return;
                }


            }
        };

        Thread thread = new Thread(logic);
        thread.start();

        while(true) {
            System.out.println("Hello World main");
            thread.sleep(300);
        }
    }
}
