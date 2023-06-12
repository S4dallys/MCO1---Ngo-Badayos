public class VendingMachine {
    private String productName;
    private Transaction[] transactions;
    private int maxSlots = 8;
    private int slotsFilled;
    private Slot[] slots;
    private Money money;

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

    public Slot[] getSlots() {
        return slots;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public void addMoney(Money money) {
        // edit
    }

    public Money getMoney() {
        return money;
    }
}