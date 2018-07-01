package sg.howard.twitterclient.timeline;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import sg.howard.twitterclient.R;

public class ImageActivity extends AppCompatActivity {
    ImageView imageBackground;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);

        imageBackground = findViewById(R.id.imgBackground2);
        constraintLayout = findViewById(R.id.constraint_image);

        Bundle bundle = getIntent().getExtras();
        Glide.with(this)
                .load(bundle.getString("url"))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(imageBackground);



    }
}
