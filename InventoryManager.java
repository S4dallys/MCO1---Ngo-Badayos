/**
 * InventoryManager is a view class related to the summary of transactions made
 *                  in the vending machine including the profits
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class InventoryManager {
    private ArrayList<Integer> inventoryStock = new ArrayList<>();
    private VendingMachine vm;
    private double totalProfit = 0;

    /**
     * Class constructor
     *
     * @param vm the VendingMachine object to associate with the InventoryManager
     */
    public InventoryManager(VendingMachine vm) {
        this.vm = vm;

        for (Slot slot : vm.getSlots())
            inventoryStock.add(slot.getStock());
        totalProfit = 0;
    }

    /**
     * Sets the total profit of the vending machine.
     * @param totalProfit the total profit to set
     */
    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    /**
     * Reconfigures the inventory manager to match the current snapshot of the vending machine.
     * This updates the inventory stock and resets the total profit to zero.
     */

    public void reconfigure() {
        this.inventoryStock.clear();

        for (Slot slot : vm.getSlots())
            inventoryStock.add(slot.getStock());

        totalProfit = 0;
    }

    /**
     * Reconfigures the inventory manager with a new VendingMachine instance.
     * This updates the inventory stock, sets the vending machine, and resets the total profit to zero.
     * 
     * @param vm the new VendingMachine object to associate with the InventoryManager
     */
    public void reconfigure(VendingMachine vm) {
        this.inventoryStock.clear();
        this.vm = vm;
        
        for (Slot slot : vm.getSlots())
            inventoryStock.add(slot.getStock());

        totalProfit = 0;
    }

    /**
     * Returns the VendingMachine object associated with the inventory manager.
     * @return the VendingMachine object
     */
    public VendingMachine getVm() {
        return vm;
    }

    /**
     * Returns the total profit of the vending machine.
     * 
     * @return the total profit
     */
    public double getTotalProfit() {
        return totalProfit;
    }

    /**
     * Returns a LinkedHashMap containing the inventory loss for each item in the vending machine.
     * The inventory loss represents the difference between the starting stock and the current stock.
     *
     * @return a LinkedHashMap where each key is the item name and the value is the inventory loss
     */
    public LinkedHashMap<String, Integer> getInventoryLost() {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();

        for (int i = 0; i < vm.getSlots().size(); i++) {
            try {
                result.put(vm.getSlots().get(i).getName(),
                inventoryStock.get(i) - vm.getSlots().get(i).getStock());
            }
            catch (Exception e) {
                result.put(vm.getSlots().get(i).getName(),
                -1);
            };
        }

        return result;
    }

    /** 
     * Prints the inventory loss for each item in the vending machine.
     * If an item is newly added and has no starting stock, it is marked as "NEW ITEM".
     */
    public void printInventoryLost() {
        for (Map.Entry<String, Integer> entry : getInventoryLost().entrySet())
            if (entry.getValue() != -1)
                System.out.println("\t" + (entry.getValue()) + " " + entry.getKey() + " purchased");
            else 
                System.out.println("\t(untracked) " + entry.getKey());

        System.out.println("\n\tNote: New items won't be tracked until trackers are reset.");
    }

    /**
     * Prints the starting stock of each item in the vending machine.
     */

    public void printStartingStock() {
        for (int i = 0; i < vm.getSlots().size(); i++)
            System.out.println("ITEM: " + vm.getSlots().get(i).getName() + " STOCK: " + inventoryStock.get(i));
    }
    /**
     * Prints the current stock of each item in the vending machine.
     */
    public void printCurrentStock() {
        for (int i = 0; i < vm.getSlots().size(); i++)
            System.out.println("ITEM: " + vm.getSlots().get(i).getName() + " STOCK: " + vm.getSlots().get(i).getStock());
    }
}
