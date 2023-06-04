public class VendingMachine {
    private String productName;
    private int maxSlots = 8;
    private int slotsFilled;
    private Slot[] slots;

    public VendingMachine(String productName){

    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSlotsFilled(int slotsFilled) {
        this.slotsFilled = slotsFilled;
    }
    
    public void setSlots(Slot[] slots) {
        this.slots = slots;
    }
}