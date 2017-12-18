package lociteam.com.loci;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MapsActivity extends Activity {

    private Integer images [] = {R.mipmap.metro,R.mipmap.paris};
    //R.drawable.pic2, R.drawable.pic3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        addImagesToThegallery();
    }

    private void addImagesToThegallery() {
        LinearLayout imageGallery = (LinearLayout)findViewById(R.id.imageGallery);
        for(Integer image : images) {
            imageGallery.addView(getImageView(image));
        }

    }

    private View getImageView(Integer image) {
        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 10, 0);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(image);
        return imageView;
    }

}