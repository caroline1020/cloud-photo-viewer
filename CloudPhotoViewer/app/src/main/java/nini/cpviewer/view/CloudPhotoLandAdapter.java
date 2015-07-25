package nini.cpviewer.view;

import android.content.Context;

/**
 * Created by nini on 15/7/23.
 */
public class CloudPhotoLandAdapter extends CloudPhotoAdapter {

    public CloudPhotoLandAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_PHOTO;
    }
}
