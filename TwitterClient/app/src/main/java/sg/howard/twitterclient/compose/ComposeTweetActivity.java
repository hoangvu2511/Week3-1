package sg.howard.twitterclient.compose;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import sg.howard.twitterclient.R;

public class ComposeTweetActivity extends AppCompatActivity implements ComposeContract.View{
    Button btnSend;
    EditText edtCompose;
    ProgressBar loader;
    ImageView imageProfile;
    ImageButton close;
    ComposeContract.Presenter presenter;
    View parent;
    FloatingActionButton fabDummy;
    TextInputLayout textInputLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        btnSend = findViewById(R.id.btnSend);
        edtCompose = findViewById(R.id.edtCompose);
        loader = findViewById(R.id.loader);
        imageProfile = findViewById(R.id.imgProfile);
        close = findViewById(R.id.closeCompose);
        parent = findViewById(R.id.parent);
        fabDummy = findViewById(R.id.fabDummy);
        textInputLayout = findViewById(R.id.textInputLayout);

        textInputLayout.setBackgroundResource(R.drawable.custom_edittext);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtCompose.getWindowToken(), 0);
                exitReveal();
                //finish();


            }
        });
        presenter = new ComposeTweetPresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
        btnSend.setOnClickListener( view -> presenter.sendTweet(edtCompose.getText().toString()));
        presenter.getUser();

        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                enterReveal();
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void enterReveal() {
        int x = (int) (fabDummy.getX() + fabDummy.getMeasuredWidth() / 2);
        int y = (int) (fabDummy.getY() + fabDummy.getMeasuredHeight() / 2);

        int finalRadius = Math.max(parent.getWidth(), parent.getHeight());

        ViewAnimationUtils.createCircularReveal(parent, x, y, 0, finalRadius).setDuration(700).start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitReveal();
    }

    public void exitReveal() {
        int x = (int) (fabDummy.getX() + fabDummy.getMeasuredWidth() / 2);
        int y = (int) (fabDummy.getY() + fabDummy.getMeasuredHeight() / 2);
        int startRadius = Math.max(parent.getWidth(), parent.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(parent, x, y, startRadius, fabDummy.getMeasuredWidth() / 2);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                parent.setVisibility(View.INVISIBLE);
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    @Override
    public void setPresenter(ComposeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendTweetSuccess(Result<Tweet> result) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtCompose.getWindowToken(), 0);
        finish();
    }

    @Override
    public void sendUser(List<Tweet> result) {
        Glide.with(ComposeTweetActivity.this)
                .load(result.get(0).user.profileImageUrlHttps)
                .apply(RequestOptions.circleCropTransform())
                .into(imageProfile);
    }


}
