/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class First extends SequenceActivity{
	public static String ID = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first);
        createUI();
	}

	public void createUI() 
	{
		
		Button nextBtn = (Button)findViewById(R.id.next);
		nextBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				TextView id = (TextView)findViewById(R.id.editID);
				ID = id.getText().toString();
				if (ID.trim().length() != 0) {
					GlobalListener.userId = ID; //set user ID
					nextTask(GameState.getInstance().getNextActivity());
				}
			}
		});
	}
	
	

}
