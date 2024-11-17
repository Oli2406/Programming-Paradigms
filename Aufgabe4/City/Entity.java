package City;
//Entity selbst wird nie instanziiert, deswegen ist es ein Interface.
//Untertypenbeziehungsbegründung: Ein Entity hat keine Untertypenbeziehung,
// da alle anderen Klassen entweder Untertypen voneinander,
// von Entity, oder von Space sind,
// und Objekte von Entity selbst nicht vom Typ Space sein können.

public interface Entity {
    void add(Entity entity);
    void remove(Entity entity);
    Entity getEntity();
}