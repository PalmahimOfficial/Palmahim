package com.IAF.Palmahim;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	public static MainActivity mainWindow;
	public String pageTitle, pageBody;
	public Drawable pageImageDrawable;
	private String[] pageTitles, pageBodys, pageImages;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mainWindow = this;
		
		pageTitle = "Undefined";
		pageBody = "Undefined";
		pageImageDrawable = null;
		
		pageTitles = getResources().getStringArray(R.array.page_titles);
		pageBodys = getResources().getStringArray(R.array.page_bodys);
		pageImages = getResources().getStringArray(R.array.page_images);
				
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

	}

	public void openPageInformation(View view)
	{
		int pageNumber = Integer.parseInt(((ImageButton)view).getContentDescription().toString());
		pageTitle = pageTitles[pageNumber];
		pageBody = pageBodys[pageNumber];
		if (pageImages[pageNumber].compareTo("null") == 0){
			pageImageDrawable = null;
		}
		else{
			pageImageDrawable = null; // TODO: Gil Nisan should do some logic here
		}
	    Intent startActivity = new Intent(this, TextInfoActivity.class);
	    startActivity(startActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
