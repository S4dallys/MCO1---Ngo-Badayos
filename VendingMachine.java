import java.util.ArrayList;

public class VendingMachine {
    private String vmName;
    private int selectedCount = 0;
    private ArrayList<Slot> slots = new ArrayList<>();
    private ArrayList<Slot> selectedSlots = new ArrayList<>();
    private Money bankTotal = new Money();
    
    final int MINSLOTS = 8;

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

    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }

    public void setSelectedSlots(ArrayList<Slot> selectedSlots) {
        this.selectedSlots = selectedSlots;
    }

    // user selected slots
    public void addSlot(int slotNo, int ordered) {
        selectedSlots.add(getSlot(slotNo));
        selectedSlots.get(selectedCount).setStock(ordered);
        selectedCount++;
    }
    public void viewSelected(int ordered) {
        for (Slot slot : selectedSlots) {
            System.out.printf("%s - %d ordered - %.2f pesos - %.1f calories\n", slot.getName(), slot.getStock(), slot.getPrice() * ordered, slot.getKcal() * ordered);
        }
    }

    // slots in machine
    public void addSlot(String name) {
        slots.add(new Slot(name, slots.size() + 1));
    }

    // adds to slot instead of setting
    public void stockSlot(int slotNo, int stock) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setStock(slot.getStock() + stock);
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    public void priceSlot(float price, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setPrice(price);
        } 
        else {
            System.out.println("Invalid slot number.");
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

    public void viewItems() {
        for (Slot slot : slots) {
            System.out.printf("%s - %d in stock - %.2f pesos - %.1f calories\n", slot.getName(), slot.getStock(), slot.getPrice(), slot.getKcal());
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

    private boolean isAvailable(Slot slot) {
        if(slot.getStock() == 0)
            return false;
        else
            return true;
    }
}
