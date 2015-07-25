package nini.cpviewer.model;

/**
 * Created by nini on 15/7/22.
 */
public class CloudPhotoItem {
    public long liked_count;
    public String title;
    public String url;

    public CloudPhotoItem() {
    }

    public long getLiked_count() {
        return liked_count;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
