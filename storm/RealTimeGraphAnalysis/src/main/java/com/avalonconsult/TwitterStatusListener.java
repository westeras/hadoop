package com.avalonconsult;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;


/**
 * Created by adam on 2/17/15.
 */
public class TwitterStatusListener implements StatusListener {

    private static final Logger KAFKA_LOG = LoggerFactory.getLogger(TwitterStatusListener.class);

    @Override
    public void onStatus(Status status) {
        JSONObject tweet = new JSONObject();
        tweet.put("user", status.getUser().getScreenName());
        tweet.put("name", status.getUser().getName());
        tweet.put("location", status.getUser().getLocation());
        tweet.put("text", status.getText());

        HashtagEntity[] hashTags = status.getHashtagEntities();
        JSONArray jsonHashTags = new JSONArray();

        for (HashtagEntity hashTag : hashTags) {
            jsonHashTags.add(hashTag.getText());
        }
        tweet.put("hashtags", jsonHashTags);

        UserMentionEntity[] mentions = status.getUserMentionEntities();
        JSONArray jsonMentions = new JSONArray();
        for (UserMentionEntity mention : mentions) {
            jsonMentions.add(mention.getScreenName());
        }
        tweet.put("mentions", jsonMentions);

        URLEntity[] urls = status.getURLEntities();
        JSONArray jsonUrls = new JSONArray();
        for (URLEntity url : urls) {
            jsonUrls.add(url.getExpandedURL());
        }
        tweet.put("urls", jsonUrls);

        if (status.isRetweet()) {
            JSONObject retweetUser = new JSONObject();
            retweetUser.put("user", status.getUser());
            retweetUser.put("name", status.getUser().getName());
            retweetUser.put("location", status.getUser().getLocation());
            retweetUser.put("retweetuser", retweetUser);
        }
        String tweetString = tweet.toJSONString();
        System.out.println("---BEGIN---");
        System.out.println(tweetString);
        System.out.println("---END---");
        KAFKA_LOG.info(tweetString);

    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {
        System.out.printf("Track Limitation Notice: " + i);
    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }
}
