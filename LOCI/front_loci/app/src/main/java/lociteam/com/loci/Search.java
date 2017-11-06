package lociteam.com.loci;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Search extends AppCompatActivity {

    private Button searchButton = null;
    private EditText departureStation= null;
    private EditText arrivalStation =null;
        //test to call service;
    private Button btnCall=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        departureStation=(EditText)findViewById(R.id.departure);
        arrivalStation=(EditText)findViewById(R.id.arrival);

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
        searchButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View viewResult){
                //dès que l'on clique le boutton, il va s'afficher le text="le boutton d'envoyer est déclenché.";
                Context context = getApplicationContext();
                CharSequence text="The shortest path will show immediately";
                int duration= Toast.LENGTH_SHORT;
                Toast toast=Toast.makeText(context,text,duration);
                toast.show();

                //change pour l'activité MapResult;
                Intent ResultScreen= new Intent(context, MapResult.class);
                //envoyez les donnes à deuxième activité.
                ResultScreen.putExtra("departure",departureStation.getText().toString());
                ResultScreen.putExtra("arrival",arrivalStation.getText().toString());

                startActivity(ResultScreen);
            }
        });
     }
}
