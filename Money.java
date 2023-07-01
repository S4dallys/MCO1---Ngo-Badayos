/**
 * Money manages the denominations, calculations, change, and stock of the denominations
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;
import java.lang.Math;

public class Money {
    private LinkedHashMap<Double, Integer> money = new LinkedHashMap<>();
    private static ArrayList<Double> acceptedDenominations;

    /**
     * Class constructor 
     */
    public Money() {
        for (Double bill : acceptedDenominations) {
            this.money.put(bill, 0);
        }
    }
    /**
    * Sets the list of accepted denominations for the vending machine.
    * The denominations are sorted in descending order.

    * @param denominations the ArrayList of accepted denominations
    */
    public static void setDenominations(ArrayList<Double> denominations) {
        Collections.sort(denominations, Collections.reverseOrder());
        acceptedDenominations = denominations;
    }

    /**
     * Returns the money attribute as a LinkedHashMap
     * 
     * @return money
     */
    public LinkedHashMap<Double, Integer> getMoney() {
        return this.money;
    }

    /**
     * Inserts money into the vending machine
     * @param amount the amount of money to insert
     * @return true if the money inserted is a valid bill, false otherwise
     */
    public boolean insertMoney(double amount) {
        if (!acceptedDenominations.contains(amount)) 
            return false;

        money.put(amount, money.get(amount) + 1);
        return true;
    }

    /**
     * Inserts money into the vending machine
     * @param amount the amount of money to insert
     * @param freq how many of the amount to insert
     * @return true if the money inserted is a valid bill, false otherwise
     */
    public boolean insertMoney(double amount, int freq) {
        if (!acceptedDenominations.contains(amount)) 
            return false;

        money.put(amount, money.get(amount) + freq);
        return true;
    }

    /**
     *  Sets all values of the money LinkedHashMap to zero
     */
    public void clearMoney() {
        for (Map.Entry<Double, Integer> entry : money.entrySet())
            entry.setValue(0);
    }

    // 
    /**
     * Merges two money classes into dest (source is unaffected)
     * 
     * @param dest destination Money
     * @param source source Money
     */
    public static void mergeMoney(Money dest, Money source) {
        for (Map.Entry<Double, Integer> entry : source.money.entrySet())
            dest.money.merge(entry.getKey(), entry.getValue(),
             (oldValue, newValue) -> oldValue + newValue);
    }

    /**
     * Holds the logic for a transaction involving a bank, the money paid, and the price of the item
     * @param bankTotal the money representing the "bank"
     * @param given the money representing the money inserted
     * @param price the price of the item to be paid
     * @return Returns change as money class if possible. Otherwise returns null 
     */
    public static Money calculateTransaction(Money bankTotal, Money given, double price) {
        Money tempBank = new Money();
        Money result = new Money();
        
        mergeMoney(result, given);
        mergeMoney(tempBank, bankTotal);
        mergeMoney(tempBank, given);
        
        double doubleGiven = getDoubleTotal(given);

        if (doubleGiven - price == 0) {
            mergeMoney(bankTotal, given);
            result.clearMoney(); 
            return result;
        } // if no change is necessary

        double change = doubleGiven - price;
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

        // if vending machine doesn't have necessary denominations to give change
        if (change != 0) {
            result.clearMoney();    
            mergeMoney(result, given);
            return result;
        };

        bankTotal.money = tempBank.money;
        return result;
    }

    /**
     * Subtracts the amount of divisor from a money class
     * @param bankTotal the money to subtract from
     * @param divisor the amount to remove
     */
    public static void subtractMoney(Money bankTotal, double divisor) {
        Money result = new Money();
        for (Map.Entry<Double, Integer> entry : bankTotal.money.entrySet()) {
            double bill = entry.getKey();
            int quantity = entry.getValue();

            if (bill <= divisor && quantity > 0) {
                int numBills = (int) Math.min(quantity, divisor / bill);
                divisor -= bill * numBills;
                bankTotal.money.put(bill, quantity - numBills);
                result.money.put(bill, numBills);
            }
        }
    }

    /**
     * Returns total monetary value of money
     * @param money money to be converted
     * @return total monetary value of money
     */
    public static double getDoubleTotal(Money money) {
        double result = 0;
        for (Map.Entry<Double, Integer> entry : money.money.entrySet()) {
            result += entry.getKey() * entry.getValue();
        }
        return result;
    }

    /**
     * Returns accepted denominations
     * 
     * @return list of acceptedDenominations
     */
    public static ArrayList<Double> getAcceptedDenominations() {
        return acceptedDenominations;
    }
}
