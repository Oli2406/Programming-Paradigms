package config;

import house.House;
import house.Resistance;

import java.util.Random;

public class Config {
  private static final Random r = new Random();
  
  private int buildCost;
  private int carbon;
  private int area;
  private int lifetime;
  private int renovationInterval;
  private int serviceCost;
  private int renovationCost;
  private int demolishCost;
  private int wasteCost;
  private float satisfactionRate;
  private float renovationCarbon;
  private int renovationWaste;
  private int demolitionWaste;
  private Resistance resistances;
  
  private boolean random = false;
  private float significanceChance;
  
  
  public Config(int buildCost, int carbon, int area, int lifetime,
                int renovationInterval, int serviceCost, int renovationCost,
                int demolishCost, int wasteCost, float satisfactionRate,
                float renovationCarbonFactor, int renovationWaste, int demolitionWaste,
                Resistance resistances, boolean randomized, float significanceChance) {
    this.random = randomized;
    this.buildCost = buildCost;
    this.carbon = carbon;
    this.area = area;
    this.lifetime = lifetime;
    this.renovationInterval = renovationInterval;
    this.serviceCost = serviceCost;
    this.renovationCost = renovationCost;
    this.demolishCost = demolishCost;
    this.wasteCost = wasteCost;
    this.satisfactionRate = satisfactionRate;
    this.renovationCarbon = renovationCarbonFactor * carbon;
    this.renovationWaste = renovationWaste;
    this.demolitionWaste = demolitionWaste;
    this.resistances = resistances;
    this.significanceChance = significanceChance;
  }
  
  public Config() {
    this.satisfactionRate = 0.0f;
    this.renovationCarbon = 0.0f;
  }
  
  public Config(Config c) {
    this(c.getBuildCost(), c.getCarbon(), c.getArea(), c.getLifetime(), c.getRenovationInterval(), c.getServiceCost(), c.getRenovationCost(), c.getDemolishCost(), c.getWasteCost(), c.getSatisfactionRate(), c.getRenovationCarbon(), c.getRenovationWaste(), c.getDemolitionWaste(), c.getResistances(), c.getRandom(), c.getSignificanceChance());
  }
  
  public boolean checkConfig() {
    return this.getBuildCost() != 0 && this.getCarbon() != 0 && this.getArea() != 0 && this.getLifetime() != 0 && this.getRenovationInterval() != 0 && this.getServiceCost() != 0 && this.getRenovationCost() != 0 && this.getDemolishCost() != 0
        && this.getWasteCost() != 0 && this.getSatisfactionRate() != 0 && this.getRenovationCarbon() != 0 && this.getRenovationWaste() != 0 && this.getDemolitionWaste() != 0;
  }
  
  public boolean getRandom() {
    return this.random;
  }
  
  public void setRandom(boolean random) {
    this.random = random;
  }
  
  public int getBuildCost() {
    return buildCost;
  }
  
  public void setBuildCost(int buildCost) {
    this.buildCost = buildCost;
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
  
  public int getRenovationInterval() {
    return renovationInterval;
  }
  
  public void setRenovationInterval(int renovationInterval) {
    this.renovationInterval = renovationInterval;
  }
  
  public int getServiceCost() {
    return serviceCost;
  }
  
  public void setServiceCost(int serviceCost) {
    this.serviceCost = serviceCost;
  }
  
  public int getRenovationCost() {
    return renovationCost;
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
  
  public float getRenovationCarbon() {
    return renovationCarbon;
  }
  
  public void setRenovationCarbon(float renovationCarbon) {
    this.renovationCarbon = renovationCarbon * carbon;
  }
  
  public int getRenovationWaste() {
    return renovationWaste;
  }
  
  public void setRenovationWaste(int renovationWaste) {
    this.renovationWaste = renovationWaste;
  }
  
  public int getDemolitionWaste() {
    return demolitionWaste;
  }
  
  public void setDemolitionWaste(int demolitionWaste) {
    this.demolitionWaste = demolitionWaste;
  }
  
  public House createHouse() {
    House h = new House();
    if (r.nextFloat() < significanceChance) {
      h.setHighSignificance(true);
      h.setLifetime(lifetime * 2);
      h.setRenovationCost(random ? (int) ((int) r.nextGaussian(renovationCost, (double) renovationCost / 5) * 1.5) : renovationCost * 3);
      h.setHighSignificance(true);
      h.setHighSignificanceAttributes(true);
    } else {
      h.setHighSignificance(false);
      h.setLifetime(lifetime);
      h.setRenovationCost(random ? (int) r.nextGaussian(renovationCost, (double) renovationCost / 5) : renovationCost);
      h.setHighSignificance(false);
    }
    h.setRevitalizationCost(renovationCost * 3);
    h.setCost(random ? (int) r.nextGaussian(buildCost, (double) buildCost / 5) : buildCost);
    h.setCarbon(random ? (int) r.nextGaussian(carbon, (double) carbon / 5) : carbon);
    h.setRevitalizationCarbon(carbon * 3);
    h.setArea(random ? (int) r.nextGaussian(area, (double) area / 5) : area);
    h.setRenovationInterval(renovationInterval);
    h.setServiceCost(random ? (int) r.nextGaussian(serviceCost, (double) serviceCost / 5) : serviceCost);
    h.setDemolishCost(random ? (int) r.nextGaussian(demolishCost, (double) demolishCost / 5) : demolishCost);
    h.setWasteCost(random ? (int) r.nextGaussian(wasteCost, (double) wasteCost / 5) : wasteCost);
    h.setSatisfactionRate(satisfactionRate);
    h.setMaxSatisfaction(h.getSatisfactionRate());
    h.setRenovationCarbon(renovationCarbon);
    h.setRenovationWaste(random ? (int) r.nextGaussian(renovationWaste, (double) renovationWaste / 5) : renovationWaste);
    h.setDemolitionWaste(random ? (int) r.nextGaussian(demolitionWaste, (double) demolitionWaste / 5) : demolitionWaste);
    h.setResistances(resistances);
    return h;
  }
  
  public Resistance getResistances() {
    return resistances;
  }
  
  public void setResistances(Resistance resistances) {
    this.resistances = resistances;
  }
  
  public float getSignificanceChance() {
    return significanceChance;
  }
  
  public void setSignificanceChance(float significanceChance) {
    this.significanceChance = significanceChance;
  }
}
