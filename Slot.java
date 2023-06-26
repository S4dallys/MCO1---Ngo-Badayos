public class Slot {
    private String name;
    private final int minStock = 10;
    private int stock;
    private float price, kcal;
    private int slotNo;

    public Slot(String name, int slotNo) {
        this.name = name;
        this.slotNo = slotNo;
    }
    
    public Slot(String name, int stock, float price, float kcal, int slotNo) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.kcal = kcal;
        this.slotNo = slotNo;
    }

    // copy constructor
    public Slot(Slot original) {
        this.name = original.name;
        this.stock = original.stock;
        this.price = original.price;
        this.kcal = original.kcal;
        this.slotNo = original.slotNo;
    }

    public String getName() {
        return name;
    }
    public float getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public float getKcal() {
        return kcal;
    }
    public int getSlotNo() {
        return slotNo;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setKcal(float kcal) {
        this.kcal = kcal;
    }
    public boolean isAvailable() {
        if(stock == 0)
            return false;
        else
            return true;
    }
}
