import java.util.Arrays;
import java.util.Collection;

public class Test {

    public Test() {
        // Create objects of the specified types
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

        // Fill the containers with some entries
        fillContainer(apSet1, Arrays.asList(new Counter<>("A"), new Counter<>("B")));
        fillContainer(apSet2, Arrays.asList(new Counter<>(1), new Counter<>(2)));
        fillContainer(apSet3, Arrays.asList(new Counter<>(new Path<>()), new Counter<>(new Path<>())));
        fillContainer(apSet4, Arrays.asList(new RCounter(), new RCounter()));
        fillContainer(apSet5, Arrays.asList(new Space<>("Space1"), new Space<>("Space2")));
        fillContainer(apSet6, Arrays.asList(new Interior<>("Interior1"), new Interior<>("Interior2")));
        fillContainer(apSet7, Arrays.asList(new Exterior<>("Exterior1"), new Exterior<>("Exterior2")));
        fillContainer(adminSet1, Arrays.asList(new RCounter(), new RCounter()));
        fillContainer(adminSet2, Arrays.asList(new Space<>("Space1"), new Space<>("Space2")));
        fillContainer(adminSet3, Arrays.asList(new Interior<>("Interior1"), new Interior<>("Interior2")));
        fillContainer(adminSet4, Arrays.asList(new Exterior<>("Exterior1"), new Exterior<>("Exterior2")));

        // Print the containers
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
    }

    private static <T> void fillContainer(ApprovableSet<T, ?, ?> container, Collection<T> entries) {
        for (T entry : entries) {
            container.add(entry);
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
    }
}
