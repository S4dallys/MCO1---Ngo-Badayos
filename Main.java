import java.util.ArrayList;
import java.util.InputMismatchException;
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
        String[] options = {"Create Vending Machine", "Vending Machine Menu"};
        do {
            displayOptions(options);
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
        String[] options = {"Regular Vending Machine", "Special Vending Machine"};
        do {
            displayOptions(options);
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
        String[] options = {"Vending Features", "Maintenance"};
        do {
            displayOptions(options);
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
        String[] options1 = {"Enter name"};

        displayOptions(options1);

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
        String[] options1 = {"Set Denominations"};
        
        if (initVendingMachine() == false) return false;

        do {
            displayOptions(options1);
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
        String[] options2 = {"Add Item 1", "Add Starting Money", "Finish"};
        do {
            options2[0] = "Add Item " + (slotNo + 1);
            System.out.println("\"Current items: \"" + newSlots.toString());
            displayOptions(options2);
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
                        catch (Exception e) {
                            errorMessage(); 
                            sc.nextLine(); 
                            continue;
                        }

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
                            errorMessage(); 
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
                        catch (Exception e) {
                            errorMessage();
                        }

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
        String[] options = {"Pick Item", "Purchase Item"};
        int slotNo, itemAmt, totalPayment, payment;
        do {
            try {
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

                            try {
                                payment = sc.nextInt();
                                sc.nextLine();
                                if(!userMoney.insertMoney(payment))
                                    invalidMessage();
                                else
                                    totalPayment += payment;
                            } catch (InputMismatchException e) {
                                invalidMessage();
                            }
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
            } catch (Exception e) {
                errorMessage();
            }
        }while(loop);

        
    }

    private static void maintenance() {
        boolean loop = true, validInput;
        String choice;
        String[] options = {"View Items", "Add Item", "Restock Item", "Set/Change Price", "Collect Payment", "Replenish Money"};
        int stock, slotNo, denomRep, denomStock;
        float price;
        String itemName;
        do {
            try {
                displayOptions(options);
                choice = sc.nextLine();
                switch (choice) {
                    case "1": // view items
                        displayItems();
                        break;
                    case "2": // add item
                        validInput = false;
                        do{ 
                            try {
                                System.out.print("Enter name of item to add: ");
                                itemName = sc.nextLine();
                                vm.addSlot(itemName);
                                validInput = true;
                            } catch(InputMismatchException e) {
                                errorMessage();
                            }
                        } while(!validInput);
                        break;
                    case "3": // restocks items. Adds to current amount in stock, not replace
                        validInput = false;
                        do{ 
                            try {
                                System.out.println("Enter slot number of item to stock:");
                                slotNo = sc.nextInt();
                                sc.nextLine();
                        
                                System.out.println("How many would you like to stock?:");
                                stock = sc.nextInt();
                                sc.nextLine();

                                if(vm.stockSlot(stock, slotNo)) {
                                    successMessage("stock");
                                    validInput = true;
                                }
                                else
                                    System.out.println("Invalid slot number."); 
                            } catch(InputMismatchException e) {
                                errorMessage();
                            }
                        } while(!validInput);
                        break;
                    case "4": // set price
                        validInput = false;
                        do{ 
                            try {
                                System.out.println("Enter slot number of item to change price:");
                                slotNo = sc.nextInt();
                                sc.nextLine();

                                System.out.println("Enter a price to set:");
                                price = sc.nextFloat();
                                sc.nextLine();

                                if(vm.priceSlot(price, slotNo)) {
                                    successMessage("price");
                                    validInput = true;
                                }
                                else
                                    System.out.println("Invalid slot number."); 
                            } catch(InputMismatchException e) {
                                errorMessage();
                            }
                        } while(!validInput);
                    case "5": // collect payment
                        System.out.println("You took out: P" + Money.getIntTotal(cassette));
                        cassette.clearMoney();
                        break;
                    case "6": // replenish money
                        validInput = false;
                        do{ 
                            try {
                                System.out.print("Enter a denomination to replenish: ");
                                denomRep = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Enter amount to replenish in selected denomination: ");
                                denomStock = sc.nextInt();
                                sc.nextLine();

                                vm.replenishMoney(cassette, denomRep, denomStock);
                            } catch(InputMismatchException e) {
                                errorMessage();
                            }
                        } while(!validInput);
                        break;
                    case "7":
                        loop = false;
                        break;
                    default:
                        invalidMessage();
                        break;
                }
            } catch (Exception e) {
                errorMessage();
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
    private static void errorMessage() {
        System.out.println("Error, try again!");
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
