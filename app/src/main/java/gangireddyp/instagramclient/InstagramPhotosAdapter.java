package gangireddyp.instagramclient;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gpalem on 2/4/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstragramPhoto> {
    private static String TAG = InstagramPhotosAdapter.class.getName();

    public InstagramPhotosAdapter(Context context, List<InstragramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Get data
        final InstragramPhoto photo = getItem(position);
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
        TextView tvMoreComments = (TextView) convertView.findViewById(R.id.tvMoreComments);
        final RoundedImageView ibVideo = (RoundedImageView) convertView.findViewById(R.id.ibVideo);
        RoundedImageView ivUserPhoto = (RoundedImageView) convertView.findViewById(R.id.ivUserPhoto);
        final ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        final VideoView vvVideo = (VideoView) convertView.findViewById(R.id.vvVideo);

        //Set media view
        vvVideo.setVisibility(VideoView.INVISIBLE);
        ivPhoto.setVisibility(ImageView.VISIBLE);
        ibVideo.setVisibility(ImageButton.INVISIBLE);
        if (photo.type == InstragramPhoto.Media.VIDEO) {
            ibVideo.setVisibility(ImageButton.VISIBLE);
            //Set Video view
            vvVideo.setMediaController(null);
            vvVideo.setVideoPath(photo.videoUrl);
            vvVideo.requestFocus();
            vvVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Hi");
                    if (vvVideo.isPlaying()) {
                        vvVideo.pause();
                    } else {
                        vvVideo.start();
                    }
                }
            });
            vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    vvVideo.stopPlayback();
                    ivPhoto.setVisibility(ImageView.VISIBLE);
                    vvVideo.setVisibility(VideoView.INVISIBLE);
                    ibVideo.setVisibility(ImageButton.VISIBLE);
                }
            });
            ibVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivPhoto.setVisibility(ImageView.INVISIBLE);
                    vvVideo.setVisibility(VideoView.VISIBLE);
                    ibVideo.setVisibility(ImageButton.INVISIBLE);
                    vvVideo.start();
                }
            });
        }

        //Set Metadata View
        tvUser.setText(photo.getFormattedUserString());
        tvCaption.setText(photo.getFormattedCaptionString());
        tvLikes.setText(photo.getFormattedLikesString());
        tvTimeStamp.setText(photo.getAbbreviatedTimeSpan());
        if (photo.commentModel == null || photo.commentModel.size() == 0) {
            tvComments.setText("");
            tvMoreComments.setText("");
            tvComments.setVisibility(TextView.GONE);
            tvMoreComments.setVisibility(TextView.GONE);
        }
        else {
            tvComments.setText(photo.getFormattedCommentString(photo.commentModel.size() - 1));
            if (photo.commentModel.size() == 1) {
                tvMoreComments.setText("");
                tvMoreComments.setVisibility(TextView.GONE);
            }
            else if (photo.commentModel.size() == 2) {
                tvMoreComments.setText(photo.getFormattedCommentString(0));
            }
            else {
                tvMoreComments.setText(Html.fromHtml("<font color=\"LightGray\">View all " + String.valueOf(photo.commentModel.size()) + " comments</font>"));
            }
        }
        tvMoreComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PhotosActivity) getContext()).fireCommentsIntent(position);
            }
        });

        //Set Image views using picasso
        ivUserPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.userphoto).placeholder(R.drawable.dp_launcher).into(ivUserPhoto);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.ic_launcher).into(ivPhoto);


        return convertView;
    }
}
