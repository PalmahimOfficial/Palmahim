package com.IAF.Palmahim;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

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
		IMAGE("image");

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
			else{
				return DEFAULT;
			}
		}
	};
	
	private int sectionId = 0;
	private TagState tagState = TagState.DEFAULT;
	
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
	    TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	    //textView.setId(id);
	    textView.setGravity(Gravity.CENTER);
	    textView.setTextColor(getResources().getColor(android.R.color.black));
	    textView.setTextSize(getResources().getDimension(R.dimen.pages_body_text_size));
	    ((TextView)findViewById(R.id.pageTextTitleText)).setTypeface(face, Typeface.NORMAL);
	    textView.setLayoutParams(paramsExample);
	    return textView;
	}
	
	private void generatePageFromAnXML()
		throws XmlPullParserException, IOException
		{
			   Typeface face=Typeface.createFromAsset(getAssets(),
		                "fonts/Tahoma.ttf");
			   ScrollView scrollView = (ScrollView)findViewById(R.id.scrollBodyContent);
			   LinearLayout pageBody = (LinearLayout)scrollView.getChildAt(0);
			   Resources res = this.getResources();
			   XmlResourceParser xpp = res.getXml(R.xml.keva);
			   xpp.next();
			   int eventType = xpp.getEventType();
			   while (eventType != XmlPullParser.END_DOCUMENT)
			   {
			    if(eventType == XmlPullParser.START_TAG)
			    {
			    	tagState = TagState.getEnum(xpp.getName());
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
			    		tagState = TagState.SECTION;
			    		break;
			    	case BOLD:
			    		tagState = TagState.SECTION;
			    		break;
			    	case BULLET:
			    		tagState = TagState.SECTION;
			    		break;
			    	case IMAGE:
			    		tagState = TagState.SECTION;
			    		break;
			    	default:
			    		break;
			    	}
			    }
			    else if(eventType == XmlPullParser.TEXT)
			    {
			    	String textValue = xpp.getText();
			    	switch (tagState){
			    	case HEAD:
			    		((TextView)findViewById(R.id.pageTextTitleText)).setText(textValue);
			    		((TextView)findViewById(R.id.pageTextTitleText)).setTypeface(face, Typeface.BOLD);
			    		break;
			    	case HEADER:
			    	{
			    		TextView textView = buildTextView(0);
			    		textView.setTypeface(face, Typeface.BOLD);
			    		textView.setText(textValue);
			    		pageBody.addView(textView, pageBody.getChildCount());
			    		break;
			    	}
			    	case TEXT:
			    	{
			    		TextView textView = buildTextView(0);
			    		textView.setText(textValue);
			    		pageBody.addView(textView, pageBody.getChildCount());
			    		break;
			    	}
			    	case BOLD:
			    	{
			    		TextView textView = buildTextView(0);
			    		textView.setTypeface(face, Typeface.BOLD);
			    		textView.setText(textValue);
			    		pageBody.addView(textView, pageBody.getChildCount());
			    		break;
			    	}
			    	case BULLET:
			    	{
			    		textValue = textValue.replace("\n", "\n\t  ");
			    		String fullString = "\t" + Html.fromHtml("&#8226;").toString() + textValue;
			    		TextView textView = buildTextView(0);
			    		textView.setGravity(Gravity.RIGHT);
			    		textView.setText(fullString);
			    		pageBody.addView(textView, pageBody.getChildCount());
			    		break;
			    	}
			    		
			    	case IMAGE:
			    	{
			    		// TODO: Generate image paragraph
			    		break;
			    	}
			    	default:
			    		break;
			    	}
			    }
			    eventType = xpp.next();
			   }
		}

}
