import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static VendingMachine vm;
    public static void main(String[] args) {
        // DECLARE OBJECTS HERE
        // FOR TESTING
        // FOR TESTING
        // FOR TESTING
        vm = new VendingMachine("Curry");
        ArrayList<Slot> slots = new ArrayList<>();
        Slot slot1 = new Slot("Carrots", 10, 15, 1);
        Slot slot2 = new Slot("Indian Curry", 10, 20, 1);
        Slot slot3 = new Slot("Japanese Curry", 10, 20, 1);
        Slot slot4 = new Slot("Thai Curry", 10, 20, 1);
        Slot slot5 = new Slot("Chilies", 10, 10, 1);
        Slot slot6 = new Slot("Tonkatsu", 10, 50, 1);
        Slot slot7 = new Slot("Eggplant", 10, 15, 1);
        Slot slot8 = new Slot("Scrambled Eggs", 10, 20, 1);
        slots.add(slot1);
        slots.add(slot2);
        slots.add(slot3);
        slots.add(slot4);
        slots.add(slot5);
        slots.add(slot6);
        slots.add(slot7);
        slots.add(slot8);
        vm.setSlots(slots);
        // FOR TESTING
        // FOR TESTING
        // FOR TESTING
        start();
    }

    private static void start() {
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        int choice;
        do {
            System.out.println("\t[1] Create Vending Machine");
            System.out.println("\t[2] Vending Machine Menu");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
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
            System.out.println("\t[1] Regular");
            System.out.println("\t[2] Special");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    createRegular(sc);
                    loop = false;
                    break;
                case 2:
                    //createSpecial();
                    loop = false;
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
            System.out.println("\t[1] Vending Features");
            System.out.println("\t[2] Maintenance");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
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
            System.out.println("\t[1] Pick Item");
            System.out.println("\t[2] Purchase Item");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: 
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
            System.out.println("\t[1] View Items");
            System.out.println("\t[2] Add Item");
            System.out.println("\t[3] Restock Item");
            System.out.println("\t[4] Set/Change Price");
            System.out.println("\t[5] Collect Payment");
            System.out.println("\t[6] Replenish Money");
            System.out.println("\t[7] Exit");
            System.out.print("\tPick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    vm.viewItems();
                    break;
                case 2: // adds to current amount in stock, not replace
                    System.out.print("Enter name of item to add: ");
                    itemName = sc.nextLine();
                    System.out.println();
                    vm.addSlot(itemName);
                    break;
                case 3:
                    System.out.println("Enter slot number of item to stock:");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("How many would you like to stock?:");
                    stock = sc.nextInt();
                    sc.nextLine();
                    vm.stockSlot(stock, slotNo);
                    break;
                case 4:
                    System.out.println("Enter slot number of item to price?:");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter a price to set:");
                    price = sc.nextFloat();
                    sc.nextLine();
                    vm.priceSlot(price, slotNo);
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    break;
            }
        }while(loop);
    }

    private static void vendingActions() {

    }
}
