import java.util.ArrayList;

public class VendingMachine {
    private String vmName;
    private final int requiredSlots = 8;
    private ArrayList<Slot> slots = new ArrayList<>();
    private Money money;

    public VendingMachine(String vmName) {
        this.vmName = vmName;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }

    public void addSlot(String name) {
        slots.add(new Slot(name, slots.size() + 1));
    }

    public void stockSlot(int stock, int slotNo) {
        if (isValidSlot(slotNo)) {
            Slot slot = slots.get(slotNo - 1);
            slot.setStock(stock);
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
            System.out.printf("%s - %d - %.2f\n", slot.getName(), slot.getStock(), slot.getPrice());
        }
    }

    public boolean checkEmpty() {
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
}
