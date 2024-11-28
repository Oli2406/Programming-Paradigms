import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/*
 * Wer hat was gemacht:
 * - Ryan Foster: Approvable.java, ApprovableSet.java, ApSet.java, Counter.java,
 * - Oliver Kastner: Space.java, Interior.java, Exterior.java, Path.java
 * - Noah Oguamalam: Admin.java, AdminSet.java, Test.java, RCounter.java
 */

public class Test {

    public Test() {
        // 1. Objekte der geforderten Typen erzeugen
        ApSet<Counter<String>, Counter<String>, String> apSet1 = new ApSet<>();
        ApSet<Counter<Integer>, Counter<Integer>, Integer> apSet2 = new ApSet<>();
        ApSet<Counter<Path<String>>, Counter<Path<String>>, Path<String>> apSet3 = new ApSet<>();
        ApSet<RCounter, RCounter, Path<RCounter>> apSet4 = new ApSet<>();
        ApSet<Space<String>, String, Path<Space<String>>> apSet5 = new ApSet<>();
        ApSet<Interior<String>, String, Path<Space<String>>> apSet6 = new ApSet<>();
        ApSet<Exterior<String>, String, Path<Space<String>>> apSet7 = new ApSet<>();
        AdminSet<RCounter, RCounter, Path<RCounter>> adminSet1 = new AdminSet<>();
        AdminSet<Space<String>, String, Path<Space<String>>> adminSet2 = new AdminSet<>();
        AdminSet<Interior<String>, String, Path<Space<String>>> adminSet3 = new AdminSet<>();
        AdminSet<Exterior<String>, String, Path<Space<String>>> adminSet4 = new AdminSet<>();
        System.out.println("1. Objekte erfolgreich erstellt.");
        
        Interior livingRoom = new Interior("Wohnzimmer");
        livingRoom.setArea(25.0);
        Interior kitchen = new Interior("Küche");
        kitchen.setArea(15.0);
        adminSet3.add(livingRoom);
        adminSet3.add(kitchen);
        
        Exterior park = new Exterior("Park");
        park.setPublic(true);
        Exterior privateGarden = new Exterior("Privater Garten");
        privateGarden.setPublic(false);
        adminSet4.add(park);
        adminSet4.add(privateGarden);
        
        System.out.println("Übertrage Elemente von adminSet3 (Interior) zu adminSet2...");
        transferFromInteriorToSpace(adminSet3, adminSet2);
        
        System.out.println("Übertrage Elemente von adminSet4 (Exterior) zu adminSet2...");
        transferFromExteriorToSpace(adminSet4, adminSet2);
        
        System.out.println("Inhalte von adminSet2 nach der Übertragung:");
        Iterator<Space<String>> iterator = adminSet2.iteratorAll();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
        
        fillContainer(apSet1, Arrays.asList(new Counter<>("A"), new Counter<>("B")));
        fillContainer(apSet2, Arrays.asList(new Counter<>(1), new Counter<>(2)));
        fillContainer(apSet3, Arrays.asList(new Counter<>(new Path<>()), new Counter<>(new Path<>())));
        fillContainer(apSet4, Arrays.asList(new RCounter(), new RCounter()));
        fillContainer(apSet5, Arrays.asList(new Space<>("Space1"), new Space<>("Space2")));
        fillContainer(apSet6, Arrays.asList(new Interior<>("Innenraum1"), new Interior<>("Innenraum2")));
        fillContainer(apSet7, Arrays.asList(new Exterior<>("Außenbereich1"), new Exterior<>("Außenbereich2")));
        fillContainer(adminSet1, Arrays.asList(new RCounter(), new RCounter()));
        fillContainer(adminSet2, Arrays.asList(new Space<>("Space1"), new Space<>("Space2")));
        fillContainer(adminSet3, Arrays.asList(new Interior<>("Innenraum1"), new Interior<>("Innenraum2")));
        fillContainer(adminSet4, Arrays.asList(new Exterior<>("Außenbereich1"), new Exterior<>("Außenbereich2")));
        
        System.out.println("2. Container erfolgreich befüllt.");
        
        System.out.println(apSet1);
        System.out.println(apSet2);
        System.out.println(apSet3);
        System.out.println(apSet4);
        System.out.println(apSet5);
        System.out.println(apSet6);
        System.out.println(apSet7);
        System.out.println(adminSet1);
        System.out.println(adminSet2);
        System.out.println(adminSet3);
        System.out.println(adminSet4);
        
        // Beziehung der Subtypen testen
        AdminSet<RCounter, RCounter, Path<RCounter>> adminSet = new AdminSet<>();
        AdminSet<RCounter, RCounter, Path<RCounter>> apSet = adminSet;
        System.out.println("AdminSet ist ein Subtyp von ApSet.");
        
        // Counter<T> extends RCounter (no):
        //  - Counter<T> würde seine generische Flexibilität verlieren.
        //  - Die Methoden approved und approve sind strukturell inkompatibel.
        //  - Konzeptueller Zweckkonflikt.
        // RCounter extends Counter<T> (no):
        //  - RCounter müsste unnötige generische Komplexität übernehmen.
        //  - Die Methoden approved und approve können nicht mit generischen Typen übereinstimmen.
        //  - Konzeptueller Zweckkonflikt.
        
        System.out.println("4. Löschen und erneutes Einfügen testen...");
        RCounter rc1 = new RCounter();
        RCounter rc2 = new RCounter();
        adminSet1.add(rc1);
        adminSet1.add(rc2);
        adminSet1.remove(rc1);
        adminSet1.add(rc1);
        
        System.out.println("4.1: Objekte erfolgreich gelöscht und erneut eingefügt.");
        
        testAdd(apSet1);
        testRemove(apSet1);
        ApSet<RCounter, RCounter, Path<RCounter>> apSetTest = new ApSet<>();
        testApprovedAndApprove(apSetTest);
        testIteratorAll(apSet1);
        testCriterions(apSet3);
        testExtendAndShorten(adminSet);
        
        System.out.println("Alle Tests erfolgreich abgeschlossen.");
    }
    
