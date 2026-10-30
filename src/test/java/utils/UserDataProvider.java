package utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDataProvider {

    @DataProvider(name = "users")
    public static Object[][] users() {
        JsonNode users = JsonDataReader.getTestData().get("users");
        List<Object[]> rows = new ArrayList<>();
        Iterator<JsonNode> it = users.elements();
        while (it.hasNext()) {
            JsonNode u = it.next();
            rows.add(new Object[]{ u.get("name").asText(), u.get("job").asText() });
        }
        return rows.toArray(new Object[0][]);
    }
}