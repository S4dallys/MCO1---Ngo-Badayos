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
    private LinkedHashMap<Double, Integer> money = new LinkedHashMap<>();
    private static ArrayList<Double> acceptedDenomenations;

    public Money() {
        // init linkedhashmap
        for (Double bill : acceptedDenomenations) {
            this.money.put(bill, 0);
        }
    }

    public static void setDenomenations(ArrayList<Double> denomenations) {
        Collections.sort(denomenations, Collections.reverseOrder());
        acceptedDenomenations = denomenations;
    }

    public LinkedHashMap<Double, Integer> getMoney() {
        return this.money;
    }

    // returns false if value is invalid
    public boolean insertMoney(double amount) {
        if (!isValidBill(amount)) 
            return false;

        money.put(amount, money.get(amount) + 1);
        return true;
    }

    // returns false if value is invalid
    public boolean insertMoney(double amount, int freq) {
        if (!isValidBill(amount)) 
            return false;

        money.put(amount, money.get(amount) + freq);
        return true;
    }

    public void clearMoney() {
        for (Map.Entry<Double, Integer> entry : money.entrySet())
            entry.setValue(0);
    }

    // check if entered amount is in correct denomenation
    private boolean isValidBill(double amount) {
        return acceptedDenomenations.contains(amount);
    }

    // merges two money classes into dest
    public static void mergeMoney(Money dest, Money source) {
        for (Map.Entry<Double, Integer> entry : source.money.entrySet())
            dest.money.merge(entry.getKey(), entry.getValue(),
             (oldValue, newValue) -> oldValue + newValue);
    }

    // returns change as money class if possible else returns given back : always returns a new money class
    public static Money calculateTransaction(Money bankTotal, Money given, double price) {
        Money tempBank = new Money();
        Money result = new Money();
        
        mergeMoney(result, given);
        mergeMoney(tempBank, given);
        mergeMoney(tempBank, bankTotal);
        
        double intGiven = getIntTotal(given);

        // if given is less than price or vending machine doesn't have enough money
        if (intGiven < price) return result; 
        else if (intGiven - price == 0) {
            mergeMoney(bankTotal, given);
            result.clearMoney(); 
            return result;
        } // if no change is necessary

        double change = intGiven - price;
        result.clearMoney();

        for (Map.Entry<Double, Integer> entry : tempBank.money.entrySet()) {
            double bill = entry.getKey();
            int quantity = entry.getValue();

            if (bill <= change && quantity > 0) {
                int numBills = (int) Math.min(quantity, change / bill);
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
    public static double getIntTotal(Money money) {
        double result = 0;
        for (Map.Entry<Double, Integer> entry : money.money.entrySet()) {
            result += entry.getKey() * entry.getValue();
        }
        return result;
    }

    public static ArrayList<Double> getAcceptedDenomenations() {
        return acceptedDenomenations;
    }
}
