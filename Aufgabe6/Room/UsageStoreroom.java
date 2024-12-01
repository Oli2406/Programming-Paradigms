package Room;

public class UsageStoreroom implements Usage{
  private double storageVolume;
  public UsageStoreroom(double storageVolume) {
    this.setStorageVolume(storageVolume);
  }

  @Override
  public int getWorkplaces() {
    return -1;
  }

  @Override
  public double getStorageVolume() {
        return this.storageVolume;
    }
    public void setStorageVolume(double storageVolume) {
        this.storageVolume = storageVolume;
    }
}
