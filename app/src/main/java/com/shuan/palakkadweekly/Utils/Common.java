package com.shuan.palakkadweekly.Utils;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


public class Common extends Application {

    public static final String EDT_LANG = "edt_lang";
    public static final String LANG = "lang";
    public static final String Login = "login";
    private static SharedPreferences mSharedPreferences;
    public static final String Tag = "tag";
    public static final String EDTITION = "edtition";
    public static final String GCMID = "gcm_id";
    private Context mContext;
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoader mImageLoader;
    private ImageLoaderConfiguration mImageLoaderConfiguration;
    private Picasso mPicasso;
    private String gmailId;
    private php call = new php();
    private static final String GCM_SENDER_ID = "339543866097";
    private GoogleCloudMessaging gcm;
    private static final int ACTION_PLAY_SERVICES_DIALOG = 100;
    private String gcmRegId;
    private HashMap<String, String> saveData;
    private HttpUrlConnectionParser parser = new HttpUrlConnectionParser();
    private String result;
    public static final String usrAddr = "addr";
    public static final String usrBalnce = "Balance";
    public static final String usrId = "usrid";
    public static final String usrImg = "img";
    public static final String usrMail = "mail";
    public static final String usrName = "usr";
    public static final String usrOkNews = "accepted News";
    public static final String usrPhone = "ph";
    public static final String usrPlce = "place";
    public static final String usrTotNews = "total_news";


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        mSharedPreferences = this.getSharedPreferences("com.shuan.palakkadweekly", MODE_PRIVATE);

        //Picasso.
        mPicasso = new Picasso.Builder(mContext).build();

        //ImageLoader.
        mImageLoader = ImageLoader.getInstance();
        mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSizePercentage(13)
                .imageDownloader(new ByteArrayUniversalImageLoader(mContext))
                .build();
        mImageLoader.init(mImageLoaderConfiguration);


        try {
            Account[] accounts = AccountManager.get(getApplicationContext()).getAccountsByType("com.google");

            for (Account account : accounts) {
                gmailId = account.name;
            }

        } catch (Exception e) {
        }


        // if(isGoogelPlayInstalled()){
        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        gcmRegId = getSharedPreferences().getString(GCMID, "");

        if (TextUtils.isEmpty(gcmRegId)) {
            new GCMReg().execute();
        } else {

        }

        //}
        Log.d("Key", getSharedPreferences().getString(Common.GCMID, ""));

        //Init DisplayImageOptions.
        //initDisplayImageOptions();

    }

    public class GCMReg extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            }
            try {
                gcmRegId = gcm.register(GCM_SENDER_ID);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return gcmRegId;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                getSharedPreferences().edit().putString(GCMID, s).commit();
                new saveServer().execute();


            }
        }
    }


    public class saveServer extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            saveData = new HashMap<String, String>();
            saveData.put("usr", gmailId);
            saveData.put("gcmId", gcmRegId);


            try {
                JSONObject json = parser.makeHttpUrlConnection(php.gcm_reg, saveData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    result = "Error";
                } else {
                    result = "success";
                }


            } catch (Exception e) {
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            }
        }
    }


    private boolean isGoogelPlayInstalled() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) mContext,
                        ACTION_PLAY_SERVICES_DIALOG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Google Play Service is not installed",
                        Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;

    }

    /**
     * Initializes a DisplayImageOptions object. The drawable shown
     * while an image is loading is based on the current theme.
     */
    /*public void initDisplayImageOptions() {

        //Create a set of options to optimize the bitmap memory usage.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;

        int emptyColorPatch = UIElementsHelper.getEmptyColorPatch(this);
        mDisplayImageOptions = null;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(emptyColorPatch)
                .showImageOnFail(emptyColorPatch)
                .showImageOnLoading(emptyColorPatch)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .decodingOptions(options)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.ARGB_4444)
                .delayBeforeLoading(400)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();

    }*/

    /*
    * Returns the status bar height for the current layout configuration.
    */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    /*
     * Returns the navigation bar height for the current layout configuration.
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    /**
     * Returns the view container for the ActionBar.
     *
     * @return
     */
    public View getActionBarView(Activity activity) {
        Window window = activity.getWindow();
        View view = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");

        return view.findViewById(resId);
    }


    /**
     * Converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * Converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }



    /*
     *Getter
     */

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public Picasso getPicasso() {
        return mPicasso;
    }
}
