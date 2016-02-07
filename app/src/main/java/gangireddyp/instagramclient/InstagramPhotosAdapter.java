package gangireddyp.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gpalem on 2/4/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstragramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstragramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get data
        InstragramPhoto photo = getItem(position);
        //Check recycled
        if (convertView == null) {
            //Create view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        //Get reference
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvUser);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
        RoundedImageView ivUserPhoto = (RoundedImageView) convertView.findViewById(R.id.ivUserPhoto);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        //Set content
        tvUser.setText(photo.getFormattedUserString());
        tvCaption.setText(photo.getFormattedCaptionString());
        tvLikes.setText(photo.getFormattedLikesString());
        tvTimeStamp.setText(photo.getAbbreviatedTimeSpan());
        tvComments.setText(photo.getFormattedCommentString(photo.usernameComments.size() - 1));
        //Insert the image using picasso
        ivUserPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.userphoto).placeholder(R.drawable.dp_launcher).into(ivUserPhoto);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.ic_launcher).into(ivPhoto);


        return convertView;
    }
}
