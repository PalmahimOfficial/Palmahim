package com.IAF.Palmahim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		ImageView imageView = (ImageView) findViewById(R.id.BaseHistoryButton);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openActivity(v);
			}
		});

	}

	private void openActivity(View view)
	{

		Intent intent = new Intent(MainActivity.this, ContentActivity.class);
		
		switch (view.getId())
		{
			case R.id.BaseHistoryButton: 
			{
				intent.putExtra("defaultName", "bus");
				break;
			}
			case R.id.NoalNineButton:
			{

				intent.putExtra("defaultName", "sas");
				break;
			}
		}

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
