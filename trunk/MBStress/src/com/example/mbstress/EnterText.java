/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EnterText extends MBActivity {

	private int mIdx = 0;
	private String[] exampleSentences;

	protected TextView exampleText;
	protected EditText inputText;
	protected Button nextBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_enter_text);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN );

		this.mTaskName = "TextEntry";	// must define this for every task
		this.mDataCapture.init(mTaskName); // must call this for every task to
											// setup the sensing side
		this.mDataCapture.activity = this;
		//this.mNextActivity = SwitchCondition.class; // setup next activity
		this.mDebugPreference = "tedebug";
		//this.mInstruction = "Instruction: Please type the statement below and click Next";

		// get example sentences
		Resources res = getResources();
		exampleSentences = res.getStringArray(R.array.example_sentences);

		this.maxRuns = 30;
		
		start();
	}

	protected void createUI() {
		super.createUI();

		// add example text field
		exampleText = new TextView(this);
		exampleText.setGravity(Gravity.CENTER);
		exampleText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		exampleText.setTextSize(20f);
		parent.addView(exampleText, wrapContent2);
		
		// add next button
		nextBtn = new Button(this);
		nextBtn.setText("Next");
		//nextBtn.setLayoutParams(wrapContent);
				parent.addView(nextBtn, wrapContent2);

		// add text input field
		inputText = new EditText(this);
//		MBActivity.matchWrap.setMargins((int) convertPixelsToDp(10),
//				(int) convertPixelsToDp(20), (int) convertPixelsToDp(20),
//				(int) convertPixelsToDp(10)); //add margin to sides
//		inputText.setLayoutParams(matchWrap);
		inputText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);//textNoSuggestions|textVisiblePassword..)
		inputText.addTextChangedListener(mDataCapture);
		parent.addView(inputText);

		// register listener with "next" button"
		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (inputText.getText().toString().trim().length() != 0) {
					if (!mTaskDone)
						iterate();
				}
			}
		});
	}
	

	public void run()
	{
		exampleText.setText(exampleSentences[mIdx]);
		inputText.setText("");
		
		if (mIdx < exampleSentences.length) {
			mIdx++;
		} 
		
		mIdx = mIdx % exampleSentences.length;
	}
}
