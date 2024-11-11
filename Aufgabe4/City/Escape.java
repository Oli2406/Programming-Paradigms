package City;

import java.util.Iterator;

public interface Escape {
    Space space();
    Iterator<Circulation> iterator(boolean lift, boolean enter);
}

