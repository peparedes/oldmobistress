/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EnterTextStress extends EnterText {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mTaskName = "TextEntryStress";	// must define this for every task
		this.mDataCapture.init(mTaskName);
		//this.mNextActivity = Outro.class;
		this.enterStressMode();


	}

	public void run()
	{
		super.run();
		
//		GameState gstate = GameState.getInstance();
//		long gameTime = 20000;
//		if (gstate.getBonus() >= 20){
//			gameTime = (long)(gstate.initTimeRemaining/2.5);
//		} if (gstate.getBonus() >= 10 && gstate.getBonus() < 20) { 
//			gameTime = (long)(gstate.initTimeRemaining/2);
//		}else if (gstate.getBonus() >= 5 && gstate.getBonus() < 10){
//			gameTime = (long)(gstate.initTimeRemaining / 1.5);
//		} 
//		gstate.setTimeRemaining(gameTime);
		this.gameState.setTimePerRound(20000);
		gameState.reinitTimer();
		StartStopWatch();

	}
	
	
	
	protected void createUI()
	{
		super.createUI();
		
		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (inputText.getText().toString().trim().length() != 0) {
					CancelStopWatch();
					updatePrice();
					
					if (!mTaskDone)
						iterate();
				}
			}
		});	
	
	}
	
	protected void updatePrice(){
		if (computeLevenshteinDistance(exampleText.getText(), inputText.getText()) > 5){
			gameState.priceUpdate(false);			
		} else{
			gameState.priceUpdate(true);
		}
		updatePriceText();
		
	}
	
	
	private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
	}
	
	public static int computeLevenshteinDistance(CharSequence str1,
	                CharSequence str2) {
	        int[][] distance = new int[str1.length() + 1][str2.length() + 1];
	
	        for (int i = 0; i <= str1.length(); i++)
	                distance[i][0] = i;
	        for (int j = 1; j <= str2.length(); j++)
	                distance[0][j] = j;
	
	        for (int i = 1; i <= str1.length(); i++)
	                for (int j = 1; j <= str2.length(); j++)
	                        distance[i][j] = minimum(
	                                        distance[i - 1][j] + 1,
	                                        distance[i][j - 1] + 1,
	                                        distance[i - 1][j - 1]
	                                                        + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
	                                                                        : 1));
	
	        return distance[str1.length()][str2.length()];
	}
}
