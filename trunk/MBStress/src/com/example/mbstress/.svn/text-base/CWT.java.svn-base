/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class CWT extends MBActivity{
 
	enum MyColors{
		RED,
		YELLOW,
		GREEN,
		BLUE;
	
		
		private int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
		
		public int toColor(){
			return colors[this.ordinal()];
		}
		
	}
	protected DragShadowBuilder shadowBuilder;
	protected static boolean debug  = false;

	protected TextView TheWord;
	protected RelativeLayout TheWordLayout;
	protected TextView UpperLeft;
	protected TextView UpperRight;
	protected TextView LowerLeft;
	protected TextView LowerRight;
	protected ArrayList<MyColors> TaskColors = new ArrayList<MyColors>();
	protected MyColors WordColor;
	protected int PrevColorIdx;
	protected int wordColorIdx;
	protected MyColors TextColor;
	protected ArrayList<TextView> Targets = new ArrayList<TextView>();
	protected Float textSize = 36f;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mTaskName = "CWT";
		this.mDataCapture.init(mTaskName); // must call this for every task to
											// setup the sensing side
		//this.mNextActivity = EnterTextIntro.class; // setup next activity
		this.mDataCapture.activity = this;
		
		//this.mInstruction = "Drag and drop the colored text in the center of the screen to one of the corner boxes. Match the color of the text to the text in the box";
		this.mDebugPreference = "stroopdebug";
		
		//init task colors
		TaskColors.add(MyColors.RED);
		TaskColors.add(MyColors.GREEN);
		TaskColors.add(MyColors.BLUE);
		TaskColors.add(MyColors.YELLOW);

		this.maxRuns =100;
		this.PrevColorIdx = -1;
		start();
	}
	
	protected void generateTask() 
	{
		wordColorIdx = (int)(Math.random() * 100 % 4);	

		if (wordColorIdx == PrevColorIdx){
		   	wordColorIdx = (wordColorIdx + 1)%4;
		}
		
	    WordColor = TaskColors.get(wordColorIdx);
	    TextColor = TaskColors.get(wordColorIdx);
	    PrevColorIdx = wordColorIdx;
	    
	}
	
	protected void createUI()	{
		super.createUI();
		
		parent.setOnDragListener(mDataCapture);

		//Upper left and right buttons
		RelativeLayout relativeLayout = new RelativeLayout(this);
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		//relativeLayout.setLayoutParams(lp);
    	UpperLeft = new TextView(this);// (TextView) findViewById(R.id.UpperLeft);
    	UpperRight = new TextView(this);
    	relativeLayout.addView(UpperLeft, lp);
    	
    	lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	relativeLayout.addView(UpperRight, lp);
    	parent.addView(relativeLayout, wrapContent);
    	
    	
    	TheWordLayout = new RelativeLayout(this);
    	parent.addView(TheWordLayout,wrapContent);
    	
    	//lower left and right TextView
    	LowerLeft = new TextView(this);// (TextView) findViewById(R.id.UpperLeft);
    	LowerRight = new TextView(this);
    	relativeLayout = new RelativeLayout(this);
		lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    	lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    	relativeLayout.addView(LowerLeft, lp);
    	
		lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    	lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	relativeLayout.addView(LowerRight, lp);
    	parent.addView(relativeLayout, wrapContent);
    	
    	//setUpWord();
    	
    	Targets.add(UpperLeft);
		Targets.add(UpperRight);
		Targets.add(LowerLeft);
		Targets.add(LowerRight);

		

		boolean clickable = false;
		for (TextView btn: Targets) {
			btn.setOnDragListener(new MyDragListener());
			btn.setTextSize(textSize);
			btn.setTypeface(null, Typeface.BOLD);
			btn.setClickable(clickable);
		}
		
	}
	
	protected void setUpWord()
	{
		removeWord();
		//center TextView
    	TheWord = new TextView(this);
    	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    	lp.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
    	TheWord.setLayoutParams(lp);
    	TheWordLayout.addView(TheWord);

    	//TheWord.setOnClickListener(new MyClickListerner());
		//set drag listener for word buttons
		TheWord.setOnLongClickListener(new View.OnLongClickListener() 
		{	
			@Override
			public boolean onLongClick(View view) {
				shadowBuilder = new View.DragShadowBuilder(view);
				long[] pattern = {0, 100};
				mVibrator.vibrate(pattern, -1);
				view.startDrag(null, shadowBuilder, view, 0);
				return false;
			}
		});		
		TheWord.setOnTouchListener(mDataCapture);
		TheWord.setTextSize(textSize);
		TheWord.setTypeface(null, Typeface.BOLD);
	}
	
	protected void removeWord()
	{
		if (TheWord != null)
		TheWordLayout.removeView(TheWord);
	}
	
	
	class MyClickListerner implements View.OnClickListener{

		@Override
		public void onClick(View view) {
			shadowBuilder = new View.DragShadowBuilder(view);
			long[] pattern = {0, 100};
			mVibrator.vibrate(pattern, -1);
			view.startDrag(null, shadowBuilder, view, 0);
		}
	}
		
	public void run()
	{
		generateTask();
		
		setUpWord();
		TheWord.setTextColor(TextColor.toColor());
		TheWord.setText(WordColor.toString());
		UpperLeft.setText(TaskColors.get(0).toString());
		UpperRight.setText(TaskColors.get(1).toString());
		LowerLeft.setText(TaskColors.get(2).toString());
		LowerRight.setText(TaskColors.get(3).toString());
	}	
	
	
	@Override
	public void onResume() {
		super.onResume();
		for (TextView btn: Targets) {
			btn.setOnDragListener(new MyDragListener());
		}
	}
	
	public class MyDragListener implements OnDragListener {
	    public boolean onDrag(View v, DragEvent event) {
	      switch (event.getAction()) {
	      case DragEvent.ACTION_DRAG_STARTED:		
	    	  
	        break;
	      case DragEvent.ACTION_DRAG_ENTERED:
	    	onEntered(v);
	        break;
	      case DragEvent.ACTION_DRAG_EXITED:
	    	onExited(v);
	        break;
	      case DragEvent.ACTION_DROP:
	    	onDropped(v);
	        break;
	      case DragEvent.ACTION_DRAG_ENDED:
	      default: 
	        break;
	      }
	      return true;
	    }
	  
	    private void onEntered(View v)
	    {
	    	((TextView)v).setTextSize(textSize+2.5f);
	    	((TextView)v).setTextColor(Color.GRAY);
	    }
	    
	    private void onExited(View v)
	    {
	    	((TextView)v).setTextSize(textSize);
	    	((TextView)v).setTextColor(Color.BLACK);
	    }
	    
	    protected void onDropped(View v)
	    {
			 ((TextView)v).setTextSize(textSize);
			 ((TextView)v).setTextColor(Color.BLACK);		    	
			 iterate();
	    }
}

}
