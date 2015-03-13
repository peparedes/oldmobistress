/**
* By: David Sun
* Date: Mar 01, 2013
*/
package com.example.mbstress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;

public class GlobalListener implements SensorEventListener, OnTouchListener, OnDragListener, TextWatcher{
	
	static class EditData implements Serializable
	{
		String content;
		Integer start, count, after;
		Long timeStamp;
		
		EditData(CharSequence s, int start, int count, int after){
			this.content = s.toString();
			this.start = start;
			this.count = count;
			this.after = after;
			timeStamp = (long)(System.nanoTime()/1000);
		}
		
		public String toString()
		{
			return content + " " + start + " " + count +" " + after +" "+ timeStamp; 
		}
	
	}
	
	static class AccelerationData implements Serializable
	{
		Double X, Y, Z;
		Double time;
		AccelerationData(float x,float y, float z, long t){
			X = (double) x;
			Y = (double) y;
			Z = (double) z;
			time = Long.valueOf(t).doubleValue() ;
		}
		
		public String toString(){
			return X + " " + Y + " " + Z + " " + time;
		}
	}
	
	static class MotionData implements Serializable
	{
		Double X, Y, area, pressure;
		Double time;
		MotionData(float x, float y, float a, float p, long t)
		{
			X = (double) x;
			Y = (double) y;
			area = (double)a;
			pressure = (double)p;
			time = Long.valueOf(t).doubleValue() ;
		}
		
		public String toString(){
			return X + " " + Y + " " + area + " " + pressure + " " + time;
		}
	}
	
	static class EventData  implements Serializable
	{
		
		Long time;
		Integer evtType;
		
		public EventData(int evt, long t) 
		{
			evtType = evt;
			time = t;
		}
		public String toString()
		{
			return evtType + " " + time;
		}
	}
	
	static class TrialBoundary implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 3172433870633802748L;
		Double begin, end;
		TrialBoundary(long b, long e)
		{
			begin =Long.valueOf(b).doubleValue();
			end = Long.valueOf(e).doubleValue();
		}
		
