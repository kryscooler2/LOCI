package lociteam.com.loci;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lociteam.com.Factory.ResponseFactory;
import lociteam.com.Model.ResponseToRequest;


public class MapResult extends AppCompatActivity  {

        private final String URL_BASE = "http://172.16.232.91:8081/subway/stations";
        private List<String> stations = new ArrayList<>();
        private final String RESPONSE_EXTRA = "RESPONSE";

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_result);
            try {
                getShortestPath();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<String> stationReordered = new ArrayList<>();
            for (int i=stations.size()-1; i>=0; i-- ){
                stationReordered.add(stations.get(i));
            }
            stations = stationReordered;

            ListView listView = (ListView) findViewById(R.id.station_list);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stations);
                listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int i,long l){
                String elem = adapterView.getAdapter().getItem(i).toString();
                String url = URL_BASE + "/" + elem;
                Toast elemToast = Toast.makeText(getApplicationContext(),elem,Toast.LENGTH_LONG);
                elemToast.show();
                doQuery(url);
            }
        });
        //how to switch between activities in android->btnColse
        Button btnBack =(Button)findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        }

    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Your shearing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your path goes here\n";
                shareBodyText += "\n ***Departure***";
                for(String station : stations) {
                    shareBodyText += "\n" + "|-" + station + "\n";
                    shareBodyText = shareBodyText.substring(0, shareBodyText.length()-1);
                }
                shareBodyText += "\n ***Arrival*** \n";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getShortestPath() throws JSONException {
        Intent intent = getIntent();
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

