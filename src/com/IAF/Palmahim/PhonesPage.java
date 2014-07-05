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
import android.provider.ContactsContract;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PhonesPage extends Activity {

	String CHOOSE_NAVIGATION_LOCATION = "��� ���� ����� �";
	String NAVIGATION_TO_SOUTH_OPTION = "��� ����";
	String NAVIGATION_TO_NORTH_OPTION = "��� ����";
	String NAVIGATE_BY_CAR = "����";
	String NAVIGATE_BY_TRANSIT = "������� �������";
	private String BASE_COMPANY = "���� ������";
	String[] Tables ={"������� ���� 50 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_09:00;���� ���� �����;.;.;.;.;._09:30;���� ����� ����;.;.;.;.;._10:00;���� ���� �����;.;.;.;.;._11:00;���� ���� �����;.;.;.;.;._11:30;���� ���\"�;.;.;.;.;._11:45;����\"� ����� ���;.;.;.;.;._12:45;���� ���\"�;.;.;.;.;._13:00;���\"� ����� ���;.;.;.;.;._13:15;���� ����� ����;-;-;-;-;._15:00;���� ����� ����;.;.;.;.;._15:30;���� ���� �����;.;.;.;.;._16:00;���� ��������;.;.;.;.;.",
			"������� ���� 23 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_07:20;���� ���� �����;.;.;.;.;._07:30;���� ������� �����;.;.;.;.;._08:00;���� ���� �����;.;.;.;.;.",
			"������� ���� 32 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_06:40;���� ��� �������;.;.;.;-;._07:45;���� ��� ����� �����;.;.;.;.;._08:00;��\"� ����� ���;.;.;.;.;._09:00;���� ������� �����;.;.;.;.;._09:45;���� ��������;.;.;.;.;._10:00;���� ������� �����;.;.;.;.;._10:45;��\"� ����� ���;.;.;.;.;._11:45;���� 23 + ������;.;.;.;.;._13:00;���� ������ 23 ������;.;.;.;.;._13:30;���� ��� ����� �����;.;.;.;.;._13:45;��\"� ����� ���;.;.;.;.;._14:00;���� ��� ����� �����;.;.;.;.;._14:00;���� ��� ����� ����;.;.;.;.;._14:15;����\"� ����� ���;.;.;.;.;._15:00;���� ����� �������;.;.;.;.;.",
			"������� ���� 42 ������_���;�����;��� �';��� �';��� �';��� �';��� �'_06:40;���� ��� ���;.;.;.;.;._07:45;���� ��� ����� �����;.;.;.;.;._08:30;���� ��� ����� �����;.;.;.;.;.",
	"��� �' ���� - ������� �����_���;�����;��� �';��� �';��� �';��� �';��� �'_07:30;���� ���� �����;.;-;-;-;-_08:00;���� ����� ����;.;-;-;-;-_08:30;���� ���� �����;.;-;-;-;-_09:00;���� ����� ����;.;-;-;-;-_09:30;���� ���� �����;.;-;-;-;-_10:00;���� ����� ����;.;-;-;-;-_10:30;���� ���� �����;.;-;-;-;-_11:00;���� ����� ����;.;-;-;-;-_11:30;���� ���� �����;.;-;-;-;-_12:00;���� ����� ����;.;-;-;-;-_12:30;���� ���� �����;.;-;-;-;-_13:00;���� ����� ����;.;-;-;-;-_13:30;���� ���� �����;.;-;-;-;-_"};
	String TREMPS_RULES = "�� ����� ����� ����� �������, ���� ��������� ��� �����/���� ������ ������ ��\"�. ��� ����� ����� ��� ���� ��� �� ��� ���� �� �� ���� ����� ����� ����� �����/����� ����/���/����/�\"� ���� �� ������ ������ ����� ���� �������� ����� �� ���� �� ��� ����� ��� ����� ����� ������� �� ���� �� ��� ����� ��� ������� ��������� ����� ����� �� ����� �� ��� ������ ��� ��� �� ���� �� ��� ������ �� ����� ���� ��� ����� ����� ���� ������ ������ ������ ������ ���� ������ ����� �� ������� �������, ��� ����� ���� ������ �����. �������: �� ���� ���� ����� ���� ��� �� �� ���� ���� ���� ����. ����� �'-�' �����: 07:00-11:00 , ���� ����/��� ���� ���������� ��� ����� ������ ������ �� ������ ��\"� ����� ������� ����� �� ������ ����� �����.";
	int TABLES_SIZE;
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

	private class PhoneTableHandler extends TableHandler{
		
		public PhoneTableHandler(Context context, LinearLayout parent) {
			super(context, parent);
			// TODO Auto-generated constructor stub
		}

		@Override
		public TableRow getCustomTableRow(Context context) {
			TableRow row = new TableRow(context);
			row.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String text = ((TextView)v).getText().toString();
					String[] nameAndPhone = text.split(":");
					addAsContactConfirmed(nameAndPhone[0] , nameAndPhone[1]);
					
				}
			});
			return row;
		}
	}
	
	public void addAsContactConfirmed(String contactName , String contactNumber) {

		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

		intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName);
		intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactNumber);
		intent.putExtra(ContactsContract.Intents.Insert.COMPANY, BASE_COMPANY);

		this.startActivity(intent);

	}
	
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


		setContentView(R.layout.activity_phone_page);

		TABLES_SIZE = (int) getResources().getDimension(R.dimen.busPageTablesSize);
		ScrollView sv = (ScrollView) findViewById(R.id.scrollBodyContent);
		LinearLayout linearLayout = (LinearLayout)sv.findViewById(R.id.pageBodyContent);
		TextView TrempsRules = (TextView)linearLayout.findViewById(R.id.phonesText);
		TrempsRules.setText(TREMPS_RULES);

		//build First Table
		TableHandler tH = new TableHandler(getApplicationContext(), linearLayout);
		for(int i=0 ; i<Tables.length; i++){
			tH.buildTable(Tables[i],TABLES_SIZE,false);
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

}
