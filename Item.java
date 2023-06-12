public class Item {
    private String name;
    private float price;
    private int stock;

    public Item(String name, float price, int stock){
        this.name = name;
        this.price = price;
        this.stock =stock;
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

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean isAvailable(){
        if(stock > 0)
            return true;
        else
            return false;
    }
}
