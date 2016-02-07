package gangireddyp.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static String TAG = PhotosActivity.class.getName();
    public String CLIENT_ID;
    public String popularEndPointUrl;

    ArrayList<InstragramPhoto> photos;
    public InstagramPhotosAdapter adapterPhotos;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        CLIENT_ID = getString(R.string.client_id);
        popularEndPointUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        photos = new ArrayList<>();
        //Create Adapter and link to source
        adapterPhotos = new InstagramPhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(adapterPhotos);

        //Setup SwipeRefresh container
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photos.clear();
                fetchPopularPhotos();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //Get popular photos
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos() {
        /* URL https://api.instagram.com/v1/tags/nofilter/media/popular?client_id=
         *
         */
        Log.i(TAG, popularEndPointUrl);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(popularEndPointUrl, null, new JsonHttpResponseHandler() {
            // onsuccess
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //JSONObject
                //Log.i(TAG, response.toString());
                addRespongeToModel(response);
                //for (InstragramPhoto photo : photos) {
                //    Log.i(TAG, photo.username + ", " + photo.imageUrl);
                //}
                adapterPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "Failed accessing instagram client.");
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "Failed accessing instagram client.");
                throwable.printStackTrace();
            }
        });
    }

    public void addRespongeToModel(JSONObject response) {
        JSONArray photosJSON = null;
        try {
            photosJSON = response.getJSONArray("data");
            for (int i = 0; i < photosJSON.length(); i++) {
                JSONObject photoJSON = photosJSON.getJSONObject(i);
                InstragramPhoto photo = new InstragramPhoto();

                //Metadata
                photo.username = photoJSON.getJSONObject("user").getString("username");
                photo.userphoto = photoJSON.getJSONObject("user").getString("profile_picture");
                photo.caption = (!photoJSON.isNull("caption")) ? photoJSON.getJSONObject("caption").getString("text") : "";
                photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                if (!photoJSON.isNull("comments") && !photoJSON.getJSONObject("comments").isNull("data")) {
                    JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
                    photo.usernameComments = new ArrayList<>();
                    photo.userComments = new ArrayList<>();
                    for (int j = 0; j < commentsJSON.length(); j++) {
                        JSONObject comment = commentsJSON.getJSONObject(j);
                        photo.usernameComments.add(comment.getJSONObject("from").getString("username"));
                        photo.userComments.add(comment.getString("text"));
                    }
                }
                photo.timestamp = (!photoJSON.isNull("caption")) ? Long.parseLong(photoJSON.getJSONObject("caption").getString("created_time")) : 0;

                //Media
                photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                photo.type = InstragramPhoto.Media.PHOTO;
                if (photoJSON.getString("type").equals("video")) {
                    photo.type = InstragramPhoto.Media.VIDEO;
                    photo.videoUrl = photoJSON.getJSONObject("videos").getJSONObject("standard_resolution").getString("url");
                }

                photos.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
