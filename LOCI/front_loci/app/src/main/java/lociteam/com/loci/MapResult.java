package lociteam.com.loci;

import java.util.Arrays;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;


/**
 * Created by linfengwang on 25/10/2017.
 */

public class MapResult extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView deptStation=(TextView)findViewById(R.id.departureStation);
        TextView arrivalStation=(TextView)findViewById(R.id.arrivalStation);

        //récupère les donnes envoyé par l'activité---activity_search
        Intent resultIntent=getIntent();
        String departure=resultIntent.getStringExtra("departure");
        String arrival=resultIntent.getStringExtra("arrival");

        //affichez les résultats reçus.
        deptStation.setText(departure);
        arrivalStation.setText(arrival);

        //créez une liste pour afficher les réusltats, pour l'instant, codage dur;

        String [] station_map = getResources().getStringArray(R.array.result_station);
        List<String> stationList= Arrays.asList(station_map);

        ListView listView = (ListView) findViewById(R.id.station_list);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stationList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int i,long l){
                
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
}
