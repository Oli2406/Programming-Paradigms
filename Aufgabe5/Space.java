import java.util.HashMap;
import java.util.Map;

public class Space<P> implements Approvable<P, Path<Space<P>>> {
    private final Map<P, Path<Space<P>>> approvals = new HashMap<>();
    private final String description;

    public Space(String description) {
        this.description = description;
    }

    @Override
    public Path<Space<P>> approved(P p) {
        return approvals.get(p);
    }

    @Override
    public void approve(P p, Path<Space<P>> t) {
        approvals.put(p, t);
    }

    @Override
    public String toString() {
        return "Space: " + description;
    }
}