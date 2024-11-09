public class Result {
    private int totalNewHouses;
    private int totalDemolishedHouses;
    private int totalNewResidents;
    private int totalDiedResidents;
    private float totalCostPerResidentPerYear;
    private float totalCostPerResidentPerDecade;
    private float wastePerResidentPerYear;
    private float totalAverageCarbonPerYear;
    private float totalSatisfactionPerYear;
    private double susScore;
    // BAD: Die Klasse Result ist eng mit der Klasse Scenario gekoppelt, da sie direkt von Scenario abhängt,
    // um ihre Daten bereitzustellen. Diese starke Kopplung macht den Code weniger flexibel und schwerer zu warten.
    /**
     * Constructor for Result
     *
     * @param totalNewHouses The total number of new houses
     * @param totalDemolishedHouses The total number of demolished houses
     * @param totalNewResidents The total number of new residents
     * @param totalDiedResidents The total number of died residents
     * @param totalCostPerResidentPerYear The total cost per resident per year
     * @param totalCostPerResidentPerDecade The total cost per resident per decade
     * @param wastePerResidentPerYear The waste per resident per year
     * @param totalAverageCarbonPerYear The total average carbon per year
     * @param totalSatisfactionPerYear The total satisfaction per year
     * @param susScore The sustainability score
     *
     * Zusicherung: Alle Parameter müssen gültige Werte sein (z.B. keine negativen Zahlen für Kosten, Abfall, CO2-Ausstoß, Zufriedenheit).
     */
    public Result(int totalNewHouses, int totalDemolishedHouses, int totalNewResidents, int totalDiedResidents, float totalCostPerResidentPerYear, float totalCostPerResidentPerDecade, float wastePerResidentPerYear, float totalAverageCarbonPerYear, float totalSatisfactionPerYear, double susScore) {
        setTotalNewHouses(totalNewHouses);
        setTotalDemolishedHouses(totalDemolishedHouses);
        setTotalNewResidents(totalNewResidents);
        setTotalDiedResidents(totalDiedResidents);
        setTotalCostPerResidentPerYear(totalCostPerResidentPerYear);
        setTotalCostPerResidentPerDecade(totalCostPerResidentPerDecade);
        setWastePerResidentPerYear(wastePerResidentPerYear);
        setTotalAverageCarbonPerYear(totalAverageCarbonPerYear);
        setTotalSatisfactionPerYear(totalSatisfactionPerYear);
        setSusScore(susScore);
    }

    /**
     * Get the total number of new houses
     * @return The total number of new houses
     */
    public int getTotalNewHouses() {
        return totalNewHouses;
    }

    /**
     * Set the total number of new houses
     * @param totalNewHouses The total number of new houses
     */
    public void setTotalNewHouses(int totalNewHouses) {
        this.totalNewHouses = totalNewHouses;
    }

    /**
     * Get the total number of demolished houses
     * @return The total number of demolished houses
     */
    public int getTotalDemolishedHouses() {
        return totalDemolishedHouses;
    }

    /**
     * Set the total number of demolished houses
     * @param totalDemolishedHouses The total number of demolished houses
     */
    public void setTotalDemolishedHouses(int totalDemolishedHouses) {
        this.totalDemolishedHouses = totalDemolishedHouses;
    }

    /**
     * Get the total number of new residents
     * @return The total number of new residents
     */
    public int getTotalNewResidents() {
        return totalNewResidents;
    }

    /**
     * Set the total number of new residents
     * @param totalNewResidents The total number of new residents
     */
    public void setTotalNewResidents(int totalNewResidents) {
        this.totalNewResidents = totalNewResidents;
    }

    /**
     * Get the total number of died residents
     * @return The total number of died residents
     */
    public int getTotalDiedResidents() {
        return totalDiedResidents;
    }

    /**
     * Set the total number of died residents
     * @param totalDiedResidents The total number of died residents
     */
    public void setTotalDiedResidents(int totalDiedResidents) {
        this.totalDiedResidents = totalDiedResidents;
    }

    /**
     * Get the total cost per resident per year
     * @return The total cost per resident per year
     */
    public float getTotalCostPerResidentPerYear() {
        return totalCostPerResidentPerYear;
    }

    /**
     * Set the total cost per resident per year
     * @param totalCostPerResidentPerYear The total cost per resident per year
     */
    public void setTotalCostPerResidentPerYear(float totalCostPerResidentPerYear) {
        this.totalCostPerResidentPerYear = totalCostPerResidentPerYear;
    }

    /**
     * Get the total cost per resident per decade
     * @return The total cost per resident per decade
     */
    public float getTotalCostPerResidentPerDecade() {
        return totalCostPerResidentPerDecade;
    }

    /**
     * Set the total cost per resident per decade
     * @param totalCostPerResidentPerDecade The total cost per resident per decade
     */
    public void setTotalCostPerResidentPerDecade(float totalCostPerResidentPerDecade) {
        this.totalCostPerResidentPerDecade = totalCostPerResidentPerDecade;
    }

    /**
     * Get the waste per resident per year
     * @return The waste per resident per year
     */
    public float getWastePerResidentPerYear() {
        return wastePerResidentPerYear;
    }

    /**
     * Set the waste per resident per year
     * @param wastePerResidentPerYear The waste per resident per year
     */
    public void setWastePerResidentPerYear(float wastePerResidentPerYear) {
        this.wastePerResidentPerYear = wastePerResidentPerYear;
    }

    /**
     * Get the total average carbon per year
     * @return The total average carbon per year
     */
    public float getTotalAverageCarbonPerYear() {
        return totalAverageCarbonPerYear;
    }

    /**
     * Set the total average carbon per year
     * @param totalAverageCarbonPerYear The total average carbon per year
     */
    public void setTotalAverageCarbonPerYear(float totalAverageCarbonPerYear) {
        this.totalAverageCarbonPerYear = totalAverageCarbonPerYear;
    }

    /**
     * Get the total satisfaction per year
     * @return The total satisfaction per year
     */
    public float getTotalSatisfactionPerYear() {
        return totalSatisfactionPerYear;
    }

    /**
     * Set the total satisfaction per year
     * @param totalSatisfactionPerYear The total satisfaction per year
     */
    public void setTotalSatisfactionPerYear(float totalSatisfactionPerYear) {
        this.totalSatisfactionPerYear = totalSatisfactionPerYear;
    }

    /**
     * Get the sustainability score
     * @return The sustainability score
     */
    public double getSusScore() {
        return susScore;
    }

    /**
     * Set the sustainability score
     * @param susScore The sustainability score
     */
    public void setSusScore(double susScore) {
        this.susScore = susScore;
    }
}
