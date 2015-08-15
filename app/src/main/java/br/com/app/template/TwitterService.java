package br.com.app.template;

import br.com.app.template.model.Token;
import br.com.app.template.model.TweetList;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by artur on 14/08/15.
 */
public interface TwitterService {

    @FormUrlEncoded
    @POST("/oauth2/token")
    Token getToken(@Header("Authorization") String auth,
                   @Header("Content-Type") String contentType,
                   @Field("grant_type") String grantType);

    @GET("/1.1/search/tweets.json?q=&geocode=-22.912214,-43.230182,1km&lang=pt&result_type=recent")
    TweetList getTweets(@Header("Authorization") String auth, @Header("Content-Type") String contentType);
}
