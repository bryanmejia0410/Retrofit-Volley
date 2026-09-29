package ue.edu.co.volleyapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String URL_POSTS = "https://jsonplaceholder.typicode.com/posts";

    private RequestQueue requestQueue;
    private ListView listViewPosts;
    private ProgressBar progressBar;
    private TextView tvStatus;

    private List<Post> posts = new ArrayList<>();
    private PostAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPosts = findViewById(R.id.lvTodos);
        progressBar = findViewById(R.id.progressBar);
        tvStatus = findViewById(R.id.tvTitle);

        adapter = new PostAdapter(this, posts);
        listViewPosts.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        consumeApi();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void consumeApi() {
        showLoading(true);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL_POSTS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        posts.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonPost = response.optJSONObject(i);
                            if (jsonPost != null) {
                                posts.add(Post.fromJson(jsonPost));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        showLoading(false);
                        tvStatus.setText(getString(R.string.registros_recibidos, posts.size()));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showVolleyError(error);
                    }
                });

        request.setTag(TAG);
        requestQueue.add(request);
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? android.view.View.VISIBLE : android.view.View.GONE);
        listViewPosts.setVisibility(show ? android.view.View.GONE : android.view.View.VISIBLE);
    }

    private void showVolleyError(VolleyError error) {
        showLoading(false);
        Log.e(TAG, "Error al consumir la API", error);
        if (error != null && error.getMessage() != null) {
            tvStatus.setText(getString(R.string.error_volley, error.getMessage()));
        } else {
            tvStatus.setText(getString(R.string.error_generico, "No se pudo obtener la respuesta"));
        }
    }
}