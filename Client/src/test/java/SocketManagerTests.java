import com.client.client.Main;
import com.client.client.SocketManager;
import org.junit.Assert;
import org.junit.Test;

public class SocketManagerTests {

    @Test
    public void socketManagerShouldCreateConnection(){
        SocketManager socketManager = new SocketManager();
        boolean test = socketManager.startConnection(Main.host, Main.port);
        Assert.assertTrue(test);
        socketManager.stopConnection();
    }

    @Test
    public void socketManagerShouldCloseConnection(){
        SocketManager socketManager = new SocketManager();
        socketManager.startConnection(Main.host, Main.port);

        Assert.assertTrue(socketManager.stopConnection());
    }
}
