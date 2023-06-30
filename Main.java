import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
    private static VendingMachine vm;
    private static InventoryManager im;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Money.setDenominations(new ArrayList<Double>());
        boolean loop = true;
        String choice;
        String[] options = { "Create Vending Machine", "Vending Machine Menu" };
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
                    if (vm != null || vm.getSlots().size() < 8)
                        test();
                    else
                        System.out.println("Vending Machine not created yet.");
                    break;
                case "3":
                    loop = false;
                    break;
            }
        } while (loop);
        sc.close();
    }

    private static VendingMachine create() {
        VendingMachine newVm = new VendingMachine("Work In Progress");
        boolean loop = true;
        String choice;
        boolean success;
        String[] options = { "Regular Vending Machine", "Special Vending Machine", "Load Vending Machine (Regular)",
                "Load Vending Machine (Special)" };
        do {
            displayOptions(options);
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 5))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1":
                    success = createRegular(newVm);

                    if (success) {
                        vm = newVm;
                        im = new InventoryManager(vm);
                    } 

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
        } while (loop);

        return newVm;
    }

    private static void test() {
        boolean loop = true;
        String choice;
        String[] options = { "Vending Features", "Maintenance" };
        do {
            displayOptions(options);
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 3))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1":
                    if (!vm.checkEmpty(vm.getSlots()))
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
        } while (loop);

    }

    private static boolean initVendingMachine(VendingMachine newVm) {
        String choice;
        boolean invalid = true;
        String[] options1 = { "Enter name" };

        do {
            displayOptions(options1);

            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 2))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
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
        String[] options1 = { "Set Denominations" };

        if (initVendingMachine(newVm) == false)
            return false;

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
        switch (choice) {
            case "1":
                do {
                    System.out.print("Please write each denomination with spaces in between (ex. 1 5 10 20): ");

                    ArrayList<Double> denominations = new ArrayList<>();
                    String temp = sc.nextLine();
                    try {
                        for (String i : temp.split(" ")) {
                            denominations.add(Double.parseDouble(i));
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid denominations, Try again!");
                        continue;
                    }

                    denominations = new ArrayList<>(new HashSet<>(denominations));

                    Money.setDenominations(denominations);
                    loop = false;
                } while (loop);
                break;
            case "2":
                return false;
        }

        loop = true;
        ArrayList<Slot> newSlots = new ArrayList<>();
        String[] options2 = { "Add Item 1", "Set Starting Money", "Finish" };
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
                        try {
                            String name;
                            System.out.println("Item name: ");
                            name = sc.nextLine();

                            int stock;
                            System.out.println("Item stock: ");
                            stock = sc.nextInt();
                            sc.nextLine();

                            if(stock < 10) throw new IllegalArgumentException("You need at least 10 stock to add.\n");

                            double price;
                            System.out.println("Item price: ");
                            price = sc.nextDouble();
                            sc.nextLine();

                            double kcal;
                            System.out.println("Item kcal: ");
                            kcal = sc.nextDouble();
                            sc.nextLine();

                            if(stock < 0 || price < 0 || kcal < 0) throw new IllegalArgumentException("Negative values are invalid.\n");

                            vm.addSlot(name, stock, price, kcal);
                            
                            invalid = false;
                            slotNo++;

                        } catch (InputMismatchException e) {
                            errorMessage();
                            sc.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } 
                    } while (invalid);

                    break;
                case "2":
                    invalid = true;
                    do {
                        Money tempCassette = new Money();
                        System.out.println(
                                "Enter in order of denominations: " + Money.getAcceptedDenominations().toString());
                        System.out.println("Ex. 1 2 3, would mean 1x First Denomination, 2x Second Denomation, etc.");

                        String input = sc.nextLine();
                        String parsedInput[] = input.split(" ");

                        int inputLen = parsedInput.length;

                        if (inputLen > Money.getAcceptedDenominations().size()) {
                            errorMessage();
                            continue;
                        }

                        try {
                            for (int i = 0; i < inputLen; i++) {
                                tempCassette.insertMoney(
                                        Money.getAcceptedDenominations().get(i),
                                        Integer.parseInt(parsedInput[i]));
                            }
                            newVm.setBankTotal(tempCassette);
                            System.out.println("You added: P" + Money.getIntTotal(tempCassette));

                            invalid = false;
                        } catch (InputMismatchException e) {
                            errorMessage();
                            invalid = true;
                        }

                    } while (invalid);

                    break;
                case "3":
                    if (newSlots.size() >= 8) {
                        newVm.setSlots(newSlots);
                        loop = false;
                        displayItems(newVm);
                    } else
                        System.out.println("Not enough slots in Vending Machine (min = 8), please add.");
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
        String[] options = { "Pick Item", "Purchase Item" };
        int slotNo = 0, itemAmt; // itemAmt is for Special VM
        double totalPayment, payment; 
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
                        try {
                            slotNo = sc.nextInt();
                        } catch (Exception e) {
                            errorMessage();
                            sc.nextLine();
                            continue;
                        }

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
                    for (Slot slot : vm.getSlots())
                        if (slot.getName().equals(vm.getSlot(slotNo).getName()) && slot.isAvailable()) {
                            // FOR SPECIAL:
                            // vm.addSlot(slotNo, itemAmt);
                            // displaySelected();
                            vm.setSelectedSlot(vm.getSlot(slotNo));
                            available = true;
                            break;
                        } else
                            available = false;
                    if (!available)
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
                        System.out.printf("Payment inserted: %.2f\n", totalPayment);
                        System.out.print("Insert money to pay: ");

                        try {
                            payment = sc.nextDouble();
                            sc.nextLine();
                            if (!userMoney.insertMoney(payment))
                                errorMessage();
                            else
                                totalPayment += payment;
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        }
                    } while (vm.getSelectedSlot().getPrice() > totalPayment);
                    System.out.println("Change: " + Money
                            .calculateTransaction(vm.getBankTotal(), userMoney, vm.getSelectedSlot().getPrice())
                            .getMoney());

                    im.setTotalProfit(im.getTotalProfit()+vm.getSelectedSlot().getPrice());

                    Slot currentSlot = vm.getSlots().get(vm.currentSlotNo);
                    currentSlot.setStock(currentSlot.getStock() - 1);
                    
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

        } while (loop);

    }

    private static void maintenance() {
        boolean loop = true, validInput;
        String choice;
        String[] options = { "View Items", "Add Item", "Restock Item", "Set/Change Price", "Collect Profit",
                "Collect All Money", "Replenish Money", "Get Transaction Summary" };
        int stock, slotNo;
        double price, kcal;
        String itemName;
        do {
            displayOptions(options, "Exit");
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 9))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1": // view items
                    displayItems(vm);
                    break;
                case "2": // add item
                    validInput = false;
                    do {
                        try {
                            System.out.print("Enter name of item to add: ");
                            itemName = sc.nextLine();

                            System.out.print("How many would you like to stock?:");
                            stock = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Enter a price to set:");
                            price = sc.nextDouble();
                            sc.nextLine();

                            System.out.print("Enter the calores(in kcal) of the item:");
                            kcal = sc.nextDouble();
                            sc.nextLine();

                            if(stock < 0 || price < 0 || kcal < 0) throw new IllegalArgumentException("Negative values are invalid.\n");

                            vm.addSlot(itemName, stock, price, kcal);
                            validInput = true;
                        } catch (InputMismatchException e) {
                            errorMessage();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } 
                    } while (!validInput);
                    break;
                case "3": // restocks items. Adds to current amount in stock, not replace
                    validInput = false;
                    do {
                        try {
                            System.out.print("Enter slot number of item to stock:");
                            slotNo = sc.nextInt();
                            sc.nextLine();
                            
                            System.out.print("How many would you like to stock?:");
                            stock = sc.nextInt();
                            sc.nextLine();

                            if(stock < 0) throw new IllegalArgumentException("Negative values are invalid.\n");

                            if (vm.stockSlot(stock, slotNo)) {
                                successMessage("stock");
                                validInput = true;
                            } else
                                System.out.println("Invalid slot number.");
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } 
                    } while (!validInput);
                    break;
                case "4": // set price
                    validInput = false;
                    do {
                        try {
                            System.out.print("Enter slot number of item to change price:");
                            slotNo = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Enter a price to set:");
                            price = sc.nextDouble();
                            sc.nextLine();

                            if(price < 0) throw new IllegalArgumentException("Negative values are invalid.\n");

                            if (vm.priceSlot(price, slotNo)) {
                                successMessage("price");
                                validInput = true;
                            } else
                                System.out.println("Invalid slot number.");
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } 
                    } while (!validInput);
                case "5": // collect payment
                    System.out.println("You took out: P" + im.getTotalProfit());
                    im.setTotalProfit(0);
                    break;
                case "6": // collect money
                    System.out.println("You took out: P" + Money.getIntTotal(vm.getBankTotal()));
                    vm.getBankTotal().clearMoney();
                    im.setTotalProfit(0);
                    break;
                case "7": // replenish money
                    boolean invalid = true;
                    validInput = false;
                    do {
                        Money tempCassette = new Money();

                        System.out.println("Enter in order of denominations: " + Money.getAcceptedDenominations().toString());
                        System.out.println("Ex. 1 2 3, would mean 1x First Denomination, 2x Second Denomation, etc.");

                        String input = sc.nextLine();
                        String parsedInput[] = input.split(" ");

                        int inputLen = parsedInput.length;

                        if (inputLen > Money.getAcceptedDenominations().size()) {
                            errorMessage();
                            continue;
                        }

                        try {
                            for (int i = 0; i < inputLen; i++) {
                                tempCassette.insertMoney(
                                        Money.getAcceptedDenominations().get(i),
                                        Integer.parseInt(parsedInput[i]));
                            }

                            Money.mergeMoney(vm.getBankTotal(), tempCassette);
                            System.out.println("You added: P" + Money.getIntTotal(tempCassette));

                            invalid = false;
                        } catch (InputMismatchException e) {
                            errorMessage();
                            invalid = true;
                        }

                    } while (invalid);
                    break;
                case "8":
                    System.out.println("\tYour transaction summary since last reset: ");
                    System.out.printf("\tYour total profit is: P%.2f\n\n", im.getTotalProfit());
                    System.out.printf("\tCurrent Balance: P%.2f\n\n", Money.getIntTotal(vm.getBankTotal()));
                    im.printInventoryLost();

                    String[] options3 = { "Reset trackers" };

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
                case "9":
                    loop = false;
                    break;
                default:
                    invalidMessage();
                    break;
            }
        } while (loop);

    }

    public static void displayItems(VendingMachine vm) {
        int i = 0;
        for (Slot slot : vm.getSlots()) {
            if (slot.isAvailable())
                System.out.printf("[%d] %s - %d in stock - %.2f pesos - %.3f calories\n", i + 1, slot.getName(),
                        slot.getStock(), slot.getPrice(), slot.getKcal());
            else
                System.out.printf("[%d] %s - NOT AVAILABLE\n", i + 1, slot.getName());
            i++;
        }
    }

    public static void displaySelected() {
        for (Slot slot : vm.getSelectedSlots()) {
            System.out.printf("%s - %d ordered - %.2f pesos - %.1f calories\n", slot.getName(), slot.getStock(),
                    slot.getPrice() * slot.getStock(), slot.getKcal() * slot.getStock());
        }
    }

    private static void displayOptions(String[] options) {
        int i = 1;
        System.out.println();
        for (String option : options) {
            System.out.printf("\t[%d] %s\n", i, option);
            i++;
        }
        System.out.printf("\t[%d] Cancel\n", i);
        System.out.print("\tPick: ");
    }

    private static void displayOptions(String[] options, String last) {
        int i = 1;
        System.out.println();
        for (String option : options) {
            System.out.printf("\t[%d] %s\n", i, option);
            i++;
        }
        System.out.printf("\t[%d] %s\n", i, last);
        System.out.print("\tPick: ");
    }

    private static void successMessage(String changed) {
        System.out.printf("Successfully changed %s.\n", changed);
    }

    private static void invalidMessage() {
        System.out.println("That is not an option. Please try again.");
    }

    private static void errorMessage() {
        System.out.println("Error, try again!\n");
    }

    private static void warningMessage() {
        System.out.println("Are you sure you want to exit? Your changes will be lost.");
    }

    // public static void displayPrep() {

    // }

    private static boolean IsInChoices(String input, String[] choices) {
        for (String i : choices)
            if (i.equals(input))
                return true;

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
