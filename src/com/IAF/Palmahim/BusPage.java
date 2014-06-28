package com.IAF.Palmahim;

import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BusPage extends Activity {

	String CHOOSE_NAVIGATION_LOCATION = "��� ���� ����� �";
	String NAVIGATION_TO_SOUTH_OPTION = "��� ����";
	String NAVIGATION_TO_NORTH_OPTION = "��� ����";
	String NAVIGATE_BY_CAR = "����";
	String NAVIGATE_BY_TRANSIT = "������� �������";
	String[] Tables ={"������� ���� 50 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_09:00;���� ���� �����;.;.;.;.;._09:30;���� ����� ����;.;.;.;.;._10:00;���� ���� �����;.;.;.;.;._11:00;���� ���� �����;.;.;.;.;._11:30;���� ���\"�;.;.;.;.;._11:45;����\"� ����� ���;.;.;.;.;._12:45;���� ���\"�;.;.;.;.;._13:00;���\"� ����� ���;.;.;.;.;._13:15;���� ����� ����;-;-;-;-;._15:00;���� ����� ����;.;.;.;.;._15:30;���� ���� �����;.;.;.;.;._16:00;���� ��������;.;.;.;.;.",
			"������� ���� 23 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_07:20;���� ���� �����;.;.;.;.;._07:30;���� ������� �����;.;.;.;.;._08:00;���� ���� �����;.;.;.;.;.",
			"������� ���� 32 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_06:40;���� ��� �������;.;.;.;-;._07:45;���� ��� ����� �����;.;.;.;.;._08:00;��\"� ����� ���;.;.;.;.;._09:00;���� ������� �����;.;.;.;.;._09:45;���� ��������;.;.;.;.;._10:00;���� ������� �����;.;.;.;.;._10:45;��\"� ����� ���;.;.;.;.;._11:45;���� 23 + ������;.;.;.;.;._13:00;���� ������ 23 ������;.;.;.;.;._13:30;���� ��� ����� �����;.;.;.;.;._13:45;��\"� ����� ���;.;.;.;.;._14:00;���� ��� ����� �����;.;.;.;.;._14:00;���� ��� ����� ����;.;.;.;.;._14:15;����\"� ����� ���;.;.;.;.;._15:00;���� ����� �������;.;.;.;.;.",
			"������� ���� 42 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_06:40;���� ��� ���;.;.;.;.;._07:45;���� ��� ����� �����;.;.;.;.;._08:30;���� ��� ����� �����;.;.;.;.;.",
			"��� �' ���� - ������� �����_���;�����;��� �';��� �';��� �';��� �';��� �'_07:30;���� ���� �����.;-;-;-;-_08:00;���� ����� ����;.;-;-;-;-_08:30;���� ���� �����;.;-;-;-;-_09:00;���� ����� ����;.;-;-;-;-_09:30;���� ���� �����;.;-;-;-;-_10:00;���� ����� ����;.;-;-;-;-_10:30;���� ���� �����;.;-;-;-;-_11:00;���� ����� ����;.;-;-;-;-_11:30;���� ���� �����;.;-;-;-;-_12:00;���� ����� ����;.;-;-;-;-_12:30;���� ���� �����;.;-;-;-;-_13:00;���� ����� ����;.;-;-;-;-_13:30;���� ���� �����;.;-;-;-;-_"};
	String TREMPS_RULES = "�� ����� ����� ����� �������, ���� ��������� ��� �����/���� ������ ������ ��\"�. ��� ����� ����� ��� ���� ��� �� ��� ���� �� �� ���� ����� ����� ����� �����/����� ����/���/����/�\"� ���� �� ������ ������ ����� ���� �������� ����� �� ���� �� ��� ����� ��� ����� ����� ������� �� ���� �� ��� ����� ��� ������� ��������� ����� ����� �� ����� �� ��� ������ ��� ��� �� ���� �� ��� ������ �� ����� ���� ��� ����� ����� ���� ������ ������ ������ ������ ���� ������ ����� �� ������� �������, ��� ����� ���� ������ �����. �������: �� ���� ���� ����� ���� ��� �� �� ���� ���� ���� ����. ����� �'-�' �����: 07:00-11:00 , ���� ����/��� ���� ���������� ��� ����� ������ ������ �� ������ ��\"� ����� ������� ����� �� ������ ����� �����.";
	
	LinearLayout navigationLayout = null;
	TextView staticTextView = null;
	int[] COLORS = {R.color.darkred,R.color.blue};
	OnClickListener navigateToSouthListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			RelativeLayout navigationRelativeLayout = (RelativeLayout)v.getParent().getParent();
			staticTextView = (TextView)navigationRelativeLayout.getChildAt(0);
			navigationLayout = (LinearLayout)navigationRelativeLayout.getChildAt(1);

			String mystring=CHOOSE_NAVIGATION_LOCATION+NAVIGATION_TO_SOUTH_OPTION;
			SpannableString content = new SpannableString(mystring);
			content.setSpan(new UnderlineSpan(), mystring.length()-NAVIGATION_TO_SOUTH_OPTION.length(), mystring.length(), 0);
			staticTextView.setText(content);
			staticTextView.setClickable(true);
			staticTextView.setOnClickListener(returnToRootNavigation);

			TextView navigateToSouthView = (TextView) navigationLayout.getChildAt(0);
			navigateToSouthView.setText(NAVIGATE_BY_CAR);
			navigateToSouthView.setOnClickListener(navigateToSouthByCarListener);

			TextView navigateToNorthView = (TextView) navigationLayout.getChildAt(1);
			navigateToNorthView.setText(NAVIGATE_BY_TRANSIT);
			navigateToNorthView.setOnClickListener(navigateToSouthByTransitListener);

		}
	};
	OnClickListener navigateToNorthListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			RelativeLayout navigationRelativeLayout = (RelativeLayout)v.getParent().getParent();
			staticTextView = (TextView)navigationRelativeLayout.getChildAt(0);
			navigationLayout = (LinearLayout)navigationRelativeLayout.getChildAt(1);

			String mystring=CHOOSE_NAVIGATION_LOCATION+NAVIGATION_TO_NORTH_OPTION;
			SpannableString content = new SpannableString(mystring);
			content.setSpan(new UnderlineSpan(), mystring.length()-NAVIGATION_TO_NORTH_OPTION.length(), mystring.length(), 0);
			staticTextView.setText(content);
			staticTextView.setClickable(true);
			staticTextView.setOnClickListener(returnToRootNavigation);

			TextView navigateToSouthView = (TextView) navigationLayout.getChildAt(0);
			navigateToSouthView.setText(NAVIGATE_BY_CAR);
			navigateToSouthView.setOnClickListener(navigateToNorthByCarListener);

			TextView navigateToNorthView = (TextView) navigationLayout.getChildAt(1);
			navigateToNorthView.setText(NAVIGATE_BY_TRANSIT);
			navigateToNorthView.setOnClickListener(navigateToNorthByTransitListener);
		}
	};

	OnClickListener returnToRootNavigation = new OnClickListener() {

		@Override
		public void onClick(View v) {
			RelativeLayout navigationRelativeLayout = (RelativeLayout)v.getParent();
			staticTextView = (TextView)navigationRelativeLayout.getChildAt(0);
			navigationLayout = (LinearLayout)navigationRelativeLayout.getChildAt(1);

			staticTextView.setText(CHOOSE_NAVIGATION_LOCATION);
			Typeface face=Typeface.createFromAsset(getAssets(),
					"fonts/Tahoma.ttf");
			staticTextView.setTypeface(face,Typeface.BOLD);
			staticTextView.setTextColor(getResources().getColor(R.color.white));
			staticTextView.setVisibility(View.VISIBLE);

			TextView navigateToSouthView = (TextView) navigationLayout.getChildAt(0);
			navigateToSouthView.setText(NAVIGATION_TO_SOUTH_OPTION);
			navigateToSouthView.setClickable(true);
			navigateToSouthView.setOnClickListener(navigateToSouthListener);

			TextView navigateToNorthView = (TextView) navigationLayout.getChildAt(1);
			navigateToNorthView.setText(NAVIGATION_TO_NORTH_OPTION);
			navigateToNorthView.setClickable(true);
			navigateToNorthView.setOnClickListener(navigateToNorthListener);

		}
	};

	OnClickListener navigateToSouthByTransitListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			getTransitDirections(31.888207f, 34.725875f, "�������� ����");
		}
	};

	OnClickListener navigateToNorthByTransitListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			getTransitDirections(31.953852f, 34.778743f, "�������� ��� �����");
		}
	};
	OnClickListener navigateToSouthByCarListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			getDirections("������ ����� ������");
		}
	};
	OnClickListener navigateToNorthByCarListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			getDirections("������ ����� ������");
		}
	};
	private TextView buildTextView(){   
		// the following change is what fixed it
		TextView textView = new TextView(this);
		Typeface face=Typeface.createFromAsset(getAssets(),
				"fonts/Tahoma.ttf");
		LayoutParams paramsExample = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		//textView.setId(id);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(getResources().getColor(android.R.color.white));
		textView.setTextSize(getResources().getDimension(R.dimen.head_link_text_size));
		textView.setPadding(5, 5, 5, 5);
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


		setContentView(R.layout.activity_bus_page);


		ScrollView sv = (ScrollView) findViewById(R.id.scrollBodyContent);
		LinearLayout linearLayout = (LinearLayout)sv.findViewById(R.id.pageBodyContent);

		TextView TrempsRules = (TextView)linearLayout.findViewById(R.id.TrempRules);
		TrempsRules.setText(TREMPS_RULES);
		
		//build First Table
		for(int i=0 ; i<Tables.length; i++){
			TableLayout tableLayout = (TableLayout)linearLayout.getChildAt(2+i);
			tableLayout.setPadding(0, 10, 0, 10);
			buildTable(tableLayout ,Tables[i],7);
		}

		RelativeLayout navigationRelativeLayout = (RelativeLayout)this.findViewById(R.id.menuLayout);
		staticTextView = (TextView)navigationRelativeLayout.findViewById(R.id.navigationStaticText);
		navigationLayout = (LinearLayout)navigationRelativeLayout.findViewById(R.id.navigationLinearLayout);

		staticTextView.setText(CHOOSE_NAVIGATION_LOCATION);
		Typeface face=Typeface.createFromAsset(getAssets(),
				"fonts/Tahoma.ttf");
		staticTextView.setTypeface(face,Typeface.BOLD);
		staticTextView.setTextColor(getResources().getColor(R.color.white));
		staticTextView.setVisibility(View.VISIBLE);

		TextView navigateToSouthView = buildTextView();
		navigateToSouthView.setText(NAVIGATION_TO_SOUTH_OPTION);
		navigateToSouthView.setClickable(true);
		navigateToSouthView.setOnClickListener(navigateToSouthListener);

		TextView navigateToNorthView = buildTextView();
		navigateToNorthView.setText(NAVIGATION_TO_NORTH_OPTION);
		navigateToNorthView.setClickable(true);
		navigateToNorthView.setOnClickListener(navigateToNorthListener);


		navigationLayout.addView(navigateToSouthView);
		navigationLayout.addView(navigateToNorthView);

	}

	public void getDirections(String location){
		try
		{
			String url = "waze://?q="+location;
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

	public void getTransitDirections(float x, float y, String pinName){
		String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s) &dirflg=r",x,y,pinName); //, , "�������� ����");
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

	private void buildTable(TableLayout tableLayout ,String content ,int numOfCols){
		String[] rows  = content.split("_");

		//tableLayout.setColumnStretchable(numOfCols-2, true);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		tableLayout.removeAllViews();


		for(int i=0;i<rows.length;i++){
			String row  = rows[i];
			TableRow tableRow = new TableRow(getApplicationContext());
			if(i<=1){
				tableRow.setBackgroundColor(getResources().getColor(R.color.darkblue));
			}
			else{
				tableRow.setBackgroundColor(getResources().getColor(getNextColor(i)));
			}
			tableRow.setLayoutParams(rowParams);
			if(i==0){
				tableRow.setWeightSum(1f);
				tableRow.setBackgroundColor(getResources().getColor(R.color.white));
			}
			else{
				tableRow.setWeightSum(numOfCols +1);
			}
			tableRow.setGravity(Gravity.RIGHT);
			final String[] cols = row.split(";");


			for (int j = cols.length-1; j >=0; j--) {

				String col = cols[j];                                 
				final TextView columsView = buildTextView();
				if(col.equals(".")){
					col =Html.fromHtml("&#8226;").toString();
				}
				if(i==0){
					columsView.setTypeface(columsView.getTypeface(), Typeface.BOLD);
					columsView.setTextColor(getResources().getColor(R.color.black));
					columsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
				}
				else{
					if(j == 1){
						columsView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,2f));
					}
					else{
						columsView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1f));
					}
				}
				columsView.setText(col);//String.format("%7s", col));
				tableRow.addView(columsView);
			}
			tableLayout.addView(tableRow,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}




	private int getNextColor(int i) {
		return COLORS[i%2];
	}
}
