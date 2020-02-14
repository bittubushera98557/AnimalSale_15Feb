package com.example.lenovo.emptypro.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.lenovo.emptypro.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalData {
    public static String     BASE_URL="http://www.apivlovepets.com/";


    static Dialog dialog;

    public static Boolean validateEmail(final String email) {
        final String EMAIL_PATTERN = Patterns.EMAIL_ADDRESS.toString();
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time );
        String date = DateFormat.format("dd-MM-yyyy HH:mm", cal).toString();
        return date;
    }

    /**
     * Show the circular progress bar as loading with the message
     *
     * @param activity on which it is to be displayed
     * @param message  that is to be shown
     */
    public static void showProgressDialog(final Context activity, String message) {
        try {
            hideDialog();
            dialog = new Dialog(activity,
                    R.style.Theme_AppCompat_Translucent);

            // Configure dialog box
            dialog.setContentView(R.layout.progress_layout);
            WindowManager.LayoutParams layoutParams = dialog.getWindow()
                    .getAttributes();
            layoutParams.dimAmount = 0.6f;
            dialog.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            // set progress drawable
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.circular_progress);
                progressBar.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.progress));
            }

            // set the message
            if (!message.isEmpty()) {
                final TextView dialogMsg = (TextView) dialog.findViewById(R.id.progress_msg);
                dialogMsg.setText(message);
            }

            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // hide the progress dialog
    public static void hideDialog() {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();

                }
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static void showSnackbar(View v,String message) {

        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_LONG);


        snackbar.show();
    }*/


    public static void showSnackbar(Activity activity, String msg){
        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), msg, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(activity.getResources().getColor(R.color.white));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    public static boolean isConnectedToInternet(Context act) {
        boolean isNetConnected;
        if(act!=null){
            try {
                ConnectivityManager ConMgr = (ConnectivityManager) act
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                isNetConnected = ConMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                        || ConMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                        .getState() == NetworkInfo.State.CONNECTED;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return isNetConnected;
        }
        else{
            return true;
        }
    }

    public static String dateSplit(String oldStr) {

        if(oldStr.equals(""))
return oldStr ;

else{
    try{
        String[] strArr=oldStr.split(" ");
        return strArr[0];
    }
    catch (Exception exp)
    {
        Log.e("GlobalData string split","excep="+exp.toString());
    }
return oldStr;
        }
    }


}
