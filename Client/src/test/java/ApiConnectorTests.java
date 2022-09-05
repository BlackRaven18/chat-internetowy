import com.client.client.ApiConnector;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ApiConnectorTests {

    @Test
    public void parseJSONObjectShouldReturnList(){
        ApiConnector connector = new ApiConnector();
        String responseBody = "{\n" +
                "  \"name\" : \"Jan\",\n" +
                "  \"surname\" : \"Kowalski\",\n" +
                "  \"login\" : \"1\",\n" +
                "  \"password\" : \"1\",\n" +
                "  \"id\" : 1" +
                "}";

        List<String> testList = connector.parseJSONObject(responseBody);
        Assert.assertFalse(testList.isEmpty());
    }

    @Test
    public void parseJSONArrayShouldReturnNotEmptyList(){
        ApiConnector connector = new ApiConnector();
        String responseBody = "[{\"password\":\"1\",\"surname\":\"Kowalski\",\"name\"" +
                ":\"Jan\",\"id\":1,\"login\":\"1\"},{\"password\":\"2\",\"surname\"" +
                ":\"Wolski\",\"name\":\"Arek\",\"id\":2,\"login\":\"2\"},{\"password\"" +
                ":\"3\",\"surname\":\"Nowak\",\"name\":\"Marek\",\"id\":3,\"login\":\"3\"}]";

        List<List<String>> testList = connector.parseJSONArray(responseBody);
        Assert.assertFalse(testList.isEmpty());
    }


}
