package sg.howard.twitterclient.timeline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.blurry.Blurry;
import sg.howard.twitterclient.DialogFragmentShare;
import sg.howard.twitterclient.EndlessRecyclerViewScrollListener;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.adapter.TimelineAdapter;
import sg.howard.twitterclient.compose.ComposeTweetActivity;

public class TimelineActivity extends AppCompatActivity implements TimelineContract.View,DialogFragmentShare.hideBlurry{
    private static String TAG = TimelineActivity.class.getSimpleName();
    RecyclerView rvTimeline;
    ProgressBar loader;
    FloatingActionButton fab;
    TimelineContract.Presenter presenter;
    TimelineAdapter adapter;
    WaveSwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;
    ConstraintLayout constraintLayout;

    public TimelineActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        rvTimeline = findViewById(R.id.rvTimeline);
        loader = findViewById(R.id.loader);
        fab = findViewById(R.id.fab);
        presenter = new TimelinePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        constraintLayout = findViewById(R.id.constraint_timeline);

        fab.setOnClickListener(view -> {
           startActivity(new Intent(this, ComposeTweetActivity.class));

        });


        setUpListView();
    }

    private void setUpListView() {
        adapter = new TimelineAdapter(TimelineActivity.this,this);
        rvTimeline.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        layoutManager.scrollToPosition(0);
        rvTimeline.setLayoutManager(layoutManager);
        rvTimeline.setAdapter(adapter);


        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                presenter.loadMore();
            }
        };
        rvTimeline.addOnScrollListener(scrollListener);

        rvTimeline.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(TimelineContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        Log.d(TAG, "Loaded " + data.size());
        adapter.setData(data);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showBlurry() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragmentShare dialogFragmentShare = new DialogFragmentShare();
        dialogFragmentShare.show(fragmentManager,"Dialog share");

        Blurry.with(TimelineActivity.this).radius(20).sampling(2).onto(constraintLayout);


    }

    @Override
    public void hideBlurry() {
        Blurry.delete(findViewById(R.id.constraint_timeline));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
