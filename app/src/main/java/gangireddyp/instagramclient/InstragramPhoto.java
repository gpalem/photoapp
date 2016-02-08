package gangireddyp.instagramclient;

import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;

import java.util.ArrayList;

/**
 * Created by gpalem on 2/4/16.
 */
public class InstragramPhoto {
    private static String TAG = InstragramPhoto.class.getName();
    protected static String instagramColor = "<font color=\"#125688\">";

    public enum Media {PHOTO, VIDEO};
    public Media type;

    public String username;
    public String userphoto;
    public String caption;
    public String imageUrl;
    public String videoUrl;
    public int imageHeight;
    public int likesCount;
    public ArrayList<CommentModel> commentModel;
    public long timestamp;

    public Spanned getFormattedUserString() {
        String formatted = instagramColor + "<b>" + username + "</b></font>";
        return Html.fromHtml(formatted);
    }

    public Spanned getFormattedCaptionString() {
        String formatted = "";

        formatted += instagramColor;
        formatted += "<b>" + username + "</b></font>  "; //username
        formatted += caption; //caption

        return Html.fromHtml(formatted);
    }

    public Spanned getFormattedLikesString() {
        String formatted = instagramColor + "<b>" + String.valueOf(this.likesCount) + " likes</b></font>";
        return Html.fromHtml(formatted);
    }

    public Spanned getFormattedCommentString(int index) {
        String formatted = "";
        if (index < commentModel.size()) {
            formatted += instagramColor;
            formatted += "<b>" + commentModel.get(index).username + "</b></font>  "; //username
            formatted += commentModel.get(index).comment; //comment
        }
        return Html.fromHtml(formatted);
    }

    public String getAbbreviatedTimeSpan() {
        long elapsed = Math.max(System.currentTimeMillis() - timestamp * 1000, 0);
        String timeSpan = "";
        if (elapsed >= DateUtils.YEAR_IN_MILLIS) {
            timeSpan = String.valueOf(elapsed / DateUtils.YEAR_IN_MILLIS) + "y";
        }
        else if (elapsed >= DateUtils.WEEK_IN_MILLIS) {
            timeSpan = String.valueOf(elapsed / DateUtils.WEEK_IN_MILLIS) + "w";
        }
        else if (elapsed >= DateUtils.DAY_IN_MILLIS) {
            timeSpan = String.valueOf(elapsed / DateUtils.DAY_IN_MILLIS) + "d";
        }
        else if (elapsed >= DateUtils.HOUR_IN_MILLIS) {
            timeSpan = String.valueOf(elapsed / DateUtils.HOUR_IN_MILLIS) + "h";
        }
        else if (elapsed >= DateUtils.MINUTE_IN_MILLIS) {
            timeSpan = String.valueOf(elapsed / DateUtils.MINUTE_IN_MILLIS) + "m";
        }
        else {
            timeSpan = String.valueOf(elapsed / DateUtils.SECOND_IN_MILLIS) + "s";
        }
        //Log.i(TAG, timeSpan + " " + DateUtils.getRelativeTimeSpanString(timestamp*1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS) + " " + System.currentTimeMillis() + " " + timestamp + " " + elapsed);
        return timeSpan;
    }
}
