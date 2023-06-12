public class Maintainance {
    private VendingMachine vm;

    // zero money
    Money zero = new Money();

    public Maintainance(VendingMachine vm) {
        this.vm = vm;
    }

    public boolean restock(int slotNo, int stock) {
        Slot slot = vm.getSlots()[slotNo-1];

        if (slot.getItem().isAvailable() == false)
            return false;

        slot.getItem().setStock(stock);

        return false;
    }

    public boolean setItemPrice(int slotNo, float price) {
        Slot slot = vm.getSlots()[slotNo-1];

        if (slot.getItem().isAvailable() == false)
            return false;

        slot.getItem().setPrice(price);

        return true;
    }

    public Money collectPayment() {
        Money money = vm.getMoney();
        vm.setMoney(zero);

        return money;
    }

    public void replenishMoney(Money money) {
        vm.addMoney(money);
    }

    public Transaction[] getTransactionsSummary() {
        // edit
    }
}
