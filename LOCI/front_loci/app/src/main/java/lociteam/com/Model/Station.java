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
    private List<String> subways;

    public Station(){}

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
        List<String> neighbors = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(NEIGHBORS);
        for (int i = 0; i < jsonArray.length(); i++) { // Walk through the Array
            neighbors.add(jsonArray.get(i).toString());
        }

        List<String> subways = new ArrayList<>();
        JSONArray jsonArray2 = jsonObject.getJSONArray(SUBWAYS);
        for (int i = 0; i < jsonArray2.length(); i++) { // Walk through the Array
            subways.add(jsonArray2.get(i).toString());
        }

        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighbors = neighbors;
        this.subways = subways;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public List<String> getSubways() {
        return subways;
    }

    public void setSubways(List<String> subways) {
        this.subways = subways;
    }
}