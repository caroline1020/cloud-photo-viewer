package nini.cpviewer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nini.cpviewer.R;
import nini.cpviewer.model.CloudItemForView;
import nini.cpviewer.model.CloudPhotoCategory;
import nini.cpviewer.model.CloudPhotoItem;

/**
 * Created by nini on 15/7/23.
 */
public class CloudPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_TITLE_AND_PHOTO = 2;
    private static final int VIEW_TYPE_PHOTO = 1;
    protected List<CloudItemForView> items = new ArrayList<>();
    protected ArrayList<Integer> titleIds;

    public CloudPhotoAdapter(Context context) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_TITLE_AND_PHOTO) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cloud_photo_title, viewGroup, false);
            return new CloudPhotoItemTitleViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cloud_photo, viewGroup, false);
            return new CloudPhotoItemViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showTitle(position)) {
            return VIEW_TYPE_TITLE_AND_PHOTO;
        } else {
            return VIEW_TYPE_PHOTO;
        }

    }

    protected boolean showTitle(int position) {
        return titleIds.contains(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CloudItemForView itemForView = items.get(i);
        if (viewHolder instanceof CloudPhotoItemTitleViewHolder) {
            ((CloudPhotoItemTitleViewHolder) viewHolder).bind(itemForView);
        } else {
            ((CloudPhotoItemViewHolder) viewHolder).bind(itemForView);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void resetData(List<CloudPhotoCategory> categories) {
        items = new ArrayList<>();
        titleIds = new ArrayList<>();
        titleIds.add(0);
        for (CloudPhotoCategory category : categories) {
            titleIds.add(category.getItems().size() + titleIds.get(titleIds.size() - 1));
            for (CloudPhotoItem cloudPhotoItem : category.getItems()) {
                items.add(new CloudItemForView(cloudPhotoItem, category.getCategory()));
            }
        }
        notifyDataSetChanged();
    }
}
