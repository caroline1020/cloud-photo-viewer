package nini.cpviewer.view;

import android.view.View;
import android.widget.TextView;

import nini.cpviewer.R;
import nini.cpviewer.model.CloudItemForView;

/**
 * Created by nini on 15/7/23.
 */
public class CloudPhotoItemTitleViewHolder extends CloudPhotoItemViewHolder {


    private final TextView categoryTitle;

    public CloudPhotoItemTitleViewHolder(View itemView) {
        super(itemView);
        categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);
    }

    public void bind(CloudItemForView itemForView) {
        super.bind(itemForView);
        categoryTitle.setText(itemForView.getTitle());
    }


}
