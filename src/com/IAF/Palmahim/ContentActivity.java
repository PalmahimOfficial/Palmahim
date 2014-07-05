package com.IAF.Palmahim;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class ContentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_layout);

		ImageView imageView = (ImageView) findViewById(R.id.contentImageView);

		String imageName = getIntent().getStringExtra("defaultName");

		int id = getResources().getIdentifier("res/drawable/" + imageName, null, null);
		imageView.setImageResource(id);

		//imageView.setImageDrawable(getResources().getDrawable(R.drawable.bus));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
