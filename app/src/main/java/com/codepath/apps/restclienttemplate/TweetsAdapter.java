package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.w3c.dom.Text;

import java.util.List;

public class TweetsAdapter extends  RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;
    // Pass in the context and list of tweet
    public TweetsAdapter(Context context,List<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

        //LayoutInflater.from(context).inflate(R.layout.item_tweet, parent,false);
        //return new ViewHolder(parent);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with the view holder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
    // Define a viewholder
    public  class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView date_time;
        ImageView ivTweetImage;
        TextView comment_count;
        TextView retweet_count;
        TextView likes_count;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            ivTweetImage = itemView.findViewById(R.id.ivTweetImage);
            date_time = itemView.findViewById(R.id.date_time);
            comment_count = itemView.findViewById(R.id.tvCommentcount);
            retweet_count = itemView.findViewById(R.id.tvRetweetcount);
            likes_count = itemView.findViewById(R.id.tvLikescount);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            comment_count.setText(tweet.comment_count);
            tvScreenName.setText(tweet.user.screenName);
            date_time.setText(tweet.date_time);
            retweet_count.setText(tweet.retweet_count);
            likes_count.setText(tweet.likes_count);

            int radius = 80;
            Glide.with(context).load(tweet.user.profileImageUrl).transform(new RoundedCorners(radius)).into(ivProfileImage);

//            Log.i("Ope", tweet.imageUrl);
            Glide.with(context).load(tweet.imageUrl).into(ivTweetImage);
        }

    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }
}
//public class TweetsAdapter extends  RecyclerView.Adapter<TweetsAdapter.ViewHolder>{}