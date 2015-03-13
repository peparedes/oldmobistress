/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Intro extends SequenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        createUI();
	}

	public void createUI() 
	{
		TextView instructionView = (TextView) findViewById(R.id.instructions);
		instructionView.setText(Html.fromHtml(GameState.getInstance().getTaskIntroText()));
		
		Button nextBtn = (Button)findViewById(R.id.next);	
		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (GameState.getInstance().finalTask())
				{
					GameState.getInstance().gameFinished();
					GameState.getInstance().reset();
					finish();
				} else {
					GameState.getInstance().currentActivityDone();
					nextTask(GameState.getInstance().getNextActivity());
				}
			}
		});
		
	}
}
