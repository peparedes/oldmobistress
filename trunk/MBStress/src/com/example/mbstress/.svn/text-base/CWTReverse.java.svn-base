/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import com.example.mbstress.MBActivity.WaitDialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class CWTReverse extends CWT{
		
	protected TextView correctTarget;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mTaskName = "CWTReverse";
		this.mDataCapture.init(mTaskName);
		//this.mNextActivity = EnterTextStressIntro.class;
		this.enterStressMode();
	}
	
	@Override
	protected void createUI()
	{
		super.createUI();
		for (TextView target: Targets) {
			target.setOnDragListener(null);
			target.setOnDragListener(new CWTReverseDragListener());
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		for (TextView target: Targets) {
			target.setOnDragListener(null);
			target.setOnDragListener(new CWTReverseDragListener());
		}
	}
	
	@Override
	protected void generateTask() 
	{
		super.generateTask();
	    int textColorIdx = (int)(wordColorIdx + 1)%4;
	    TextColor = TaskColors.get(textColorIdx);
	    correctTarget = Targets.get(textColorIdx);
	}
	
	
	@Override
	public void run()
	{
		super.run();
//		GameState gstate = GameState.getInstance();
//		long gameTime = gstate.initTimeRemaining;
//		if (gstate.getBonus() >= 20){
//			gameTime = (long)(gstate.initTimeRemaining/2.5);
//		} if (gstate.getBonus() >= 10 && gstate.getBonus() < 20) { 
//			gameTime = (long)(gstate.initTimeRemaining/2);
//		}else if (gstate.getBonus() >= 5 && gstate.getBonus() < 10){
//			gameTime = (long)(gstate.initTimeRemaining / 1.5);
//		} 
//		
//		gstate.setTimeRemaining(gameTime);
		
		this.gameState.setTimePerRound(4000);
		gameState.reinitTimer();
		StartStopWatch();
	}	
	
	protected void updatePrice(View v){
		TextView btn = (TextView)v;
		gameState.priceUpdate(btn == correctTarget);
		updatePriceText();
		
	}
		
	public class CWTReverseDragListener extends MyDragListener {
	    protected void onDropped(View v)
	    {
		      CancelStopWatch();
		      updatePrice(v);
		      super.onDropped(v);
	    }
	}
	
	
}
