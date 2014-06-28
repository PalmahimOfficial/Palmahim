package com.IAF.Palmahim;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

	// For Splash Screen
	
    private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 5000;
    private ImageView splash;
	private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what) {
                case STOPSPLASH:
                        //remove SplashScreen from view
                        splash.setVisibility(View.GONE);
                		for (View v : mainViews){
                			v.setVisibility(View.VISIBLE);
                		}
                        break;
                }
                super.handleMessage(msg);
        }
	};
	private View[] mainViews;
    
	public static MainActivity mainWindow;
	public String pageXml;
	private ImageSwitcher imageSwitcher;
	private ImageView footerImageView;
	private Random rand;
	private int NUM_OF_SWITCHER_PHOTOS = 8;
	private Animation in;
	private Animation out;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		mainWindow = this;
		
		mainViews = new View[]{
				findViewById(R.id.scrollView1),
				findViewById(R.id.photosView),
				findViewById(R.id.headerMainActivity)
		};
		for (View v : mainViews){
			v.setVisibility(View.INVISIBLE);
		}
		
		splash = (ImageView) findViewById(R.id.splashscreenimage);
		splash.setVisibility(View.VISIBLE);
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		
		pageXml = "Undefined";
		rand = new Random();
		
		footerImageView = (ImageView) findViewById(R.id.photosView);
		
		
		//set footer banner imageSwitcher
/*		footerImageView.setFactory(new ViewFactory() {
		   public View makeView() {
			   ImageView imageView = new ImageView(footerImageView.getContext());
	             imageView.setScaleType(ImageView.ScaleType.CENTER);

	            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
	                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		      return imageView;
		   }
		});*/
		in = AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in);
		out = AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.slide_out_right);
		footerImageView.setAnimation(in);
		footerImageView.startAnimation(in);
		footerImageView.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
                switchImage(null);
                footerImageView.postDelayed(this, 10000);
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
		footerImageView.startAnimation(in);
		footerImageView.setImageDrawable(getResources().getDrawable(getNextImage(rand.nextInt(NUM_OF_SWITCHER_PHOTOS-1))));
	}

	private int getNextImage(int photoIndex) {
		String nextImage = "roll_bar_image_"+photoIndex;
		
		return getResources().getIdentifier("@drawable/" + nextImage, null, getPackageName());
	}
	
}
