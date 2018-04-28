package com.stadio.model.redisUtils;


public class RedisConst
{
    //select DB
    public static final long TIME_TO_LIVE_SHORT = 60*3; // 3 MINUTES UNIT
    public static final long TIME_TO_LIVE_LONG = 60*12; // 12 MINUTES UNIT
    public static final long TIME_TO_LIVE_TOO_LONG = 60*30; // 30 MINUTES UNIT
    public static final int DB_MOVIE = 1;
    public static final int DB_ARTIST = 2;

    public static final String MOVIE_TOP = "movie_top_";
    public static final String MOVIE_DETAILS = "movie_details";

    public static final String ARTIST_DETAILS = "artist_details";

}
