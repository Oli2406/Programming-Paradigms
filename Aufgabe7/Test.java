// Test.java
@Responsible(developer = "Oliver Kastner")
@Invariant("Heat pumps and office units must remain consistent with defined constraints.")
public class Test {

  public static void main(String[] args) {
    System.out.println("### Running Tests ###\n");

    Inventory inventory1 = new Inventory();
    Inventory inventory2 = new Inventory();
    Inventory inventory3 = new Inventory();

    OfficeManager officeManager1 = new OfficeManager(inventory1);
    OfficeManager officeManager2 = new OfficeManager(inventory2);
    OfficeManager officeManager3 = new OfficeManager(inventory3);

    Test testInstance = new Test();

    System.out.println("### Testing Office Operator 1 ###");
    testInstance.runTestsForOperator1(inventory1, officeManager1);

    System.out.println("\n### Testing Office Operator 2 ###");
    testInstance.runTestsForOperator2(inventory2, officeManager2);

    System.out.println("\n### Testing Office Operator 3 ###");
    testInstance.runTestsForOperator3(inventory3, officeManager3);

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

  public void runTestsForOperator1(Inventory inventory, OfficeManager officeManager) {
    System.out.println("1. Adding Heat Pumps...");
    testAddHeatPump(inventory, new LowTempHeatPump(HeatPumpPowerLevel.LOW, 1000.0));
    testAddHeatPump(inventory, new HighTempHeatPump(HeatPumpPowerLevel.HIGH, 2500.0));
    inventory.showHeatPumps();

    System.out.println("\n2. Adding Office Units...");
    OfficeUnit office1 = new OfficeUnit(HeatingType.FLOOR, OfficeSize.SMALL);
    OfficeUnit office2 = new OfficeUnit(HeatingType.RADIATOR, OfficeSize.MEDIUM);
    officeManager.addOffice(office1);
    officeManager.addOffice(office2);
    officeManager.showOffices();

    System.out.println("\n3. Assigning Heat Pumps...");
    testAssignPumpToOffice(officeManager, office1);
    testAssignPumpToOffice(officeManager, office2);
    officeManager.showOffices();

    System.out.println("\n4. Displaying Prices...");
    System.out.println("Available Heat Pump Price: " + inventory.totalAvailablePrice());
    System.out.println("Installed Heat Pump Price: " + officeManager.totalInstalledPrice());

    System.out.println("\n5. Returning Heat Pumps...");
    testReturnHeatPump(officeManager, office1);
    testReturnHeatPump(officeManager, office2);
    officeManager.showOffices();
    inventory.showHeatPumps();
  }

  public void runTestsForOperator2(Inventory inventory, OfficeManager officeManager) {
    System.out.println("1. Adding Heat Pumps...");
    testAddHeatPump(inventory, new LowTempHeatPump(HeatPumpPowerLevel.MEDIUM, 1500.0));
    testAddHeatPump(inventory, new HighTempHeatPump(HeatPumpPowerLevel.MEDIUM, 2000.0));
    inventory.showHeatPumps();

    System.out.println("\n2. Adding Office Units...");
    OfficeUnit office1 = new OfficeUnit(HeatingType.RADIATOR, OfficeSize.LARGE);
    OfficeUnit office2 = new OfficeUnit(HeatingType.FLOOR, OfficeSize.MEDIUM);
    officeManager.addOffice(office1);
    officeManager.addOffice(office2);
    officeManager.showOffices();

    System.out.println("\n3. Assigning Heat Pumps...");
    testAssignPumpToOffice(officeManager, office1);
    testAssignPumpToOffice(officeManager, office2);
    officeManager.showOffices();

    System.out.println("\n4. Displaying Prices...");
    System.out.println("Available Heat Pump Price: " + inventory.totalAvailablePrice());
    System.out.println("Installed Heat Pump Price: " + officeManager.totalInstalledPrice());

    System.out.println("\n5. Returning Heat Pumps...");
    testReturnHeatPump(officeManager, office1);
    testReturnHeatPump(officeManager, office2);
    officeManager.showOffices();
    inventory.showHeatPumps();
  }

  public void runTestsForOperator3(Inventory inventory, OfficeManager officeManager) {
    System.out.println("1. Adding Heat Pumps...");
    testAddHeatPump(inventory, new LowTempHeatPump(HeatPumpPowerLevel.LOW, 1000.0));
    testAddHeatPump(inventory, new LowTempHeatPump(HeatPumpPowerLevel.MEDIUM, 1500.0));
    inventory.showHeatPumps();

    System.out.println("\n2. Adding Office Units...");
    OfficeUnit office1 = new OfficeUnit(HeatingType.FLOOR, OfficeSize.SMALL);
    OfficeUnit office2 = new OfficeUnit(HeatingType.RADIATOR, OfficeSize.LARGE);
    officeManager.addOffice(office1);
    officeManager.addOffice(office2);
    officeManager.showOffices();

    System.out.println("\n3. Assigning Heat Pumps...");
    testAssignPumpToOffice(officeManager, office1);
    testAssignPumpToOffice(officeManager, office2);
    officeManager.showOffices();

    System.out.println("\n4. Displaying Prices...");
    System.out.println("Available Heat Pump Price: " + inventory.totalAvailablePrice());
    System.out.println("Installed Heat Pump Price: " + officeManager.totalInstalledPrice());

    System.out.println("\n5. Returning Heat Pumps...");
    testReturnHeatPump(officeManager, office1);
    testReturnHeatPump(officeManager, office2);
    officeManager.showOffices();
    inventory.showHeatPumps();
  }

  @Responsible(developer = "Oliver Kastner")
  @Precondition("HeatPump must not be null and must match constraints.")
  @Postcondition("HeatPump must be added to inventory.")
  public void testAddHeatPump(Inventory inventory, HeatPump pump) {
    inventory.addHeatPump(pump);
  }

  @Responsible(developer = "Oliver Kastner")
  @Precondition("Office unit must exist, and a suitable heat pump must be available.")
  @Postcondition("Office unit must be assigned a compatible heat pump.")
  public void testAssignPumpToOffice(OfficeManager manager, OfficeUnit unit) {
    manager.assignPumpToOffice(unit);
  }

  @Responsible(developer = "Oliver Kastner")
  @Precondition("Office unit must have an installed heat pump.")
  @Postcondition("Heat pump must be removed and returned to inventory.")
  public void testReturnHeatPump(OfficeManager manager, OfficeUnit unit) {
    manager.removePumpFromOffice(unit);
  }
}