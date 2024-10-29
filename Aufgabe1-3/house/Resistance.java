package house;

public class Resistance {
    private boolean earthquakeResistance;
    private boolean wildfireResistance;
    private boolean floodResistance;
    private boolean tornadoResistance;
    private boolean energyResistance;
    private boolean fireResistance;

    public Resistance(boolean earthquakeResistances, boolean wildfireResistances, boolean floodResistances, boolean tornadoResistances, boolean energyResistances, boolean fireResistances) {
        this.earthquakeResistance = earthquakeResistances;
        this.wildfireResistance = wildfireResistances;
        this.floodResistance = floodResistances;
        this.tornadoResistance = tornadoResistances;
        this.energyResistance = energyResistances;
        this.fireResistance = fireResistances;
    }

    public Resistance() {
        this.earthquakeResistance = false;
        this.wildfireResistance = false;
        this.floodResistance = false;
        this.tornadoResistance = false;
        this.energyResistance = false;
        this.fireResistance = false;
    }

    public boolean isEarthquakeResistance() {
        return earthquakeResistance;
    }

    public void setEarthquakeResistance(boolean earthquakeResistance) {
        this.earthquakeResistance = earthquakeResistance;
    }

    public boolean isWildfireResistance() {
        return wildfireResistance;
    }

    public void setWildfireResistance(boolean wildfireResistance) {
        this.wildfireResistance = wildfireResistance;
    }

    public boolean isFloodResistance() {
        return floodResistance;
    }

    public void setFloodResistance(boolean floodResistance) {
        this.floodResistance = floodResistance;
    }

    public boolean isTornadoResistance() {
        return tornadoResistance;
    }

    public void setTornadoResistance(boolean tornadoResistance) {
        this.tornadoResistance = tornadoResistance;
    }

    public boolean isEnergyResistance() {
        return energyResistance;
    }

    public void setEnergyResistance(boolean energyResistance) {
        this.energyResistance = energyResistance;
    }

    public boolean isFireResistance() {
        return fireResistance;
    }

    public void setFireResistance(boolean fireResistance) {
        this.fireResistance = fireResistance;
    }

    public int numberOfResistances() {
        int numberOfResistances = 0;
        if(earthquakeResistance) {
            numberOfResistances++;
        }
        if(wildfireResistance) {
            numberOfResistances++;
        }
        if(floodResistance) {
            numberOfResistances++;
        }
        if(tornadoResistance) {
            numberOfResistances++;
        }
        if(energyResistance) {
            numberOfResistances++;
        }
        if(fireResistance) {
            numberOfResistances++;
        }
        return numberOfResistances;
    }
}
