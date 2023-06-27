import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;


public class Main {
    private static VendingMachine vm;
    private static Money cassette;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // DECLARE OBJECTS HERE
        // FOR TESTING
        // FOR TESTING
        // FOR TESTING
        ArrayList<Integer> defaultDenom = new ArrayList<Integer>();
        defaultDenom.add(1);
        defaultDenom.add(5);
        defaultDenom.add(10);
        defaultDenom.add(20);
        defaultDenom.add(50);

        Money.setDenomenations(defaultDenom);

        cassette = new Money();

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
            
            if (!IsInChoices(choice, makeChoices(1, 3))) {
                invalidMessage();
                continue;
            }
      
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
            }
        } while(loop);
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

            if (!IsInChoices(choice, makeChoices(1, 3))) {
                invalidMessage();
                continue;
            }

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
      
            if (!IsInChoices(choice, makeChoices(1, 3))) {
                invalidMessage();
                continue;
            }

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
            }
        }while(loop);

        
    }

    private static boolean initVendingMachine() {
        String choice;

        System.out.println("\t[1] Enter Name");
        System.out.println("\t[2] Cancel");
        System.out.print("\tPick: ");

        choice = sc.nextLine();

        switch(choice) {
            case "1":
                System.out.print("\tName: ");
                String name = sc.nextLine();
                vm = new VendingMachine(name);
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

        do {
            System.out.println("\t[1] Set Denominations");
            System.out.println("\t[2] Cancel");
            System.out.print("\tPick: ");
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 3))) {
                invalidMessage();
                continue;
            }

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
            }
        } while (loop);

        loop = true;
        ArrayList<Slot> newSlots = new ArrayList<>();
        do {
            System.out.println("Current items: " + newSlots.toString());

            System.out.println("\t[1] Add Item " + (slotNo + 1));
            System.out.println("\t[2] Set Starting Money");
            System.out.println("\t[3] Finish");
            System.out.println("\t[4] Cancel");

            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 4))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1":
                    boolean invalid = true;
                    do {
                        Slot newSlot;

                        try {
                            String name;
                            System.out.println("Item name: ");
                            name = sc.nextLine();

                            newSlot = new Slot(name, slotNo);

                            System.out.println("Item price: ");
                            newSlot.setPrice(sc.nextFloat());

                            sc.nextLine();

                            System.out.println("Item stock: ");
                            newSlot.setStock(sc.nextInt());

                            sc.nextLine();

                            System.out.println("Item kcal: ");
                            newSlot.setPrice(sc.nextFloat());

                            sc.nextLine();
                        }
                        catch (Exception e) {System.out.println("Error, Try again!"); sc.nextLine(); continue;}

                        invalid = false;
                        
                        newSlots.add(newSlot);
                        slotNo++;
                    } while (invalid);

                    break;
                case "2":
                    cassette = null;
                    
                    do {
                        Money tempCassette = new Money();
                        System.out.println("Enter in order of denominations: " + Money.getAcceptedDenomenations().toString());
                        System.out.println("Ex. 5 4 5, would mean 5x First Denomenation, 4x Second Denomation, etc.");

                        String input = sc.nextLine();
                        String parsedInput[] = input.split(" ");

                        int inputLen = parsedInput.length;

                        if (inputLen > Money.getAcceptedDenomenations().size()) {
                            System.out.println("Error, try again!");
                            continue;
                        }
                        
                        try {
                            for (int i = 0; i < inputLen; i++) {
                                tempCassette.insertMoney(
                                    Money.getAcceptedDenomenations().get(i), 
                                    Integer.parseInt(parsedInput[i])
                                );
                            }
                            cassette = tempCassette;
                        }
                        catch (Exception e) {System.out.println("Error, try again!");}

                    } while (cassette == null);

                    break;
                case "3":
                    if (newSlots.size() >= 8) {vm.setSlots(newSlots); loop = false; displayItems();}
                    else System.out.println("Not enough slots in Vending Machine (min = 8), please add.");
                    break;
                case "4":
                    return false;
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
        int slotNo, itemAmt, totalPayment, payment;
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
                case "2":
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
                    } while(vm.getTotal() > totalPayment);
                    System.out.println("Change: " + Money.calculateTransaction(cassette, userMoney, (int)vm.getTotal()).getMoney());
                    vm.clearSelected();
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
        int stock, slotNo, denomRep, denomStock;
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

    private static boolean IsInChoices(String input, String[] choices) {
        for (String i : choices) 
            if (i.equals(input)) return true;

        return false;
    }

    private static String[] makeChoices(int start, int end) {
        String[] choices = new String[end - start + 1];

        for (int i = 0; i <= (end - start); i++) {
            choices[i] = Integer.toString(start + i);
        }

        return choices;
    }
}
