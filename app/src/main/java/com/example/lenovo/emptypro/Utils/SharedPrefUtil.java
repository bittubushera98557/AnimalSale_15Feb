package com.example.lenovo.emptypro.Utils;

import android.content.Context;
import android.content.SharedPreferences;





public class SharedPrefUtil {

    private static final String USER_PREFERENCES = "user_preferences" ;

    private static final String USER_LOGGED_IN = "user_logged_in" ;
    private static final String ACCESS_TOKEN = "access_token" ;
    private static final String USER_ID  = "user_id" ;
     private static final String Device_TOKEN= "deviceToken";

    public static void clearPreferences(Context context) {
        getPreferences(context).edit().clear().commit();
 }

    public static SharedPreferences getPreferences(Context context) {

        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }


    public static void setUserId(Context context, String user_id) {
        getPreferences(context).edit().putString(USER_ID, user_id).apply();
    }

    public static String getUserId(Context context) {
        return getPreferences(context).getString(USER_ID,"");
    }

      public static void setDeviceToken(Context context, String token) {
        getPreferences(context).edit().putString(Device_TOKEN, token).apply();
    }

    public static String getDeviceToken(Context context) {
        return getPreferences(context).getString(Device_TOKEN,"");
    }

    public static void setUserEmail(Context context, String email) {
        getPreferences(context).edit().putString("email", email).apply();
    }

    public static String getUserEmail(Context context) {
        return getPreferences(context).getString("email","");
    }

    public static void setUserMobile(Context context, String mobile) {
        getPreferences(context).edit().putString("mobile", mobile).apply();
    }

    public static String getUserMobile(Context context) {
        return getPreferences(context).getString("mobile","");
    }

    public static void setUserFirstName(Context context, String firstName) {
        getPreferences(context).edit().putString("firstName", firstName).apply();
    }

    public static String getUserFirstName(Context context) {
        return getPreferences(context).getString("firstName","");
    }
}
