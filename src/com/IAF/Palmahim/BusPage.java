package com.IAF.Palmahim;

import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class BusPage extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_bus_page);
	}
	
	public void getDirections(View view){
		try
		{
		   String url = "waze://?q=תחנה מרכזית יבנה";
		    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
		   startActivity( intent );
		}
		catch ( ActivityNotFoundException ex  )
		{
		  Intent intent =
		    new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
		  startActivity(intent);
		}
	}
	
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }
	
	public void getTransitDirections(View view){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s) &dirflg=r", 31.888207f, 34.725875f, "טרמפיאדת יבנה");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
        	if(!appInstalledOrNot("com.google.android.maps.MapsActivity")){
        		startActivity(intent);
        	}else{
      		  Intent intent2 =
      			    new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.google.android.apps.maps" ) );
      			  startActivity(intent2);
        	}
        }
        catch(ActivityNotFoundException ex)
        {

        }
	}
}
