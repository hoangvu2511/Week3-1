package sg.howard.twitterclient;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import sg.howard.twitterclient.timeline.TimelineActivity;
import sg.howard.twitterclient.timeline.TimelineContract;

public class DialogFragmentShare extends DialogFragment {
    TimelineContract.View contractView;

    public DialogFragmentShare() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_share,container);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        hideBlurry activity = (hideBlurry) getActivity();
        activity.hideBlurry();

    }

    public interface hideBlurry {
        void hideBlurry();
    }
}
