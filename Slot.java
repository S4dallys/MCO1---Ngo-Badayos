public class Slot {
    private int slotNo;
    private Item item;
    private static int nextSlotNo = 0;

    public Slot(Item item) {
        this.item = item;
        slotNo = nextSlotNo;
        nextSlotNo++;
    }

    public Slot() {
        this.item = null;
        slotNo = nextSlotNo;
        nextSlotNo++;
    }

    public int getSlotNo() {
        return slotNo;
    }

    public Item getItem() {
        return item;
    }

    public int getNextSlotNo() {
        return nextSlotNo;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    public boolean hasNullItem() {
        return (item == null) ? true : false;
    }
    
    public static boolean isAllSlotsFilled(int maxSlots) {
        return (nextSlotNo >= maxSlots);
    }

    public static void resetNextSlotNo() {
        nextSlotNo = 0;
    }
}