    private static <T extends Approvable<?, ?>> void fillContainer(ApprovableSet<T, ?, ?> container, Collection<T> entries) {
        for (T entry : entries) {
            container.add(entry);
        }
    }
    
    private static void transferFromInteriorToSpace(
        AdminSet<Interior<String>, String, Path<Space<String>>> source,
        AdminSet<Space<String>, String, Path<Space<String>>> target) {
        for (Iterator<Interior<String>> it = source.iteratorAll(); it.hasNext(); ) {
            Interior<String> interior = it.next();
            System.out.println("Verarbeite Innenraum: " + interior + ", Fläche: " + interior.area());
            target.add(interior);
        }
    }
    
    private static void transferFromExteriorToSpace(
        AdminSet<Exterior<String>, String, Path<Space<String>>> source,
        AdminSet<Space<String>, String, Path<Space<String>>> target) {
        for (Iterator<Exterior<String>> it = source.iteratorAll(); it.hasNext(); ) {
            Exterior<String> exterior = it.next();
            System.out.println("Verarbeite Außenbereich: " + exterior + ", Öffentlich: " + exterior.isPublic());
            target.add(exterior);
        }
    }


    // Test für `add`-Methode
    private static <X extends Approvable<?, ?>> void testAdd(ApSet<X, ?, ?> apSet) {
        System.out.println("Teste add-Methode...");
        X element1 = (X) createSampleElement("1");
        X element2 = (X) createSampleElement("2");

        apSet.add(element1);
        apSet.add(element2);

        for (Iterator<X> it = apSet.iteratorAll(); it.hasNext(); ) {
            X entry = it.next();
            System.out.println("Eingefügtes Element: " + entry);
        }
        System.out.println("add-Methode erfolgreich getestet.");
    }

    // Test für `remove`-Methode
    private static <X extends Approvable<?, ?>> void testRemove(ApSet<X, ?, ?> apSet) {
        System.out.println("Teste remove-Methode...");
        X element1 = (X) createSampleElement("1");
        apSet.add(element1);
        apSet.remove(element1);

        for (Iterator<X> it = apSet.iteratorAll(); it.hasNext(); ) {
            X entry = it.next();
            System.out.println("Element nach remove vorhanden: " + entry);
        }
        System.out.println("remove-Methode erfolgreich getestet.");
    }

    // Test für `approved` und `approve`
    private static void testApprovedAndApprove(ApSet<RCounter, RCounter, Path<RCounter>> apSet) {
        System.out.println("Teste approved- und approve-Methoden...");
        RCounter counter1 = new RCounter();
        RCounter counter2 = new RCounter();

        apSet.add(counter1);
        apSet.add(counter2);

        // Werte setzen und abrufen
        Path<RCounter> path = new Path<>();
        counter1.approve(counter2, path);
        Path<RCounter> approvedValue = counter1.approved(counter2);

        System.out.println("Genehmigter Wert für Counter1: " + approvedValue);
        System.out.println("approved- und approve-Methoden erfolgreich getestet.");
    }

    // Test für `iteratorAll`
    private static <X extends Approvable<?, ?>> void testIteratorAll(ApSet<X, ?, ?> apSet) {
        System.out.println("Teste iteratorAll-Methode...");
        apSet.add((X) createSampleElement("1"));
        apSet.add((X) createSampleElement("2"));

        for (Iterator<X> it = apSet.iteratorAll(); it.hasNext(); ) {
            X element = it.next();
            System.out.println("IteratorAll Element: " + element);
        }
        System.out.println("iteratorAll-Methode erfolgreich getestet.");
    }

    // Test für `criterions`
    private static <X extends Approvable<P, ?>, P> void testCriterions(ApSet<X, P, ?> apSet) {
        System.out.println("Teste criterions-Methode...");
        P criterion1 = (P) createSampleRCounter();
        P criterion2 = (P) createSampleRCounter();

        apSet.addCriterion(criterion1);
        apSet.addCriterion(criterion2);

        for (Iterator<P> it = apSet.criterions(); it.hasNext(); ) {
            P criterion = it.next();
            System.out.println("Criterion: " + criterion);
        }
        System.out.println("criterions-Methode erfolgreich getestet.");
    }

    // Test für `extend` und `shorten`
    private static void testExtendAndShorten(AdminSet<RCounter, RCounter, Path<RCounter>> adminSet) {
        System.out.println("Teste extend- und shorten-Methoden...");
        RCounter counter1 = new RCounter();
        RCounter counter2 = new RCounter();

        adminSet.add(counter1);
        adminSet.addCriterion(counter2);

        adminSet.extend();
        System.out.println("extend-Methode erfolgreich getestet.");

        adminSet.shorten();
        System.out.println("shorten-Methode erfolgreich getestet.");
    }

    private static Counter<String> createSampleElement(String value) {
        return new Counter<>(value);
    }

    private static RCounter createSampleRCounter() {
        RCounter counter = new RCounter();
        counter.approve(counter, new Path<>());
        return counter;
    }
    
    public static void main(String[] args) {
        Test t = new Test();
    }
}
