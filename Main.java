import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static VendingMachine vm;
    private static Money cassette;
    public static void main(String[] args) {
        // DECLARE OBJECTS HERE
        // FOR TESTING
        // FOR TESTING
        // FOR TESTING
        ArrayList<Integer> d = new ArrayList<>();
        d.add(1); d.add(10); d.add(20); d.add(50); d.add(100);

        Money.setDenomenations(d);

        cassette = new Money();
        cassette.insertMoney(1);

        vm = new VendingMachine("Curry");
        ArrayList<Slot> slots = new ArrayList<>();
        Slot slot1 = new Slot("Carrots", 0, 15, 50, 1);
        Slot slot2 = new Slot("Indian Curry", 10, 20, 50, 2);
        Slot slot3 = new Slot("Japanese Curry", 10, 20, 50, 3);
        Slot slot4 = new Slot("Thai Curry", 10, 20, 50, 4);
        Slot slot5 = new Slot("Chilies", 10, 10, 50, 5);
        Slot slot6 = new Slot("Tonkatsu", 10, 50, 50, 6);
        Slot slot7 = new Slot("Eggplant", 10, 15, 50, 7);
        Slot slot8 = new Slot("Scrambled Eggs", 10, 20, 50, 8);
        Slot slot9 = new Slot("Test", 10, 20, 50, 9);
        slots.add(slot1);
        slots.add(slot2);
        slots.add(slot3);
        slots.add(slot4);
        slots.add(slot5);
        slots.add(slot6);
        slots.add(slot7);
        slots.add(slot8);
        slots.add(slot9);
        vm.setSlots(slots);

        InventoryManager im = new InventoryManager(vm);

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
                    invalidMessage();
                    break;
            }
        } while(loop);
        sc.close();
    }

    private static void create(Scanner sc) {
        boolean loop = true;
        int choice;
        do {
            System.out.println("\t[1] Regular Vending Machine");
            System.out.println("\t[2] Special Vending Machine");
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
                    createSpecial(sc);
                    loop = false;
                    break;
                case 3:
                    return;
                default:
                    invalidMessage();
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
                    if(!vm.checkEmpty(vm.getSlots())) 
                        vendingActions(sc);
                    else {
                        System.out.println("There are no items at the moment.");
                        System.out.println("Please try again later.");
                    }
                    break;
                case 2:
                    maintenance(sc);
                    break;
                case 3:
                    return;
                default:
                    invalidMessage();
                    break;
            }
        }while(loop);
    }

    private static void createRegular(Scanner sc) {
        System.out.print("Enter name of Vending Machine: ");
        String vmName = sc.nextLine();
        vm = new VendingMachine(vmName);
    }

    private static void createSpecial(Scanner sc) {
        System.out.println("That option is not available at the moment.");
    }

    private static void vendingActions(Scanner sc) {
        Money userMoney;
        boolean loop = true, available = true;
        int choice, slotNo, itemAmt, payment, totalPayment;
        do {
            System.out.println("\t[1] Pick Item");
            System.out.println("\t[2] Purchase Item");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: 
                    displayItems();
                    System.out.print("Enter the slot number of the item you pick: ");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the amount to be added to your meal: ");
                    itemAmt = sc.nextInt();
                    sc.nextLine();
                    for(Slot slot : vm.getSlots())
                        if(slot.getName().equals(vm.getSlot(slotNo).getName()) && slot.isAvailable()) {
                            vm.addSlot(slotNo, itemAmt);
                            displaySelected();
                            available = true;
                            break;
                        } 
                        else
                            available = false;
                    if(!available)
                        System.out.println("That item is not available. Try again.");
                    break;
                case 2:
                    userMoney = new Money();
                    payment = 0;
                    totalPayment = 0;
                    System.out.printf("Total amount to be paid: %.2f\n", vm.getTotal());
                    do {
                        System.out.printf("Payment inserted: %d\n", totalPayment);
                        System.out.print("Insert money to pay: ");
                        payment = sc.nextInt();
                        sc.nextLine();
                        if(!userMoney.insertMoney(payment))
                            invalidMessage();
                        else
                            totalPayment += payment;
                    } while(vm.getTotal() >= totalPayment);
                    System.out.println("Change: " + Money.calculateTransaction(cassette, userMoney, (int)vm.getTotal()).getMoney());
                    vm.clearSelected();
                    break;
                case 3:
                    return;
                default:
                    invalidMessage();
                    break;
            }
        }while(loop);
    }

    private static void maintenance(Scanner sc) {
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
                case 1: // view items
                    displayItems();
                    break;
                case 2: // add item
                    System.out.print("Enter name of item to add: ");
                    itemName = sc.nextLine();
                    System.out.println();
                    vm.addSlot(itemName);
                    break;
                case 3: // restocks items. Adds to current amount in stock, not replace
                    System.out.println("Enter slot number of item to stock:");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("How many would you like to stock?:");
                    stock = sc.nextInt();
                    sc.nextLine();
                    if(vm.stockSlot(stock, slotNo))
                        //get out of loop
                        successMessage("stock");
                    else
                        System.out.println("Invalid slot number."); 
                    break;
                case 4: // set price
                    System.out.println("Enter slot number of item to change price:");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter a price to set:");
                    price = sc.nextFloat();
                    sc.nextLine();
                    if(vm.priceSlot(price, slotNo))
                        //get out of loop
                        successMessage("price");
                    else
                        System.out.println("Invalid slot number."); 
                case 5: // collect payment
                    break;
                case 6: // replenish money
                    System.out.println("Enter a denomination to replenish: ");
                    break;
                case 7:
                    return;
                default:
                    invalidMessage();
                    break;
            }
        }while(loop);
    }
    
    public static void displayItems() {
        int i = 0;
        for(Slot slot : vm.getSlots()) {
            if(slot.isAvailable())
                System.out.printf("[%d] %s - %d in stock - %.2f pesos - %.1f calories\n", i+1, slot.getName(), slot.getStock(), slot.getPrice(), slot.getKcal());
            else
                System.out.printf("[%d] %s - NOT AVAILABLE\n", i+1, slot.getName());
            i++;
        }
    }

    public static void displaySelected() {
        for (Slot slot : vm.getSelectedSlots()) {
            System.out.printf("%s - %d ordered - %.2f pesos - %.1f calories\n", slot.getName(), slot.getStock(), slot.getPrice() * slot.getStock(), slot.getKcal() * slot.getStock());
        }
    }

    private static void successMessage(String changed) {
        System.out.printf("Successfully changed %s.\n",changed);
    }
    private static void invalidMessage() {
        System.out.println("That is not an option. Please try again.");
    }
    private static void warningMessage() {
        System.out.println("Are you sure you want to exit? Your changes will be lost.");
    }

    // public static void displayPrep() {

    // }
}
