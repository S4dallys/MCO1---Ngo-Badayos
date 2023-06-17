public class Slot {
    private String name;
    private int stock;
    private float price;
    private int slotNo;

    public Slot(String name, int slotNo) {
        this.name = name;
        this.slotNo = slotNo;
    }
    public Slot(int stock) {
        this.stock = stock;
    }
    public Slot(float price) {
        this.price = price;
    }
    public Slot(String name, int stock, float price, int slotNo) {
        this.name = name;
        this.stock = stock;
        this. price = price;
        this.slotNo = slotNo;
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

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}
