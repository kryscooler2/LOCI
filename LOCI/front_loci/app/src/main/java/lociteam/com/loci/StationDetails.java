package lociteam.com.loci;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lociteam.com.Factory.ResponseFactory;
import lociteam.com.Model.ResponseToRequest;

/**
 * Created by Romain on 10/12/2017.
 */

public class StationDetails extends AppCompatActivity {

    private final String URL_BASE = "http://172.16.232.91:8081/subway/stations";
    private final String RESPONSE_EXTRA = "RESPONSE";
    private List<String> stationNeighbors = new ArrayList<>();
    private List<String> stationSubways = new ArrayList<>();
    private String stationDescription="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        try {
            informationsCreation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) findViewById(R.id.textViewDescription);
        textView.setText(stationDescription);
        ListView listViewSubways = (ListView) findViewById(R.id.station_subways);
        ArrayAdapter<String> adapterSubways = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stationSubways);
        listViewSubways.setAdapter(adapterSubways);
        ListView listViewNeighbors = (ListView) findViewById(R.id.station_neighbors);
        ArrayAdapter<String> adapterNeighbors = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stationNeighbors);
        listViewNeighbors.setAdapter(adapterNeighbors);

        listViewNeighbors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String elem = adapterView.getAdapter().getItem(i).toString();
                String url = URL_BASE + "/" + elem;
                Toast elemToast = Toast.makeText(getApplicationContext(),elem,Toast.LENGTH_LONG);
                elemToast.show();
                doQuery(url);
            }
        });

        Button btnBack = (Button) findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    private void informationsCreation() throws JSONException {
        Intent intent = getIntent();
        String responseString = intent.getStringExtra(RESPONSE_EXTRA);

        final ResponseToRequest response = getResponse(responseString);
        List<String> responseNeighbors = getResponseNeighbors(response);
        List<String> responseSubways = getResponseSubways(response);
        stationNeighbors.addAll(responseNeighbors);
        stationDescription = getResponseDescription(response);
        stationSubways.addAll(responseSubways);
    }

    private ResponseToRequest getResponse(String responseString) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseString);
        ResponseToRequest response = createResponse();
        response.initialiseWithJson(jsonObject);
        return response;
    }

    private ResponseToRequest createResponse() {
        ResponseFactory responseFactory = new ResponseFactory();
        return responseFactory.create();
    }

    private List<String> getResponseNeighbors(ResponseToRequest response) {
        List<String> neigbhors = new ArrayList<>();
        neigbhors.addAll(response.getNeighbors());
        return neigbhors;
    }

    private String getResponseDescription(ResponseToRequest response) {
        String desc = new String(response.getDescription());
        return desc;
    }

    private List<String> getResponseSubways(ResponseToRequest response) {
        List<String> subways = new ArrayList<>();
        subways.addAll(response.getSubways());
        return subways;
    }

    private void doQuery(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Intent intent = new Intent(getApplicationContext(), StationDetails.class);
                intent.putExtra(RESPONSE_EXTRA, response.toString());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Error while fetching data")
                        .setMessage("Something wrong happened while trying to get data from the web service.\n" +
                                "See the following error message:" + error.getLocalizedMessage())
                        .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
