// VIEW CLASS FOR TRANSCACTION SUMMARY

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class InventoryManager {
    private int startingTotal;
    private ArrayList<Integer> inventoryStock = new ArrayList<>();
    private VendingMachine vm;
    
    public InventoryManager(VendingMachine vm) {
        this.vm = vm;

        for (Slot slot : vm.getSlots())
            inventoryStock.add(slot.getStock());

        this.startingTotal = Money.getIntTotal(vm.getBankTotal());
    }

    // sets inventory manager to the current snapshot of the vm
    public void reconfigure() {
        this.inventoryStock.clear();

        for (Slot slot : vm.getSlots())
            inventoryStock.add(slot.getStock());

        this.startingTotal = Money.getIntTotal(vm.getBankTotal());
    }

    public void reconfigure(VendingMachine vm) {
        this.inventoryStock.clear();
        this.vm = vm;
        
        for (Slot slot : vm.getSlots())
            inventoryStock.add(slot.getStock());

        this.startingTotal = Money.getIntTotal(vm.getBankTotal());
    }

    public VendingMachine getVm() {
        return vm;
    }

    public int getTotalProfit() {
        return Money.getIntTotal(vm.getBankTotal()) - startingTotal;
    }

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

    public void printInventoryLost() {
        for (Map.Entry<String, Integer> entry : getInventoryLost().entrySet())
            System.out.println("ITEM: " + entry.getKey() + " STOCK PURCHASED: " + ((entry.getValue() == -1) ? "NEW ITEM" : entry.getValue()));
    }

    public void printStartingStock() {
        for (int i = 0; i < vm.getSlots().size(); i++)
            System.out.println("ITEM: " + vm.getSlots().get(i).getName() + " STOCK: " + inventoryStock.get(i));
    }

    public void printCurrentStock() {
        for (int i = 0; i < vm.getSlots().size(); i++)
            System.out.println("ITEM: " + vm.getSlots().get(i).getName() + " STOCK: " + vm.getSlots().get(i).getStock());
    }
}
