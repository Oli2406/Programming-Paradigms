package house;

import java.util.Random;

public class MinimalHouse extends House {
    private static final Random r = new Random();

    private final int BUILD_COST = (int) r.nextGaussian(100000, (double) 100000 /5);
    private final int CARBON = (int) r.nextGaussian(40, (double) 40 /5);
    private final int AREA = (int) r.nextGaussian(100, (double) 100 /5);
    private final int LIFETIME = 50;
    private final int RENOVATION_INTERVAL = 20;
    private final int SERVICE_COST = (int) r.nextGaussian(3000, (double) 3000 /5);
    private final int RENOVATION_COST = (int) r.nextGaussian(25000, (double) 25000 /5);
    private final int DEMOLISH_COST = (int) r.nextGaussian(20000, (double) 20000 /5);
    private final int WASTE_COST = (int) r.nextGaussian(10000, (double) 10000 /5);
    private final float SATISFACTION_RATE = 0.75f;
    private final float RENOVATION_CARBON = 0.75f * CARBON;
    private final int RENOVATION_WASTE = (int) r.nextGaussian(30, (double) 30 /5);
    private final int DEMOLITION_WASTE = (int) r.nextGaussian(50, (double) 50 /5);
    private final boolean EARTHQUAKE_RESISTANCES = false, WILDFIRE_RESISTANCES = false, FLOOD_RESISTANCES = false, TORNADO_RESISTANCES = false, ENERGY_RESISTANCES = false, FIRE_RESISTANCES = false;

    public MinimalHouse() {
        super();
        setCost(BUILD_COST);
        setCarbon(CARBON);
        setArea(AREA);
        setLifetime(LIFETIME);
        setRenovationInterval(RENOVATION_INTERVAL);
        setServiceCost(SERVICE_COST);
        setRenovationCost(RENOVATION_COST);
        setDemolishCost(DEMOLISH_COST);
        setWasteCost(WASTE_COST);
        setSatisfactionRate(SATISFACTION_RATE);
        setMaxSatisfaction(SATISFACTION_RATE);
        setRenovationCarbon(RENOVATION_CARBON);
        setRenovationWaste(RENOVATION_WASTE);
        setDemolitionWaste(DEMOLITION_WASTE);
        setResistances(new Resistance(EARTHQUAKE_RESISTANCES, WILDFIRE_RESISTANCES, FLOOD_RESISTANCES, TORNADO_RESISTANCES, ENERGY_RESISTANCES, FIRE_RESISTANCES));
    }
}