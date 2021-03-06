package com.IAF.Palmahim;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableHandler {

	Context context;
	LinearLayout parent;
	int[] COLORS = {R.color.darkred,R.color.blue};
	int COLS_AVG_SIZE = 100;
	
	public TableHandler(Context context ,LinearLayout parent){
		this.context = context;
		this.parent = parent;
		COLS_AVG_SIZE = (int) context.getResources().getDimension(R.dimen.table_cols_avg_size);
	}
	
	private TextView buildTextView(){   
		// the following change is what fixed it
		TextView textView = new TextView(context);
		Typeface face=Typeface.createFromAsset(context.getAssets(),
				"fonts/Tahoma.ttf");
		LayoutParams paramsExample = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		//textView.setId(id);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(context.getResources().getColor(android.R.color.white));
		textView.setTextSize(context.getResources().getDimension(R.dimen.table_text_size));
		textView.setPadding(5, 5, 5, 5);
		textView.setTypeface(face, Typeface.NORMAL);
		textView.setLayoutParams(paramsExample);
		return textView;
	}

	public void buildTable(final String tableContent, final boolean isContentClickable){
		TextView tableTextView = buildTextView();
		tableTextView.setTextSize(context.getResources().getDimension(R.dimen.table_header_text_size));
		final int numOfCols = tableContent.split("_")[1].split(";").length;
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(numOfCols*COLS_AVG_SIZE,LayoutParams.WRAP_CONTENT);
		llp.setMargins(0, 10, 0, 10);
		tableTextView.setLayoutParams(llp);
		parent.addView(tableTextView, parent.getChildCount());
		tableTextView.setPadding(0, 10, 0, 10);
		int headerText = tableContent.indexOf("_");
		tableTextView.setText(tableContent.substring(0, headerText));
		tableTextView.setTextColor(context.getResources().getColor(R.color.black));
		tableTextView.setBackgroundColor(context.getResources().getColor(R.color.white));
		tableTextView.setTypeface(tableTextView.getTypeface(), Typeface.BOLD);
		tableTextView.setOnClickListener(new OnClickListener() {

			TextView relatedTextView = null;
			int relatedIndex = 0;
			TableLayout table = null;
			@Override
			public void onClick(View v) {
				relatedTextView = (TextView)v;
				LinearLayout parent = ((LinearLayout)v.getParent());
				relatedIndex = parent.indexOfChild(v);

				if(table == null){
					table = new TableLayout(context.getApplicationContext());
					table.setLayoutParams(v.getLayoutParams());
						buildTable(table ,tableContent, numOfCols);
					
					table.setOnClickListener(new OnClickListener() {
							TableLayout relatedTable = null;
							@Override
							public void onClick(View v) {
								relatedTable = (TableLayout)v;
								LinearLayout parent = (LinearLayout)v.getParent();
								parent.removeViewAt(relatedIndex);
								parent.addView(relatedTextView, relatedIndex);
								FX.slide_up(context.getApplicationContext(), v);
							}
						});
				}
					
				parent.removeViewAt(relatedIndex);
				parent.addView(table, relatedIndex);
				FX.slide_down(context.getApplicationContext(), table);
			}
		});
	}
	
	public TableRow getCustomTableRow(Context context){
		return new TableRow(context.getApplicationContext());
	}
	
	private void buildTable(TableLayout tableLayout ,String content, int numOfCols){
		String[] rows  = content.split("_");

		//tableLayout.setColumnStretchable(numOfCols-2, true);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		tableLayout.removeAllViews();


		for(int i=0;i<rows.length;i++){
			String row  = rows[i];
			TableRow tableRow = getCustomTableRow(context);
			if(i<=1){
				tableRow.setBackgroundColor(context.getResources().getColor(R.color.darkblue));
			}
			else{
				tableRow.setBackgroundColor(context.getResources().getColor(getNextColor(i)));
			}
			tableRow.setLayoutParams(rowParams);
			if(i==0){
				tableRow.setWeightSum(1f);
				tableRow.setBackgroundColor(context.getResources().getColor(R.color.white));
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
					columsView.setTextColor(context.getResources().getColor(R.color.black));
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
