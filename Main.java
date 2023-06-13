import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner sc = new Scanner(System.in);
        int choice = 1;

        while (choice != 3) {
            System.out.println("1. Create Vending Machine");
            System.out.println("2. Test Vending Machine");
            System.out.println("3. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // catch \n

            if (choice == 1) {

            }
            else if (choice == 2) {
                test();
            }
            else if (choice == 3) {
                continue;
            }
            else {
                // error message
            }
        }

        sc.close(); // close scanner
    }

    private static void test() {

    }

    private static void testVending() {

    }

    private static void testMaintenance() {

    }

    private static void doAction() {
    }
}
