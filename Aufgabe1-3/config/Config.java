package config;

import Resident.Resident;
import enums.ScenarioType;
import house.House;
import house.Resistance;

import java.util.ArrayList;
import java.util.Random;

// STYLE: Objektorientiertes Paradigma
// Nominale Abstraktion (Klasse Config)
public class Config {
  private static final Random r = new Random();

  // Nominale Abstraktion (Variablen Namen helfen der lesbarkeit)
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
  private float significanceChance;
  private boolean random = false;

  /**
   * Constructor for Config
   * @param buildCost cost to build a house
   * @param carbon carbon footprint of a house
   * @param area area of a house
   * @param lifetime lifetime of a house
   * @param renovationInterval interval for renovation
   * @param serviceCost cost for services
   * @param renovationCost cost for renovation
   * @param demolishCost cost for demolition
   * @param wasteCost cost for waste
   * @param satisfactionRate rate of satisfaction
   * @param renovationCarbonFactor factor for renovation carbon
   * @param renovationWaste waste from renovation
   * @param demolitionWaste waste from demolition
   * @param resistances resistances of a house
   * @param significanceChance chance for a house to be significant
   * @param randomized if the values should be randomized
   */
  public Config(int buildCost, int carbon, int area, int lifetime,
                int renovationInterval, int serviceCost, int renovationCost,
                int demolishCost, int wasteCost, float satisfactionRate,
                float renovationCarbonFactor, int renovationWaste, int demolitionWaste,
                Resistance resistances, float significanceChance, boolean randomized) {
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

  /**
   * Constructor for Config
   */
  public Config() {
    this.satisfactionRate = 0.0f;
    this.renovationCarbon = 0.0f;
  }

  /**
   * Copy Constructor for Config
   * @param c Config to copy
   */
  public Config(Config c) {
    this(c.getBuildCost(), c.getCarbon(), c.getArea(), c.getLifetime(), c.getRenovationInterval(), c.getServiceCost(), c.getRenovationCost(), c.getDemolishCost(), c.getWasteCost(), c.getSatisfactionRate(), c.getRenovationCarbon(), c.getRenovationWaste(), c.getDemolitionWaste(), c.getResistances(), c.getSignificanceChance(), c.getRandom());
  }

    /**
     * Constructor for Config
     * @param type type of scenario
     */
  public Config(ScenarioType type) {
    switch (type) {
      case MINIMAL -> {
        this.buildCost = 100000;
        this.carbon = 40;
        this.area = 100;
        this.lifetime = 50;
        this.renovationInterval = 20;
        this.serviceCost = 3000;
        this.renovationCost = 25000;
        this.demolishCost = 20000;
        this.wasteCost = 10000;
        this.satisfactionRate = 0.75f;
        this.renovationCarbon = 0.75f * carbon;
        this.renovationWaste = 30;
        this.demolitionWaste = 50;
        this.resistances = new Resistance(false, false, false, false, false, false);
        this.significanceChance = 0.1f;
        this.random = true;
      }
      case BIO -> {
        this.buildCost = 150000;
        this.carbon = 20;
        this.area = 100;
        this.lifetime = 50;
        this.renovationInterval = 20;
        this.serviceCost = 3000;
        this.renovationCost = 25000;
        this.demolishCost = 20000;
        this.wasteCost = 7500;
        this.satisfactionRate = 0.8f;
        this.renovationCarbon = 0.5f * carbon;
        this.renovationWaste = 30;
        this.demolitionWaste = 50;
        this.resistances = new Resistance(true, false, true, false, false, true);
        this.significanceChance = 0.2f;
        this.random = true;
      }
      case PREMIUM -> {
        this.buildCost = 250000;
        this.carbon = 40;
        this.area = 200;
        this.lifetime = 100;
        this.renovationInterval = 25;
        this.serviceCost = 3000;
        this.renovationCost = 40000;
        this.demolishCost = 20000;
        this.wasteCost = 5000;
        this.satisfactionRate = 0.9f;
        this.renovationCarbon = 0.5f * carbon;
        this.renovationWaste = 15;
        this.demolitionWaste = 50;
        this.resistances = new Resistance(true, true, true, true, true, true);
        this.significanceChance = 0.6f;
        this.random = true;
      }
    }
  }

  /**
   * Check if the config is valid
   * @return true if the config is valid
   */
  public boolean checkConfig() {
    return this.getBuildCost() != 0 && this.getCarbon() != 0 && this.getArea() != 0 && this.getLifetime() != 0 && this.getRenovationInterval() != 0 && this.getServiceCost() != 0 && this.getRenovationCost() != 0 && this.getDemolishCost() != 0
        && this.getWasteCost() != 0 && this.getSatisfactionRate() != 0 && this.getRenovationCarbon() != 0 && this.getRenovationWaste() != 0 && this.getDemolitionWaste() != 0;
  }

  /**
   * Get if the values are randomized
   * @return true if the values are randomized
   */
  public boolean getRandom() {
    return this.random;
  }

  /**
   * Set if the values are randomized
   * @param random if the values should be randomized
   */
  public void setRandom(boolean random) {
    this.random = random;
  }

  /**
   * Get the build cost
   * @return the build cost
   */
  public int getBuildCost() {
    return buildCost;
  }

  /**
   * Set the build cost
   * @param buildCost the build cost
   */
  public void setBuildCost(int buildCost) {
    this.buildCost = buildCost;
  }

  /**
   * Get the carbon footprint
   * @return the carbon footprint
   */
  public int getCarbon() {
    return carbon;
  }

  /**
   * Set the carbon footprint
   * @param carbon the carbon footprint
   */
  public void setCarbon(int carbon) {
    this.carbon = carbon;
  }

  /**
   * Get the area
   * @return the area
   */
  public int getArea() {
    return area;
  }

  /**
   * Set the area
   * @param area the area
   */
  public void setArea(int area) {
    this.area = area;
  }

  /**
   * Get the lifetime
   * @return the lifetime
   */
  public int getLifetime() {
    return lifetime;
  }

  /**
   * Set the lifetime
   * @param lifetime the lifetime
   */
  public void setLifetime(int lifetime) {
    this.lifetime = lifetime;
  }

  /**
   * Get the renovation interval
   * @return the renovation interval
   */
  public int getRenovationInterval() {
    return renovationInterval;
  }

  /**
   * Set the renovation interval
   * @param renovationInterval the renovation interval
   */
  public void setRenovationInterval(int renovationInterval) {
    this.renovationInterval = renovationInterval;
  }

  /**
   * Get the service cost
   * @return the service cost
   */
  public int getServiceCost() {
    return serviceCost;
  }

  /**
   * Set the service cost
   * @param serviceCost the service cost
   */
  public void setServiceCost(int serviceCost) {
    this.serviceCost = serviceCost;
  }

  /**
   * Get the renovation cost
   * @return the renovation cost
   */
  public int getRenovationCost() {
    return renovationCost;
  }

  /**
   * Set the renovation cost
   * @param renovationCost the renovation cost
   */
  public void setRenovationCost(int renovationCost) {
    this.renovationCost = renovationCost;
  }

  /**
   * Get the demolish cost
   * @return the demolish cost
   */
  public int getDemolishCost() {
    return demolishCost;
  }

  /**
   * Set the demolish cost
   * @param demolishCost the demolish cost
   */
  public void setDemolishCost(int demolishCost) {
    this.demolishCost = demolishCost;
  }

  /**
   * Get the waste cost
   * @return the waste cost
   */
  public int getWasteCost() {
    return wasteCost;
  }

  /**
   * Set the waste cost
   * @param wasteCost the waste cost
   */
  public void setWasteCost(int wasteCost) {
    this.wasteCost = wasteCost;
  }

  /**
   * Get the satisfaction rate
   * @return the satisfaction rate
   */
  public float getSatisfactionRate() {
    return satisfactionRate;
  }

  /**
   * Set the satisfaction rate
   * @param satisfactionRate the satisfaction rate
   */
  public void setSatisfactionRate(float satisfactionRate) {
    this.satisfactionRate = satisfactionRate;
  }

  /**
   * Get the renovation carbon
   * @return the renovation carbon
   */
  public float getRenovationCarbon() {
    return renovationCarbon;
  }

  /**
   * Set the renovation carbon
   * @param renovationCarbon the renovation carbon
   */
  public void setRenovationCarbon(float renovationCarbon) {
    this.renovationCarbon = renovationCarbon * carbon;
  }

  /**
   * Get the renovation waste
   * @return the renovation waste
   */
  public int getRenovationWaste() {
    return renovationWaste;
  }

  /**
   * Set the renovation waste
   * @param renovationWaste the renovation waste
   */
  public void setRenovationWaste(int renovationWaste) {
    this.renovationWaste = renovationWaste;
  }

  /**
   * Get the demolition waste
   * @return the demolition waste
   */
  public int getDemolitionWaste() {
    return demolitionWaste;
  }

  /**
   * Set the demolition waste
   * @param demolitionWaste the demolition waste
   */
  public void setDemolitionWaste(int demolitionWaste) {
    this.demolitionWaste = demolitionWaste;
  }

  /**
   * Create a house with given residents
   * @param residents residents of the new house
   * @return the house with the residents
   */
  public House createHouse(ArrayList<Resident> residents) {
    // Nominale Abstraktion (Verwendung von House Objekt)
    House h = new House(residents);
    return setHouseSettings(h);
  }

  /**
   * Create a house with a number of residents
   * @param numberOfResidents number of residents
   * @return the house
   */
  public House createHouse(int numberOfResidents) {
    // Nominale Abstraktion (Verwendung von House Objekt)
    House h = new House(numberOfResidents);
    return setHouseSettings(h);
  }

  /**
   * Set the settings for a house
   * @param h house to set the settings for
   * @return the house with the settings
   */
  private House setHouseSettings(House h) {
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

  /**
   * Get the resistances
   * @return the resistances
   */
  public Resistance getResistances() {
    return resistances;
  }

  /**
   * Set the resistances
   * @param resistances the resistances
   */
  public void setResistances(Resistance resistances) {
    this.resistances = resistances;
  }

  /**
   * Get the significance chance
   * @return the significance chance
   */
  public float getSignificanceChance() {
    return significanceChance;
  }

  /**
   * Set the significance chance
   * @param significanceChance the significance chance
   */
  public void setSignificanceChance(float significanceChance) {
    this.significanceChance = significanceChance;
  }
}
