package com.IAF.Palmahim;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity {

	public static MainActivity mainWindow;
	public String pageXml;
	private ImageSwitcher imageSwitcher;
	private Random rand;
	private int NUM_OF_SWITCHER_PHOTOS = 8;
	private Animation in;
	private Animation out;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mainWindow = this;
		
		pageXml = "Undefined";
		rand = new Random();
		
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		
		imageSwitcher = (ImageSwitcher) findViewById(R.id.photosView);
		
		
		//set footer banner imageSwitcher
		imageSwitcher.setFactory(new ViewFactory() {
		   public View makeView() {
			   ImageView imageView = new ImageView(imageSwitcher.getContext());
	             imageView.setScaleType(ImageView.ScaleType.CENTER);

	            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
	                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		      return imageView;
		   }
		});
		in = AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.slide_in_left);
		out = AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.slide_out_right);
		imageSwitcher.setInAnimation(in);
		imageSwitcher.setOutAnimation(out);
		imageSwitcher.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
                switchImage(null);
                imageSwitcher.postDelayed(this, 10000);
            }
        }, 10000);
		
		switchImage(null);

		

	}

	public void openBusPage(View view){
		pageXml = ((ImageButton)view).getContentDescription().toString();
	    Intent startActivity = new Intent(this, BusPage.class);
	    startActivity(startActivity);
	}
	
	public void openPageInformation(View view)
	{
		pageXml = ((ImageButton)view).getContentDescription().toString();
	    Intent startActivity = new Intent(this, TextInfoActivity.class);
	    startActivity(startActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void switchImage(View view){
		imageSwitcher.setInAnimation(in);
		imageSwitcher.setOutAnimation(out);
		imageSwitcher.setBackgroundResource(getNextImage(rand.nextInt(NUM_OF_SWITCHER_PHOTOS-1)));
	}

	private int getNextImage(int photoIndex) {
		String nextImage = "roll_bar_image_"+photoIndex;
		System.out.println("roll_bar_image_"+photoIndex);
		return getResources().getIdentifier("@drawable/" + nextImage, null, getPackageName());
	}
	
}
