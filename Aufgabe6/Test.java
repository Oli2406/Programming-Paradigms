import Room.*;

public class Test {
    public static void main(String[] args) {
        Room r = new RoomWindowless("Toilet", 10, 10, new UsageStoreroom(20), 100);
        System.out.println(r.getUsage().getWorkplaces());
        System.out.println(r.getUsage().getStorageVolume());
        r.setUsage(new UsageOffice(5));
        System.out.println(r.getUsage().getWorkplaces());
        System.out.println(r.getUsage().getStorageVolume());
    }
}
