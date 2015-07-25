package nini.cpviewer;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nini on 15/7/25.
 */
public class HttpClient {

    private HttpClient(Context context) {
        requestQueue = Volley.newRequestQueue(context);

    }

    private static HttpClient instance;
    private static RequestQueue requestQueue;
    private String TAG = "POST_LIST";

    public static HttpClient getInstance(Context context) {
        if (instance == null) {
            instance = new HttpClient(context);
        }
        return instance;
    }

    public void loadData(final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
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

    public void stop() {
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}
