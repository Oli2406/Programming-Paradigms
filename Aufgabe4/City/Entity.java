package City;
//Untertypenbeziehungsbegründung: Ein Entity hat keine Untertypenbeziehung,
// da alle anderen Klassen entweder Untertypen voneinander,
// von Entity, oder von Space sind,
// und Objekte von Entity selbst nicht vom Typ Space sein können.
//Typbegründung: Entity selbst wird nie instanziiert, und gibt nur grob vor was in solchen Klassen umgesetzt werden soll.
public interface Entity {
    void add(Entity entity);
    void remove(Entity entity);
    Entity getEntity();
}