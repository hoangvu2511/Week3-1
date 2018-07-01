package sg.howard.twitterclient.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.twitter.sdk.android.core.models.Tweet;
import com.varunest.sparkbutton.SparkButton;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.timeline.ImageActivity;
import sg.howard.twitterclient.timeline.TimelineContract;
import sg.howard.twitterclient.util.TimelineConverter;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    List<Tweet> data;
    Context context;
    Tweet tweet;
    TimelineContract.View contractView;
    private int lastPosition = -1;

    public TimelineAdapter(Context context, TimelineContract.View contractView) {
        data = new ArrayList<>();
        this.contractView = contractView;
        this.context = context;
    }

    public void setData(List<Tweet> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_a_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        tweet = data.get(position);

        holder.text.setText(tweet.text);
        holder.user_name.setText(tweet.user.name);
        holder.screen_name.setText("@" + tweet.user.screenName);
        holder.time.setText("• " + TimelineConverter.dateToAge(tweet.createdAt, DateTime.now()));

        holder.like.setText(tweet.favoriteCount + "");
        holder.retweet.setText(tweet.retweetCount + "");

        Glide.with(context)
                .load(tweet.user.profileImageUrlHttps)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageProfile);

        if (tweet.entities.media.size() > 0) {
            Glide.with(context)
                    .load(tweet.entities.media.get(0).mediaUrlHttps)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(holder.imageBackground);


            holder.imageBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context,ImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url",data.get(position).entities.media.get(0).mediaUrlHttps);
                    intent.putExtras(bundle);

                    Pair<View,String> pair = Pair.create(holder.imageBackground,"imageShare");

                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,pair);


                    context.startActivity(intent,optionsCompat.toBundle());



                }
            });


        }
        else{
            holder.imageBackground.setVisibility(View.GONE);
        }

        holder.imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contractView.showBlurry();
            }
        });
        setAnimation(holder.itemView, position);



    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text, user_name, screen_name, time, like,retweet;
        ImageView imageProfile, imageBackground, imageShare, imageBackground2;
        SparkButton buttonLike,buttonRetweet;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tvText);
            user_name = itemView.findViewById(R.id.tvUsername);
            screen_name = itemView.findViewById(R.id.tvScreen_name);
            imageProfile = itemView.findViewById(R.id.imgProfile);
            imageBackground = itemView.findViewById(R.id.imgBackground);
            imageBackground2 = itemView.findViewById(R.id.imgBackground2);
            imageShare = itemView.findViewById(R.id.imgShare);
            time = itemView.findViewById(R.id.tvTime);
            like = itemView.findViewById(R.id.tvLike);
            retweet = itemView.findViewById(R.id.tvRetweet);
            buttonLike = itemView.findViewById(R.id.sparkButtonLike);
            buttonRetweet = itemView.findViewById(R.id.sparkButtonRetweet);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
