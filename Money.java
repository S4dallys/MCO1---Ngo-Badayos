/*  
USAGE: 
1.) !! Declare static Money.setDenomenations() first before using class. !!
2.) calculateTransaction() takes in bankTotal: Money, given: Money, and price: int 
    NOTE: function always returns a new class 
    returns given (clone) if a. given is less than price b. VM can't return exact change
    returns change if all goes well
3.) insertMoney() returns false if denomenation is invalid so no need for extra logic there
4.) static getIntTotal() can be used to get the total monetary value of a money class, probably useful
5.) clearMoney() sets Money.money to zero, can be used for canceling purchases
*/

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;

public class Money {
    private LinkedHashMap<Integer, Integer> money = new LinkedHashMap<>();
    public static ArrayList<Integer> acceptedDenomenations;

    // use for testing
    /* 
    public static void main(String[] args) {
        ArrayList<Integer> d = new ArrayList<>();
        d.add(1);
        d.add(5);
        d.add(10);
        d.add(20);
        d.add(50);

        Money.setDenomenations(d);

        Money m = new Money();
        Money bank = new Money();

        bank.insertMoney(5);

        m.insertMoney(10);
        m.insertMoney(20);
        m.insertMoney(20);
        m.insertMoney(20);
        m.insertMoney(20);
        m.insertMoney(20);

        System.out.println("GIVEN " + Money.getIntTotal(m) + "\nBANK: " + Money.getIntTotal(bank));
        System.out.println("\nCHANGE: " + Money.calculateTransaction(bank, m, 105).money);
        System.out.println("\nUPDATED BANK " + getIntTotal(bank));
    }
    */

    // public static void main(String[] args) {
    //     ArrayList<Integer> d = new ArrayList<>();
    //     d.add(1); d.add(10); d.add(20); d.add(50); d.add(100);
    //     Money.setDenomenations(d);
    //     Money m = new Money();
    //     System.out.println(m.getMoney());
    // }
    

    public Money() {
        if (acceptedDenomenations == null) throw new NullPointerException("Please initialize accepted Money denomenations using:\nMoney.setDenomenations()");

        // init linkedhashmap
        for (Integer bill : acceptedDenomenations) {
            this.money.put(bill, 0);
        }
    }

    public static void setDenomenations(ArrayList<Integer> denomenations) {
        Collections.sort(denomenations, Collections.reverseOrder());
        acceptedDenomenations = denomenations;
    }

    public LinkedHashMap<Integer, Integer> getMoney() {
        return this.money;
    }

    // returns false if value is invalid
    public boolean insertMoney(int amount) {
        if (!isValidBill(amount)) 
            return false;

        money.put(amount, money.get(amount) + 1);
        return true;
    }

    public void clearMoney() {
        money.clear();
    }

    // check if entered amount is in correct denomenation
    private boolean isValidBill(int amount) {
        return acceptedDenomenations.contains(amount);
    }

    // merges two money classes into dest
    public static void mergeMoney(Money dest, Money source) {
        for (Map.Entry<Integer, Integer> entry : source.money.entrySet())
            dest.money.merge(entry.getKey(), entry.getValue(),
             (oldValue, newValue) -> oldValue + newValue);
    }

    // returns change as money class if possible else returns given back : always returns a new money class
    public static Money calculateTransaction(Money bankTotal, Money given, int price) {
        Money tempBank = new Money();
        Money result = new Money();
        
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
            mergeMoney(result, given);
            return result;
        };

        bankTotal.money = tempBank.money;
        return result;
    }

    // returns total monetary value of money
    public static int getIntTotal(Money money) {
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : money.money.entrySet()) {
            result += entry.getKey() * entry.getValue();
        }
        return result;
    }
}
