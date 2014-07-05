package com.IAF.Palmahim;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class MainActivity extends Activity {

	// For Splash Screen
	
    public static final int STARTSPLASH = 0;
    public static final int STOPSPLASH = 1;
    //time in milliseconds
    private static final long SPLASHTIME = 3000;
    private static final int ENLARGE_FACTOR = 5;
    private ImageView splash;
	public Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what) {
                case STARTSPLASH:
	                Message newMsg = new Message();
	        		newMsg.what = STOPSPLASH;
	        		splashHandler.sendMessageDelayed(newMsg, SPLASHTIME);
                	break;
                case STOPSPLASH:
                	setContentView(R.layout.activity_main);
                        //remove SplashScreen from view
                        /*splash.setVisibility(View.GONE);
                		for (View v : mainViews){
                			v.setVisibility(View.VISIBLE);
                		}*/
                		
                		pageXml = "Undefined";
                		rand = new Random();
                		
                		footerImageView = (LinearLayout) findViewById(R.id.baseImages);
                		
                		
                		createFooterImagesView();
                		
                		//set footer banner imageSwitcher
                		/*footerImageView.setFactory(new ViewFactory() {
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
//                		footerImageView.postDelayed(new Runnable() {
//                            int i = 0;
//                            public void run() {
//                                switchImage(null);
//                                footerImageView.postDelayed(this, 10000);
//                            }
//                        }, 10000);
//                		
//                		switchImage(null);
                		
                        break;
                }
                super.handleMessage(msg);
        }
	};
	private View[] mainViews;
    
	public static MainActivity mainWindow;
	public String pageXml;
	private ImageSwitcher imageSwitcher;
	private LinearLayout footerImageView;
	private Random rand;
	private int NUM_OF_SWITCHER_PHOTOS = 18;
	private Animation in;
	private Animation out;
	
	private void setOnClickForAllImages(boolean isClickable){
		for(int i=0; i<footerImageView.getChildCount(); i++){
			ImageView image = (ImageView) footerImageView.getChildAt(i);
			image.setClickable(isClickable);
		}
	}
	
	private void createFooterImagesView() {
		int photoIndex = 0;
		
		boolean allPhotosFound = false;
		while(!allPhotosFound){
			String nextImage = "roll_bar_image_"+ photoIndex++;
			int photoResourceId = getResources().getIdentifier("@drawable/" + nextImage, null, getPackageName());
			allPhotosFound = (0 == photoResourceId);
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(photoResourceId);
			imageView.setLayoutParams(new LayoutParams((int)getResources().getDimension(R.dimen.MainActivityRollImageHorizontalSize),(int) getResources().getDimension(R.dimen.MainActivityRollImageVerticalSize)));
			imageView.setClickable(true);
			imageView.setOnClickListener(new OnClickListener() {
				private int imageIndex = 0;
				private ImageView imageView;
				private OnClickListener enlargedListener =  new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.baseImagesScroolView);
						setOnClickForAllImages(true);
						LinearLayout ll = (LinearLayout)hsv.getChildAt(0);
						v.setVisibility(ImageView.INVISIBLE);
						imageView.setVisibility(ImageView.VISIBLE);
						//ll.addView(imageView, imageIndex);
						FX.slide_down(getApplicationContext(), imageView);
					}
				};
				@Override
				public void onClick(View v) {
					imageView = (ImageView) v;
					LinearLayout p = (LinearLayout) v.getParent();
					setOnClickForAllImages(false);
					imageIndex = p.indexOfChild(v);
					//p.removeViewAt(imageIndex);
					v.setVisibility(ImageView.GONE);
					ImageView iv = (ImageView)findViewById(R.id.enlargedImage);
//					if(iv.getDrawable() != null){
//						enlargedListener.onClick(iv);
//					}
					iv.setImageDrawable(((ImageView)v).getDrawable());
					TableRow.LayoutParams lp = (TableRow.LayoutParams) imageView.getLayoutParams();
					RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(lp.width*ENLARGE_FACTOR,lp.height*ENLARGE_FACTOR);
					rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
					iv.setLayoutParams(rl);
					iv.setVisibility(ImageView.VISIBLE);
					iv.setClickable(true);
					iv.setOnClickListener(enlargedListener);
					FX.slide_up(getApplicationContext(), imageView);
					
				}
			});
			footerImageView.addView(imageView,imageView.getLayoutParams());
		}
	}
	private TextView buildTextView(){   
		// the following change is what fixed it
		TextView textView = new TextView(this);
		Typeface face=Typeface.createFromAsset(getAssets(),
				"fonts/Tahoma.ttf");
		LayoutParams paramsExample = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		//textView.setId(id);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(getResources().getColor(android.R.color.black));
		textView.setTextSize(getResources().getDimension(R.dimen.pages_body_text_size));
		textView.setTypeface(face, Typeface.NORMAL);
		textView.setLayoutParams(paramsExample);
		return textView;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash_screen_layout);
		
		mainWindow = this;
		
	    ChangeLog cl = new ChangeLog(this);
	    if (cl.firstRun()){
	    	cl.getLogDialog().show();
	    }
	    else{
    		Message newMsg = new Message();
    		newMsg.what = STOPSPLASH;
    		splashHandler.sendMessageDelayed(newMsg, SPLASHTIME);
	    }
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
	
//	public void switchImage(View view){
//		footerImageView.startAnimation(in);
//		footerImageView.setImageDrawable(getResources().getDrawable(getNextImage(rand.nextInt(NUM_OF_SWITCHER_PHOTOS-1))));
//	}
//
//	private int getNextImage(int photoIndex) {
//		String nextImage = "roll_bar_image_"+photoIndex;
//		
//		return getResources().getIdentifier("@drawable/" + nextImage, null, getPackageName());
//	}
	
}
