package nini.cpviewer.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by nini on 15/7/22.
 */
public class CloudPhotoCategory {
    private static ObjectMapper mapper = new ObjectMapper();
    String category;
    List<CloudPhotoItem> items;

    public CloudPhotoCategory() {
    }

    public String getCategory() {
        return category;
    }

    public List<CloudPhotoItem> getItems() {
        return items;
    }

    public static List<CloudPhotoCategory> parseResult(String s) {
        try {
            JSONObject reader = new JSONObject(s);
            String array = reader.getString("result");
            if (array == null || array.isEmpty()) {
                return null;
            }
            return mapper.readValue(
                    array,
                    mapper.getTypeFactory().constructCollectionType(
                            List.class, CloudPhotoCategory.class));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
