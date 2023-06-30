/**
 * SPECIAL COPY WITH THE METHODS N STUFF
 */
import java.util.ArrayList;

    // IN vendingActions() method case "1" after first do while loop
    // System.out.print("Enter the amount to be added to your meal: ");
    // itemAmt = sc.nextInt();
    // sc.nextLine();

    // IN vendingActions() method case "1" inside Slot for loop if statement
    // vm.addSlot(slotNo, itemAmt);
    // displaySelected();

    // IN vendingActions() method case "2" after totalPayment = 0
    // System.out.printf("Total amount to be paid: %.2f\n", vm.getTotal());
    
    // IN vendingActions() method case "2" after vm.setSelectedSlot(null);
    // vm.clearSelected();

    // USED IN SPECIAL VM MAIN CLASS
    // public static void displaySelected() {
    //     for (Slot slot : vm.getSelectedSlots()) {
    //         System.out.printf("%s - %d ordered - %.2f pesos - %.1f calories\n", slot.getName(), slot.getStock(),
    //                 slot.getPrice() * slot.getStock(), slot.getKcal() * slot.getStock());
    //     }
    // }

public class SpecialVendingMachine{
    private String vmName;
    private ArrayList<Slot> slots = new ArrayList<>();
    private ArrayList<Slot> selectedSlots = new ArrayList<>();
    private Slot selectedSlot;
    private Money bankTotal;
    int currentSlotNo;
    
    final int minSlots = 8;

    /**
     * @param vmName
     * 
     */
    public SpecialVendingMachine(String vmName) {
        this.vmName = vmName;
    }

    /**
     * 
     * @return
     */
    public Money getBankTotal() {
        return bankTotal;
    }

    /**
     * 
     * @return
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Slot> getSlots() {
        return slots;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Slot> getSelectedSlots() {
        return selectedSlots;
    }

    /**
     * Returns the slot of the selected item
     * 
     * @return the slot of the selected item
     */
    public Slot getSelectedSlot() {
        return selectedSlot;
    }

    /**
     * 
     * @param
     */
    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }

    /**
     * 
     * @param
     */
    public void setBankTotal(Money bankTotal) {
        this.bankTotal = bankTotal;
    }

    /**
     * 
     * @param
     * USED IN SPECIAL VM
     */
    public void setSelectedSlots(ArrayList<Slot> selectedSlots) {
        this.selectedSlots = selectedSlots;
    }

    /**
     * 
     * @return
     */
    public void setSelectedSlot(Slot selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    /**
     * 
     * @param slotNo
     * @param ordered
     */
    public void addSlot(int slotNo, int ordered) {
        Slot selectedSlot = null;
        Slot originalSlot = getSlot(slotNo);

        for (Slot slot : selectedSlots) {
            if (slot.getName().equals(originalSlot.getName())) {
                selectedSlot = slot;
            }
        }

        if (selectedSlot == null) {
            selectedSlot = new Slot(originalSlot);
            selectedSlots.add(selectedSlot);
            selectedSlots.get(selectedSlots.size()-1).setStock(ordered);
        } else {
            int currentStock = selectedSlot.getStock();
            selectedSlot.setStock(currentStock + ordered);
        }

        originalSlot.setStock(originalSlot.getStock() - ordered);
    }

    /**
     * Clears selected items 
     */
    public void clearSelected() {
        selectedSlots.clear();
    }

    /**
     * 
     * @param name
     */
    public void addSlot(String name, int stock, double price, double kcal) {
        slots.add(new Slot(name, stock, price, kcal));
    }

    /**
     * 
     * @param stock
     * @param slotNo
     * @return
     */
    public boolean stockSlot(int stock, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setStock(slot.getStock() + stock);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @param price
     * @param slotNo
     * @return
     */
    public boolean priceSlot(double price, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setPrice(price);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @param slotNo
     * @return
     */
    public Slot getSlot(int slotNo) {
        if (isValidSlot(slotNo)) {
            currentSlotNo = slotNo-1;
            return slots.get(slotNo - 1);
        } 
        else {
            return null;
        }
    }

    /**
     * 
     * @param slots
     * @return
     */
    public boolean checkEmpty(ArrayList<Slot> slots) {
        for (Slot slot : slots) {
            if (slot.getStock() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param slotNo
     * @return
     */
    private boolean isValidSlot(int slotNo) {
        return slotNo >= 1 && slotNo <= slots.size();
    }

    /**
     * 
     * @return
     */
    public double getTotal() {
        double sum = 0;
        for(Slot slot : selectedSlots) {
            sum += slot.getPrice() * slot.getStock();
        }
        return sum;
    }

    /**
     * 
     * @param bank
     * @param denomination
     * @param stock
     */
    public void replenishMoney(Money bank, int denomination, int stock) {
        for(int i = 0; i < stock; i++) {
            bank.insertMoney(denomination);
        }
    }
}
