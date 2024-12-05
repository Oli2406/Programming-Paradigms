// Test.java
@Responsible(developer = "Oliver Kastner")
@Invariant("Heat pumps and office units must remain consistent with defined constraints.")
public class Test {
  
  public static void main(String[] args) {
    System.out.println("### Running Tests ###\n");
    
    // Initialize Inventory and OfficeManager
    Inventory inventory = new Inventory();
    OfficeManager officeManager = new OfficeManager(inventory);
    
    Test testInstance = new Test();
    
    // 1. Add Heat Pumps to Inventory
    System.out.println("1. Adding Heat Pumps...");
    testInstance.testAddHeatPump(inventory, new LowTempHeatPump(HeatPumpPowerLevel.LOW, 1000.0));
    testInstance.testAddHeatPump(inventory, new LowTempHeatPump(HeatPumpPowerLevel.MEDIUM, 1500.0));
    testInstance.testAddHeatPump(inventory, new HighTempHeatPump(HeatPumpPowerLevel.HIGH, 2500.0));
    testInstance.testAddHeatPump(inventory, new HighTempHeatPump(HeatPumpPowerLevel.MEDIUM, 2000.0));
    inventory.showHeatPumps();
    
    // 2. Add Office Units
    System.out.println("\n2. Adding Office Units...");
    OfficeUnit office1 = new OfficeUnit(HeatingType.FLOOR, OfficeSize.SMALL);
    OfficeUnit office2 = new OfficeUnit(HeatingType.RADIATOR, OfficeSize.MEDIUM);
    OfficeUnit office3 = new OfficeUnit(HeatingType.FLOOR, OfficeSize.MEDIUM);
    OfficeUnit office4 = new OfficeUnit(HeatingType.RADIATOR, OfficeSize.LARGE);
    
    officeManager.addOffice(office1);
    officeManager.addOffice(office2);
    officeManager.addOffice(office3);
    officeManager.addOffice(office4);
    officeManager.showOffices();
    
    // 3. Assign Heat Pumps
    System.out.println("\n3. Assigning Heat Pumps...");
    testInstance.testAssignPumpToOffice(officeManager, office1);
    testInstance.testAssignPumpToOffice(officeManager, office2);
    testInstance.testAssignPumpToOffice(officeManager, office3);
    testInstance.testAssignPumpToOffice(officeManager, office4);
    officeManager.showOffices();
    
    // Display Prices
    System.out.println("\n4. Displaying Prices...");
    System.out.println("Available Heat Pump Price: " + inventory.totalAvailablePrice());
    System.out.println("Installed Heat Pump Price: " + officeManager.totalInstalledPrice());
    
    // 5. Return Heat Pumps
    System.out.println("\n5. Returning Heat Pumps...");
    testInstance.testReturnHeatPump(officeManager, office1);
    testInstance.testReturnHeatPump(officeManager, office2);
    officeManager.showOffices();
    inventory.showHeatPumps();
    
    // Reflection Analysis
    System.out.println("\n### Reflection Analysis ###");
    ReflectionUtility.analyzeClasses(
        HeatPump.class,
        LowTempHeatPump.class,
        HighTempHeatPump.class,
        OfficeUnit.class,
        Inventory.class,
        OfficeManager.class,
        Test.class
    );
  }
  
  @Precondition("HeatPump must not be null and must match constraints.")
  @Postcondition("HeatPump must be added to inventory.")
  public void testAddHeatPump(Inventory inventory, HeatPump pump) {
    inventory.addHeatPump(pump);
  }
  
  @Precondition("Office unit must exist, and a suitable heat pump must be available.")
  @Postcondition("Office unit must be assigned a compatible heat pump.")
  public void testAssignPumpToOffice(OfficeManager manager, OfficeUnit unit) {
    manager.assignPumpToOffice(unit);
  }
  
  @Precondition("Office unit must have an installed heat pump.")
  @Postcondition("Heat pump must be removed and returned to inventory.")
  public void testReturnHeatPump(OfficeManager manager, OfficeUnit unit) {
    manager.removePumpFromOffice(unit);
  }
}
