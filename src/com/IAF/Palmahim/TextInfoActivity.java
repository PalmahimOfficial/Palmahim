package com.IAF.Palmahim;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.dimen;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.Layout;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class TextInfoActivity extends Activity {
	private enum TagState{
		DEFAULT(""),
		PAGE("page"),
		HEAD("head"),
		SECTIONS("sections"),
		SECTION("section"),
		HEADER("header"),
		TEXT("text"),
		BOLD("bold"),
		BULLET("bullet"),
		IMAGE("image"),
		NUMBER("number"),
		RIGHT("right"),
		LEFT("left"),
		ABOVE("above"),
		BELOW("below");

		String tag_name;

		private TagState (String tagName){
			this.tag_name = tagName;
		}

		public static TagState getEnum (String tagName){
			if(tagName.compareTo(PAGE.tag_name)==0){
				return PAGE;
			}
			else if(tagName.compareTo(HEAD.tag_name)==0){
				return HEAD;
			}
			else if(tagName.compareTo(HEADER.tag_name)==0){
				return HEADER;
			}
			else if(tagName.compareTo(TEXT.tag_name)==0){
				return TEXT;
			}
			else if(tagName.compareTo(BOLD.tag_name)==0){
				return BOLD;
			}
			else if(tagName.compareTo(BULLET.tag_name)==0){
				return BULLET;
			}
			else if(tagName.compareTo(IMAGE.tag_name)==0){
				return IMAGE;
			}
			else if(tagName.compareTo(RIGHT.tag_name)==0){
				return RIGHT;
			}
			else if(tagName.compareTo(LEFT.tag_name)==0){
				return LEFT;
			}
			else if(tagName.compareTo(ABOVE.tag_name)==0){
				return ABOVE;
			}
			else if(tagName.compareTo(BELOW.tag_name)==0){
				return BELOW;
			}
			else if(tagName.compareTo(TEXT.tag_name)==0){
				return TEXT;
			}
			else if(tagName.compareTo(NUMBER.tag_name)==0){
				return NUMBER;
			}

			else{
				return DEFAULT;
			}
		}
	};

	private String BASE_COMPANY = "בסיס פלמחים";
	private int sectionId = 0;
	private TagState tagState = TagState.DEFAULT;
	private boolean isImage = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_text_info);

		try {
			generatePageFromAnXML();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private TextView buildTextView(int id){   
		// the following change is what fixed it
		TextView textView = new TextView(this);
		Typeface face=Typeface.createFromAsset(getAssets(),
				"fonts/Tahoma.ttf");
		LayoutParams paramsExample = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		//textView.setId(id);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(getResources().getColor(android.R.color.black));
		textView.setTextSize(getResources().getDimension(R.dimen.pages_body_text_size));
		((TextView)findViewById(R.id.pageTextTitleText)).setTypeface(face, Typeface.NORMAL);
		textView.setLayoutParams(paramsExample);
		return textView;
	}

	@SuppressLint("InlinedApi") private void generatePageFromAnXML()
			throws XmlPullParserException, IOException
			{
		Typeface face=Typeface.createFromAsset(getAssets(),
				"fonts/Tahoma.ttf");
		ScrollView scrollView = (ScrollView)findViewById(R.id.scrollBodyContent);
		LinearLayout pageBody = (LinearLayout)scrollView.getChildAt(0);

		//image Layouts and texts
		RelativeLayout relativeImageTextLayout = null;
		LinearLayout imageTextLinearLayout= new LinearLayout(this);
		ImageView imgView = null;

		//text Tag TextView
		TextView textView = buildTextView(sectionId++);

		
		LinearLayout addTextTo = pageBody;
		Resources res = this.getResources();
		XmlResourceParser xpp = res.getXml(R.xml.keva);
		xpp.next();
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_TAG)
			{
				tagState = TagState.getEnum(xpp.getName());
				switch(tagState){
					case IMAGE:
					{
						isImage = true;
						
						relativeImageTextLayout = new RelativeLayout(this);
						imgView = new ImageView(this);
						imgView.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.MATCH_PARENT));
						relativeImageTextLayout.setGravity(Gravity.CENTER);
						imgView.setId(sectionId++);
						
						imageTextLinearLayout.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.MATCH_PARENT));
						imageTextLinearLayout.setOrientation(LinearLayout.VERTICAL);
						imageTextLinearLayout.setPadding(7, 7, 7, 7);
						imageTextLinearLayout.setId(sectionId++);
						addTextTo = imageTextLinearLayout;
						break;
					}
					case HEADER:
					{
						textView = buildTextView(sectionId++);
						break;
					}
					case TEXT:
					{
						textView = buildTextView(sectionId++);
						break;
					}
					case BOLD:
					{
						textView = buildTextView(sectionId++);
						break;
					}
					case BULLET:
					{
						textView = buildTextView(sectionId++);
						break;
					}
					case NUMBER:
					{
						textView = buildTextView(sectionId++);
						break;
					}
					
				}
			}
			else if(eventType == XmlPullParser.END_TAG)
			{
				switch (tagState){
				case HEAD:
					tagState = TagState.PAGE;
					break;
				case SECTIONS:
					tagState = TagState.PAGE;
					break;
				case SECTION:
					tagState = TagState.SECTIONS;
					break;
				case TEXT:
				{
					addTextTo.addView(textView, addTextTo.getChildCount());
					if(!isImage){
						tagState = TagState.SECTION;
					}
					else{
						tagState = TagState.IMAGE;
					}
					break;
				}
				case BOLD:
				{
					addTextTo.addView(textView, addTextTo.getChildCount());
					if(!isImage){
						tagState = TagState.SECTION;
					}
					else{
						tagState = TagState.IMAGE;
					}
					break;
				}
				case BULLET:
				{
					addTextTo.addView(textView, addTextTo.getChildCount());
					if(!isImage){
						tagState = TagState.SECTION;
					}
					else{
						tagState = TagState.IMAGE;
					}
					break;
				}
				case IMAGE:
				{
					isImage = false;
					pageBody.addView(relativeImageTextLayout, pageBody.getChildCount());
					addTextTo = pageBody;
					tagState = TagState.SECTION;
					break;
				}
				case RIGHT:
				{
					tagState = TagState.IMAGE;
					if(relativeImageTextLayout != null){
						RelativeLayout.LayoutParams imageLayoutParams= new RelativeLayout.LayoutParams(imgView.getLayoutParams());
						imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						//imageLayoutParams.addRule(RelativeLayout.RIGHT_OF,imageTextLinearLayout.getId());
						relativeImageTextLayout.addView(imgView,relativeImageTextLayout.getChildCount(),imageLayoutParams);

						RelativeLayout.LayoutParams textLayoutParams= new RelativeLayout.LayoutParams(imageTextLinearLayout.getLayoutParams());
						//textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						textLayoutParams.addRule(RelativeLayout.LEFT_OF,imgView.getId());
						relativeImageTextLayout.addView(imageTextLinearLayout,relativeImageTextLayout.getChildCount(),textLayoutParams);
					}
					else{
						Log.e("XMLParser", "Error with <image> tag in page xml");
					}
					break;
				}
				case LEFT:
				{
					tagState = TagState.IMAGE;
					if(relativeImageTextLayout != null){
						RelativeLayout.LayoutParams imageLayoutParams= new RelativeLayout.LayoutParams(imgView.getLayoutParams());
						imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						//imageLayoutParams.addRule(RelativeLayout.RIGHT_OF,imageTextLinearLayout.getId());
						relativeImageTextLayout.addView(imgView,relativeImageTextLayout.getChildCount(),imageLayoutParams);

						RelativeLayout.LayoutParams textLayoutParams= new RelativeLayout.LayoutParams(imageTextLinearLayout.getLayoutParams());
						//textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						textLayoutParams.addRule(RelativeLayout.RIGHT_OF,imgView.getId());
						relativeImageTextLayout.addView(imageTextLinearLayout,relativeImageTextLayout.getChildCount(),textLayoutParams);
					}
					else{
						Log.e("XMLParser", "Error with <image> tag in page xml");
					}
					break;
				}
				case ABOVE:
				{
					tagState = TagState.IMAGE;
					if(relativeImageTextLayout != null){
						RelativeLayout.LayoutParams imageLayoutParams= new RelativeLayout.LayoutParams(imgView.getLayoutParams());
						imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
						//imageLayoutParams.addRule(RelativeLayout.RIGHT_OF,imageTextLinearLayout.getId());
						relativeImageTextLayout.addView(imgView,relativeImageTextLayout.getChildCount(),imageLayoutParams);

						RelativeLayout.LayoutParams textLayoutParams= new RelativeLayout.LayoutParams(imageTextLinearLayout.getLayoutParams());
						//textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						textLayoutParams.addRule(RelativeLayout.BELOW,imgView.getId());
						relativeImageTextLayout.addView(imageTextLinearLayout,relativeImageTextLayout.getChildCount(),textLayoutParams);
					}
					else{
						Log.e("XMLParser", "Error with <image> tag in page xml");
					}
					break;
				}
				case BELOW:
				{
					tagState = TagState.IMAGE;
					if(relativeImageTextLayout != null){
						RelativeLayout.LayoutParams imageLayoutParams= new RelativeLayout.LayoutParams(imgView.getLayoutParams());
						imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
						//imageLayoutParams.addRule(RelativeLayout.RIGHT_OF,imageTextLinearLayout.getId());
						relativeImageTextLayout.addView(imgView,relativeImageTextLayout.getChildCount(),imageLayoutParams);

						RelativeLayout.LayoutParams textLayoutParams= new RelativeLayout.LayoutParams(imageTextLinearLayout.getLayoutParams());
						//textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						textLayoutParams.addRule(RelativeLayout.ABOVE,imgView.getId());
						relativeImageTextLayout.addView(imageTextLinearLayout,relativeImageTextLayout.getChildCount(),textLayoutParams);
					}
					else{
						Log.e("XMLParser", "Error with <image> tag in page xml");
					}
					break;
				}
				case NUMBER:
				{
					if(!isImage){
						tagState = TagState.SECTION;
					}
					else{
						tagState = TagState.IMAGE;
					}
					break;
				}
				default:
					break;
				}
			}
			else if(eventType == XmlPullParser.TEXT)
			{
				String textValue = xpp.getText();
				switch (tagState){
				case HEAD:
				{
					((TextView)findViewById(R.id.pageTextTitleText)).setText(textValue);
					((TextView)findViewById(R.id.pageTextTitleText)).setTypeface(face, Typeface.BOLD);
					break;
				}
				case HEADER:
				{
					textView.setTypeface(face, Typeface.BOLD);
					textView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
					textView.setText(textValue);
					pageBody.addView(textView, pageBody.getChildCount());
					break;
				}
				case TEXT:
				{
					textView.setText(textValue);
					break;
				}
				case BOLD:
				{
					textView.setTypeface(face, Typeface.BOLD);
					textView.setText(textValue);
					break;
				}
				case BULLET:
				{
					textValue = textValue.replace("\n", "\n\t  ");
					String fullString = "\t" + Html.fromHtml("&#8226;").toString() + textValue;
					textView.setGravity(Gravity.RIGHT);
					textView.setText(fullString);
					break;
				}
				case RIGHT:
				{
					if(imgView != null){
						int imageId = getResources().getIdentifier("@drawable/" + textValue, null, getPackageName());
						Drawable resource = getResources().getDrawable(imageId);
						imgView.setImageDrawable(resource);
					}
					else{
						Log.e("XMLParser", "Error With <image> tag in page xml");
					}
					break;
				}
				case LEFT:
				{
					if(imgView != null){
						int imageId = getResources().getIdentifier("@drawable/" + textValue, null, getPackageName());
						Drawable resource = getResources().getDrawable(imageId);
						imgView.setImageDrawable(resource);
					}
					else{
						Log.e("XMLParser", "Error With <image> tag in page xml");
					}
					break;
				}
				case ABOVE:
				{
					if(imgView != null){
						int imageId = getResources().getIdentifier("@drawable/" + textValue, null, getPackageName());
						Drawable resource = getResources().getDrawable(imageId);
						imgView.setImageDrawable(resource);
					}
					else{
						Log.e("XMLParser", "Error With <image> tag in page xml");
					}
					break;
				}
				case BELOW:
				{
					if(imgView != null){
						int imageId = getResources().getIdentifier("@drawable/" + textValue, null, getPackageName());
						Drawable resource = getResources().getDrawable(imageId);
						imgView.setImageDrawable(resource);
					}
					else{
						Log.e("XMLParser", "Error With <image> tag in page xml");
					}
					break;
				}
				case NUMBER:
				{
					textView.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
					textView.setTypeface(face, Typeface.BOLD);
					textView.setClickable(true);
					textView.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							String text = ((TextView)v).getText().toString();
							String[] nameAndPhone = text.split(":");
							addAsContactConfirmed(nameAndPhone[0] , nameAndPhone[1]);
						}
					});
					textView.setText(textValue);
					pageBody.addView(textView, pageBody.getChildCount());
					//Linkify.addLinks(textView, Linkify.ALL);
					break;
				}
				default:
					break;
				}
			}
			eventType = xpp.next();
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
}
