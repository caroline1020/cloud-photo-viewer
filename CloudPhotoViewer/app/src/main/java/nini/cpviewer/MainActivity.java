package nini.cpviewer;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import nini.cpviewer.model.CloudPhotoCategory;
import nini.cpviewer.view.CategoryAdapter;
import nini.cpviewer.view.CloudPhotoAdapter;
import nini.cpviewer.view.CloudPhotoLandAdapter;
import nini.cpviewer.widget.SpacesItemDecoration;
import nini.cpviewer.widget.UI;


public class MainActivity extends Activity {

    private RecyclerView photoRecyclerView;
    private CategoryAdapter categoryAdapter;
    private CloudPhotoAdapter photoAdapter;
    private TextView stateView;

    private List<CloudPhotoCategory> categories;
    private boolean autoScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateView = (TextView) findViewById(R.id.stateView);
        if (isOrientationPortrait()) {
            initPortraitView();
        } else {
            initLandscapeView();
        }

        HttpClient.getInstance(this).loadData(responseListener, errorListener);
    }

    private boolean isOrientationPortrait() {
        return MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void initLandscapeView() {

        RecyclerView categoryRecyclerView = (RecyclerView) findViewById(R.id.categoryTitleRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(this);
        categoryRecyclerView.setAdapter(categoryAdapter);


        photoRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        photoRecyclerView.addItemDecoration(new SpacesItemDecoration(UI.toPixel(this, 5)));
        photoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                int selectedCategoryIndex = getSelectedCategoryIndex(visibleItemPosition);
                if (!autoScroll) {
                    categoryAdapter.setSelectedCategory(selectedCategoryIndex);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    autoScroll = false;
                }
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CloudPhotoCategory tag = (CloudPhotoCategory) view.getTag();
                autoScroll = true;
                photoRecyclerView.smoothScrollToPosition(getStartIndex(tag));
                categoryAdapter.setSelectedCategory(tag);
            }
        };
        categoryAdapter.setOnClickListener(onClickListener);
    }

    private int getStartIndex(CloudPhotoCategory category) {
        if (categories == null || categories.isEmpty()) {
            return 0;
        }
        int startIndex = 0;
        for (CloudPhotoCategory cloudPhotoCategory : categories) {
            if (cloudPhotoCategory.equals(category)) {
                return startIndex;
            }
            startIndex += cloudPhotoCategory.getItems().size();
        }
        return startIndex;

    }

    private int getSelectedCategoryIndex(int firstVisiblePosition) {
        for (int i = 0; i < categories.size(); i++) {
            firstVisiblePosition -= categories.get(i).getItems().size();
            if (firstVisiblePosition < 0) {
                return i;
            }
        }
        return 0;
    }

    private void updateData(final List<CloudPhotoCategory> categories) {
        this.categories = categories;
        stateView.setVisibility(View.GONE);
        if (isOrientationPortrait()) {
            photoAdapter.resetData(categories);
        } else {
            categoryAdapter.setData(categories);
            categoryAdapter.setSelectedCategory(categories.get(0));
            CloudPhotoLandAdapter photoLandAdapter = new CloudPhotoLandAdapter(this);
            photoRecyclerView.setAdapter(photoLandAdapter);
            photoLandAdapter.resetData(categories);
            photoRecyclerView.scrollToPosition(0);

        }
    }

    private void initPortraitView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(UI.toPixel(this, 5)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoAdapter = new CloudPhotoAdapter(this);
        recyclerView.setAdapter(photoAdapter);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onStop() {
        super.onStop();
        HttpClient.getInstance(this).stop();
    }

    private void updateStateTextView(String string) {
        stateView.setText(string);
        stateView.setTextColor(0xfff69c4c);
    }

    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if (response == null || response.isEmpty()) {
                updateStateTextView(getString(R.string.no_results));
                return;
            }
            List<CloudPhotoCategory> categories = CloudPhotoCategory.parseResult(response);
            if (categories == null || categories.isEmpty()) {
                updateStateTextView(getString(R.string.no_results));
                return;
            }
            updateData(categories);

        }


    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            updateStateTextView(String.format(getString(R.string.error_reason), error.toString()));
        }
    };

}
