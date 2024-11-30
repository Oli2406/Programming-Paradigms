public class RCounter implements Approvable<RCounter, Path<RCounter>> {
    private CustomMap<RCounter, Path<RCounter>> approvals = new CustomMap<>();
    private int count = 0;

    @Override
    public Path<RCounter> approved(RCounter y) {
        this.count += 1000;
        y.count += 1;
        return approvals.get(y);
    }

    @Override
    public void approve(RCounter y, Path<RCounter> t) {
        approvals.put(y, t);
    }

    @Override
    public String toString() {
        return "RCounter: " + count;
    }
}