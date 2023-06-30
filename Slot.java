public class Slot {
    private String name;
    private int stock;
    private double price, kcal;

    /**
     * Class constructor 
     */
    public Slot(String name, int stock, double price, double kcal) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.kcal = kcal;
    }

    /**
     * Class constructor to make a copy
     */
    public Slot(Slot original) {
        this.name = original.name;
        this.stock = original.stock;
        this.price = original.price;
        this.kcal = original.kcal;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     */
    public double getPrice() {
        return price;
    }
    /**
     * 
     * @return
     */
    public int getStock() {
        return stock;
    }
    /**
     * 
     * @return
     */
    public double getKcal() {
        return kcal;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * 
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * 
     * @param kcal
     */
    public void setKcal(double kcal) {
        this.kcal = kcal;
    }
    /**
     * 
     * @return
     */
    public boolean isAvailable() {
        if(stock == 0)
            return false;
        else
            return true;
    }
}
