import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashSet;


public class Main {
    private static VendingMachine vm;
    private static InventoryManager im;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Money.setDenomenations(new ArrayList<Integer>());

        start();
        sc.close();
    }

    private static void start() {
        boolean loop = true;
        String choice;
        String[] options = {"Create Vending Machine", "Vending Machine Menu"};
        do {
            displayOptions(options, "Exit");
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
                    loop = false;
                    break;
            }
        } while(loop);
    }

    private static VendingMachine create() {
        VendingMachine newVm = new VendingMachine("Work In Progress");
        boolean loop = true;
        String choice;
        boolean success;
        String[] options = {"Regular Vending Machine", "Special Vending Machine", "Load Vending Machine (Regular)", "Load Vending Machine (Special)"};
        do {
            displayOptions(options);
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 3))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1":
                    success = createRegular(newVm);

                    if (success) {vm = newVm; im = new InventoryManager(vm);} // do something

                    loop = false;
                    break;
                case "2":
                    createSpecial(newVm);
                    loop = false;
                    break;
                case "3":
                    System.out.println("Sample Vending Machine Loaded!");
                    vm = Sample.getRegVm();
                    im = new InventoryManager(vm);
                    loop = false;
                    break;
                case "4":
                    System.out.println("That option is not available at the moment.");
                    loop = false;
                    break;
                case "5":
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

    private static boolean initVendingMachine(VendingMachine newVm) {
        String choice;
        boolean invalid = true;
        String[] options1 = {"Enter name"};

        do {
            displayOptions(options1);

            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 2))) {
                    invalidMessage();
                    continue;
            }

            switch(choice) {
                case "1":
                    System.out.print("\tName: ");
                    String name = sc.nextLine();
                    newVm = new VendingMachine(name);
                    return true;
                case "2":
                    break;
            }

            invalid = false;
        } while (invalid);

        return false;
    }

    private static boolean createRegular(VendingMachine newVm) {
        boolean loop = true;
        int slotNo = 0;
        String choice;
        String[] options1 = {"Set Denominations"};
        
        if (initVendingMachine(newVm) == false) return false;

        do {
            displayOptions(options1);
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 3))) {
                invalidMessage();
                continue;
            }

            loop = false;
        } while (loop);

            loop = true;
            switch(choice) {
                case "1":
                    do {
                        System.out.print("Please write each denomination with spaces in between (ex. 1 5 10 20): ");
                        
                        ArrayList<Integer> denominations = new ArrayList<>();
                        String temp = sc.nextLine();
                        try {
                            for (String i : temp.split(" ")) {
                                denominations.add(Integer.parseInt(i));
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid denomenations, Try again!"); 
                            continue;
                        }
                        
                        denominations = new ArrayList<>(new HashSet<>(denominations));
                        
                        Money.setDenomenations(denominations);
                        loop = false;
                    } while (loop);
                    break;
                case "2":
                    return false;
            }
        

        loop = true;
        ArrayList<Slot> newSlots = new ArrayList<>();
        String[] options2 = {"Add Item 1", "Set Starting Money", "Finish"};
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
                            newSlot.setPrice(sc.nextDouble());

                            sc.nextLine();

                            System.out.println("Item stock: ");
                            newSlot.setStock(sc.nextInt());

                            sc.nextLine();

                            System.out.println("Item kcal: ");
                            newSlot.setKcal(sc.nextDouble());

                            sc.nextLine();
                        }
                        catch (InputMismatchException e) {
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
                    invalid = true;   
                    do {
                        Money tempCassette = new Money();
                        System.out.println("Enter in order of denominations: " + Money.getAcceptedDenomenations().toString());
                        System.out.println("Ex. 1 2 3, would mean 1x First Denomenation, 2x Second Denomation, etc.");

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
                            newVm.setBankTotal(tempCassette);

                            invalid = false;
                        }
                        catch (InputMismatchException e) {
                            errorMessage();
                            invalid = true;
                        }


                    } while (invalid);
                    
                    break;
                case "3":
                    if (newSlots.size() >= 8) {newVm.setSlots(newSlots); loop = false; displayItems(newVm);}
                    else System.out.println("Not enough slots in Vending Machine (min = 8), please add.");
                    break;
                case "4":
                    return false;
            }
        } while (loop);
        return true;
    }

    private static boolean createSpecial(VendingMachine newVm) {
        System.out.println("That option is not available at the moment.");
        return false;
    }

    private static void vendingActions() {
        Money userMoney;
        boolean loop = true, available = true;
        String choice;
        String[] options = {"Pick Item", "Purchase Item"};
        int slotNo = 0, itemAmt, totalPayment, payment; //itemAmt is for Special VM
        do {
           
                displayOptions(options);
                choice = sc.nextLine();

                if (!IsInChoices(choice, makeChoices(1, 3))) {
                    invalidMessage();
                    continue;
                }
            
                switch (choice) {
                    case "1": 
                        displayItems(vm);
                        boolean invalid = true;
                        do {
                            System.out.print("Enter the slot number of the item you pick: ");
                            try {slotNo = sc.nextInt();} 
                            catch (Exception e) {errorMessage(); sc.nextLine(); continue;}

                            if (!IsInChoices(Integer.toString(slotNo), makeChoices(1, vm.getSlots().size()))) {
                                invalidMessage();
                                continue;
                            }

                            sc.nextLine();
                            invalid = false;
                        } while (invalid);


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
                                    errorMessage();
                                else
                                    totalPayment += payment;
                            } catch (InputMismatchException e) {
                                sc.nextLine();
                                errorMessage();
                            }
                        } while(vm.getSelectedSlot().getPrice() > totalPayment);
                        System.out.println("Change: " + Money.calculateTransaction(vm.getBankTotal(), userMoney, (int)vm.getSelectedSlot().getPrice()).getMoney());
                        Slot currentSlot = vm.getSlots().get(vm.currentSlot);
                        currentSlot.setStock(currentSlot.getStock()-1);
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
        boolean loop = true, validInput;
        String choice;
        String[] options = {"View Items", "Add Item", "Restock Item", "Set/Change Price", "Collect Payment", "Replenish Money", "Get Transaction Summary"};
        int stock, slotNo, denomRep, denomStock;
        double price;
        String itemName;
        do {
            displayOptions(options, "Exit");
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 8))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1": // view items
                    displayItems(vm);
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
                            sc.nextLine();
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
                            price = sc.nextDouble();
                            sc.nextLine();

                            if (vm.priceSlot(price, slotNo)) {
                                successMessage("price");
                                validInput = true;
                            }
                            else
                                System.out.println("Invalid slot number."); 
                        } catch(InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        }
                    } while(!validInput);
                case "5": // collect payment
                    System.out.println("You took out: P" + Money.getIntTotal(vm.getBankTotal()));
                    vm.getBankTotal().clearMoney();
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

                            vm.replenishMoney(vm.getBankTotal(), denomRep, denomStock);
                        } catch(InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        }
                    } while(!validInput);
                    break;
                case "7":
                    System.out.println("\tYour transaction summary since last reset: ");
                    System.out.printf("\tYour total profit is: P%d\n\n", im.getTotalProfit());
                    im.printInventoryLost();

                    String[] options3 = {"Reset trackers"};

                    displayOptions(options3, "Back");

                    choice = sc.nextLine();

                    if (!IsInChoices(choice, makeChoices(1, 2))) {
                        invalidMessage();
                        continue;
                    }

                    switch (choice) {
                        case "1":
                            System.out.println("\tTrackers reset.");
                            im.reconfigure();
                            break;
                        case "2":
                            break;
                    }
                    // do something
                case "8":
                    loop = false;
                    break;
                default:
                    invalidMessage();
                    break;
            }
        }while(loop);

        
    }
    
    public static void displayItems(VendingMachine vm) {
        int i = 0;
        for(Slot slot : vm.getSlots()) {
            if(slot.isAvailable())
                System.out.printf("[%d] %s - %d in stock - %.2f pesos - %.3f calories\n", i+1, slot.getName(), slot.getStock(), slot.getPrice(), slot.getKcal());
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
        System.out.printf("\t[%d] Cancel\n", i);
        System.out.print("\tPick: ");
    }

    private static void displayOptions(String[] options, String last) {
        int i = 1;
        for(String option : options) {
            System.out.printf("\t[%d] %s\n", i, option);
            i++;
        }
        System.out.printf("\t[%d] %s\n", i, last);
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
