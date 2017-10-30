package lociteam.com.loci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    private Button nextStep = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        nextStep=(Button)findViewById(R.id.BtnNextStep);
        //definit un listener à boutton -NextStep;
        nextStep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vu){
                Intent searchIntent= new Intent(getApplicationContext(), Search.class);
                startActivity(searchIntent);
            }
        });
    }
}
