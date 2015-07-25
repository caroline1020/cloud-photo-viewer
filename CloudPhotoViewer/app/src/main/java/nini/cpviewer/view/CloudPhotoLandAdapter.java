package nini.cpviewer.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nini.cpviewer.model.CloudItemForView;
import nini.cpviewer.model.CloudPhotoCategory;
import nini.cpviewer.model.CloudPhotoItem;

/**
 * Created by nini on 15/7/23.
 */
public class CloudPhotoLandAdapter extends CloudPhotoAdapter{

    public CloudPhotoLandAdapter(Context context) {
        super(context);
    }

    @Override
    public void resetData(List<CloudPhotoCategory> categories) {
        items = new ArrayList<>();
        titleIds = new ArrayList<>();
        for (CloudPhotoCategory category : categories) {
            for (CloudPhotoItem cloudPhotoItem : category.getItems()) {
                items.add(new CloudItemForView(cloudPhotoItem, category.getCategory()));
            }
        }
        notifyDataSetChanged();
    }
}
