package nini.cpviewer.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nini.cpviewer.R;
import nini.cpviewer.model.CloudPhotoCategory;

/**
 * Created by nini on 15/7/24.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;

    public CategoryViewHolder(View itemView, View.OnClickListener onClickListener) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.categoryTitle);
        itemView.setOnClickListener(onClickListener);
        itemView.setClickable(true);

    }

    public void bind(CloudPhotoCategory category, boolean selected) {
        itemView.setTag(category);
        title.setText(category.getCategory());
        itemView.setSelected(selected);
        itemView.setBackgroundResource(selected ?
                R.drawable.bg_item_category_title_selected : R.drawable.bg_item_category_title_normal);
        title.setTextColor(selected ?
                itemView.getResources().getColor(R.color.default_dark_text) :
                itemView.getResources().getColor(R.color.default_light_text));
    }
}
