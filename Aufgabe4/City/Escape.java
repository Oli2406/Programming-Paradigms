package City;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Escape {
    private Space space;
    private List<Space> path;

    public Escape(Space space, List<Space> path) {
        this.space = space;
        this.path = path;
    }

    public Space space() {
        return space;
    }

    /*public Iterator<Space> iterator(boolean lift, boolean enter) {
        List<Space> filteredPath = new ArrayList<>();
        for (Space s : path) {
            if (!lift && s.isLift()) {
                continue;
            }
            filteredPath.add(s);
        }
        if (filteredPath.size() > 10) {
            throw new IllegalStateException("Path is too long, not sufficiently accessible.");
        }
        return filteredPath.iterator();
    }*/
}