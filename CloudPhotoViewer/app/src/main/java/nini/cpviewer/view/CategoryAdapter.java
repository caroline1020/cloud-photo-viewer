package nini.cpviewer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nini.cpviewer.R;
import nini.cpviewer.model.CloudPhotoCategory;

/**
 * Created by nini on 15/7/23.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<CloudPhotoCategory> items = new ArrayList<>();
    private CloudPhotoCategory selectedCategory;
    private View.OnClickListener onClickListener;

    public CategoryAdapter(Context context) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_title, viewGroup, false);
        return new CategoryViewHolder(itemView, onClickListener);

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((CategoryViewHolder) viewHolder).bind(items.get(i), selectedCategory == items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<CloudPhotoCategory> categories) {
        items = new ArrayList<>();
        items.addAll(categories);
        notifyDataSetChanged();
    }

    public void setSelectedCategory(CloudPhotoCategory category) {
        this.selectedCategory = category;
        notifyDataSetChanged();
    }

    public void setSelectedCategory(int category) {
        this.selectedCategory = items.get(category);
        notifyDataSetChanged();
    }

}
