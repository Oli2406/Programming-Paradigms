package House;

import java.util.Random;
import Enums.EventType;

public class House {
    Random r = new Random();

    private int residents;
    private int cost;
    private int carbon;
    private int area;
    private int lifetime;
    private int serviceCost;
    private int renovationCost;
    private int demolishCost;
    private int wasteCost;
    private float satisfactionRate;
    private int renovationInterval;
    private int renovationLifetime;
    private float renovationCarbon;
    private float maxSatisfaction;
    private final float wastePerYearPerResident = 0.27f;
    private int renovationWaste;
    private int demolitionWaste;

    public House() {
        this.residents = r.nextInt(1,5);
        this.renovate();
    }


    public int getResidents() {
        return residents;
    }

    public void setResidents(int residents) {
        this.residents = residents;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCarbon() {
        return carbon;
    }

    public void setCarbon(int carbon) {
        this.carbon = carbon;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(int serviceCost) {
        this.serviceCost = serviceCost;
    }

    public int getRenovationCost() {
        return r.nextInt(Math.min(5000, renovationCost-1),renovationCost);
    }

    public void setRenovationCost(int renovationCost) {
        this.renovationCost = renovationCost;
    }

    public int getDemolishCost() {
        return demolishCost;
    }

    public void setDemolishCost(int demolishCost) {
        this.demolishCost = demolishCost;
    }

    public int getWasteCost() {
        return wasteCost;
    }

    public void setWasteCost(int wasteCost) {
        this.wasteCost = wasteCost;
    }

    public float getSatisfactionRate() {
        return satisfactionRate;
    }

    public void setSatisfactionRate(float satisfactionRate) {
        this.satisfactionRate = satisfactionRate;
    }

    public int getRenovationInterval() {
        return renovationInterval;
    }

    public void setRenovationInterval(int renovationInterval) {
        this.renovationInterval = renovationInterval;
    }

    public void age() {
        lifetime = Math.max(0, lifetime - 1);
        renovationLifetime = Math.max(0, renovationLifetime - 1);
        if(satisfactionRate-0.012f < 0) {
            satisfactionRate = 0;
        } else {
            satisfactionRate -= 0.0000012f;
        }
    }

    public int getRenovationLifetime() {
        return renovationLifetime;
    }

    public void setRenovationLifetime(int renovationLifetime) {
        this.renovationLifetime = renovationLifetime;
    }

    public void renovate() {
        renovationLifetime = (int) r.nextGaussian(renovationInterval, (double) renovationInterval /4);
        //TODO: discuss proper value for Increase
        float satisfactionIncrease = (float) r.nextGaussian(0.13, 0.02);
        if(satisfactionRate + satisfactionIncrease > maxSatisfaction) {
            satisfactionRate = maxSatisfaction-0.01f;
        } else {
            satisfactionRate += satisfactionIncrease;
        }
    }
    public float getRenovationCarbon() {
        return renovationCarbon;
    }

    public void setRenovationCarbon(float renovationCarbon) {
        this.renovationCarbon = renovationCarbon;
    }
    
    public float getWastePerYearPerResident() {
        return wastePerYearPerResident;
    }
    
    public int getRenovationWaste() {
        return renovationWaste;
    }
    
    public int getDemolitionWaste() {
        return demolitionWaste;
    }

    public void setRenovationWaste(int renovationWaste) {
        this.renovationWaste = renovationWaste;
    }

    public void setDemolitionWaste(int demolitionWaste) {
        this.demolitionWaste = demolitionWaste;
    }

    public void reduceSatisfaction(EventType event) {
        float maxSatisfactionReduction;
        float minSatisfactionReduction = switch (event) {
            case EARTHQUAKE -> {
                maxSatisfactionReduction = 0.1f;
                yield 0.02f;
            }
            case FIRE -> {
                maxSatisfactionReduction = 0.15f;
                yield 0.01f;
            }
            case INFESTATION -> {
                maxSatisfactionReduction = 0.07f;
                yield 0.01f;
            }
            default -> {
                maxSatisfactionReduction = 0.1f;
                yield 0.01f;
            }
        };

        float satisfactionReduction = r.nextFloat(minSatisfactionReduction, maxSatisfactionReduction);
        satisfactionRate = satisfactionRate * (1.0f - satisfactionReduction);
    }

    public void setMaxSatisfaction(float maxSatisfaction) {
        this.maxSatisfaction = maxSatisfaction;
    }
}
