package gangireddyp.instagramclient;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by gpalem on 2/7/16.
 */
public class GlobalData extends Application {
    private ArrayList<InstragramPhoto> mediaData;

    public void setMediaData(ArrayList<InstragramPhoto> mediaData) {
        this.mediaData = mediaData;
    }

    public ArrayList<InstragramPhoto> getMediaData() {
        return mediaData;
    }
}
