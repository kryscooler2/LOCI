package lociteam.com.Model;

import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Station implements ResponseToRequest {

    private List<Long> ids;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private List<String> neighbors;

    public Station(){}

    public Station(Parcel in){
        this.ids = in.readArrayList(List.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.neighbors = in.readArrayList(List.class.getClassLoader());
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<String> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public void initialiseWithJson(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString(NAME);
        String description = jsonObject.getString(DESCRIPTION);
        Double latitude = jsonObject.getDouble(LATITUDE);
        Double longitude = jsonObject.getDouble(LONGITUDE);
        //JSONArray test = jsonObject.getJSONArray(IDS);
        List<String> neighbors = new ArrayList<>();
        JSONArray test = new JSONArray();
        for (int i = 0; i < test.length(); i++) { // Walk through the Array.
            JSONObject obj = test.getJSONObject(i);
            JSONArray array = obj.getJSONArray(NEIGHBORS);
            neighbors.add(array.toString());
        }
        List<Long> ids = new ArrayList<>();
        JSONArray test2 = new JSONArray();
        for (int j = 0; j < test.length(); j++) { // Walk through the Array.
            JSONObject obj2 = test2.getJSONObject(j);
            JSONArray array2 = obj2.getJSONArray(IDS);
            ids.add(array2.getLong(j));
        }

        this.ids = ids;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighbors = neighbors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}