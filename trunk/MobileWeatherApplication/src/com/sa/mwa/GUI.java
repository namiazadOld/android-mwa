package com.sa.mwa;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GUI extends Activity {

	IPeerRemoteService remoteService;
	TextView mCallBackText;
	Button button,button2,button3;
	PeerServiceConnector connection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mCallBackText = (TextView)findViewById(R.id.widget31);
		
		//starting service
		//startService(new Intent("com.sa.mwa.PEER_SERVICE"));
		
		//updating ui
		//mCallBackText.setText("Service Started");
		
		final GuiNotifyTemperatureChanged guiListener = new GuiNotifyTemperatureChanged(handler);
		
		button = (Button)findViewById(R.id.service);		
		button2 = (Button)findViewById(R.id.callback);
		button3 = (Button)findViewById(R.id.widget35);
		button2.setOnClickListener(new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			
			startService(new Intent("com.sa.mwa.PEER_SERVICE"));
			//binding listeners to service
			
			List<INotifyTemperatureChanged> listeners = new ArrayList<INotifyTemperatureChanged>();
			listeners.add(guiListener);
			
			//create connection to service
			connection = new PeerServiceConnector(listeners);
			bindService(new Intent(IPeerRemoteService.class.getName()),
                    connection, Context.BIND_AUTO_CREATE);
			
			mCallBackText.setText("Binding.");
		}});
		
		button3.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				try {
					remoteService.unregisterCallBack(guiListener);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				unbindService(connection);
				
				stopService(new Intent("com.sa.mwa.PEER_SERVICE"));
				button3.setText("HELLO");
			}
			
		});
		
	}
	
	@Override
	protected void onDestroy() {
		stopService(new Intent("com.sa.mwa.PEER_SERVICE"));
	}
	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg) 
		{
			if (msg.what == 1)
				mCallBackText.setText("Received Message: " + msg.obj);
		};
	};
}
