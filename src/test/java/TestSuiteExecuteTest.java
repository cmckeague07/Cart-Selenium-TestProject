import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.Test;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginPageTest.class,
        CartTest.class,
        PerformanceTest.class
})
public class TestSuiteExecuteTest {
    // This class remains empty; it is used only as a holder for the above annotations
}