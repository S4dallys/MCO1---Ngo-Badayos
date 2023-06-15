public class VendingMachine {
    private String vmName;
    private final int maxSlots = 8;
    private Slot[] slots;
    private int slotCount = 0;
    private Money money;

    public VendingMachine(String vmName) {
        this.vmName = vmName;
        slots = new Slot[maxSlots];
    }

    public void addSlot(String name) {
        slots[slotCount] = new Slot(name, slotCount+1);
        slotCount++;
    }
    public void addSlot(int stock, int slotNo) {
        slots[slotNo] = new Slot(stock);
    }
    public void addSlot(float price, int slotNo) {
        slots[slotNo] = new Slot(price);
    }

    public Slot getSlot(int slotNo) {
        return slots[slotNo - 1];
    }

    public boolean checkEmpty() {
        for(Slot slot : slots) {
            if(slot != null && slot.getStock() > 0)
                return false;
        }
        return true;
    }
}