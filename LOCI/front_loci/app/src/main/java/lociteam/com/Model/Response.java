package lociteam.com.Model;


import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public interface Response extends Parcelable {
    String IDS = "ids";
    String NAME = "name";
    String DESCRIPTION = "description";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";
    String NEIGHBORS = "neighbors";

    void initialiseWithJson(JSONObject jsonObject) throws JSONException;

}