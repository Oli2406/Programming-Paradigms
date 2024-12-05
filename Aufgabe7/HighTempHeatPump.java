@Responsible(developer = "Oliver Kastner")
public class HighTempHeatPump extends HeatPump {
    public HighTempHeatPump(HeatPumpPowerLevel powerLevel, double price) {
        super(powerLevel, price);
    }

    @Override
    public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatible(size);
    }

    @Override
    public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatibleExact(size);
    }

    @Override
    public String getType() {
        return "HighTempHeatPump";
    }

    @Override
    public String toString() {
        return "HighTempHeatPump: " + getPowerLevel() + " for " + getPrice() + "€";
    }
}
