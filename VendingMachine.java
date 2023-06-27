import java.util.ArrayList;

public class VendingMachine {
    private String vmName;
    private ArrayList<Slot> slots = new ArrayList<>();
    private ArrayList<Slot> selectedSlots = new ArrayList<>();
    private Slot selectedSlot;
    private Money bankTotal = new Money();
    
    final int minSlots = 8;

    public VendingMachine(String vmName) {
        this.vmName = vmName;
    }

    public Money getBankTotal() {
        return bankTotal;
    }

    public String getVmName() {
        return vmName;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public ArrayList<Slot> getSelectedSlots() {
        return selectedSlots;
    }

    public Slot getSelectedSlot() {
        return selectedSlot;
    }

    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }

    public void setSelectedSlots(ArrayList<Slot> selectedSlots) {
        this.selectedSlots = selectedSlots;
    }

    public void setSelectedSlot(Slot selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    // user selected slots
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

    public void clearSelected() {
        selectedSlots.clear();
    }

    // slots in machine
    public void addSlot(String name) {
        slots.add(new Slot(name, slots.size() + 1));
    }

    // adds to slot instead of setting
    public boolean stockSlot(int slotNo, int stock) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setStock(slot.getStock() + stock);
            return true;
        } else {
            return false;
        }
    }

    public boolean priceSlot(float price, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setPrice(price);
            return true;
        } else {
            return false;
        }
    }

    public Slot getSlot(int slotNo) {
        if (isValidSlot(slotNo)) {
            return slots.get(slotNo - 1);
        } 
        else {
            return null;
        }
    }

    public boolean checkEmpty(ArrayList<Slot> slots) {
        for (Slot slot : slots) {
            if (slot.getStock() > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidSlot(int slotNo) {
        return slotNo >= 1 && slotNo <= slots.size();
    }

    public float getTotal() {
        float sum = 0;
        for(Slot slot : selectedSlots) {
            sum += slot.getPrice() * slot.getStock();
        }
        return sum;
    }

    public void replenishMoney(Money bank, int denomination, int stock) {
        for(int i = 0; i < stock; i++) {
            bank.insertMoney(denomination);
        }
    }
}
