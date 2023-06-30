/**
 * Slot represents the item in the slot as well as its details
 */
public class Slot {
    private String name;
    private int stock;
    private double price, kcal;

    /**
     * Class constructor 
     * 
     * @param name the name of the item in the slot
     * @param stock the current stock of the item
     * @param price the price of the item
     * @param kcal the calorie content of the item
     */
    public Slot(String name, int stock, double price, double kcal) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.kcal = kcal;
    }

    /**
     * Class constructor to createa copy
     * 
     * @param original the original Slot object to be copied
     */
    public Slot(Slot original) {
        this.name = original.name;
        this.stock = original.stock;
        this.price = original.price;
        this.kcal = original.kcal;
    }

    /**
     * Returns the name of the item in the slot.
     * 
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item in the slot.
     * 
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }
    /**
     * Returns the current stock of the item in the slot.
     * 
     * @return the current stock of the item    
     */
    public int getStock() {
        return stock;
    }
    /**
     * Returns the calorie content of the item in the slot.
     * 
     * @return the calorie content of the item
     */
    public double getKcal() {
        return kcal;
    }
    
    /**
     * Sets the name of the item in the slot.
     * @param name the name of the item
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the price of the item in the slot.
     * 
     * @param price the price of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Sets the current stock of the item in the slot.
     * 
     * @param stock the current stock of the item
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Sets the calorie content of the item in the slot.
     * @param kcal the calorie content of the item
     */
    public void setKcal(double kcal) {
        this.kcal = kcal;
    }
    /**
     * Checks if the item in the slot is available (i.e., the stock is greater than zero).
     * 
     * @return true if the item is available, false otherwise
     */
    public boolean isAvailable() {
        if(stock == 0)
            return false;
        else
            return true;
    }
}
