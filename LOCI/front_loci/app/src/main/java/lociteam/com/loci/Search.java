package lociteam.com.loci;

import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lociteam.com.Factory.ResponseFactory;
import lociteam.com.Model.ResponseToRequest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import android.support.v7.app.AlertDialog;


public class Search extends AppCompatActivity {

    private final String URL_BASE = "http://172.16.232.91:8081/subway/SP/";

    private final String TYPES_OF_RESOURCE_EXTRA = "TYPES_OF_RESOURCE";
    private final String RESPONSE_EXTRA = "RESPONSE";
    private final String SELECTED_RESPONSE_EXTRA = "SELECTED_RESPONSE";

    private Button searchButton = null;
    private AutoCompleteTextView departureStation= null;
    private AutoCompleteTextView arrivalStation =null;
        //test to call service;
    private Button btnCall=null;
    private Button btnGMap=null;

    private List<String> stations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        try {
            autoCompleteCreation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        departureStation=(AutoCompleteTextView)findViewById(R.id.departure);
        departureStation.setThreshold(2);

        arrivalStation=(AutoCompleteTextView)findViewById(R.id.arrival);
        arrivalStation.setThreshold(2);

        // On associe un adaptateur à notre liste de couleurs…
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stations);
        // puis on indique que notre AutoCompleteTextView utilise cet adaptateur
        departureStation.setAdapter(adapter);
        arrivalStation.setAdapter(adapter);

        searchButton=(Button)findViewById(R.id.search);

        //test call LOCI service;
        btnCall = (Button) findViewById(R.id.btncall);
        btnCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //call the service ;
                Uri  telephone=Uri.parse("tel:0626970787");
                Intent secondIntent = new Intent(Intent.ACTION_DIAL,telephone);
                startActivity(secondIntent);
            }
        });

        //on rajoute un listener sur le clic du boutton search
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View viewResult){
                //dès que l'on clique le boutton, il va s'afficher le text="le boutton d'envoyer est déclenché.";
                Context context = getApplicationContext();
                CharSequence text="The shortest path will show immediately";
                int duration= Toast.LENGTH_SHORT;
                Toast toast=Toast.makeText(context,text,duration);
                toast.show();
                if(!departureStation.getText().toString().equals("") &&!arrivalStation.getText().toString().equals("")) {
                    if(!departureStation.getText().toString().equals(arrivalStation.getText().toString())) {
                        String url = URL_BASE + departureStation.getText().toString() + "/" + arrivalStation.getText().toString();
                        doQuery(url);
                    } else {
                        Toast changeStationToast = Toast.makeText(context,"You have chosen the same station for the departure and the arrival.",Toast.LENGTH_LONG);
                        changeStationToast.show();
                    }


                } else {
                    Toast addStationToast = Toast.makeText(context,"You Need to write two stations",Toast.LENGTH_LONG);
                    addStationToast.show();
                }


            }
        });
        //test Google maps
        btnGMap=(Button)findViewById(R.id.btnGoogleMap);
        btnGMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View viewGoogle){
                Intent googleMapScreen=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(googleMapScreen);
            }
        });
     }

    private void autoCompleteCreation() throws JSONException {
        Intent intent = getIntent();
        String resourceType = intent.getStringExtra(TYPES_OF_RESOURCE_EXTRA);
        String responseString = intent.getStringExtra(RESPONSE_EXTRA);

        final ArrayList<ResponseToRequest> responseList = getResponses(responseString);
        List<String> responseNames = getResponseName(responseList);
        stations.addAll(responseNames);
    }

    private ArrayList<ResponseToRequest> getResponses(String responseString) throws JSONException {
        JSONArray jsonArray = new JSONArray(responseString);
        ArrayList<ResponseToRequest> responseList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ResponseToRequest response = createResponse();
            response.initialiseWithJson(jsonObject);
            responseList.add(response);
        }
        return responseList;
    }

    private ResponseToRequest createResponse() {
        ResponseFactory responseFactory = new ResponseFactory();
        return responseFactory.create();
    }

    private List<String> getResponseName(List<ResponseToRequest> responseList) {
        List<String> names = new ArrayList<>();
        for (ResponseToRequest response : responseList) {
            names.add(response.getName());
        }
        return names;
    }

    private void doQuery(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Intent intent = new Intent(getApplicationContext(), MapResult.class);
                intent.putExtra(RESPONSE_EXTRA, response.toString());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
				Intent intenterror = new Intent(getApplicationContext(), MainMenuServerError.class);
                startActivity(intenterror);
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
        requestQueue.add(jsonArrayRequest);
    }
}
