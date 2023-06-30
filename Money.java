/*  //SHOULD I DELETE THISS????????? //SHOULD I DELETE THISS?????????//SHOULD I DELETE THISS?????????
//SHOULD I DELETE THISS?????????//SHOULD I DELETE THISS?????????//SHOULD I DELETE THISS?????????
//SHOULD I DELETE THISS?????????//SHOULD I DELETE THISS?????????//SHOULD I DELETE THISS?????????
USAGE: 
1.) !! Declare static Money.setDenominations() first before using class. !!
2.) calculateTransaction() takes in bankTotal: Money, given: Money, and price: int 
    NOTE: function always returns a new class 
    returns given (clone) if a. given is less than price b. VM can't return exact change
    returns change if all goes well
3.) insertMoney() returns false if denomination is invalid so no need for extra logic there
4.) static getDoubleTotal() can be used to get the total monetary value of a money class, probably useful
5.) clearMoney() sets Money.money to zero, can be used for canceling purchases
*/ //SHOULD I DELETE THISS?????????
//SHOULD I DELETE THISS?????????
//SHOULD I DELETE THISS?????????
//SHOULD I DELETE THISS?????????

/**
 * Money manages the denominations, calculations, change, and stock of the denominations
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;

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

    * @param denominations the list of accepted denominations
    */
    public static void setDenominations(ArrayList<Double> denominations) {
        Collections.sort(denominations, Collections.reverseOrder());
        acceptedDenominations = denominations;
    }

    /**
     * Returns the hashmap of money
     * 
     * @return hashmap of money
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
        if (!isValidBill(amount)) 
            return false;

        money.put(amount, money.get(amount) + 1);
        return true;
    }

    /**
     * Inserts money into the vending machine
     * @param amount the amount of money to insert
     * @param freq
     * @return true if the money inserted is a valid bill, false otherwise
     */
    public boolean insertMoney(double amount, int freq) {
        if (!isValidBill(amount)) 
            return false;

        money.put(amount, money.get(amount) + freq);
        return true;
    }

    /**
     * 
     */
    public void clearMoney() {
        for (Map.Entry<Double, Integer> entry : money.entrySet())
            entry.setValue(0);
    }

    /**
     * Checks if the entered amount is in the accepted denominations
     * 
     * @param amount the user inputted amount to add in denominations
     * @return true if amount is a valid denominations, false otherwise
     */
    private boolean isValidBill(double amount) {
        return acceptedDenominations.contains(amount);
    }

    // 
    /**
     * Merges two money classes into dest
     * 
     * @param dest
     * @param source
     */
    public static void mergeMoney(Money dest, Money source) {
        for (Map.Entry<Double, Integer> entry : source.money.entrySet())
            dest.money.merge(entry.getKey(), entry.getValue(),
             (oldValue, newValue) -> oldValue + newValue);
    }

    /**
     * Returns change as money class if possible. Otherwise returns given back : always returns a new money class
     * 
     * @param bankTotal the money in the ATM's cassette
     * @param given the money the user will pay
     * @param price the price of the item to be paid
     * @return change as money class if possible. Otherwise returns given back : always returns a new money class
     */
    public static Money calculateTransaction(Money bankTotal, Money given, double price) {
        Money tempBank = new Money();
        Money result = new Money();
        
        mergeMoney(result, given);
        mergeMoney(tempBank, bankTotal);
        
        double doubleGiven = getDoubleTotal(given);

        // if given is less than price or vending machine doesn't have enough money
        if (doubleGiven < price) return result; 
        else if (doubleGiven - price == 0) {
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
     * Returns total monetary value of money
     * @param money
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
     * @return list of acceptedDenominations
     */
    public static ArrayList<Double> getAcceptedDenominations() {
        return acceptedDenominations;
    }
}
