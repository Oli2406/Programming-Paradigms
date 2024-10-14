import java.util.Random;

public class Test {

    public Test() {
        Scenario s = new Scenario(ScenarioType.MINIMAL);
        s.run();
    }

    public static void main(String[] args) {
        Test t = new Test();
    }
}
