package nini.cpviewer.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nini.cpviewer.R;
import nini.cpviewer.model.CloudItemForView;
import nini.cpviewer.widget.RoundedTransformation;
import nini.cpviewer.widget.UI;

/**
 * Created by nini on 15/7/23.
 */
public class CloudPhotoItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView networkImageView;
    private final TextView titleTextView;
    private final TextView likedCountTextView;
    private RoundedTransformation transformation;

    protected CloudPhotoItemViewHolder(View itemView) {
        super(itemView);

        transformation = new RoundedTransformation(UI.toPixel(itemView.getContext(), 10), 0);
        networkImageView = (ImageView) itemView.findViewById(R.id.photoImageView);

        titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        likedCountTextView = (TextView) itemView.findViewById(R.id.likedCountTextView);
    }

    public void bind(CloudItemForView itemForView) {
        Picasso.with(itemView.getContext()).load(itemForView.getItem().getUrl()).fit()
        .placeholder(R.drawable.icon_progressing)
                .error(R.drawable.icon_error).centerCrop().transform(transformation).into(networkImageView);
        titleTextView.setText(itemForView.getItem().getTitle());
        likedCountTextView.setText(String.valueOf(itemForView.getItem().getLiked_count()));
    }


}
