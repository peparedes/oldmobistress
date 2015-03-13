/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

public class GameState {

	enum GameType
	{
		NORMAL,
		COUNTER;
	}
	
	private String userName;
	private double bonus;
	private long timerRemaining;
	private static double initPrice = 0;
	private static long initTimeRemaining = 6000; //ms
	private static GameState instance; 
	
	private List<Double>bonusHistory = new ArrayList<Double>();
	private ObjectOutputStream bonusOOS;

	//public static string[] normalSequence = {"CWTIntro", "CWT", "EnterTextIntro", "EnterText",
	public Class<?>[] normalSequence = {Intro.class, CWT.class, Intro.class, EnterText.class, Intro.class, Intro.class, CWTReverse.class, Intro.class, EnterTextStress.class, Intro.class}; 
	public Class<?>[] counterSequence = {Intro.class, CWTReverse.class, Intro.class, EnterTextStress.class, Intro.class, Intro.class, CWT.class, Intro.class, EnterText.class, Intro.class};
	
	private GameType gameType;
	private int gameTaskIndex;
	private Context appContext;
	
	private String[] taskInstructions;
	private String[] counterTaskInstructions;
	
	
	public static GameState getInstance()
	{
		if (instance == null)
			instance = new GameState();
		
		return instance;
	}
	private GameState()
	{
		bonus = initPrice;
		timerRemaining = initTimeRemaining;
		gameType = GameType.NORMAL;
		gameTaskIndex = 0;
	}
	
	public void reset() {
		instance = new GameState();
	}
	
	public void counterCondition()
	{
		gameType = GameType.COUNTER;
		gameTaskIndex = 0;
	}
	
	public Class getNextActivity()
	{
		Class nextTask;
		if (gameType == GameType.NORMAL) 
		{
			nextTask = normalSequence[gameTaskIndex];
		} else {
			nextTask = counterSequence[gameTaskIndex];
		}
		return nextTask;
	}
	
	public void currentActivityDone()
	{
		gameTaskIndex++;
	}
	
	public String getTaskIntroText()
	{
		if (gameType == GameType.NORMAL) 
		{
			return taskInstructions[gameTaskIndex];
		} else {
			return counterTaskInstructions[gameTaskIndex];
		}
	}
	
	public boolean finalTask() {
		return gameTaskIndex == normalSequence.length-1;
	}
	
	public void reinitTimer(){
		timerRemaining = initTimeRemaining;

	}
	
	public void priceUpdate(boolean add)
	{
		bonusHistory.add(bonus);
		
		if (add)
			bonus += 0.5;
		else
			bonus -= 0.5;
	}
	
	public void init(Context applicationContext) {
		if (appContext == null) {
			appContext = applicationContext;
		
			Resources res = appContext.getResources();
			taskInstructions = res.getStringArray(R.array.task_instructions);
			counterTaskInstructions = res.getStringArray(R.array.reverse_task_instructions);
		}
	}
	
	public void saveState() {
		
		
	}
	
	public double getBonus(){
		return bonus;
	}
	
	public void setTimeRemaining(long len){
		timerRemaining = len;
	}
	
	public long getTimeRemaining()
	{
		return this.timerRemaining;
	}
	
	public long getTimePerRound() 
	{
		return initTimeRemaining;
	}
	
	
	public void setTimePerRound(long initTime)
	{
		initTimeRemaining = initTime;
		
	}
	
	public void initID(String userId){
		this.userName = userId;
	}
	
	public void gameFinished() 
	{
		File root = android.os.Environment.getExternalStorageDirectory();			    
	    File dir = new File (root.getAbsolutePath() + "/Stroop/" + GlobalListener.userId);
	    dir.mkdirs();
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(dir, "bonus.obj"), true));
			oos.writeObject(bonusHistory);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
