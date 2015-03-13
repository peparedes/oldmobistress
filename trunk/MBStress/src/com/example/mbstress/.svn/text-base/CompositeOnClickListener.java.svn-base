package com.example.mbstress;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.View.OnClickListener;

public class CompositeOnClickListener implements OnClickListener
{
    List<OnClickListener> listeners;

    public CompositeOnClickListener(){
        listeners = new ArrayList<OnClickListener>();
    }

    public void addOnClickListener(OnClickListener listener){
        listeners.add(listener);
    }

    @Override
    public void onClick(View v){
       for(OnClickListener listener : listeners){
          listener.onClick(v);
       }
    }
}