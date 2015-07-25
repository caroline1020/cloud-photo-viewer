package nini.cpviewer.model;

/**
 * Created by nini on 15/7/23.
 */
public class CloudItemForView {
    CloudPhotoItem item;
    String title;

    public CloudItemForView(CloudPhotoItem item, String title) {
        this.item = item;
        this.title = title;
    }

    public CloudPhotoItem getItem() {
        return item;
    }

    public String getTitle() {
        return title;
    }
}
