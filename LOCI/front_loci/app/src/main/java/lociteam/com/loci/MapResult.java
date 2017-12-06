package lociteam.com.loci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;


public class MapResult extends AppCompatActivity  {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_result);
                TextView deptStation=(TextView)findViewById(R.id.departureStation);
                TextView arrivalStation=(TextView)findViewById(R.id.arrivalStation);

                //récupère les donnes envoyé par l'activité_search
                Intent resultIntent=getIntent();
                String departure=resultIntent.getStringExtra("departure");
                String arrival=resultIntent.getStringExtra("arrival");

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
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }

