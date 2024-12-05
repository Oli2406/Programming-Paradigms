public enum HeatPumpPowerLevel {
    LOW,
    MEDIUM,
    HIGH;

    public boolean isCompatible(OfficeSize size) {
        return size == OfficeSize.SMALL && (this == LOW || this == MEDIUM) || size == OfficeSize.MEDIUM && (this == MEDIUM || this == HIGH) || size == OfficeSize.LARGE && this == HIGH;
    }

    public boolean isCompatibleExact(OfficeSize size) {
        return size == OfficeSize.SMALL && this == LOW || size == OfficeSize.MEDIUM && this == MEDIUM || size == OfficeSize.LARGE && this == HIGH;
    }
}
