import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


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
        do {
            System.out.println("\t[1] Create Vending Machine");
            System.out.println("\t[2] Vending Machine Menu");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
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
        }while(loop);
        
    }

    private static VendingMachine create() {
        VendingMachine newVm = null;
        boolean loop = true;
        String choice;
        do {
            System.out.println("\t[1] Regular Vending Machine");
            System.out.println("\t[2] Special Vending Machine");
            System.out.println("\t[3] Cancel");
            System.out.print("\tPick: ");
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
        do {
            System.out.println("\t[1] Vending Features");
            System.out.println("\t[2] Maintenance");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
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

        System.out.println("\t[1] Enter Name");
        System.out.println("\t[2] Cancel");
        System.out.println("\tPick: ");

        choice = sc.nextLine();

        switch(choice) {
            case "1":
                System.out.println("\tName: ");
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
        
        if (initVendingMachine() == false) return false;

        System.out.println("\t[1] Set Denomenations");
        System.out.println("\t[2] Cancel");
        System.out.println("\tPick: ");

        choice = sc.nextLine();
        sc.nextLine();
        while (loop) {
            switch(choice) {
                case "1":
                    System.out.println("Please write each denomenation with a space in between (ex. 1 5 10 20)");
                    
                    ArrayList<Integer> denomenations = new ArrayList<>();
                    String temp = sc.nextLine();

                    for (String i : temp.split(" ")) {
                        denomenations.add(Integer.parseInt(i));
                    }

                    Money.setDenomenations(denomenations);
                    loop = false;
                    break;
                case "2":
                    
                    return false;
                default:
                    break;
            }
        }

        loop = true;
        ArrayList<Slot> newSlots = new ArrayList<>();
        while (loop) {
            System.out.println("Current items: " + newSlots.toString());

            System.out.println("\t[1] Add Item " + (slotNo + 1));
            System.out.println("\t[2] Add Starting Money");
            System.out.println("\t[3] Finish");
            System.out.println("\t[4] Cancel");

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
                    System.out.println("Enter in order of denomenations: " + Money.getAcceptedDenomenations().toString());
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
                    if (slotNo > 0) {vm.setSlots(newSlots); loop = false;}
                    else System.out.println("No slots in Vending Machine, please add some.");
                    break;
                case "4":
                    return false;
                default:
                    break;
            }
        }

        
        return true;
    }

    private static boolean createSpecial() {
        System.out.println("That option is not available at the moment.");
        return false;
    }

    private static void vendingActions() {
        boolean loop = true;
        String choice;
        int slotNo, itemAmt;
        do {
            System.out.println("\t[1] Pick Item");
            System.out.println("\t[2] Purchase Item");
            System.out.println("\t[3] Exit");
            System.out.print("\tPick: ");
            choice = sc.nextLine();
         
            switch (choice) {
                case "1": 
                    displayItems();
                    System.out.print("Enter the slot number of the item you pick: ");
                    slotNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the amount to be added to your meal: ");
                    itemAmt = sc.nextInt();
                    sc.nextLine();
                    vm.addSlot(slotNo, itemAmt);
                    displaySelected();
                    break;
                case "2":
                    System.out.printf("Total amount to be paid: %.2f\n", vm.getTotal());
                    System.out.println("Insert money to pay: ");
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
        int stock, slotNo;
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
            choice = sc.nextLine();
            sc.nextLine();
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
                    break;
                case "6": // replenish money
                    System.out.println("Enter a denomination to replenish: ");
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

    private static void successMessage(String changed) {
        System.out.printf("Successfully changed %s.\n",changed);
    }
    private static void invalidMessage() {
        System.out.println("That is not an option. Please try again.");
    }

    // public static void displayPrep() {

    // }
}
