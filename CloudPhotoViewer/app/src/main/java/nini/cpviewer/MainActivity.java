package nini.cpviewer;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nini.cpviewer.model.CloudPhotoCategory;
import nini.cpviewer.view.CategoryAdapter;
import nini.cpviewer.view.CloudPhotoAdapter;
import nini.cpviewer.view.CloudPhotoLandAdapter;
import nini.cpviewer.widget.SpacesItemDecoration;
import nini.cpviewer.widget.Utils;


public class MainActivity extends Activity {
    private RequestQueue requestQueue;


    private CategoryAdapter categoryAdapter;
    private RecyclerView photoRecyclerView;

    private CloudPhotoAdapter photoAdapter;
    private TextView stateView;
    private String TAG = "POST_LIST";

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

        requestQueue = Volley.newRequestQueue(this);
        loadData(responseListener, errorListener);
    }

    private boolean isOrientationPortrait() {
        return MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void initLandscapeView() {
        photoRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        photoRecyclerView.addItemDecoration(new SpacesItemDecoration(Utils.toPixel(this, 5)));
        photoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView categoryTitleRecyclerView = (RecyclerView) findViewById(R.id.categoryTitleRecyclerView);
        categoryTitleRecyclerView.setLayoutManager(new LinearLayoutManager(this)
        );
        categoryAdapter = new CategoryAdapter(this);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CloudPhotoCategory tag = (CloudPhotoCategory) view.getTag();
                if (tag != null) {
                    if (photoRecyclerView.getChildCount() > 0) {
                        photoRecyclerView.smoothScrollToPosition(0);
                    }
                    if (tag != categoryAdapter.getSelectedCategory()) {
                        CloudPhotoLandAdapter adapter = new CloudPhotoLandAdapter(MainActivity.this);
                        photoRecyclerView.setAdapter(adapter);
                        categoryAdapter.setSelectedCategory(tag);
                        adapter.resetData(Arrays.asList(tag));
                    }
                }
            }
        };

        categoryAdapter.setOnClickListener(onClickListener);
        categoryTitleRecyclerView.setAdapter(categoryAdapter);
    }

    private void updateData(List<CloudPhotoCategory> categories) {
        stateView.setVisibility(View.GONE);
        if (isOrientationPortrait()) {
            photoAdapter.resetData(categories);
        } else {
            photoRecyclerView.removeAllViews();
            categoryAdapter.setData(categories);
            categoryAdapter.setSelectedCategory(categories.get(0));
            CloudPhotoLandAdapter photoLandAdapter = new CloudPhotoLandAdapter(this);
            photoRecyclerView.setAdapter(photoLandAdapter);
            photoLandAdapter.resetData(Collections.singletonList(categories.get(0)));
            photoRecyclerView.scrollToPosition(0);
        }
    }

    private void initPortraitView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(Utils.toPixel(this, 5)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoAdapter = new CloudPhotoAdapter(this);
        recyclerView.setAdapter(photoAdapter);

    }

    private void loadData(final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        String url = "https://api.parse.com/1/functions/list";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                responseListener, errorListener) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-Parse-Application-Id", "8LOsCIV8EiNzIJH1lk4AofgJ2jM0gR8Eq8IKKR7K");
                headers.put("X-Parse-REST-API-Key", "KUZpQytsRq7vD884VxNnfBy3KssDKs1g5wCZ7711");
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        request.setTag(TAG);
        requestQueue.add(request);
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
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
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
