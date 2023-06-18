import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;

public class Money {
    private LinkedHashMap<Integer, Integer> money = new LinkedHashMap<>();
    private ArrayList<Integer> acceptedDenomenations;

    // public static void main(String[] args) {
    //     ArrayList<Integer> d = new ArrayList<>();
    //     d.add(1);
    //     d.add(5);
    //     d.add(10);
    //     d.add(20);
    //     d.add(50);

    //     Money m = new Money(d);
    //     Money bank = new Money(d);

    //     bank.insertMoney(5);

    //     m.insertMoney(10);
    //     m.insertMoney(20);
    //     m.insertMoney(5);
    //     m.insertMoney(20);
    //     m.insertMoney(20);
    //     m.insertMoney(20);
    //     m.insertMoney(20);

    //     System.out.println("GIVEN " + Money.getIntTotal(m) + "\nBANK: " + Money.getIntTotal(bank));
    //     System.out.println("\nCHANGE: " + Money.giveChange(bank, m, 114).money);
    //     System.out.println("\nUPDATED BANK " + getIntTotal(bank));
    // }

    public Money(ArrayList<Integer> acceptedDenomenations) {
        Collections.sort(acceptedDenomenations, Collections.reverseOrder());
        this.acceptedDenomenations = acceptedDenomenations;

        for (Integer bill : acceptedDenomenations) {
            this.money.put(bill, 0);
        }
    }

    public LinkedHashMap<Integer, Integer> getMoney() {
        return this.money;
    }

    // returns false if value is invalid
    public boolean insertMoney(int amount) {
        if (!isDenomenationValid(amount)) 
            return false;

        int val = money.getOrDefault(amount, 0);
        money.put(amount, val + 1);
        return true;
    }

    public void clearMoney() {
        money.clear();
    }

    // check if entered amount is in correct denomenation
    private boolean isDenomenationValid(int amount) {
        return acceptedDenomenations.contains(amount);
    }

    // merges two money classes into dest
    public static void mergeMoney(Money dest, Money source) {
        for (Map.Entry<Integer, Integer> entry : source.money.entrySet())
            dest.money.merge(entry.getKey(), entry.getValue(),
             (oldValue, newValue) -> oldValue + newValue);
    }

    // returns change as money class or null if no change can be produced (not enough denomenations)
    public static Money calculateTransaction(Money bankTotal, Money given, int price) {
        Money tempBank = new Money(given.acceptedDenomenations);
        Money result = new Money(given.acceptedDenomenations);
        
        mergeMoney(result, given);
        mergeMoney(tempBank, given);
        mergeMoney(tempBank, bankTotal);
        
        int intGiven = getIntTotal(given);

        // if given is less than price or vending machine doesn't have enough money
        if (intGiven < price) return result; 
        else if (intGiven - price == 0) {
            mergeMoney(bankTotal, given);
            result.clearMoney(); 
            return result;
        } // if no change is necessary

        int change = intGiven - price;
        result.clearMoney();

        for (Map.Entry<Integer, Integer> entry : tempBank.money.entrySet()) {
            System.out.println(tempBank.money);
            int bill = entry.getKey();
            int quantity = entry.getValue();

            if (bill <= change && quantity > 0) {
                int numBills = Math.min(quantity, change / bill);
                change -= bill * numBills;
                tempBank.money.put(bill, quantity - numBills);
                result.money.put(bill, numBills);
            }
        }

        // if vending machine doesn't have necessary denomenations to give change
        if (change != 0) {
            result.clearMoney();    
            result.money.putAll(given.money);
            return result;
        };

        bankTotal.money = tempBank.money;
        return result;
    }

    public static int getIntTotal(Money money) {
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : money.money.entrySet()) {
            result += entry.getKey() * entry.getValue();
        }
        return result;
    }
}
