package gangireddyp.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gpalem on 2/7/16.
 */
public class CommentsAdapter extends ArrayAdapter<CommentModel> {
    private static String TAG = InstagramPhotosAdapter.class.getName();

    public CommentsAdapter(Context context, List<CommentModel> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get data
        final CommentModel comment = getItem(position);
        //Check recycled
        if (convertView == null) {
            //Create view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }

        TextView tvCommentUser = (TextView) convertView.findViewById(R.id.tvCommentUser);
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvCommentTimeSpan = (TextView) convertView.findViewById(R.id.tvCommentTimeSpan);
        ImageView ivCommentUserPhoto = (ImageView) convertView.findViewById(R.id.ivCommentUserPhoto);

        tvCommentUser.setText(comment.getFormattedUserString());
        tvComment.setText(comment.getFormattedCommentString());
        tvCommentTimeSpan.setText(comment.getAbbreviatedTimeSpan());
        ivCommentUserPhoto.setImageResource(0);
        Picasso.with(getContext()).load(comment.imageUrl).placeholder(R.drawable.dp_launcher).into(ivCommentUserPhoto);

        return convertView;
    }
}
