package City;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//Untertypenbeziehungsbegründung: Eine Stadt ist ein Entity.
//Typbegründung: Eine Stadt soll instanziert werden können
public class City implements Entity {
    HashSet<Entity> entities;
    ArrayList<PublicRoad> publicRoads;

    public City() {
        entities = new HashSet<>();
        publicRoads = new ArrayList<>();
    }
    @Override
    public void add(Entity entity) {
        entities.add(entity);
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }
    
    @Override
    public Entity getEntity() {
        return this;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public void addPublicRoad(PublicRoad publicRoad) {
        publicRoads.add(publicRoad);
    }

    public void removePublicRoad(PublicRoad publicRoad) {
        publicRoads.remove(publicRoad);
    }

    public List<PublicRoad> getPublicRoads() {
        return publicRoads;
    }
}