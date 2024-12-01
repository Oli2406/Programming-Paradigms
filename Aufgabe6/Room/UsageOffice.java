package Room;

public class UsageOffice implements Usage{
  private int workspaces;

  public UsageOffice(int workspaces) {
    this.setWorkplaces(workspaces);
  }
    public void setWorkplaces(int workspaces) {
        this.workspaces = workspaces;
    }

  @Override
  public int getWorkplaces() {
    return this.workspaces;
  }

  @Override
  public double getStorageVolume() {
    return -1;
  }
}
