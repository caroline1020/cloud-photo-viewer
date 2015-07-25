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

    String category;
    List<CloudPhotoItem> items;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CloudPhotoItem> getItems() {
        return items;
    }

    public void setItems(List<CloudPhotoItem> items) {
        this.items = items;
    }

    public CloudPhotoCategory() {
    }

    public static List<CloudPhotoCategory> parseResult(String s) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JSONObject reader = new JSONObject(s);
            String array = reader.getString("result");
            if (array == null || array.isEmpty()) {
                return null;
            }
            List<CloudPhotoCategory> result = mapper.readValue(
                    array,
                    mapper.getTypeFactory().constructCollectionType(
                            List.class, CloudPhotoCategory.class));

            return result;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
