package id.loginusa.dosis.util.externalconnection;

/**
 * Created by mfachmirizal on 10-May-16.
 */
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import id.loginusa.dosis.util.Logging;
import id.loginusa.dosis.util.StaticVar;

public class BaseGenericUrl extends GenericUrl {
 //   static String url = "";


    public BaseGenericUrl(String encodedUrl) {
        super(encodedUrl);
    }

    public BaseGenericUrl(URL url) {
        super(url);
    }

    public BaseGenericUrl() {
        super();
    }


    /** get the user. */

//    public static ExternalConnection getUser(String url) {
//        return new ExternalConnection(
//                //"https://www.googleapis.com/plus/v1/people/" + userId + "/activities/public"
//                url
//                //StaticVar.SERVER_URL+"/"+StaticVar.SERVER_CONTEXT+"/ws/dal/ADUser?where=username='"+userId+"'&l=Openbravo&p=PwOd6SgWF74HY4u51bfrUxjtB9g="
//        );
//    }

    /** Feed of Google+ activities. */
//    public static class ActivityFeed {
//
//        /** List of Google+ activities. */
//        @Key("items")
//        private List<Activity> activities;
//
//        public List<Activity> getActivities() {
//            return activities;
//        }
//    }
//
//    private static void parseResponse(HttpResponse response) throws IOException {
//        ActivityFeed feed = response.parseAs(ActivityFeed.class);
//        if (feed.getActivities().isEmpty()) {
//            System.out.println("No activities found.");
//        } else {
//            if (feed.getActivities().size() == MAX_RESULTS) {
//                System.out.print("First ");
//            }
//            System.out.println(feed.getActivities().size() + " activities found:");
//            for (Activity activity : feed.getActivities()) {
//                System.out.println();
//                System.out.println("-----------------------------------------------");
//                System.out.println("HTML Content: " + activity.getActivityObject().getContent());
//                System.out.println("+1's: " + activity.getActivityObject().getPlusOners().getTotalItems());
//                System.out.println("URL: " + activity.getUrl());
//                System.out.println("ID: " + activity.get("id"));
//            }
//        }
//    }

    //duplikat testing



  //  @Override

}