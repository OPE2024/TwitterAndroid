package com.codepath.apps.restclienttemplate.models;



import static com.facebook.stetho.inspector.network.ResponseHandlingInputStream.TAG;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imageUrl;
    public String date_time;
    public String comment_count;
    public String retweet_count;
    public  String likes_count;
    public long id;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;



    // empty constructor needed by Parceler Library
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.imageUrl = "";
        tweet.date_time = tweet.getRelativeTimeAgo(tweet.createdAt);
        tweet.comment_count = jsonObject.getString("favorite_count");
        tweet.retweet_count = jsonObject.getString("retweet_count");
        tweet.likes_count = jsonObject.getString("favorite_count");
        tweet.id = jsonObject.getLong("id");
        //
        if (jsonObject.has("full_text")){
            tweet.body = jsonObject.getString("full_text");
        }else {
            tweet.body = jsonObject.getString("text");
        }
        //
        if (jsonObject.getJSONObject("entities").has("media")){
            JSONArray media = jsonObject.getJSONObject("entities").getJSONArray("media");
            if (media.length()>0){
                tweet.imageUrl = media.getJSONObject(0).getString("media_url_https").toString();
            }
        }
        return tweet;

    }
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    @SuppressLint("LongLogTag")
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (ParseException e) {
            Log.i(TAG, "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }
}
