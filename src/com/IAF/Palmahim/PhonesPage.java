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

	String CHOOSE_NAVIGATION_LOCATION = "אני רוצה להגיע ל";
	String NAVIGATION_TO_SOUTH_OPTION = "שער דרום";
	String NAVIGATION_TO_NORTH_OPTION = "שער צפון";
	String NAVIGATE_BY_CAR = "ברכב";
	String NAVIGATE_BY_TRANSIT = "בתחבורה ציבורית";
	private String BASE_COMPANY = "בסיס פלמחים";
	String[] Tables ={"אוטובוס צבאי 50 מקומות_שעה;מסלול;יום א';יום ב';יום ג';יום ד';יום ה'_09:00;רכבת יבנה לבסיס;.;.;.;.;._09:30;מהעץ לרכבת יבנה;.;.;.;.;._10:00;רכבת יבנה לבסיס;.;.;.;.;._11:00;רכבת יבנה לבסיס;.;.;.;.;._11:30;מהעץ למה\"ן;.;.;.;.;._11:45;מימה\"ן לרחבת העץ;.;.;.;.;._12:45;מהעץ למה\"ן;.;.;.;.;._13:00;ממה\"ן לרחבת העץ;.;.;.;.;._13:15;מהעץ לרכבת יבנה;-;-;-;-;._15:00;מהעץ לרכבת יבנה;.;.;.;.;._15:30;רכבת יבנה לבסיס;.;.;.;.;._16:00;מהעץ לראשונים;.;.;.;.;.",
			"אוטובוס צבאי 23 מקומות_שעה;מסלול;יום א';יום ב';יום ג';יום ד';יום ה'_07:20;רכבת יבנה לבסיס;.;.;.;.;._07:30;רכבת ראשונים לבסיס;.;.;.;.;._08:00;רכבת יבנה לבסיס;.;.;.;.;.",
			"אוטובוס צבאי 32 מקומות_שעה;מסלול;יום א';יום ב';יום ג';יום ד';יום ה'_06:40;הסעת קבע לרחובות;.;.;.;-;._07:45;רחבת העץ פיזור פנימי;.;.;.;.;._08:00;מה\"ן לרחבת העץ;.;.;.;.;._09:00;רכבת ראשונים לבסיס;.;.;.;.;._09:45;מהעץ לראשונים;.;.;.;.;._10:00;רכבת ראשונים לבסיס;.;.;.;.;._10:45;מה\"ן לרחבת העץ;.;.;.;.;._11:45;מבנה 23 + מוסכים;.;.;.;.;._13:00;מהעץ למיבנה 23 מוסכים;.;.;.;.;._13:30;רחבת העץ פיזור פנימי;.;.;.;.;._13:45;מה\"ן לרחבת העץ;.;.;.;.;._14:00;רחבת העץ פיזור פנימי;.;.;.;.;._14:00;רחבת העץ לרכבת יבנה;.;.;.;.;._14:15;מימה\"ן לרחבת העץ;.;.;.;.;._15:00;מהעץ לרכבת ראשונים;.;.;.;.;.",
			"אוטובוס צבאי 42 מקומות_שעה;מסלול;יום א';יום ב';יום ג';יום ד';יום ה'_06:40;הסעת קבע לוד;.;.;.;.;._07:45;רחבת העץ פיזור פנימי;.;.;.;.;._08:30;רחבת העץ פיזור פנימי;.;.;.;.;.",
	"ימי א' בלבד - אוטובוס אזרחי_שעה;מסלול;יום א';יום ב';יום ג';יום ד';יום ה'_07:30;רכבת יבנה לבסיס;.;-;-;-;-_08:00;מהעץ לרכבת יבנה;.;-;-;-;-_08:30;רכבת יבנה לבסיס;.;-;-;-;-_09:00;מהעץ לרכבת יבנה;.;-;-;-;-_09:30;רכבת יבנה לבסיס;.;-;-;-;-_10:00;מהעץ לרכבת יבנה;.;-;-;-;-_10:30;רכבת יבנה לבסיס;.;-;-;-;-_11:00;מהעץ לרכבת יבנה;.;-;-;-;-_11:30;רכבת יבנה לבסיס;.;-;-;-;-_12:00;מהעץ לרכבת יבנה;.;-;-;-;-_12:30;רכבת יבנה לבסיס;.;-;-;-;-_13:00;מהעץ לרכבת יבנה;.;-;-;-;-_13:30;רכבת יבנה לבסיס;.;-;-;-;-_"};
	String TREMPS_RULES = "חל איסור מוחלט לנסוע בטרמפים, מלבד מטרמפיאדת עין הקורא/יבנה ובהתאם לנהלים הר\"מ. בעת עלייה לטרמפ בצע שיחה קלה עם הנה בדוק אם יש לנהג אישור כניסה עדכני לבסיס/תעודת קצין/נגד/חוגר/צ\"א בדוק אם התמונה בתעודה תואמת לנהג ושהתעודה בתוקף אל תעלה על רכב שאינו בעל לוחית זיהוי ישראלית אל תעלה על רכב שנהגו עצר מיוזמתו והתנהגותו נראית חשודה אל תמסור את יעד נסיעתך אלא שאל את הנהג על יעד נסיעתו אל תיכנס לרכב שבו המושב הקדמי פנוי ובמושב האחורי יושבים נוסעים בזמן נסיעתך בטרמפ או בתחבורה ציבורית, דאג שנשקך עליך יאובטח כהלכה. לחיילות: אל תעלי לרכב שאינו צבאי אלא אם כן עולה איתך חייל נוסף. בימים א'-ה' בשעות: 07:00-11:00 , ישנו קצין/נגד תורן בטרמפיאדות עין הקורא וביבנה המוודא את הבדיקה הנ\"ל ומאשר לחיילים לעלות על טרמפים לבסיס בהתאם.";
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
