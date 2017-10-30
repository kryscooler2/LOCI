package lociteam.com.loci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by linfengwang on 26/10/2017.
 */

public class MetroLineDetail extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_result);

        TextView txtStation= (TextView)findViewById(R.id.arrivalStation);

        Intent intent=getIntent();
        //getting attached intent data
        String station=intent.getStringExtra("Gare_du_nord");
        txtStation.setText(station);
    }
}
