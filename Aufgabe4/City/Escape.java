package City;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//TODO: Check if this class is a subtype of Entity
public class Escape {
    
    private final List<Space> path = new ArrayList<>();

    public Escape(PublicRoad path) {
        this.path.add(path);
    }
    
    public void add(Space space) {
        path.add(space);
    }
    
    public List<Space> getPath() {
        return path;
    }

    public Space space() {
        return path.getFirst();
    }

    public Iterator<Space> iterator(boolean lift, boolean enter) {
        List<Space> filteredPath = new ArrayList<>();
        for (Space s : path) {
            if (!lift && s.isLift()) {
                continue; //TODO: Rewrite or check this method for plausibility
            }
            filteredPath.add(s);
        }
        if (filteredPath.size() > 10) {
            throw new IllegalStateException("Path is too long, not sufficiently accessible.");
        }
        return !enter ? filteredPath.reversed().iterator() : filteredPath.iterator();
    }
    
    @Override
    public String toString() {
        return path.toString();
    }
}