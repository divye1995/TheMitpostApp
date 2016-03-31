package com.example.root.themitpostapp.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 3/3/16.
 */
public class Constant {
    //Json Article Constants
    final public static String KEY_ARTICLE_TITLE="article_title";
    final public static String KEY_ARTICLE_FEATURE_IMAGE="article_feature_image";
    final public static String KEY_ARTICLE_CONTENT="article_content";
    //Log TAGS
    final public static String TAG_MAIN_ACTIVITY="MainActivity";
    final public static String TAG_ARTICLE_SERVICE="ArticleService";
    final public static String TAG_ARTICLE_ADAPTER="ArticleAdapter";
    final public static String TAG_ARTICLE_READER_ACTIVITY="ArticleReaderActivity";


    //Intent Extra KEY-VALUE
    final public static String ARTICLE_BUNDLE="article_bundle";
    public static final String CATEGORY_KEY="category";
    public static final String CALENDAR_VALUE="Calendar";
    public static final String ABOUT_US_VALUE="About Us";

    //Response headers
    final public static String RESPONSE_KEY_TITLE="title";
    final public static String RESPONSE_KEY_CONTENT="content";
    final public static String RESPONSE_KEY_DATE="date";
    final public static String RESPONSE_KEY_FEATURE_IMAGE="featured_image";
    public final static String JSON_OBJECT_TITLE="title";
    public final static String JSON_OBJECT_CONTENT="content";
    public final static String JSON_OBJECT_IMAGE="featured_image";
    public final static String JSON_OBJECT_PREVIEW="excerpt";
    public final static String JSON_OBJECT_URL="URL";
    public final static String JSON_OBJECT_ID="ID";
    public final static String JSON_OBJECT_DATE="date";


    //URLS
    final public static String SAMPLE_URL="https://public-api.wordpress.com/rest/v1.1/sites/www.themitpost.com/posts/?";
    final public static String REQUEST_URL_FIELDS="pretty=true&fields=ID%2Ctitle%2CURL%2Cexcerpt%2Cfeatured_image%2Cdate";
    final public static String REQUEST_URL_QUERY="&number=10&sticky=exclude&order_by=date&order=DESC";
    final public static String REQUEST_URL_CATEGORY="&category=";
    final public static String JSON_RESPONSE_KEY_POSTS="posts";
    public static final String AMP_URL="amp/";
    public static final String CALENDAR_URL="http://www.themitpost.com/?post_type=tribe_events";
    public static final String ABOUT_US_URL="http://www.themitpost.com/about/";
    //Error Toast Message
    final public static String ERROR_MESSAGE_1="Sorry,Something went wrong";

    //STRINGS
    final public static String PROGRESS_PLEASE_WAIT="Please Wait....";
    public static final String ERROR_URL_UNAVAILABLE="Error URL unavailable. Please Refresh";
    public static final String ERROR_UNABLE_TO_REFRESH="Unable to Refresh. Try again";
    public static final String REFRESH_HELP="Swipe down to Refresh feed";

    //CATEGORIES
    final public static String CATEGORY_CAMPUS_COMMUNITY="Campus & Community";
    final public static String CATEGORY_NATION_WORLD="Nation & World";
    final public static String CATEGORY_ART_CULTURE="Art & Culture";
    final public static String CATEGORY_LATEST="Latest";

    //CATEGORY MAP
    final public static Map<String,String> CATEGORY_MAP;
    static{
        CATEGORY_MAP=new HashMap<String,String>();
        CATEGORY_MAP.put(CATEGORY_CAMPUS_COMMUNITY,"campus_and_community");
        CATEGORY_MAP.put(CATEGORY_ART_CULTURE,"arts");
        CATEGORY_MAP.put(CATEGORY_NATION_WORLD,"national_and_world");
    }

    //Title Strings
    public static final String TITLE_MAIN_ACTIVITY="THE MIT POST";

//-----------------------------------Integer Constants-------------------------------------------------------
    public static final int MY_SOCKET_TIMEOUT_MS=30000;//Socket timeout
}
