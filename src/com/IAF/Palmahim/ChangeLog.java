package com.IAF.Palmahim;

import java.io.IOException;
import java.util.zip.Inflater;

import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChangeLog {

    private final Context context;
    private String lastVersion, thisVersion;

    // this is the key for storing the version name in SharedPreferences
    private static final String VERSION_KEY = "PREFS_VERSION_KEY";
    private View activeChangeLog = null;
    private XMLChangeLogPageParser parser;
    private static final String NO_VERSION = "";

    /**
     * Constructor
     *
     * Retrieves the version names and stores the new version name in
     * SharedPreferences
     *
     * @param context
     */
    public ChangeLog(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
        activeChangeLog = View.inflate(context, R.layout.alert_screen_layout, null);
        parser = new XMLChangeLogPageParser(context);
    }

    /**
     * Constructor
     *
     * Retrieves the version names and stores the new version name in
     * SharedPreferences
     *
     * @param context
     * @param sp
     *            the shared preferences to store the last version name into
     */
    public ChangeLog(Context context, SharedPreferences sp) {
        this.context = context;

        // get version numbers
        this.lastVersion = sp.getString(VERSION_KEY, NO_VERSION);
        Log.d(TAG, "lastVersion: " + lastVersion);
        try {
            this.thisVersion = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            this.thisVersion = NO_VERSION;
            Log.e(TAG, "could not get version name from manifest!");
            e.printStackTrace();
        }
        Log.d(TAG, "appVersion: " + this.thisVersion);
    }

    /**
     * @return The version name of the last installation of this app (as
     *         described in the former manifest). This will be the same as
     *         returned by <code>getThisVersion()</code> the second time this
     *         version of the app is launched (more precisely: the second time
     *         ChangeLog is instantiated).
     * @see AndroidManifest.xml#android:versionName
     */
    public String getLastVersion() {
        return this.lastVersion;
    }

    /**
     * @return The version name of this app as described in the manifest.
     * @see AndroidManifest.xml#android:versionName
     */
    public String getThisVersion() {
        return this.thisVersion;
    }

    /**
     * @return <code>true</code> if this version of your app is started the
     *         first time
     */
    public boolean firstRun() {
        return !this.lastVersion.equals(this.thisVersion);
    }

    /**
     * @return <code>true</code> if your app including ChangeLog is started the
     *         first time ever. Also <code>true</code> if your app was
     *         deinstalled and installed again.
     */
    public boolean firstRunEver() {
        return NO_VERSION.equals(this.lastVersion);
    }

    /**
     * @return An AlertDialog displaying the changes since the previous
     *         installed version of your app (what's new). But when this is the
     *         first run of your app including ChangeLog then the full log
     *         dialog is show.
     */
    public AlertDialog getLogDialog() {
        return this.getDialog();
    }

    /**
     * @return an AlertDialog with a full change log displayed
     */
    public AlertDialog getFullLogDialog() {
        return this.getDialog();
    }

    private class XMLChangeLogPageParser extends XMLCustomParser{

		public XMLChangeLogPageParser(Context context) {
			super(context);
		}

		@Override
		public void setup() {
			scrollView = (ScrollView)activeChangeLog.findViewById(R.id.scrollBodyContent);
			addTextTo = (LinearLayout)scrollView.getChildAt(0);
			pageBody = (LinearLayout)scrollView.getChildAt(0);
			scrollBodyLinksContent = null;
			pageSections = null;
			face=Typeface.createFromAsset(context.getAssets(),
					"fonts/Tahoma.ttf");
		}

		@Override
		public RelativeLayout getFooterLayout() {
			return null;
		}

		@Override
		public void setHeadText(String textValue) {
			((TextView)activeChangeLog.findViewById(R.id.pageTextTitleText)).setText(textValue);
			((TextView)activeChangeLog.findViewById(R.id.pageTextTitleText)).setTypeface(face, Typeface.BOLD);
		}

		@Override
		public int getXML() {
			return context.getResources().getIdentifier("@xml/" + "change_log", null, context.getPackageName());
		}
		
	}
    
    private AlertDialog getDialog() {
//    	ImageView firstTimeImage = new ImageView(this.context);
//    	firstTimeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.first_time_screen));

        AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(
                        this.context, android.R.style.Theme_Dialog));
        try {
			parser.generatePageFromAnXML();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        builder.setView(activeChangeLog)
                .setCancelable(false)
                // OK button
                .setPositiveButton(
                        context.getResources().getString(
                                R.string.changelog_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                updateVersionInPreferences();
                        		Message newMsg = new Message();
                        		newMsg.what = MainActivity.STARTSPLASH;
                        		MainActivity.mainWindow.splashHandler.sendMessage(newMsg);
                            }
                        });

        return builder.create();
    }

    private void updateVersionInPreferences() {
        // save new version number to preferences
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(VERSION_KEY, "0"); // TODO: thisVersion
        // // on SDK-Versions > 9 you should use this:
        // if(Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
        // editor.commit();
        // } else {
        // editor.apply();
        // }
        editor.commit();
    }
    
    private static final String TAG = "ChangeLog";
}