@Responsible(developer = "Oliver Kastner")
public class LowTempHeatPump extends HeatPump {
    public LowTempHeatPump(HeatPumpPowerLevel powerLevel, double price) {
        super(powerLevel, price);
    }

    @Override
    public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatible(size);
    }

    @Override
    public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.FLOOR && getPowerLevel().isCompatibleExact(size);
    }

    @Override
    public String getType() {
        return "LowTempHeatPump";
    }

    @Override
    public String toString() {
        return "LowTempHeatPump: " + getPowerLevel() + " for " + getPrice() + "€";
    }
}