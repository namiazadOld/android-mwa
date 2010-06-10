package com.sa.mwa;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class PeerService extends Service{

	private final RemoteCallbackList<INotifyTemperatureChanged> callBacks = new RemoteCallbackList<INotifyTemperatureChanged>();
	private  Random random;
	
	public static final int TEMPERATURE_MESSAGE = 1;

	//PeerService introduces its own interface using this method.
	@Override
	public IBinder onBind(Intent intent) {
		if (IPeerRemoteService.class.getName().equals(intent.getAction()))
			return binder;
		return null;
	}
	
	//handler the messages and broadcast them to all listeners
	private final Handler handler = new Handler(){
		
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == TEMPERATURE_MESSAGE)
			{
				final int n = callBacks.beginBroadcast();
				for (int i = 0; i < n; i++)
				{
					try
					{
						callBacks.getBroadcastItem(i).temperatureChanged(binder.retrieveTemparature(), binder.retrieveHumidity());
					}
					catch (RemoteException re)
					{
						
					}
				}
				
				callBacks.finishBroadcast();
				sendMessageDelayed(obtainMessage(TEMPERATURE_MESSAGE), 100);
			}
		};
		
	};
	
	@Override
	public void onCreate() {
		
		//starting the message sending process
		handler.sendEmptyMessage(TEMPERATURE_MESSAGE);
		
		random = new Random();
	}
	

	@Override
	public void onDestroy() {
		handler.removeMessages(1);
	}
	
	//implementation of the interface that this service exposes
	public final IPeerRemoteService.Stub binder = new IPeerRemoteService.Stub() {
		@Override
		public void registerCallBack(INotifyTemperatureChanged ntc)
				throws RemoteException {
			if (ntc != null)
				callBacks.register(ntc);
		}

		@Override
		public void unregisterCallBack(INotifyTemperatureChanged ntc)
				throws RemoteException {
			if (ntc != null)
				callBacks.unregister(ntc);
		}

		@Override
		public double retrieveHumidity() throws RemoteException {
			return random.nextInt(100);
		}

		@Override
		public double retrieveTemparature() throws RemoteException {
			return random.nextInt(40);
		}
	};
	
	
}
