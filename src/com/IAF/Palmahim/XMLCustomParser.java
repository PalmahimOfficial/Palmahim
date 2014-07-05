package com.IAF.Palmahim;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;


public abstract class XMLCustomParser {

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
		TABLE("table"),
		FOOTER("footer");

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
			else if(tagName.compareTo(TABLE.tag_name)==0){
				return TABLE;
			}
			else if(tagName.compareTo(TEXT.tag_name)==0){
				return TEXT;
			}
			else if(tagName.compareTo(NUMBER.tag_name)==0){
				return NUMBER;
			}
			else if(tagName.compareTo(FOOTER.tag_name)==0){
				return FOOTER;
			}

			else{
				return DEFAULT;
			}
		}
	}

	private Context context;;
	private String BASE_COMPANY = "בסיס פלמחים";
	private int sectionId = 0;
	private TagState tagState = TagState.DEFAULT;
	private boolean isImage = false;
	int TABLES_SIZE;
	LinearLayout addTextTo;
	TableHandler tablesHandler;
	LinearLayout pageSections;
	LinearLayout pageBody;
	ScrollView scrollView;
	Typeface face;
	HorizontalScrollView scrollBodyLinksContent;
	
	public XMLCustomParser(Context context) {
		this.context = context;
	}
	
	
	private TextView buildTextView(int id){   
		// the following change is what fixed it
		TextView textView = new TextView(context);
		Typeface face=Typeface.createFromAsset(context.getAssets(),
				"fonts/Tahoma.ttf");
		LayoutParams paramsExample = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		//textView.setId(id);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(context.getResources().getColor(android.R.color.black));
		textView.setTextSize(context.getResources().getDimension(R.dimen.pages_body_text_size));
		textView.setTypeface(face, Typeface.NORMAL);
		textView.setLayoutParams(paramsExample);
		return textView;
	}
	
	@SuppressLint("InlinedApi") public void generatePageFromAnXML()
			throws XmlPullParserException, IOException
			{
		
		setup();
		//image Layouts and texts
		RelativeLayout relativeImageTextLayout = null;
		LinearLayout imageTextLinearLayout= new LinearLayout(context);
		ImageView imgView = null;

		//text Tag TextView
		TextView textView = buildTextView(sectionId++);

		//Footer Banner RelativeLayout and ImageView
		RelativeLayout footerLayout = getFooterLayout();
		
		//Tables Handler
		tablesHandler = getTableHandler();
		
		addTextTo = pageBody;
		Resources res = context.getResources();
		int xmlId = getXML();
		XmlResourceParser xpp = res.getXml(xmlId);
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
						
						relativeImageTextLayout = new RelativeLayout(context);
						imgView = new ImageView(context);
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
					textView.setPadding(0, 2, 0, 2);
					addViewToMainView(textView);
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
					LinearLayout.LayoutParams marginLayoutParams = new LinearLayout.LayoutParams(textView.getLayoutParams());
					marginLayoutParams.setMargins(0, 5, 0, 5);
					addViewToMainView(textView , marginLayoutParams);
					
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
					LinearLayout.LayoutParams marginLayoutParams = new LinearLayout.LayoutParams(textView.getLayoutParams());
					marginLayoutParams.setMargins(0, -2, 20, -2);
					addViewToMainView(textView , marginLayoutParams);
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
					addViewToMainView(relativeImageTextLayout);
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
				textValue = textValue.replace('(', '^');
				textValue = textValue.replace(')', '(');
				textValue = textValue.replace('^', ')');
				switch (tagState){
				case HEAD:
				{
					setHeadText(textValue);
					
					break;
				}
				case HEADER:
				{
					LinearLayout.LayoutParams marginLayoutParams = new LinearLayout.LayoutParams(textView.getLayoutParams());
					marginLayoutParams.setMargins(0, 15, 0, 15);
					
					textView.setTypeface(face, Typeface.BOLD);
					textView.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
					textView.setTextSize(context.getResources().getDimension(R.dimen.pages_body_headers_size));
					textView.setText(textValue);
					addViewToMainView(textView, marginLayoutParams);
					
					// Putting link in the head
					TextView newTextView = new TextView(context);
					newTextView.setPadding(12, 5, 12, 5);
					newTextView.setTextColor(context.getResources().getColor(android.R.color.white));
					newTextView.setTextSize(context.getResources().getDimension(R.dimen.head_link_text_size));
					newTextView.setText(textValue);
					newTextView.setTag(textView);
					newTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							scrollView.scrollTo(0, ((View)view.getTag()).getTop());
						}
					});
					pageSections.addView(newTextView, 0);
					break;
				}
				case TEXT:
				{
					textView.setText(textValue);
					break;
				}
				case TABLE:
				{
					tablesHandler.buildTable(textValue,TABLES_SIZE,false);
					break;
				}
				case BOLD:
				{
					textView.setTypeface(face, Typeface.BOLD);
					textView.setTextSize(context.getResources().getDimension(R.dimen.head_link_text_size));
					textView.setText(textValue);
					break;
				}
				case BULLET:
				{
					String fullString = Html.fromHtml("&#8226;").toString() + textValue;
					textView.setGravity(Gravity.RIGHT);
					textView.setText(fullString);
					break;
				}
				case RIGHT:
				{
					if(imgView != null){
						int imageId = context.getResources().getIdentifier("@drawable/" + textValue, null, context.getPackageName());
						Drawable resource = context.getResources().getDrawable(imageId);
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
						int imageId = context.getResources().getIdentifier("@drawable/" + textValue, null, context.getPackageName());
						Drawable resource = context.getResources().getDrawable(imageId);
						imgView.setImageDrawable(resource);
					}
					else{
						Log.e("XMLParser", "Error With <image> tag in page xml");
					}
					break;
				}
				case NUMBER:
				{
					textView.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
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
					addViewToMainView(textView);
					//Linkify.addLinks(textView, Linkify.ALL);
					break;
				}
				case FOOTER:
				{
					int imageId = context.getResources().getIdentifier("@drawable/" + textValue, null, context.getPackageName());
					//Drawable resource = getResources().getDrawable(imageId);
					footerLayout.setBackgroundResource(imageId);
					break;
				}
				default:
					break;
				}
			}
			eventType = xpp.next();
		}
		
		new Handler().postDelayed(new Runnable(){   
			@Override
			public void run() {
				//HorizontalScrollView scrollBodyLinksContent = (HorizontalScrollView)findViewById(R.id.scrollBodyLinksContent);
				scrollBodyLinksContent.scrollTo(scrollBodyLinksContent.getRight(), scrollBodyLinksContent.getTop());
			    }       

			},100L);
			}

	
	private void addAsContactConfirmed(String contactName , String contactNumber) {

		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

		intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName);
		intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactNumber);
		intent.putExtra(ContactsContract.Intents.Insert.COMPANY, BASE_COMPANY);

		context.startActivity(intent);

	}
	
	private void addViewToMainView(View textView,
			android.widget.LinearLayout.LayoutParams marginLayoutParams) {
		addTextTo.addView(textView, addTextTo.getChildCount(),marginLayoutParams);
		
	}


	private void addViewToMainView(View textView) {
		addTextTo.addView(textView, addTextTo.getChildCount());
	}
	
	private TableHandler getTableHandler() {
		return new TableHandler(context.getApplicationContext(),pageBody);
	}
	
	public abstract void setup();
	
	//public abstract LinearLayout getPageBody();
	
	public abstract int getXML();
	
	public abstract RelativeLayout getFooterLayout();
	
	public abstract void setHeadText(String textValue);
	
	//public abstract TableHandler getTableHandler();

/*	private RelativeLayout getFooterLayout() {
		return (RelativeLayout)findViewById(R.id.footerBanner);
	}


	

	private void setHeadText(String textValue) {
		((TextView)findViewById(R.id.pageTextTitleText)).setText(textValue);
		((TextView)findViewById(R.id.pageTextTitleText)).setTypeface(face, Typeface.BOLD);
		
	}


	




	
	private void setup(){
		scrollView = (ScrollView)findViewById(R.id.scrollBodyContent);
		addTextTo = (LinearLayout)scrollView.getChildAt(0);
		scrollBodyLinksContent = (HorizontalScrollView)findViewById(R.id.scrollBodyLinksContent);
		pageSections = (LinearLayout)scrollBodyLinksContent.getChildAt(0);
	}*/
}
