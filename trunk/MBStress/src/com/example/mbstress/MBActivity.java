/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public abstract class MBActivity extends SequenceActivity {
	static final LinearLayout.LayoutParams wrapContent2 = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	public static final LinearLayout.LayoutParams wrapContent = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
	public static final LinearLayout.LayoutParams matchParent = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
	public static final LinearLayout.LayoutParams wrapMatch = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT,1f);
	public static final LinearLayout.LayoutParams matchWrap = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1f);

	private SensorManager mSensorManager;
	//private Sensor mAccelerometer;
	protected Vibrator mVibrator;

	protected GlobalListener mDataCapture;
	//protected Class<?> mNextActivity;
	
	protected String mDebugPreference;
	
	protected LinearLayout parent;
	//protected TextView instruction;
	//protected String mInstruction;
	protected String mTaskName;
	
	protected RelativeLayout header;
	protected LinearLayout footer;
	
	protected TextView mTimeLeft;
	protected TextView mTotalPrice;
	
	protected FrameLayout timerFrame;
	protected FrameLayout scoreFrame;
	
	protected TextView X, Y, Z, C; // ugly
	protected GameState gameState;
	protected MyCountDownTimer myCountDownTimer;
	protected DecimalFormat df = new DecimalFormat("#.##");

	protected Integer nRuns;
	protected Integer maxRuns;
	protected Boolean mTaskDone;
	
	protected DialogFragment waitDialogFrag;
	protected Semaphore semaphore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_generic_layout);
		mDataCapture = GlobalListener.getInstance();
		// Get an instance of the SensorManager
		mVibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		mSensorManager.registerListener(this.mDataCapture, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		gameState = GameState.getInstance();
		nRuns = 0;
		mTaskDone = false;
		semaphore = new Semaphore(1);
		createUI();
	}

	protected void createUI() {
		parent = (LinearLayout) findViewById(R.id.topview);
		if (parent != null) {
			// align everything to center
			parent.setGravity(Gravity.CENTER_HORIZONTAL);
			header = (RelativeLayout)findViewById(R.id.headerSection);
		}
		//addDebugInfo();
	}
	
	protected void enterStressMode()
	{
		gameState.reinitTimer();
		mTimeLeft = (TextView)findViewById(R.id.timeLeft); 
		findViewById(R.id.timeLeftContainer).setVisibility(android.view.View.VISIBLE);
		mTimeLeft.setText(df.format(gameState.getTimeRemaining())+ "s");
		
		mTotalPrice = (TextView)findViewById(R.id.score);
		findViewById(R.id.scoreContainer).setVisibility(android.view.View.VISIBLE);
		mTotalPrice.setText("$" + gameState.getBonus());
	}
		
	protected void timerFinished(){
		gameState.reinitTimer(); 
	}
	
	protected void CancelStopWatch(){
		timerFinished();
		if (myCountDownTimer != null) {
			myCountDownTimer.cancel();
			myCountDownTimer = null;
		}
	}
	
	protected void StartStopWatch()
	{
		myCountDownTimer = new MyCountDownTimer(gameState.getTimeRemaining(), 10);
		myCountDownTimer.start();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mSensorManager.registerListener(mDataCapture, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mDataCapture);
		CancelStopWatch();
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_enter_text, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_settings:
			showSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void addDebugInfo() {
		// should debug info be displayed?
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Boolean debug = sharedPref.getBoolean(mDebugPreference, false);
		TableLayout debugView = (TableLayout) findViewById(R.id.debugView);

		if (debug) {
			//debugView.setVisibility(View.VISIBLE);
			X = (TextView) findViewById(R.id.x_axis);
			Y = (TextView) findViewById(R.id.y_axis);
			Z = (TextView) findViewById(R.id.z_axis);
			C = (TextView) findViewById(R.id.events);
		} else {
			debugView.setVisibility(View.GONE);
		}
	}

	private void showSettings() {
		nextTask(Preferences.class);
	}

	protected void iterate()
	{
		if (semaphore.tryAcquire()){
			try {
				if (nRuns < maxRuns) {
					if (nRuns > 0) {//set the bounary for the previous trial
						this.mDataCapture.setTrialBoundary();
					}
					run();
					nRuns++;
				} else 
				{
					done();
				}
			} finally {
				semaphore.release();
			}
		}
	
	}
	
	public abstract void run();
	
	protected void start()
	{
		mDataCapture.begin();
		iterate();
	}
	
	protected void done() 
	{
		if (!mTaskDone){
			mTaskDone = true;
			//tell sensors to stop 
			mDataCapture.end();
			//write data
			
            new Logging().execute();
			showWaitDialog();
			
		}
	}
	
	private void showWaitDialog()
	{
		Resources res = getResources();
		waitDialogFrag = WaitDialog.newInstance(res.getString(R.string.wait_message));
		waitDialogFrag.show(getFragmentManager(), "waitdialog");
	}
	
	private void closeWaitDialog()
	{
		waitDialogFrag.dismiss();
		//start next activity
		GameState.getInstance().currentActivityDone();
		nextTask(GameState.getInstance().getNextActivity());
	}
	
	public float convertDpToPixel(float dp){
	    Resources resources = this.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi/160f);
	    return px;
	}

	public float convertPixelsToDp(float px){
	    Resources resources = this.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}
	
	class MyCountDownTimer extends CountDownTimer
	{

	    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		public void onTick(long millisUntilFinished) {
	    	 if (millisUntilFinished/1000 < 1) {
	    		 mTimeLeft.setTextColor(Color.RED);
	    	 } else {
	    		 mTimeLeft.setTextColor(Color.BLACK);
	    	 }
	    	
	    	 gameState.setTimeRemaining(millisUntilFinished);
	    	 updateTimerText();
		}

		@Override
		public void onFinish() 
		{
			gameState.priceUpdate(false);	
			updatePriceText();
			timerFinished();
			iterate();
		}
	}
	
	protected void updatePriceText(){
		mTotalPrice.setText("$" + String.format("%1$.2f", gameState.getBonus()));		
	}
	
	protected void updateTimerText()
	{
		mTimeLeft.setText(String.format("%1$.2f", gameState.getTimeRemaining()/1000.0));
	}
	
	 private class Logging extends AsyncTask<Void, String, String> {

         @Override
         protected String doInBackground(Void... params) {
	        	try{
	 				mDataCapture.recordData(true);
	 			}catch(Exception e){
	 			}
               return "Executed";
         }      

         @Override
         protected void onPostExecute(String result) {
        	 closeWaitDialog();
//               TextView txt = (TextView) findViewById(R.id.output);
//               txt.setText("Executed"); // txt.setText(result);
//               //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
         }

        
         @Override
         protected void onProgressUpdate(String... values) {
        	 super.onProgressUpdate(values);
         }
   }   
	 
   public static  class WaitDialog extends DialogFragment {
	   
	   static WaitDialog newInstance(String message) {
		   WaitDialog f = new WaitDialog();
	        // Supply num input as an argument.
	        Bundle args = new Bundle();
	        args.putString("message", message);
	        f.setArguments(args);
	        return f;
	    }
	   
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	
	        String message = getArguments().getString("message");

	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(message);
//	               .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
//	                   public void onClick(DialogInterface dialog, int id) {
//	                       // FIRE ZE MISSILES!
//	                   }
//	               })
//	               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//	                   public void onClick(DialogInterface dialog, int id) {
//	                       // User cancelled the dialog
//	                   }
//	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}
	
}
