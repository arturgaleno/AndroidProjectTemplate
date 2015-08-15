package br.com.app.template;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.IgnoredWhenDetached;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.com.app.template.model.Token;
import br.com.app.template.model.Tweet;
import br.com.app.template.model.TweetList;

/**
 * Created by artur on 13/08/15.
 *
 * A very simple sample using AndroidAnnotations, RecyclerView, CardView accessing
 * a REST service using OkHttp and Retrofit.
 */
@EFragment(R.layout.fragment_main)
public class MainActivityFragment extends Fragment {

    public static final String TAG = MainActivityFragment.class.getName();

    @ViewById
    RecyclerView recyclerView;

    @AfterViews
    void init() {

        if (recyclerView.getLayoutManager() == null)
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (recyclerView.getItemAnimator() == null)
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() <= 0) {
            getTweets();
        }
    }

    @Background
    void getTweets() {

        AppApplication appApplication = (AppApplication) getActivity().getApplication();

        TwitterService twitterService = appApplication.getTwitterService();

        try {
            String urlApiKey = URLEncoder.encode(BuildConfig.TWITTER_USER_API_KEY, "UTF-8");
            String urlApiSecret = URLEncoder.encode(BuildConfig.TWITTER_USER_API_SECRET, "UTF-8");
            String combined = urlApiKey + ":" + urlApiSecret;
            String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

            Token credentials = twitterService.getToken("Basic " + base64Encoded,
                    "application/x-www-form-urlencoded;charset=UTF-8", "client_credentials");

            TweetList tweets = twitterService.getTweets("Bearer " + credentials.getAccess_token(), "application/json");

            populateRecycleView(tweets.getStatuses());
        } catch (Exception e) {
            Snackbar.make(getView(), "OOPS, Something was wrong on tweet load", Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @IgnoredWhenDetached
    @UiThread
    void populateRecycleView(List<Tweet> tweets) {

        TweetAdapter tweetAdapter = new TweetAdapter((ArrayList<Tweet>) tweets, this);
        recyclerView.setAdapter(tweetAdapter);
    }

    @Override
    public void onDestroy() {
        recyclerView.setAdapter(null);
        super.onDestroy();
    }
}
