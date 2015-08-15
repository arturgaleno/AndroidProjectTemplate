package br.com.app.template;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import br.com.app.template.model.Tweet;
import br.com.app.template.model.TweetList;
import br.com.app.template.model.User;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityFragmentTest {

    @Mock
    TwitterService twitterService;

    private MainActivity_ activity;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        when(twitterService.getTweets(Mockito.anyString(), Mockito.anyString())).thenReturn(getTweets());

        final ActivityController<MainActivity_> controller = Robolectric
                .buildActivity(MainActivity_.class)
                .create()
                .start()
                .resume()
                .postResume()
                .visible();

        activity = controller.get();
    }

    @Test
    public void shoudDisplayTweetsInList() {

        TweetList tweets = twitterService.getTweets(Mockito.anyString(), Mockito.anyString());

        MainActivityFragment fragment = (MainActivityFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(MainActivityFragment.TAG);

        fragment.populateRecycleView(tweets.getStatuses());

        assertThat(fragment.recyclerView.getAdapter().getItemCount()).isEqualTo(2);
        assertThat(fragment.recyclerView).isVisible();
    }

    @NonNull
    private TweetList getTweets() {

        TweetList tweetList = new TweetList();

        User user = new User();
        user.setProfile_image_url("http://x.y.z");
        user.setScreen_name("user");

        Tweet tweet1 = new Tweet();
        tweet1.setText("tweet1");
        tweet1.setUser(user);

        Tweet tweet2 = new Tweet();
        tweet2.setText("tweet2");
        tweet2.setUser(user);

        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet1);
        tweets.add(tweet2);

        tweetList.setStatuses(tweets);

        return tweetList;
    }

}