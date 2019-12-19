import atomics.client.FanClient;
import atomics.client.ReporterClient;
import atomics.server.MatchMachine;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest
        extends TestCase
{
    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        String[] args0 = new String[]{"0", "127.0.0.1","5000", "127.0.0.1", "5001", "127.0.0.1", "5002"};
        MatchMachine.main(args0);

        String[] args1 = new String[]{"1", "127.0.0.1","5000", "127.0.0.1", "5001", "127.0.0.1", "5002"};
        MatchMachine.main(args1);

        String[] args2 = new String[]{"2", "127.0.0.1","5000", "127.0.0.1", "5001", "127.0.0.1", "5002"};
        MatchMachine.main(args2);

        String[] argsR = new String[]{"127.0.0.1","5000", "127.0.0.1", "5001", "127.0.0.1", "5002"};
        String[] argsClientes = new String[]{"isUnitTest"};
        ReporterClient.main(argsR, argsClientes);

        String[] argsF = new String[]{"127.0.0.1","5000", "127.0.0.1", "5001", "127.0.0.1", "5002"};
        FanClient.main(argsF, argsClientes);
    }
}
