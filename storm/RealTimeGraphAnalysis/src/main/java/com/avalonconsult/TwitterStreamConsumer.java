package com.avalonconsult;

import twitter4j.*;

import java.io.IOException;

/**
 * Created by adam on 2/17/15.
 */
public class TwitterStreamConsumer {

    public static void main(String[] args) throws TwitterException, IOException {

        StatusListener listener = new TwitterStatusListener();
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);

        FilterQuery query = new FilterQuery().track(args);
        twitterStream.filter(query);
    }
}
