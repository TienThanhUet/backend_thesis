package com.stadio.model.define;

import java.util.concurrent.TimeUnit;

public class Constant
{

    public static String DEFAULT_PASS_USER = "12345678";

    public static Integer PAGE_SIZE = 5;

    public static String VERIFY_FACEBOOK_TOKEN_URL = "https://graph.facebook.com/debug_token";

    public static String VERIFY_ACCCOUNT_KIT_TOKEN_URL = "https://graph.accountkit.com/v1.3/me/";

    public static String FACEBOOK_APP_ID = "1611905259125488";

    public static String FACEBOOK_APP_SECRET = "76e928c8dfaa568982d6ae833a23e327";

    public static String VERIFY_GOOGLE_TOKEN_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";

    public static String FIREBASE_GOOGLE_IOS_CLIENT_ID = "510065496973-qlms4h4d9i5ocdoec25ogv5afao68o83.apps.googleusercontent.com";

    public static String FIREBASE_GOOGLE_ANDROID_CLIENT_ID = "510065496973-qlms4h4d9i5ocdoec25ogv5afao68o83.apps.googleusercontent.com";

    public interface Notification
    {
        int MAX_DEVICES = 1000;
        int TTL = (int) TimeUnit.MINUTES.toSeconds(300);
    }

    public interface QUEUE_NAME
    {
        String NOTIFICATION = "notification";
    }

    public interface EXCHANGE_NAME
    {
        String NOTIFICATION = "notification";
    }

}
