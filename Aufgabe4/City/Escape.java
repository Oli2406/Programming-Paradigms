package City;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Untertypsbeziehungsbegründung: Escape ist kein Ort wo man sich aufhält und auch kein Gebäude, sondern eine beschreibung, wie man das Gebäude/Raum verlassen kann.
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
            if(lift) {
                filteredPath.add(s);
            } else {
                if(s.isLift()) {
                    throw new IllegalStateException("Path contains a lift, not sufficiently accessible.");
                } else {
                    filteredPath.add(s);
                }
            }
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