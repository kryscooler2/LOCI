package lociteam.com.loci;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class UserData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        ArrayList stationList = getIntent().getStringArrayListExtra("stationsList");

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        AutoCompleteTextView choice = (AutoCompleteTextView) findViewById(R.id.choiceModify);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stationList);
        choice.setAdapter(adapter);

        Intent myIntent = getIntent();
        if (myIntent.getIntExtra("choice", 0) == 0){
            choice.setHint("Home station");
        }else{
            choice.setHint("Work station");
        }


        Button btnValidate = (Button) findViewById(R.id.validateModifications);
        btnValidate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView choice = (AutoCompleteTextView) findViewById(R.id.choiceModify);
                choice.setThreshold(2);
                Intent myIntent = getIntent();

                SharedPreferences.Editor editor = pref.edit();
                if (!choice.getText().toString().equals("")) {
                    switch (myIntent.getIntExtra("choice", 0)){
                        case 0:
                            editor.putString("home", choice.getText().toString());
                            break;
                        default:
                            editor.putString("work", choice.getText().toString());
                    }
                    editor.commit();
                    finish();
                }else {
                    Context context = getApplicationContext();
                    CharSequence text = "Please fill at least one of the fields or click on the button at the bottom to quit this page";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        Button quit = (Button) findViewById(R.id.back);
        quit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
