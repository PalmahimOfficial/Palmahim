package com.IAF.Palmahim;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class TextInfoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_text_info);
		
	    Typeface face=Typeface.createFromAsset(getAssets(),
                "fonts/Tahoma.ttf");
		
		((TextView)findViewById(R.id.pageTextTitleText)).setText(MainActivity.mainWindow.pageTitle);
		((TextView)findViewById(R.id.pageTextTitleText)).setTypeface(face, Typeface.BOLD);
		((TextView)findViewById(R.id.pageBodyText)).setText(MainActivity.mainWindow.pageBody);
		((TextView)findViewById(R.id.pageBodyText)).setTypeface(face, Typeface.BOLD);
	}

}
