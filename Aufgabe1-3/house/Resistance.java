package house;

// STYLE: Objektorientiertes Paradigma
// Nominale Abstraktion (Klasse Resistance)
public class Resistance {
  private boolean earthquakeResistance;
  private boolean wildfireResistance;
  private boolean floodResistance;
  private boolean tornadoResistance;
  private boolean energyResistance;
  private boolean fireResistance;

  /**
   * Constructor for Resistance
   * @param earthquakeResistances The resistance to earthquakes
   * @param wildfireResistances The resistance to wildfires
   * @param floodResistances The resistance to floods
   * @param tornadoResistances The resistance to tornadoes
   * @param energyResistances The resistance to energy
   * @param fireResistances The resistance to fires
   */
  public Resistance(boolean earthquakeResistances, boolean wildfireResistances, boolean floodResistances, boolean tornadoResistances, boolean energyResistances, boolean fireResistances) {
    this.earthquakeResistance = earthquakeResistances;
    this.wildfireResistance = wildfireResistances;
    this.floodResistance = floodResistances;
    this.tornadoResistance = tornadoResistances;
    this.energyResistance = energyResistances;
    this.fireResistance = fireResistances;
  }

  /**
   * Constructor for Resistance
   */
  public Resistance() {
    this.earthquakeResistance = false;
    this.wildfireResistance = false;
    this.floodResistance = false;
    this.tornadoResistance = false;
    this.energyResistance = false;
    this.fireResistance = false;
  }

  /**
   * Getter for earthquakeResistance
   * @return The resistance to earthquakes
   */
  public boolean isEarthquakeResistance() {
    return earthquakeResistance;
  }

  /**
   * Setter for earthquakeResistance
   * @param earthquakeResistance The resistance to earthquakes
   */
  public void setEarthquakeResistance(boolean earthquakeResistance) {
    this.earthquakeResistance = earthquakeResistance;
  }

  /**
   * Getter for wildfireResistance
   * @return The resistance to wildfires
   */
  public boolean isWildfireResistance() {
    return wildfireResistance;
  }

  /**
   * Setter for wildfireResistance
   * @param wildfireResistance The resistance to wildfires
   */
  public void setWildfireResistance(boolean wildfireResistance) {
    this.wildfireResistance = wildfireResistance;
  }

  /**
   * Getter for floodResistance
   * @return The resistance to floods
   */
  public boolean isFloodResistance() {
    return floodResistance;
  }

  /**
   * Setter for floodResistance
   * @param floodResistance The resistance to floods
   */
  public void setFloodResistance(boolean floodResistance) {
    this.floodResistance = floodResistance;
  }

  /**
   * Getter for tornadoResistance
   * @return The resistance to tornadoes
   */
  public boolean isTornadoResistance() {
    return tornadoResistance;
  }

  /**
   * Setter for tornadoResistance
   * @param tornadoResistance The resistance to tornadoes
   */
  public void setTornadoResistance(boolean tornadoResistance) {
    this.tornadoResistance = tornadoResistance;
  }

  /**
   * Getter for energyResistance
   * @return The resistance to energy
   */
  public boolean isEnergyResistance() {
    return energyResistance;
  }

  /**
   * Setter for energyResistance
   * @param energyResistance The resistance to energy
   */
  public void setEnergyResistance(boolean energyResistance) {
    this.energyResistance = energyResistance;
  }

  /**
   * Getter for fireResistance
   * @return The resistance to fires
   */
  public boolean isFireResistance() {
    return fireResistance;
  }

  /**
   * Setter for fireResistance
   * @param fireResistance The resistance to fires
   */
  public void setFireResistance(boolean fireResistance) {
    this.fireResistance = fireResistance;
  }

  /**
   * Returns the number of resistances
   * @return The number of resistances
   */
  public int numberOfResistances() {
    int numberOfResistances = 0;
    if (earthquakeResistance) {
      numberOfResistances++;
    }
    if (wildfireResistance) {
      numberOfResistances++;
    }
    if (floodResistance) {
      numberOfResistances++;
    }
    if (tornadoResistance) {
      numberOfResistances++;
    }
    if (energyResistance) {
      numberOfResistances++;
    }
    if (fireResistance) {
      numberOfResistances++;
    }
    return numberOfResistances;
  }
}
