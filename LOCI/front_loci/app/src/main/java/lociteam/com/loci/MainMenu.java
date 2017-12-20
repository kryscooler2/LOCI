package lociteam.com.loci;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class MainMenu extends AppCompatActivity {
    private final String URL_BASE = "http://172.16.234.210:8090/subway/stations/";
    private final String TYPES_OF_RESOURCE_EXTRA = "TYPES_OF_RESOURCE";
    private final String RESPONSE_EXTRA = "RESPONSE";
    private Button nextStep = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        nextStep = (Button) findViewById(R.id.BtnNextStep);
        //definit un listener à boutton -NextStep;
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vu) {
                doQuery(URL_BASE);
            }
        });
    }


    private void doQuery(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                intent.putExtra(RESPONSE_EXTRA, response.toString());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intenterror = new Intent(getApplicationContext(), MainMenuServerError.class);
                startActivity(intenterror);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
