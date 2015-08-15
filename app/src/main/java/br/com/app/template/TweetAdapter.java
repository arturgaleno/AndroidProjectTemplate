package br.com.app.template;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import br.com.app.template.model.Tweet;

/**
 * Created by artur on 13/08/15.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private ArrayList<Tweet> tweets;

    private WeakReference<Fragment> fragment;

    public TweetAdapter(ArrayList<Tweet> tweets, Fragment fragment) {
        this.tweets = tweets;
        this.fragment = new WeakReference<Fragment>(fragment);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_card_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Tweet tweet = tweets.get(position);

        Glide.with(fragment.get())
                .load(tweet.getUser().getProfile_image_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .crossFade()
                .into(holder.imgProfile);

        holder.txtTwitterUser.setText("@" + tweet.getUser().getScreen_name());

        holder.txtTweet.setText(tweet.getText());
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTwitterUser;

        public ImageView imgProfile;

        public TextView txtTweet;

        public ViewHolder(View itemView) {
            super(itemView);

            ViewCompat.setTranslationZ(itemView, 5);
            ViewCompat.setElevation(itemView, 5);

            CardView cardView = (CardView) itemView;
            cardView.setPreventCornerOverlap(true);

            txtTwitterUser = (TextView) itemView.findViewById(R.id.txtTwitterUser);
            imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            txtTweet = (TextView) itemView.findViewById(R.id.txtTweet);
        }
    }

}
