@Responsible(developer = "Oliver Kastner")
public class HighTempHeatPump extends HeatPump {
    public HighTempHeatPump(HeatPumpPowerLevel powerLevel, double price) {
        super(powerLevel, price);
    }
    
    @Responsible(developer = "Oliver Kastner")
    @Override
    public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatible(size);
    }

    @Responsible(developer = "Ryan Foster")
    @Override
    public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatibleExact(size);
    }
    
    @Responsible(developer = "Oliver Kastner")
    @Override
    public String getType() {
        return "HighTempHeatPump";
    }
    
    @Responsible(developer = "Oliver Kastner")
    @Override
    public String toString() {
        return "HighTempHeatPump: " + getPowerLevel() + " for " + getPrice() + "€";
    }
}
