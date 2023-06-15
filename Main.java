import java.util.Scanner;

public class Main {
    private static VendingMachine vm;
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        int choice;
        do {
            System.out.println("[1] Create Vending Machine");
            System.out.println("[2] Vending Machine Menu");
            System.out.println("[3] Exit");
            System.out.print("Pick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    create(sc);
                    break;
                case 2:
                    if(vm != null)
                        test(sc);
                    else
                        System.out.println("Vending Machine not created yet.");
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }while(loop);
        sc.close();
    }

    private static void create(Scanner sc) {
        boolean loop = true;
        int choice;
        do {
            System.out.println("[1] Regular");
            System.out.println("[2] Special");
            System.out.println("[3] Exit");
            System.out.print("Pick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    createRegular(sc);
                    break;
                case 2:
                    //createSpecial();
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }while(loop);
    }

    private static void test(Scanner sc) {
        boolean loop = true;
        int choice;
        do {
            System.out.println("[1] Vending Features");
            System.out.println("[2] Maintenance");
            System.out.println("[3] Exit");
            System.out.print("Pick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    if(!vm.checkEmpty()) 
                        testVending(sc);
                    else {
                        System.out.println("There are no items at the moment.");
                        System.out.println("Please try again later.");
                    }
                    break;
                case 2:
                    testMaintenance(sc);
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }while(loop);
    }

    private static void createRegular(Scanner sc) {
        System.out.print("Enter name of Vending Machine: ");
        String vmName = sc.nextLine();
        vm = new VendingMachine(vmName);
    }

    private static void testVending(Scanner sc) {
        boolean loop = true;
        int choice, restock;
        do {
            System.out.println("[1] Pick Item");
            System.out.println("[2] Purchase Item");
            System.out.println("[3] Exit");
            System.out.print("Pick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: // add to current amount in stock, not replace
                    break;
                case 2:
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }while(loop);
    }

    private static void testMaintenance(Scanner sc) {
        boolean loop = true;
        int choice, stock, slotNo;
        float price;
        String itemName;
        do {
            System.out.println("[1] Add Item");
            System.out.println("[2] Restock Item");
            System.out.println("[3] Set/Change Price");
            System.out.println("[4] Collect Payment");
            System.out.println("[5] Replenish Money");
            System.out.println("[6] Exit");
            System.out.print("Pick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: // add to current amount in stock, not replace
                    System.out.print("Enter name of item to add: ");
                    itemName = sc.nextLine();
                    System.out.println();
                    vm.addSlot(itemName);
                    break;
                case 2:
                    System.out.println("Enter slot number of item to stock:");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("How many would you like to stock?:");
                    stock = sc.nextInt();
                    sc.nextLine();
                    vm.addSlot(stock, slotNo);
                    break;
                case 3:
                    System.out.println("Enter slot number of item to price?:");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter a price to set:");
                    price = sc.nextFloat();
                    sc.nextLine();
                    vm.addSlot(price, slotNo);
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    return;
                default:
                    break;
            }
        }while(loop);
    }

    private static void vendingActions() {

    }
}