		public String toString()
		{
			return begin + " " + end;
		}
	}
	
	public static String userId;
	private static GlobalListener instance = null;

	private ObjectOutputStream accelOOS, touchOOS, eventOOS, accelBoundaryOOS, touchBoundaryOOS, eventBoundaryOOS, editsOOS, editsBoundaryOOS;
	private boolean mExternalStorageAvailable = false;
	private boolean mExternalStorageWriteable = false;

	//accelerometer data
	private List<AccelerationData> acceleration = new ArrayList<AccelerationData>();
	private List<TrialBoundary> accelBoundary = new ArrayList<TrialBoundary>();
	private int accelerationLen = 0;

	//touch data
	private List<MotionData> touch = new ArrayList<MotionData>();
	private List<TrialBoundary> touchBoundary = new ArrayList<TrialBoundary>();
	private int motionLen = 0;
	
	private List<EventData> events = new ArrayList<EventData>();
	private List<TrialBoundary> eventBoundary = new ArrayList<TrialBoundary>();
	private int eventLen = 0;

	private List<EditData> edits = new ArrayList<EditData>();
	private List<TrialBoundary> editsBoundary = new ArrayList<TrialBoundary>();
	private int editsLen = 0;

	private List<ObjectOutputStream> outputStreams = new ArrayList<ObjectOutputStream>();

	public MBActivity activity;
	private Context appContext;
	//sensor capture start
	private boolean start; 
	private final int touchEventOffSet = 1000;

	public static GlobalListener getInstance() {
		if (instance == null ){
			instance = new GlobalListener();
		}
		return instance;
	}


	private GlobalListener() {
		start = false;
	}

	public void SetContext(Context ct){
		appContext = ct;
	}
	
	private void clearDataRecords()
	{
		this.accelBoundary.clear();
		this.acceleration.clear();
		this.touch.clear();
		this.touchBoundary.clear();
		this.eventBoundary.clear();
		this.events.clear();
		this.edits.clear();
		this.editsBoundary.clear();
	}
	
	private ObjectOutputStream objectWriterFactory(File dir, String fname)
	{
		try{
			return new ObjectOutputStream(new FileOutputStream(new File(dir, fname), true));
		} catch (Exception e){ 
			e.printStackTrace();
		}
		return null;
	}
	
	public void init(String taskName) {
		//check external storage
		checkExternalStorage();
		
		//clear storage
		clearDataRecords();

		//open file output streams
		try {
			if (mExternalStorageAvailable &&
					mExternalStorageWriteable){
			    File root = android.os.Environment.getExternalStorageDirectory();			    
			    File dir = new File (root.getAbsolutePath() + "/Stroop/" +userId);
			    dir.mkdirs();
			    String filePrefix = "/" + taskName;
				
				accelOOS = objectWriterFactory(dir, filePrefix + "_accel.obj");
				accelBoundaryOOS = objectWriterFactory(dir, filePrefix + "_accelBoundary.obj");
				outputStreams.add(accelOOS);
				outputStreams.add(accelBoundaryOOS);
				
				touchOOS =objectWriterFactory(dir, filePrefix + "_touch.obj");
				touchBoundaryOOS = objectWriterFactory(dir, filePrefix +  "_touchBoundary.obj");
				outputStreams.add(touchOOS);
				outputStreams.add(touchBoundaryOOS);
				
				eventOOS = objectWriterFactory(dir, filePrefix + "_event.obj");
				eventBoundaryOOS = objectWriterFactory(dir, filePrefix + "_eventBoundary.obj");
				outputStreams.add(eventOOS);
				outputStreams.add(eventBoundaryOOS);
				
				editsOOS = objectWriterFactory(dir, filePrefix + "_edits.obj");
				editsBoundaryOOS = objectWriterFactory(dir, filePrefix + "_editsBoundary.obj");
				outputStreams.add(editsOOS);
				outputStreams.add(editsBoundaryOOS);
				
				motionLen = 0;
				accelerationLen = 0;
				eventLen = 0;
				editsLen = 0;
				
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {	

	}



	@Override
	public void onSensorChanged(SensorEvent event) {
		if (start && event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{ 
			acceleration.add(new AccelerationData(event.values[0], event.values[1], event.values[2], event.timestamp));
//			activity.X.setText(Float.toString(event.values[0]));
//			activity.Y.setText(Float.toString(event.values[1]));
//			activity.Z.setText(Float.toString(event.values[2]));	
//			activity.C.setText(Long.toString(count));
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (start) {
			synchronized(touch){
				 final int historySize = event.getHistorySize();
			     for (int h = 0; h < historySize; h++) {
			    	 touch.add(new MotionData(event.getHistoricalX(h), event.getHistoricalY(h), event.getHistoricalSize(h), event.getHistoricalPressure(h), event.getHistoricalEventTime(h)));
			     }
				
				touch.add(new MotionData(event.getX(),event.getY(), event.getSize(), event.getPressure(),  event.getEventTime()));
				int action = event.getAction();
				events.add(new EventData(touchEventOffSet+ action, event.getEventTime()));
			}
		}
		return false;
	}

	public void setTrialBoundary() {
		this.touchBoundary.add(new TrialBoundary(motionLen+1, touch.size()));
		motionLen = touch.size();
		
		this.eventBoundary.add(new TrialBoundary(eventLen+1, events.size()));
		eventLen = events.size();
		
		this.accelBoundary.add(new TrialBoundary(accelerationLen+1, acceleration.size()));
		accelerationLen = acceleration.size();
		
		this.editsBoundary.add(new TrialBoundary(editsLen+1, edits.size()));
		editsLen = edits.size();
	}
		
	private void checkExternalStorage()
	{

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but all we need
			//  to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

	private void WriteArrayToFile(List data, OutputStreamWriter writer) throws IOException{
		String d = "";
		for (Object o: data) {
			d += o.toString();
		}
		writer.write(d);
	}
	
	public void WriteData(boolean finalWrite) throws IOException 
	{
		accelOOS.writeObject(acceleration);
        accelBoundaryOOS.writeObject(accelBoundary);
        
        touchOOS.writeObject(touch);
        touchBoundaryOOS.writeObject(touchBoundary);
        
        editsOOS.writeObject(edits);
        editsBoundaryOOS.writeObject(editsBoundary);
        
        eventOOS.writeObject(events);
        eventBoundaryOOS.writeObject(eventBoundary);
        
        flushOOS();
		if (finalWrite){
			closeOOS();
		}
		clearDataRecords();
	}
	
	private void closeOOS()
	{
		try {
			touchOOS.close();
			accelOOS.close();
			eventOOS.close();
			touchBoundaryOOS.close();
			eventBoundaryOOS.close();
			accelBoundaryOOS.close();
			editsBoundaryOOS.close();
			editsOOS.close();
			
		
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void flushOOS()
	{
		for (ObjectOutputStream oos: outputStreams)
		{
			try {
				oos.flush();
				oos.reset();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}


	public void end() {
		start = false;
	}


	public void begin() {
		start = true;
	}


	@Override
	 public boolean onDrag(View v, DragEvent event) {
		onDragEntered(event);
	     return false;
	 }
	  
	private void onDragEntered(DragEvent event)
	{
		if (start) {
			synchronized(touch) {
			    touch.add(new MotionData(event.getX(),event.getY(), 0, 0, (long)(System.nanoTime()/1000)));
	
				int action = event.getAction();
				
				events.add(new EventData(action, (long)(System.nanoTime()/1000)));
			}
		}
	}
	
	@Override
	public void afterTextChanged(Editable arg0) {
	}


	@Override
	public void beforeTextChanged(CharSequence s, int strt, int count,
			int after) {
		if (start) {
			edits.add(new EditData(s, strt, count, after));
		}
	}


	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		
	}


	public void recordData(boolean lastWrite) {
		this.setTrialBoundary();
		try
		{
			WriteData(lastWrite);
		}catch(Exception e){}
		
	}
}
