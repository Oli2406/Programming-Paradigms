@Responsible(developer = "Oliver Kastner")
@Invariant("powerLevel != null && price >= 0.0")
public class HighTempHeatPump extends HeatPump {
    @Precondition("powerLevel != null && price >= 0.0")
    @Postcondition("this.getPowerLevel() == powerLevel && this.getPrice() == price")
    public HighTempHeatPump(HeatPumpPowerLevel powerLevel, double price) {
        super(powerLevel, price);
    }
    
    @Responsible(developer = "Oliver Kastner")
    @Precondition("heatingType != null && size != null")
    @Postcondition("result == (heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatible(size))")
    @Override
    public boolean isCompatible(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatible(size);
    }

    @Responsible(developer = "Ryan Foster")
    @Precondition("heatingType != null && size != null")
    @Postcondition("result == (heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatibleExact(size))")
    @Override
    public boolean isCompatibleExact(HeatingType heatingType, OfficeSize size) {
        return heatingType == HeatingType.RADIATOR && getPowerLevel().isCompatibleExact(size);
    }
    
    @Responsible(developer = "Oliver Kastner")
    @Postcondition("result.equals(\"HighTempHeatPump\")")
    @Override
    public String getType() {
        return "HighTempHeatPump";
    }
    
    @Responsible(developer = "Oliver Kastner")
    @Postcondition("result != null && result.contains(getPowerLevel().toString()) && result.contains(String.valueOf(getPrice()))")
    @Override
    public String toString() {
        return "HighTempHeatPump: " + getPowerLevel() + " for " + getPrice() + "€";
    }
}
