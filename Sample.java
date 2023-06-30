/**
 * Sample gives a sample Vending Machine to be used for testing
 */

import java.util.ArrayList;

public class Sample {
    public static VendingMachine getRegVm() {
        VendingMachine sample = new VendingMachine("Sample");
        ArrayList<Slot> sampleSlots = new ArrayList<>();
        ArrayList<Double> sampleDenominations = new ArrayList<>();
        Money sampleBankTotal;

        sampleDenominations.add(0.25);
        sampleDenominations.add(0.50);
        sampleDenominations.add(1d);
        sampleDenominations.add(5d);
        sampleDenominations.add(10d);
        sampleDenominations.add(20d);
        sampleDenominations.add(50d);
        sampleDenominations.add(100d);

        Money.setDenominations(sampleDenominations);

        sampleBankTotal = new Money();

        sampleBankTotal.insertMoney(0.25, 200);
        sampleBankTotal.insertMoney(0.50, 200);
        sampleBankTotal.insertMoney(1, 200);
        sampleBankTotal.insertMoney(5, 200);
        sampleBankTotal.insertMoney(10, 200);
        sampleBankTotal.insertMoney(20, 200);
        sampleBankTotal.insertMoney(50, 200);
        sampleBankTotal.insertMoney(100, 200);

        sampleSlots.add(new Slot("Carrots", 30, 38.25, 0.041));
        sampleSlots.add(new Slot("Chili", 20, 10, 0.101));
        sampleSlots.add(new Slot("Red Pickles", 25, 10, 0.075));
        sampleSlots.add(new Slot("Rice", 50, 30, 0.12));
        sampleSlots.add(new Slot("Curry Sauce", 70, 25, .12));
        sampleSlots.add(new Slot("Tonkatsu", 45, 60, 0.386));
        sampleSlots.add(new Slot("Egg", 50, 15, 0.155));
        sampleSlots.add(new Slot("Cabbage", 35, 10, 0.025));

        sample.setSlots(sampleSlots);
        sample.setBankTotal(sampleBankTotal);

        return sample;
    }
}
