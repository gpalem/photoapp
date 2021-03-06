package gangireddyp.instagramclient;

import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;

/**
 * Created by gpalem on 2/7/16.
 */
public class CommentModel {
    private static String TAG = InstragramPhoto.class.getName();
    private static String instagramColor = "<font color=\"#125688\">";

    public String username;
    public String comment;
    public String imageUrl;
    public long timestamp;

    public Spanned getFormattedUserString() {
        String formatted = instagramColor + "<b>" + username + "</b></font>";
        return Html.fromHtml(formatted);
    }

    public Spanned getFormattedCommentString() {
        String formatted = "<font color=\"black\">" + comment + "</font>";
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
        return timeSpan;
    }
}
