package sg.howard.twitterclient.timeline;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class TimelinePresenter implements TimelineContract.Presenter {
    TwitterApiClient client = null;
    TimelineContract.View mView;

    int count = 30;

    public TimelinePresenter(@NonNull TimelineContract.View view, TwitterSession session){
        mView= view;
        mView.setPresenter(this);
        client = new TwitterApiClient(session);

    }

    @Override
    public void start() {
        client.getStatusesService()
                .homeTimeline(20, null, null, null, null, null, null)
                .enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        mView.onGetStatusesSuccess(result.data);
                        mView.onRefresh();

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        mView.showError(exception.getMessage());
                    }
                });
    }

    @Override
    public void loadMore() {
        mView.showLoading(true);
        client.getStatusesService()
                .homeTimeline(count,null, null, null, null, null, null)
                .enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        mView.showLoading(false);
                        mView.onGetStatusesSuccess(result.data);
                        count = count + 10;
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        mView.showLoading(false);
                        mView.showError(exception.getMessage());
                    }
                });
    }

    @Override
    public void loadItem(int page) {
        mView.showLoading(true);

    }
}
