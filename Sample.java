import java.util.ArrayList;

public class Sample {
    public static VendingMachine getRegVm() {
        VendingMachine sample = new VendingMachine("Sample");
        ArrayList<Slot> sampleSlots = new ArrayList<>();
        ArrayList<Double> sampleDenomenations = new ArrayList<>();
        Money sampleBankTotal;

        sampleDenomenations.add(0.25);
        sampleDenomenations.add(0.50);
        sampleDenomenations.add(1d);
        sampleDenomenations.add(5d);
        sampleDenomenations.add(10d);
        sampleDenomenations.add(20d);
        sampleDenomenations.add(50d);
        sampleDenomenations.add(100d);

        Money.setDenomenations(sampleDenomenations);

        sampleBankTotal = new Money();

        sampleBankTotal.insertMoney(0.25, 200);
        sampleBankTotal.insertMoney(0.50, 200);
        sampleBankTotal.insertMoney(1, 200);
        sampleBankTotal.insertMoney(5, 200);
        sampleBankTotal.insertMoney(10, 200);
        sampleBankTotal.insertMoney(20, 200);
        sampleBankTotal.insertMoney(50, 200);
        sampleBankTotal.insertMoney(100, 200);

        sampleSlots.add(new Slot("Carrots", 30, 38.25, 0.041, 0));
        sampleSlots.add(new Slot("Chili", 20, 10, 0.101, 1));
        sampleSlots.add(new Slot("Red Pickles", 25, 10, 0.075, 2));
        sampleSlots.add(new Slot("Rice", 50, 30, 0.12, 3));
        sampleSlots.add(new Slot("Curry Sauce", 70, 25, .12, 4));
        sampleSlots.add(new Slot("Tonkatsu", 45, 60, 0.386, 5));
        sampleSlots.add(new Slot("Egg", 50, 15, 0.155, 6));
        sampleSlots.add(new Slot("Cabbage", 35, 10, 0.025, 7));

        sample.setSlots(sampleSlots);
        sample.setBankTotal(sampleBankTotal);

        return sample;
    }
}
