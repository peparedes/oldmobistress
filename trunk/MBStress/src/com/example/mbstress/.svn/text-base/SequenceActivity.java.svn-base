/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public abstract class SequenceActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initGameState();
		
	}
	
	private void initGameState()
	{
		GameState gState = GameState.getInstance();
		gState.init(this.getApplicationContext());
	}
	
	protected void nextTask(Class<?> activity)
    {
		if (activity != null) {
	    	Intent intent = new Intent(this, activity);
	    	//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);        
	    	startActivity(intent);
	    	finish();
		}
    }
//	public boolean onCreateOptionsMenu(Menu menu) {
//	    MenuInflater inflater = getMenuInflater();
//	    inflater.inflate(R.menu.menu2, menu);
//		return true;
//	}
	
	public void onStop(){
		super.onStop();
		GameState.getInstance().saveState();
	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//	    // Handle item selection
//	    switch (item.getItemId()) {
//	        case R.id.menu_settings:
//    	    	Intent intent = new Intent(this, Preferences.class);
//    	    	startActivity(intent);
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	    }
//	}
	public void onBackPressed() {
		   Intent setIntent = new Intent(Intent.ACTION_MAIN);
		   setIntent.addCategory(Intent.CATEGORY_HOME);
		   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   startActivity(setIntent);
		}
	
	
}
