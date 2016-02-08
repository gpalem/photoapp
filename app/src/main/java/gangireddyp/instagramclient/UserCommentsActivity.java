package gangireddyp.instagramclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class UserCommentsActivity extends AppCompatActivity {

    public ArrayList<CommentModel> comments;
    public CommentsAdapter adapterComments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comments);

        final GlobalData appData = (GlobalData) getApplicationContext();
        int itemPosition = getIntent().getIntExtra("position", 0);
        comments = appData.getMediaData().get(itemPosition).commentModel;
        //Set adapter view
        adapterComments = new CommentsAdapter(this, comments);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(adapterComments);
    }
}
