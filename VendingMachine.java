/**
 * VendingMachine manages the slots/items, and the money in side it
 */
import java.util.ArrayList;

public class VendingMachine {
    private String vmName;
    private ArrayList<Slot> slots = new ArrayList<>();
    private Slot selectedSlot;
    private Money bankTotal;
    private int currentSlotNo;
    
    static final int minSlots = 8, minStock = 10;

    /**
     * Constructs a VendingMachine object with the specified name.
     *
     * @param vmName the name of the vending machine
     */
    public VendingMachine(String vmName) {
        this.vmName = vmName;
    }

    /**
     * Returns the total amount of money in the vending machine's bank
     * 
     * @return the total amount of money in the bank
     */
    public Money getBankTotal() {
        return bankTotal;
    }

    /**
     * Returns the name of the vending machine.
     * 
     * @return the name of the vending machine
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * Returns the list of slots/items in the vending machine.
     * 
     * @return the list of slots/items
     */
    public ArrayList<Slot> getSlots() {
        return slots;
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
     * Returns the selected slot's slot number
     * 
     * @return the selected slot's slot number
     */
    public int getCurrentSlotNo() {
        return currentSlotNo;
    }
    /**
     *  Sets the list of slots/items in the vending machine.
     * 
     * @param slots the list of slots/items
     */
    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }

    /**
     * Sets the total amount of money in the vending machine's bank.
     * 
     * @param bankTotal the total amount of money in the bank
     */
    public void setBankTotal(Money bankTotal) {
        this.bankTotal = bankTotal;
    }

    /**
     * Sets the currently selected slot/item.
     * 
     * @return selectedSlot the selected slot/item
     */
    public void setSelectedSlot(Slot selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    /**
     * Adds a new slot/item to the vending machine with the specified details.
     *
     * @param name  the name of the item
     * @param stock the stock/quantity of the item
     * @param price the price of the item
     * @param kcal  the calorie content of the item
     */
    public void addSlot(String name, int stock, double price, double kcal) {
        slots.add(new Slot(name, stock, price, kcal));
    }

    /**
     * Changes the name of the item in the specified slot.
     *
     * @param name   the name to be changed to
     * @param slotNo  the slot number
     * @return true if the slot is valid and the name is successfully changed, false otherwise
     */
    public boolean changeName(String name, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setName(name);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Adds to the the stock/quantity of the item in the specified slot.
     *
     * @param stock   the stock to be added
     * @param slotNo  the slot number
     * @return true if the slot is valid and the stock is successfully increased, false otherwise
     */
    public boolean changeStock(int stock, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setStock(slot.getStock() + stock);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Changes the price of the item in the specified slot.
     *
     * @param price   the price to be changed to
     * @param slotNo  the slot number
     * @return true if the slot is valid and the s--price is successfully changed, false otherwise
     */
    public boolean changePrice(double price, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setPrice(price);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Changes the kcal of the item in the specified slot.
     *
     * @param kcal   the kcal to be changed to
     * @param slotNo  the slot number
     * @return true if the slot is valid and the kcal is successfully changed, false otherwise
     */
    public boolean changeKcal(double kcal, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setKcal(kcal);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the slot/item with the specified slot number.
     *
     * @param slotNo the slot number
     * @return the slot/item if the slot is valid, or null otherwise
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
     * Checks if all slots/items in the vending machine are empty (out of stock).
     *
     * @param slots the list of slots/items
     * @return true if all slots are empty, false otherwise
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
     * Checks if the specified slot number is valid.
     *
     * @param slotNo the slot number
     * @return true if the slot number is valid, false otherwise
     */
    private boolean isValidSlot(int slotNo) {
        return slotNo >= 1 && slotNo <= slots.size();
    }

    /**
     * Replenishes the vending machine's bank with money of the specified denomination and stock.
     *
     * @param bank        the Money object representing the bank
     * @param denomination the denomination of the money to be replenished
     * @param stock       the number of units of money to be replenished
     */
    public void replenishMoney(Money bank, int denomination, int stock) {
        for(int i = 0; i < stock; i++) {
            bank.insertMoney(denomination);
        }
    }
}
