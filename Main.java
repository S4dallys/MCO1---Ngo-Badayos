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
                    if (vm != null && vm.getSlots().size() >= vm.minSlots)
                        features();
                    else
                        System.out.println("\n\tPlease create a Vending Machine first!.");
                    break;
                case "3":
                    loop = false;
                    break;
            }
        } while (loop);
        sc.close();
    }

    /**
     * Create Vending Machine menu
     */
    private static VendingMachine create() {
        VendingMachine newVm = new VendingMachine("Work In Progress");
        boolean loop = true;
        String choice;
        boolean success;
        String[] options = { "Regular Vending Machine", "Special Vending Machine", "Load Regular (Premade)",
                "Load Special (Premade)" };
        do {
            displayOptions(options);
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 5))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1":
                    ArrayList<Double> oldDenominations = Money.getAcceptedDenominations();
                    success = createRegular(newVm);

                    if (success) {
                        vm = newVm;
                        im = new InventoryManager(vm);
                    } else {
                        Money.setDenominations(oldDenominations);
                    }

                    loop = false;
                    break;
                case "2":
                    createSpecial(newVm);
                    loop = false;
                    break;
                case "3":
                    System.out.println("\n\tSample Vending Machine Loaded!");
                    vm = Sample.getRegVm();
                    im = new InventoryManager(vm);
                    loop = false;
                    break;
                case "4":
                    System.out.println("\n\tThat option is not available at the moment.");
                    loop = false;
                    break;
                case "5":
                    loop = false;
                    break;
            }
        } while (loop);

        return newVm;
    }
    
    /**
     * Vending Machine features menu
     */
    private static void features() {
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
                        System.out.println("\n\tThere are no items at the moment.");
                        System.out.println("\tPlease try again later.");
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

    /**
     * Initialize new Vending Machine prompts
     */
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
                    System.out.print("\n\tName: ");
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

    /**
     * Creates Regular Vending Machine 
     */
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
                    System.out.print("\n\tEnter each denomination with spaces in between\n\t(ex. 1 5 10 20): ");

                    ArrayList<Double> denominations = new ArrayList<>();
                    String temp = sc.nextLine();
                    try {
                        for (String i : temp.split(" ")) {
                            denominations.add(Double.parseDouble(i));
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("\n\tInvalid denominations, Try again!");
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
            displayCurrentItems(newSlots);
            displayOptions(options2);
            choice = sc.nextLine();

            if (!IsInChoices(choice, makeChoices(1, 4))) {
                invalidMessage();
                continue;
            }

            switch (choice) {
                case "1":
                    newSlots.add(makeSlot());
                    slotNo++;
                    break;
                case "2":
                    boolean invalid = true;
                    do {
                        Money tempCassette = new Money();
                        System.out.println(
                                "\n\tEnter in order of denominations: " + Money.getAcceptedDenominations().toString());
                        System.out.println("\tEx. 1 2 3, would mean 1x First Denomination, 2x Second Denomation, etc.");
                        System.out.println("\t>> ");

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
                            System.out.println("\n\t* You added: P" + Money.getDoubleTotal(tempCassette));

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
                        System.out.println("\n\tItems added! Vending Machine has been created.");
                    } else
                        System.out.println("\n\tVending Machine needs at least 8 slots.");
                    break;
                case "4":
                    return false;
            }
        } while (loop);
        return true;
    }

    /**
     * Creates Special Vending Machine (NOT AVAILABLE)
     */
    private static boolean createSpecial(VendingMachine newVm) {
        System.out.println("\n\t// That option is not available at the moment.");
        return false;
    }

    /**
     * Menu for vending feautures
     */
    private static void vendingActions() {
        Money userMoney;
        boolean loop = true, available = true;
        String choice;
        String[] options = { "Pick Item", "Purchase Item" };
        int slotNo = 0;
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
                        System.out.print("\n\tEnter slot number: ");
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

                    for (Slot slot : vm.getSlots())
                        if (slot.getName().equals(vm.getSlot(slotNo).getName()) && slot.isAvailable()) {
                            vm.setSelectedSlot(vm.getSlot(slotNo));
                            available = true;
                            break;
                        } else
                            available = false;
                    if (!available)
                        System.out.println("\n\t// That item is not available. Try again.");
                    break;
                case "2":
                    userMoney = new Money();
                    payment = 0;
                    totalPayment = 0;
                    System.out.printf("\n\tAmount to be paid: %.2f\n", vm.getSelectedSlot().getPrice());
                    do {
                        System.out.printf("\tPayment inserted: %.2f\n", totalPayment);
                        System.out.print("\tInsert money to pay: ");

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
                    System.out.println("\n\tChange: " + Money
                            .calculateTransaction(vm.getBankTotal(), userMoney, vm.getSelectedSlot().getPrice())
                            .getMoney());

                    im.setTotalProfit(im.getTotalProfit()+vm.getSelectedSlot().getPrice());

                    Slot currentSlot = vm.getSlots().get(vm.getCurrentSlotNo());
                    currentSlot.setStock(currentSlot.getStock() - 1);
                    
                    vm.setSelectedSlot(null);

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

    /**
     * Menu for  maintenance feautures
     */
    private static void maintenance() {
        boolean loop = true, validInput;
        String choice;
        String[] options = { "View Items", "Add Item", "Change Item Name", "Restock Item", "Set/Change Price", "Change calorie content", "Collect Profit",
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
                    vm.getSlots().add(makeSlot());
                    break;
                case "3": // changes Item name
                    validInput = false;
                    do {
                        System.out.print("\n\tEnter slot number of item to stock:");
                        slotNo = sc.nextInt();
                        sc.nextLine();
                        
                        System.out.print("\tEnter a new name for the item:");
                        itemName = sc.nextLine();

                        if (vm.changeName(itemName, slotNo)) {
                            successMessage("name");
                            validInput = true;
                        } else
                            System.out.println("\n\t// Invalid slot number.");
            
                    } while (!validInput);
                    break;
                case "4": // restocks items. adds to current amount in stock, not replace
                    validInput = false;
                    do {
                        try {
                            System.out.print("\n\tEnter slot number of item to stock:");
                            slotNo = sc.nextInt();
                            sc.nextLine();
                            
                            System.out.print("\tHow many would you like to stock?:");
                            stock = sc.nextInt();
                            sc.nextLine();

                            if(stock < 0) throw new IllegalArgumentException("\n\t// Negative values are invalid.\n");

                            if (vm.changeStock(stock, slotNo)) {
                                successMessage("stock");
                                validInput = true;
                            } else
                                System.out.println("\n\t// Invalid slot number.");
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } 
                    } while (!validInput);
                    break;
                case "5": // set price
                    validInput = false;
                    do {
                        try {
                            System.out.print("\n\tEnter slot number of item to change price:");
                            slotNo = sc.nextInt();
                            sc.nextLine();

                            System.out.print("\tEnter a price to set:");
                            price = sc.nextDouble();
                            sc.nextLine();

                            if(price < 0) throw new IllegalArgumentException("\n\t// Negative values are invalid.\n");

                            if (vm.changePrice(price, slotNo)) {
                                successMessage("price");
                                validInput = true;
                            } else
                                System.out.println("\n\t// Invalid slot number.");
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } 
                    } while (!validInput);
                case "6": // changes calorie content
                    validInput = false;
                    do {
                        try {
                            System.out.print("\n\tEnter slot number of item to change price:");
                            slotNo = sc.nextInt();
                            sc.nextLine();

                            System.out.print("\tEnter kcal to change to:");
                            kcal = sc.nextDouble();
                            sc.nextLine();

                            if(kcal < 0) throw new IllegalArgumentException("\n\t// Negative values are invalid.\n");

                            if (vm.changePrice(kcal, slotNo)) {
                                successMessage("kcal");
                                validInput = true;
                            } else
                                System.out.println("\n\t// Invalid slot number.");
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            errorMessage();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } 
                    } while (!validInput);
                    break;
                case "7": // collect payment
                    System.out.println("\n\t* You took out: P" + im.getTotalProfit());
                    im.setTotalProfit(0);
                    break;
                case "8":
                    System.out.println("\n\t* You took out: P" + Money.getDoubleTotal(vm.getBankTotal()));
                    vm.getBankTotal().clearMoney();
                    im.setTotalProfit(0);
                    break;
                case "9": // replenish money
                    boolean invalid = true;
                    validInput = false;
                    do {
                        Money tempCassette = new Money();

                        System.out.println("\n\tEnter in order of denominations: " + Money.getAcceptedDenominations().toString());
                        System.out.println("\tEx. 1 2 3, would mean 1x First Denomination, 2x Second Denomation, etc.");
                        System.out.println("\t>> ");

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
                            System.out.println("\n\t* You added: P" + Money.getDoubleTotal(tempCassette));

                            invalid = false;
                        } catch (InputMismatchException e) {
                            errorMessage();
                            invalid = true;
                        }

                    } while (invalid);
                    break;
                case "10":
                    System.out.println("\n\t* TRANSACTION SUMMARY (since last reset)");
                    System.out.printf("\t* Total profit: P%.2f\n", im.getTotalProfit());
                    System.out.printf("\t* Balance in machine: P%.2f\n\n", Money.getDoubleTotal(vm.getBankTotal()));
                    im.printInventoryLost();

                    String[] options3 = { "Reset trackers" };

                    invalid = true;

                    do {
                        displayOptions(options3, "Back");

                        choice = sc.nextLine();

                        if (!IsInChoices(choice, makeChoices(1, 2))) {
                            invalidMessage();
                            continue;
                        }
                        
                        invalid = false;
                    } while (invalid);

                    switch (choice) {
                        case "1":
                            System.out.println("\n\t* Trackers reset.");
                            im.reconfigure();
                            break;
                        case "2":
                            break;
                    }
                case "11":
                    loop = false;
                    break;
                default:
                    invalidMessage();
                    break;
            }
        } while (loop);

    }

    /**
     * Displays list of items in the Vending Machine
     */
    public static void displayItems(VendingMachine vm) {
        int size = 0, pointer = 1, j = 1;

        System.out.println();
     
        while (pointer <= vm.getSlots().size()) {
            size = Math.min(4, vm.getSlots().size() - j + 1);
            
            
            j = pointer;
            for (int i = 0; i < size; i++) {
                System.out.printf("\t%-30s", "[" + j + "] " + vm.getSlot(j).getName() + " - P" + vm.getSlot(j).getPrice()); j++;
            } 
            System.out.println("\n");
            j = pointer;
            for (int i = 0; i < size; i++) {
                System.out.printf("\t%6s left, %-17s", "* " + vm.getSlot(j).getStock(), vm.getSlot(j).getKcal() + " kcal"); j++;
            }
            
            pointer += 4;
            System.out.println("\n\n");
        }

        
    }

    /**
     * Displays list of items in the Vending Machine
     */
    public static void displayCurrentItems(ArrayList<Slot> newSlots) {
        System.out.print("\n\t~ Current Items: [ ");
        for (Slot slot : newSlots) 
            System.out.printf("%s ", slot.getName());
        System.out.print("] ~");
    }

    /**
     * Displays list of options for a user to pick in a menu
     */
    private static void displayOptions(String[] options) {
        int i = 1;
        System.out.println();
        for (String option : options) {
            System.out.printf("\t[%d] %s\n", i, option);
            i++;
        }
        System.out.printf("\t[%d] Cancel\n", i);
        System.out.print("\t>> Pick: ");
    }

    /**
     * Displays list of options for a user to pick in a menu with a different exit keyword
     */
    private static void displayOptions(String[] options, String last) {
        int i = 1;
        System.out.println();
        for (String option : options) {
            System.out.printf("\t[%d] %s\n", i, option);
            i++;
        }
        System.out.printf("\t[%d] %s\n", i, last);
        System.out.print("\t>> Pick: ");
    }

    /**
     * Displays if a attribute of an item has succesfully been changed.
     * 
     * @param changed name of the attribute changed
     */
    private static void successMessage(String changed) {
        System.out.printf("\n\t~ Successfully changed %s.\n", changed);
    }

    /**
     * Displays an invalid message if the user picks an outside option
     */
    private static void invalidMessage() {
        System.out.println("\n\tThat is not an option. Please try again.");
    }

    /**
     * Displays an invalid message if the user enters an incorrect value
     */
    private static void errorMessage() {
        System.out.println("\n\t// Error, try again!");
    }

    /**
     * Displays an invalid message if the user wishes to exit mid-creation of a Vending Machine
     */
    private static void warningMessage() {
        System.out.println("\n\t// Are you sure you want to exit? Your changes will be lost.");
    }

    /**
     * Checks if the input of the user is in the menu choices
     * 
     * @param input input of the user
     * @param choices list of choices to pick
     * @return true if the input is in choices, false otherwise
     */
    private static boolean IsInChoices(String input, String[] choices) {
        for (String i : choices)
            if (i.equals(input))
                return true;

        return false;
    }

    /**
     * Description
     * 
     * @param start
     * @param end
     * @return
     */
    private static String[] makeChoices(int start, int end) {
        String[] choices = new String[end - start + 1];

        for (int i = 0; i <= (end - start); i++) {
            choices[i] = Integer.toString(start + i);
        }

        return choices;
    }

    /**
     * 
     * @return
     */
    private static Slot makeSlot() {
        boolean invalid = true;
        do {
            try {
                String name;
                System.out.print("\n\tItem name: ");
                name = sc.nextLine();

                int stock;
                System.out.print("\n\tItem stock: ");
                stock = sc.nextInt();
                sc.nextLine();

                if(stock < vm.minStock) throw new IllegalArgumentException("\n\tYou need at least 10 stock to add.");

                double price;
                System.out.print("\n\tItem price: ");
                price = sc.nextDouble();
                sc.nextLine();

                double kcal;
                System.out.print("\n\tItem kcal: ");
                kcal = sc.nextDouble();
                sc.nextLine();

                if(stock < 0 || price < 0 || kcal < 0) throw new IllegalArgumentException("\n\t// Negative values are invalid.");
                
                invalid = false;
                return new Slot(name, stock, price, kcal);

            } catch (InputMismatchException e) {
                errorMessage();
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } 
        } while (invalid);

        return null;
    }
}


