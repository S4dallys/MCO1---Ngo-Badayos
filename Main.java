import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static VendingMachine vm;
    private static Money cassette;

    static Scanner sc = new Scanner(System.in);

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
        cassette.insertMoney(10);
        cassette.insertMoney(20);
        cassette.insertMoney(50);
        cassette.insertMoney(100);

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
        sc.close();
    }

    private static void start() {
        boolean loop = true;
        String choice;
        String[] options = {"Create Vending Machine", "Vending Machine Menu"};
        do {
            displayOptions(options);
            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    create();
                    break;
                case "2":
                    if(vm != null)
                        test();
                    else
                        System.out.println("Vending Machine not created yet.");
                    break;
                case "3":
                    return;
                default:
                    invalidMessage();
                    break;
            }
        } while(loop);
    }

    private static VendingMachine create() {
        VendingMachine newVm = null;
        boolean loop = true;
        String choice;
        String[] options = {"Regular Vending Machine", "Special Vending Machine"};
        do {
            displayOptions(options);
            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    createRegular();
                    loop = false;
                    break;
                case "2":
                    createSpecial();
                    loop = false;
                    break;
                case "3":
                    loop = false;
                    break;
                default:
                    invalidMessage();
                    break;
            }
        }while(loop);

        return newVm;
    }

    private static void test() {
        boolean loop = true;
        String choice;
        String[] options = {"Vending Features", "Maintenance"};
        do {
            displayOptions(options);
            choice = sc.nextLine();
      
            switch (choice) {
                case "1":
                    if(!vm.checkEmpty(vm.getSlots())) 
                        vendingActions();
                    else {
                        System.out.println("There are no items at the moment.");
                        System.out.println("Please try again later.");
                    }
                    break;
                case "2":
                    maintenance();
                    break;
                case "3":
                    loop = false;
                    break;
                default:
                    invalidMessage();
                    break;
            }
        }while(loop);

        
    }

    private static boolean initVendingMachine() {
        String choice;
        String[] options1 = {"Enter name"};

        displayOptions(options1);

        choice = sc.nextLine();

        switch(choice) {
            case "1":
                System.out.print("\tName: ");
                vm = new VendingMachine(sc.nextLine());
                break;
            case "2":
                break;
        }
        return (choice == "2") ? false : true;
    }

    private static boolean createRegular() {
        boolean loop = true;
        int slotNo = 0;
        String choice;
        String[] options1 = {"Set Denominations"};
        
        if (initVendingMachine() == false) return false;

        do {
            displayOptions(options1);
            choice = sc.nextLine();

            switch(choice) {
                case "1":
                    System.out.print("Please write each denomination with spaces in between (ex. 1 5 10 20): ");
                    
                    ArrayList<Integer> denominations = new ArrayList<>();
                    String temp = sc.nextLine();

                    for (String i : temp.split(" ")) {
                        denominations.add(Integer.parseInt(i));
                    }

                    Money.setDenomenations(denominations);
                    loop = false;
                    break;
                case "2":
                    return false;
                default:
                    invalidMessage();
                    break;
            }
        } while (loop);

        loop = true;
        ArrayList<Slot> newSlots = new ArrayList<>();
        String[] options2 = {"Add Item " + (slotNo + 1), "Add Starting Money", "Finish"};
        do {
            System.out.println("\"Current items: \"" + newSlots.toString());
            displayOptions(options2);
            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Item name: ");
                    Slot newSlot = new Slot(sc.nextLine(), slotNo);

                    System.out.println("Item price: ");
                    newSlot.setPrice(sc.nextFloat());

                    System.out.println("Item stock: ");
                    newSlot.setPrice(sc.nextInt());

                    System.out.println("Item kcal: ");
                    newSlot.setPrice(sc.nextFloat());
                    
                    newSlots.add(newSlot);
                    slotNo++;
                    break;
                case "2":
                    System.out.println("Enter in order of denominations: " + Money.getAcceptedDenomenations().toString());
                    System.out.println("Ex. 5 5 5, would mean 5x First Denomenation, 5x Second Denomation, etc.");

                    cassette = new Money();

                    int k = 0;
                    for (String i : sc.nextLine().split(" ")) {
                        for (int j = 0; j < Integer.parseInt(i); j++)
                            cassette.insertMoney(Money.getAcceptedDenomenations().get(k));
                        k++;
                    }                      
                    break;
                case "3":
                    if (slotNo > 0) {
                        vm.setSlots(newSlots); 
                        loop = false;
                    }
                    else 
                        System.out.println("No slots in Vending Machine, please add.");
                    break;
                case "4":
                    return false;
                default:
                    break;
            }
        } while (loop);
        return true;
    }

    private static boolean createSpecial() {
        System.out.println("That option is not available at the moment.");
        return false;
    }

    private static void vendingActions() {
        Money userMoney;
        boolean loop = true, available = true;
        String choice;
        String[] options = {"Pick Item", "Purchase Item"};
        int slotNo, itemAmt, totalPayment, payment;
        do {
            displayOptions(options);
            choice = sc.nextLine();
         
            switch (choice) {
                case "1": 
                    displayItems();
                    System.out.print("Enter the slot number of the item you pick: ");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    // FOR SPECIAL:
                    // System.out.print("Enter the amount to be added to your meal: ");
                    // itemAmt = sc.nextInt();
                    // sc.nextLine();
                    for(Slot slot : vm.getSlots())
                        if(slot.getName().equals(vm.getSlot(slotNo).getName()) && slot.isAvailable()) {
                            // FOR SPECIAL:
                            // vm.addSlot(slotNo, itemAmt);
                            // displaySelected();
                            vm.setSelectedSlot(vm.getSlot(slotNo));
                            available = true;
                            break;
                        } 
                        else
                            available = false;
                    if(!available)
                        System.out.println("That item is not available. Try again.");
                    break;
                case "2":
                    userMoney = new Money();
                    payment = 0;
                    totalPayment = 0;
                    // FOR SPECIAL
                    // System.out.printf("Total amount to be paid: %.2f\n", vm.getTotal());
                    System.out.printf("Amount to be paid: %.2f\n", vm.getSelectedSlot().getPrice());
                    do {
                        System.out.printf("Payment inserted: %d\n", totalPayment);
                        System.out.print("Insert money to pay: ");
                        payment = sc.nextInt();
                        sc.nextLine();
                        if(!userMoney.insertMoney(payment))
                            invalidMessage();
                        else
                            totalPayment += payment;
                    } while(vm.getTotal() > totalPayment);
                    System.out.println("Change: " + Money.calculateTransaction(cassette, userMoney, (int)vm.getSelectedSlot().getPrice()).getMoney());
                    vm.setSelectedSlot(null);
                    // FOR SPECIAL
                    // vm.clearSelected();
                    loop = false;
                    break;
                case "3":
                    loop = false;
                    break;
                default:
                    invalidMessage();
                    break;
            }
        }while(loop);

        
    }

    private static void maintenance() {
        boolean loop = true;
        String choice;
        String[] options = {"View Items", "Add Item", "Restock Item", "Set/Change Price", "Collect Payment", "Replenish Money"};
        int stock, slotNo, denomRep, denomStock;
        float price;
        String itemName;
        do {
            displayOptions(options);
            choice = sc.nextLine();
            switch (choice) {
                case "1": // view items
                    displayItems();
                    break;
                case "2": // add item
                    System.out.print("Enter name of item to add: ");
                    itemName = sc.nextLine();
                    System.out.println();
                    vm.addSlot(itemName);
                    break;
                case "3": // restocks items. Adds to current amount in stock, not replace
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
                case "4": // set price
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
                case "5": // collect payment
                    System.out.println("You took out: P" + Money.getIntTotal(cassette));
                    cassette.clearMoney();
                    break;
                case "6": // replenish money
                    System.out.print("Enter a denomination to replenish: ");
                    denomRep = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter amount to replenish in selected denomination: ");
                    denomStock = sc.nextInt();
                    sc.nextLine();
                    vm.replenishMoney(cassette, denomRep, denomStock);
                    break;
                case "7":
                    loop = false;
                    break;
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

    private static void displayOptions(String[] options) {
        int i = 1;
        for(String option : options) {
            System.out.printf("\t[%d] %s\n", i, option);
            i++;
        }
        System.out.printf("\t[%d] Exit\n", i);
        System.out.print("\tPick: ");
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
