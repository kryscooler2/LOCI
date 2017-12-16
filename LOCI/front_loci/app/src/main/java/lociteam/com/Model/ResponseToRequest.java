package lociteam.com.Model;


import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface ResponseToRequest extends Parcelable {
    String IDS = "ids";
    String NAME = "name";
    String DESCRIPTION = "description";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";
    String NEIGHBORS = "neighbors";
    String SUBWAYS = "subways";

    void initialiseWithJson(JSONObject jsonObject) throws JSONException;

    public String getName();

    public String getDescription();

    public Double getLatitude();

    public Double getLongitude();

    public List<String> getNeighbors();

    public List<String> getSubways();
}