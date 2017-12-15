package lociteam.com.loci;

import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
    private final String URL_BASE = "http://192.168.1.12:8081/subway/SP/";    //"http://172.16.232.91:8081/subway/SP/";

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


        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("home", "");
        editor.putString("work", "");
        editor.commit();

        try {
            autoCompleteCreation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        departureStation=(AutoCompleteTextView)findViewById(R.id.departure);
        departureStation.setThreshold(2);

        arrivalStation=(AutoCompleteTextView)findViewById(R.id.arrival);
        arrivalStation.setThreshold(2);
        arrivalStation.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchButton.performClick();
                    return true;
                }
                return false;
            }
        });

        // On associe un adaptateur à notre liste de couleurs…
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stations);
        // puis on indique que notre AutoCompleteTextView utilise cet adaptateur
        departureStation.setAdapter(adapter);
        arrivalStation.setAdapter(adapter);

        Button change = (Button) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String swap = departureStation.getText().toString();
                departureStation.setText(arrivalStation.getText().toString());
                arrivalStation.setText(swap);
            }
        });

        searchButton=(Button)findViewById(R.id.search);

        Button btnUserHome = (Button) findViewById(R.id.btnHome);
        btnUserHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (pref.getString("home", null).toString().equals("")){
                    Intent myIntent = new Intent(getApplicationContext(), UserData.class);
                    myIntent.putExtra("choice", 0);
                    myIntent.putStringArrayListExtra("stationsList", (ArrayList<String>) stations);//.putExtra(RESPONSE_EXTRA, response.toString());
                    startActivity(myIntent);
                }else {
                    arrivalStation.setText(pref.getString("home", null));
                }
            }
        });
        Button btnUserWork = (Button) findViewById(R.id.btnWork);
        btnUserWork.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (pref.getString("work", null).toString().equals("")){
                    Intent myIntent = new Intent(getApplicationContext(), UserData.class);
                    myIntent.putExtra("choice", 1);
                    myIntent.putStringArrayListExtra("stationsList", (ArrayList<String>) stations);//.putExtra(RESPONSE_EXTRA, response.toString());
                    startActivity(myIntent);
                }else {
                    arrivalStation.setText(pref.getString("work", null));
                }
            }
        });

        Button configHome = (Button) findViewById(R.id.configHome);
        configHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(getApplicationContext(), UserData.class);
                myIntent.putExtra("choice", 0);
                myIntent.putStringArrayListExtra("stationsList", (ArrayList<String>) stations);//.putExtra(RESPONSE_EXTRA, response.toString());
                startActivity(myIntent);
            }
        });

        Button configWork = (Button) findViewById(R.id.configWork);
        configWork.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(getApplicationContext(), UserData.class);
                myIntent.putExtra("choice", 1);
                myIntent.putStringArrayListExtra("stationsList", (ArrayList<String>) stations);//.putExtra(RESPONSE_EXTRA, response.toString());
                startActivity(myIntent);
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
                        boolean departureStationFound= false;
                        boolean arrivalStationFound= false;
                        for (int i=0; i < stations.size(); i++){
                            if (departureStation.getText().toString().equals(stations.get(i))){
                                departureStationFound = true;
                            }
                            if (arrivalStation.getText().toString().equals(stations.get(i))){
                                arrivalStationFound = true;
                            }
                        }
                        if (departureStationFound == true && arrivalStationFound == true){
                            String url = URL_BASE + departureStation.getText().toString() + "/" + arrivalStation.getText().toString();
                            doQuery(url);
                        } else{
                            Toast changeStationToast = Toast.makeText(context,"At least one of the station written doesn't exist.",Toast.LENGTH_LONG);
                            changeStationToast.show();
                        }
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
