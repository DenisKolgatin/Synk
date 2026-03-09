import java.util.Scanner;

public class Cardinal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите первое число (a): ");
        int a = scanner.nextInt();

        System.out.print("Введите второе число (b): ");
        int b = scanner.nextInt();

        // Сравнение
        if (a > b) {
            System.out.println("a > b");
        } else if (a < b) {
            System.out.println("a < b");
        } else {
            System.out.println("a = b");
        }

        // Арифметические операции
        System.out.println("Сложение:       " + (a + b));
        System.out.println("Вычитание a-b:  " + (a - b));
        System.out.println("Умножение:      " + (a * b));

        // Деление с проверкой на ноль
        if (b != 0) {
            System.out.printf("Деление:        %.3f%n", (double) a / b);
            System.out.println("Целочисленное:  " + (a / b));
            System.out.println("Остаток:        " + (a % b));
        } else {
            System.out.println("Деление:        НЕЛЬЗЯ (деление на 0)");
            System.out.println("Целочисленное:  НЕЛЬЗЯ");
            System.out.println("Остаток:        НЕЛЬЗЯ");
        }

        scanner.close();
    }
}